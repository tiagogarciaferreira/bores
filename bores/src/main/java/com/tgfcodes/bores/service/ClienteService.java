package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.enumeration.TipoPessoa;
import com.tgfcodes.bores.repository.ClienteRepository;
import com.tgfcodes.bores.repository.PedidoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Cliente cliente) {
		Cliente clienteExistente = this.clienteRepository.porEmailOuCpfCnpj(cliente.getEmail(), TipoPessoa.removerFormatacao(cliente.getCpfOuCnpj()));
		if(clienteExistente != null) {
			if(clienteExistente.getEmail().equalsIgnoreCase(cliente.getEmail())) {
				throw new NegocioException("Email: Já esta cadastrado para outro cliente.");
			}
			if(clienteExistente.getCpfOuCnpj().equals(cliente.getCpfOuCnpj())) {
				String documento = cliente.getTipoPessoa().equals(TipoPessoa.FISICA) ? "CPF: " : "CNPJ: ";
				throw new NegocioException(documento + "Já esta cadastrado para outro cliente.");
			}
		}
		this.clienteRepository.save(cliente);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Cliente cliente) {
		boolean remover = this.pedidoRepository.existsByCliente(cliente);
		if(remover) {
			throw new NegocioException("Cliente: Não pode ser excluído. Possue pedidos associados.");
		}
		cliente = this.buscarPorId(cliente.getId());
		this.clienteRepository.delete(cliente);
	}

	@Transactional(readOnly = true)
	public List<Cliente> completar(String nome) {
		return (StringUtils.hasText(nome) ? this.clienteRepository.porNome(nome) : null);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Cliente> pesquisar(){
		return this.clienteRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return this.clienteRepository.findById(id).get();
	}
	
}
