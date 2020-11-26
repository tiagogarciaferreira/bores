package com.tgfcodes.bores.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import com.tgfcodes.bores.validation.annotation.Required;

@Entity
@Table(name = "subcategoria")
public class Subcategoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria = new Categoria();
	/*
	 * @Transient
	 * 
	 * @OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY) private
	 * List<Produto> produtos = new ArrayList<>();
	 */
	@Version
	private Long version;

	public Subcategoria() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Required
	@Size(min = 4, max = 30, message = "Deve conter entre 4 e 30 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	/*
	 * public List<Produto> getProdutos() { return produtos; }
	 * 
	 * public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
	 */

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
		Subcategoria other = (Subcategoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
