package com.tgfcodes.bores.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.tgfcodes.bores.model.enumeration.FormaPagamento;
import com.tgfcodes.bores.model.enumeration.StatusPedido;
import com.tgfcodes.bores.validation.annotation.Required;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreationTimestamp
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	private String observacao;
	@Column(name = "valor_frete")
	private BigDecimal valorFrete = BigDecimal.ZERO;
	@Column(name = "valor_desconto")
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	@Required
	private StatusPedido status = StatusPedido.ORCAMENTO;
	@Required
	@Column(name = "forma_pagamento")
	private FormaPagamento formaPagamento;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor = new Usuario();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente = new Cliente();
	@NotNull(message = "Campo obrigatório.")
	@Column(name = "data_entrega")
	private LocalDateTime dataEntrega;
	@Embedded
	private EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<ItemPedido> itens = new ArrayList<>();

	public Pedido() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return (dataCriacao == null) ? LocalDateTime.now(ZoneId.of("America/Sao_Paulo")) : this.dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public LocalDateTime getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	public boolean isNovo() {
		return this.id == null;
	}
	
	@PrePersist
	private void prePersist() {
		this.enderecoEntrega.setUf(this.enderecoEntrega.getEstado().getNome());
	}
	
	@PreUpdate
	private void preUpdate() {
		this.enderecoEntrega.setUf(this.enderecoEntrega.getEstado().getNome());
	}

	@PostLoad
	private void postLoad() {
		this.enderecoEntrega.setEstado(this.enderecoEntrega.getCidade().getEstado());
		this.enderecoEntrega.setUf(this.enderecoEntrega.getEstado().getNome());
	}
	
	public BigDecimal getValorSubtotal() {
		return this.getValorTotal().subtract(this.getValorFrete()).add(this.getValorDesconto());
	}
	
	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
		for (ItemPedido item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total.add(item.getValorTotal());
			}
		}
		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		if (this.isOrcamento()) {
			Produto produto = new Produto();
			ItemPedido item = new ItemPedido();
			item.setProduto(produto);
			item.setPedido(this);
			this.getItens().add(0, item);
		}
	}

	public boolean isOrcamento() {
		return StatusPedido.ORCAMENTO.equals(this.getStatus());
	}

	public void removerItemVazio() {
		ItemPedido primeiroItem = this.getItens().get(0);
		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			this.getItens().remove(0);
		}
	}

	public boolean isValorTotalNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}

	public boolean isEmitido() {
		return StatusPedido.EMITIDO.equals(this.getStatus());
	}

	public boolean isNaoEmissivel() {
		return !this.isEmissivel();
	}

	public boolean isEmissivel() {
		return !this.isNovo() && this.isOrcamento();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
