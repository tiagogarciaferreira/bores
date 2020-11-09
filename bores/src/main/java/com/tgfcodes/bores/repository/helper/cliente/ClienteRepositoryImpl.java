package com.tgfcodes.bores.repository.helper.cliente;

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

import com.tgfcodes.bores.model.Cliente;

@Repository
public class ClienteRepositoryImpl implements ClienteQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Cliente> pesquisar() {
		return new LazyDataModel<Cliente>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, FilterMeta> filterBy) {
				TypedQuery<Cliente> queryCliente = (TypedQuery<Cliente>) adicionarFiltro(sortField, sortOrder, filterBy);
				setPageSize(pageSize);
				Integer totalRegistros = total(filterBy).intValue();
				setRowCount(totalRegistros);
				return queryCliente.setFirstResult(first).setMaxResults(pageSize).getResultList();
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
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Cliente.class);
		Root<Cliente> clienteRoot = criteriaQuery.from(Cliente.class);
		criteriaQuery = (sortOrder == null) ? criteriaQuery.select(builder.count(clienteRoot)) : criteriaQuery.select(clienteRoot);
		
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && !StringUtils.isEmpty(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(clienteRoot.get(sortField)) : builder.desc(clienteRoot.get(sortField)));
		}
		
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if(meta.getFilterField().equals("nome")) {
						criteriaQuery.where(builder.like(builder.lower(clienteRoot.get("nome")), "%"+pesquisarPor+"%"));
					}
					else if(meta.getFilterField().equals("cpf_cnpj")) {
						criteriaQuery.where(builder.like(builder.lower(clienteRoot.get("cpf_cnpj")), "%"+pesquisarPor+"%"));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
}
