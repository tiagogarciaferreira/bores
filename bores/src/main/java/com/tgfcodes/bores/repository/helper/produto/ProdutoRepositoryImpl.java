package com.tgfcodes.bores.repository.helper.produto;

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

import com.tgfcodes.bores.dto.EstatisticasEstoqueDTO;
import com.tgfcodes.bores.model.Produto;

@Repository
public class ProdutoRepositoryImpl implements ProdutoQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Produto> pesquisar() {
		return new LazyDataModel<Produto>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Produto> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, FilterMeta> filterBy) {
				TypedQuery<Produto> queryEstado = (TypedQuery<Produto>) adicionarFiltro(sortField, sortOrder, filterBy);
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
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Produto.class);
		Root<Produto> produtoRoot = criteriaQuery.from(Produto.class);
		criteriaQuery = (sortOrder == null) ? criteriaQuery.select(builder.count(produtoRoot)) : criteriaQuery.select(produtoRoot);
		
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && StringUtils.hasText(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(produtoRoot.get(sortField)) : builder.desc(produtoRoot.get(sortField)));
		}
		
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if(meta.getFilterField().equals("sku")) {
						criteriaQuery.where(builder.like(builder.lower(produtoRoot.get("sku")), pesquisarPor));
					}
					else if(meta.getFilterField().equals("nome")) {
						criteriaQuery.where(builder.like(builder.lower(produtoRoot.get("nome")), "%"+pesquisarPor+"%"));
					}
					else if(meta.getFilterField().equals("subcategoria")) {
						criteriaQuery.where(builder.like(builder.lower(produtoRoot.get("subcategoria").get("nome")), "%"+pesquisarPor+"%"));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public EstatisticasEstoqueDTO totalEstoque() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
     	CriteriaQuery criteriaQuery = builder.createQuery(EstatisticasEstoqueDTO.class);
		Root<Produto> produtoRoot = criteriaQuery.from(Produto.class);
		criteriaQuery.multiselect(builder.sum(produtoRoot.get("quantidadeEstoque")), builder.sum(builder.prod(produtoRoot.get("quantidadeEstoque"), produtoRoot.get("valorUnitario"))));
		return (EstatisticasEstoqueDTO) entityManager.createQuery(criteriaQuery).getSingleResult();
	}
}
