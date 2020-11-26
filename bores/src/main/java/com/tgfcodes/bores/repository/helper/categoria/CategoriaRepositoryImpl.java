package com.tgfcodes.bores.repository.helper.categoria;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.model.Categoria;

@Repository
public class CategoriaRepositoryImpl implements CategoriaQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Categoria> pesquisar() {
		return new LazyDataModel<Categoria>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Categoria> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, FilterMeta> filterBy) {
				TypedQuery<Categoria> queryCategoria = (TypedQuery<Categoria>) adicionarFiltro(sortField, sortOrder, filterBy);
				setPageSize(pageSize);
				Integer totalRegistros = total(filterBy).intValue();
				setRowCount(totalRegistros);
				return queryCategoria.setFirstResult(first).setMaxResults(pageSize).getResultList();
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
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Categoria.class);
		Root<Categoria> categoriaRoot = criteriaQuery.from(Categoria.class);
		criteriaQuery = (sortOrder == null) ? criteriaQuery.select(builder.count(categoriaRoot)) : criteriaQuery.select(categoriaRoot);
		
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && !StringUtils.isEmpty(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(categoriaRoot.get(sortField)) : builder.desc(categoriaRoot.get(sortField)));
		}
		
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if(meta.getFilterField().equals("nome")) {
						criteriaQuery.where(builder.like(builder.lower(categoriaRoot.get("nome")), "%"+pesquisarPor+"%"));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
}
