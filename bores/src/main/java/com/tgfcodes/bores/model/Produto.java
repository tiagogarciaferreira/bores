package com.tgfcodes.bores.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tgfcodes.bores.validation.annotation.Required;
import com.tgfcodes.bores.validation.annotation.SKU;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Required
	@Size(min = 5, max = 30, message = "Deve conter entre 5 e 30 carateres.")
	private String nome;
	@Required
	@Size(min = 10, max = 255, message = "Deve conter entre 10 e 255 carateres.")
	private String descricao;
	@Required
	@Size(min = 6, max = 20, message = "Deve conter entre 6 e 20 carateres.")
	@SKU
	private String sku;
	@NotNull(message = "Campo obrigatório.")
	@DecimalMin(value = "1.00", message = "O valor mínimo é R$ 1,00.")
	@DecimalMax(value = "9999999.99", message = "O valor máximo é R$ 9.999.999,99.")
	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;
	@NotNull(message = "Campo obrigatório.")
	@Min(value = 1, message = "O estoque mínimo é 1.")
	@Max(value = 9999, message = "O estoque máximo é 9.999.")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subcategoria_id")
	private Subcategoria subcategoria = new Subcategoria();
	@Version
	private Integer version;

	public Produto() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public boolean isNovo() {
		return this.id == null;
	}
	
	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		this.sku = this.sku.toUpperCase();
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}