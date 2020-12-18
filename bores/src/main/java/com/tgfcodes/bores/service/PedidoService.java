package com.tgfcodes.bores.service;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.event.PedidoEvent;
import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.enumeration.StatusPedido;
import com.tgfcodes.bores.repository.CidadeRepository;
import com.tgfcodes.bores.repository.ClienteRepository;
import com.tgfcodes.bores.repository.PedidoRepository;
import com.tgfcodes.bores.repository.UsuarioRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EstoqueService estoqueService;
	@Autowired
	private ApplicationEventPublisher publisher;
	

	@Transactional(readOnly = false)
	public void salvar(Pedido pedido) {
		if(pedido.isNovo()) {
			pedido.setCliente(this.clienteRepository.getOne(pedido.getCliente().getId()));
			pedido.setVendedor(this.usuarioRepository.getOne(pedido.getVendedor().getId()));
			pedido.getEnderecoEntrega().setCidade(this.cidadeRepository.getOne(pedido.getEnderecoEntrega().getCidade().getId()));
		}
		if(!pedido.isAlteravel()) {
			throw new NegocioException("Pedido não pode ser alterado com status " + pedido.getStatus().getDescricao() + ".");
		}
		if(pedido.getItens().isEmpty()) {
			throw new NegocioException("Pedido não possue nenhum item adicionado.");
		}
		if(pedido.isValorTotalNegativo()) {
			throw new NegocioException("Pedido o valor total está negativo.");
		}
		this.pedidoRepository.save(pedido);
	}
	
	@Transactional(readOnly = false)
	public void emitir(Pedido pedido) {
		pedido = this.pedidoRepository.save(pedido);
		if(!pedido.isEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status " + pedido.getStatus().getDescricao() + ".");
		}
		this.estoqueService.baixarItensEstoque(pedido);
		pedido.setStatus(StatusPedido.EMITIDO);
		this.pedidoRepository.save(pedido);
		this.publisher.publishEvent(new PedidoEvent(pedido));
	}
	
	@Transactional(readOnly = false)
	public void cancelar(Pedido pedido) {
		if(!pedido.isCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado com status " + pedido.getStatus().getDescricao() + ".");
		}
		if(pedido.isEmitido()) {
			this.estoqueService.voltarItensEstoque(pedido);
		}
		pedido.setStatus(StatusPedido.CANCELADO);
		this.pedidoRepository.save(pedido);
		this.publisher.publishEvent(new PedidoEvent(pedido));
	}	
	
	public void enviarEmail(Pedido pedido) {
		this.publisher.publishEvent(new PedidoEvent(pedido));
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Pedido> pesquisar(){
		return this.pedidoRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Pedido buscarPorId(Long id) {
		return this.pedidoRepository.findById(id).get();
	}

}
