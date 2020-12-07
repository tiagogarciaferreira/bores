package com.tgfcodes.bores.repository.helper.estado;

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
import com.tgfcodes.bores.model.Estado;

@Repository
public class EstadoRepositoryImpl implements EstadoQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Estado> pesquisar() {
		return new LazyDataModel<Estado>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Estado> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, FilterMeta> filterBy) {
				TypedQuery<Estado> queryEstado = (TypedQuery<Estado>) adicionarFiltro(sortField, sortOrder, filterBy);
				setPageSize(pageSize);
				Integer totalRegistros = total(filterBy).intValue();
				setRowCount(totalRegistros);
				return queryEstado.setFirstResult(first).setMaxResults(pageSize).getResultList();
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
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Estado.class);
		Root<Estado> estadoRoot = criteriaQuery.from(Estado.class);
		criteriaQuery = (sortOrder == null) ? criteriaQuery.select(builder.count(estadoRoot)) : criteriaQuery.select(estadoRoot);
		
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && StringUtils.hasText(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(estadoRoot.get(sortField)) : builder.desc(estadoRoot.get(sortField)));
		}
		
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if(meta.getFilterField().equals("sigla")) {
						criteriaQuery.where(builder.like(builder.lower(estadoRoot.get("sigla")), "%"+pesquisarPor+"%"));
					}
					else if(meta.getFilterField().equals("nome")) {
						criteriaQuery.where(builder.like(builder.lower(estadoRoot.get("nome")), "%"+pesquisarPor+"%"));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
}
