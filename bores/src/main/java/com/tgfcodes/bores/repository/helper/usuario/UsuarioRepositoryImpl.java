package com.tgfcodes.bores.repository.helper.usuario;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.model.Usuario;

@Repository
public class UsuarioRepositoryImpl implements UsuarioQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Usuario> pesquisar() {
		return new LazyDataModel<Usuario>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, FilterMeta> filterBy) {
				TypedQuery<Usuario> queryUsuario = (TypedQuery<Usuario>) adicionarFiltro(sortField, sortOrder, filterBy);
				setPageSize(pageSize);
				Integer totalRegistros = total(filterBy).intValue();
				setRowCount(totalRegistros);
				return queryUsuario.setFirstResult(first).setMaxResults(pageSize).getResultList();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	private Long total(Map<String, FilterMeta> filterBy) {
		TypedQuery<Long> queryTotal = (TypedQuery<Long>) adicionarFiltro(null, null, filterBy);
		return queryTotal.getSingleResult().longValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TypedQuery adicionarFiltro(String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Usuario.class);
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		
		if(sortOrder != null) {
			usuarioRoot.fetch("grupos", JoinType.LEFT);
			criteriaQuery = criteriaQuery.select(usuarioRoot);
		}else {
			criteriaQuery = criteriaQuery.select(builder.count(usuarioRoot));
		}
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && StringUtils.hasText(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(usuarioRoot.get(sortField)) : builder.desc(usuarioRoot.get(sortField)));
		}
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if(meta.getFilterField().equals("nome")) {
						criteriaQuery.where(builder.like(builder.lower(usuarioRoot.get("nome")), "%"+pesquisarPor+"%"));
					}
					else if(meta.getFilterField().equals("email")) {
						criteriaQuery.where(builder.like(builder.lower(usuarioRoot.get("email")), "%"+pesquisarPor+"%"));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long contar(Grupo grupo) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
     	CriteriaQuery criteriaQuery = builder.createQuery(Long.class);
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		Join<Usuario, Grupo> join = usuarioRoot.join("grupos");
		criteriaQuery.multiselect(builder.count(usuarioRoot.get("id")));
		criteriaQuery.where(builder.equal(join.get("id"), grupo.getId()));
		return (Long) entityManager.createQuery(criteriaQuery).getSingleResult();		
	}
	
	@Override
	public List<String> permissoes(Usuario usuario) {
		return entityManager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario",
				String.class).setParameter("usuario", usuario).getResultList();
	}
}
