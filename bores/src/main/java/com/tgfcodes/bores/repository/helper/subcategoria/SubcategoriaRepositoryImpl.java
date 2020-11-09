package com.tgfcodes.bores.repository.helper.subcategoria;

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
import com.tgfcodes.bores.model.Subcategoria;

@Repository
public class SubcategoriaRepositoryImpl implements SubcategoriaQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Subcategoria> pesquisar() {
		return new LazyDataModel<Subcategoria>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Subcategoria> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, FilterMeta> filterBy) {
				TypedQuery<Subcategoria> querySubcategoria = (TypedQuery<Subcategoria>) adicionarFiltro(sortField, sortOrder, filterBy);
				setPageSize(pageSize);
				Integer totalRegistros = total(filterBy).intValue();
				setRowCount(totalRegistros);
				return querySubcategoria.setFirstResult(first).setMaxResults(pageSize).getResultList();
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
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Subcategoria.class);
		Root<Subcategoria> subcategoriaRoot = criteriaQuery.from(Subcategoria.class);
		criteriaQuery = (sortOrder == null) ? criteriaQuery.select(builder.count(subcategoriaRoot)) : criteriaQuery.select(subcategoriaRoot);
		
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && !StringUtils.isEmpty(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(subcategoriaRoot.get(sortField)) : builder.desc(subcategoriaRoot.get(sortField)));
		}
		
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if (meta.getFilterField().equals("id")) {
						criteriaQuery.where(builder.equal(subcategoriaRoot.get("id"), pesquisarPor));
					}
					else if(meta.getFilterField().equals("nome")) {
						criteriaQuery.where(builder.like(builder.lower(subcategoriaRoot.get("nome")), "%"+pesquisarPor+"%"));
					}
					else if (meta.getFilterField().equals("categoria")) {
						Categoria categoria = new Categoria();
						categoria.setNome(pesquisarPor);
						criteriaQuery.where(builder.like(builder.lower(subcategoriaRoot.get("categoria").get("nome")), "%"+pesquisarPor+"%"));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
}
