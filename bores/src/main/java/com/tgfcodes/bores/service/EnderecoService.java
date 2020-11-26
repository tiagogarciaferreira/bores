package com.tgfcodes.bores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.Endereco;
import com.tgfcodes.bores.repository.ClienteRepository;
import com.tgfcodes.bores.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional(readOnly = false)
	public void salvar(Endereco endereco) {
		this.enderecoRepository.save(endereco);
	}

	@Transactional(readOnly = false)
	public void excluir(Endereco endereco) {
		endereco = this.buscarPorId(endereco.getId());
		if(endereco.getCliente().getId() != null) {
			throw new NegocioException("Endereço: Não pode ser excluído. Possue cliente associado.");
		}
		this.enderecoRepository.delete(endereco);
	}
	
	@Transactional(readOnly = true)
	public List<Endereco> listar(Long clienteId){
		Cliente cliente = this.clienteRepository.findById(clienteId).get();
		return this.enderecoRepository.findByCliente(cliente);
	}

	@Transactional(readOnly = true)
	public Endereco buscarPorId(Long id) {
		return this.enderecoRepository.findById(id).get();
	}

}
