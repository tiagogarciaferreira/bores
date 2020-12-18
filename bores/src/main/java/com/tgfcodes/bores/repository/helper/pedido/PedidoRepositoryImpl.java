package com.tgfcodes.bores.repository.helper.pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.dto.GraficoPedidoDTO;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.enumeration.StatusPedido;

@Repository
public class PedidoRepositoryImpl implements PedidoQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public LazyDataModel<Pedido> pesquisar() {
		return new LazyDataModel<Pedido>() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
				TypedQuery<Pedido> queryPedido = (TypedQuery<Pedido>) adicionarFiltro(sortField, sortOrder, filterBy);
				setPageSize(pageSize);
				Integer totalRegistros = total(filterBy).intValue();
				setRowCount(totalRegistros);
				return queryPedido.setFirstResult(first).setMaxResults(pageSize).getResultList();
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
     	CriteriaQuery criteriaQuery = (sortOrder == null) ? builder.createQuery(Long.class) : builder.createQuery(Pedido.class);
		Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
		criteriaQuery = (sortOrder == null) ? criteriaQuery.select(builder.count(pedidoRoot)) : criteriaQuery.select(pedidoRoot);
		
		if(sortOrder != null) {
			pedidoRoot.fetch("vendedor", JoinType.LEFT);
			pedidoRoot.fetch("cliente", JoinType.LEFT);
			criteriaQuery = criteriaQuery.select(pedidoRoot);
		}else {
			criteriaQuery = criteriaQuery.select(builder.count(pedidoRoot));
		}
		if (sortOrder != null && !sortOrder.equals(SortOrder.UNSORTED) && StringUtils.hasText(sortField)) {
			criteriaQuery.orderBy(sortOrder.equals(SortOrder.ASCENDING) ? builder.asc(pedidoRoot.get(sortField)) : builder.desc(pedidoRoot.get(sortField)));
		}
		if (filterBy.size() > 0) {
			for (FilterMeta meta : filterBy.values()) {
				if(meta.getFilterValue() !=  null) {
					var pesquisarPor = meta.getFilterValue().toString().toLowerCase();
					if (meta.getFilterField().equals("id")) {
						criteriaQuery.where(builder.equal(pedidoRoot.get("id"), pesquisarPor));
					}
					else if(meta.getFilterField().equals("cliente")) {
						criteriaQuery.where(builder.like(builder.lower(pedidoRoot.get("cliente").get("nome")), "%"+pesquisarPor+"%"));
					}
					else if(meta.getFilterField().equals("vendedor")) {
						criteriaQuery.where(builder.like(builder.lower(pedidoRoot.get("vendedor").get("nome")), "%"+pesquisarPor+"%"));
					}
					else if(meta.getFilterField().equals("dataCriacao")) {
						pesquisarPor = pesquisarPor.toString().replaceAll("[^0-9\\-\\,]","");
						String[] datas = pesquisarPor.split(",");
						LocalDateTime desde = LocalDateTime.of(LocalDate.parse(datas[0]), LocalTime.of(0,0,0));
						LocalDateTime ate = LocalDateTime.of(LocalDate.parse(datas[1]), LocalTime.of(23,59,59));
						criteriaQuery.where(builder.greaterThanOrEqualTo(pedidoRoot.get("dataCriacao").as(LocalDateTime.class), desde),
											builder.lessThanOrEqualTo(pedidoRoot.get("dataCriacao").as(LocalDateTime.class), ate));
					}
					else if(meta.getFilterField().equals("status")) {
						criteriaQuery.where(builder.equal(pedidoRoot.get("status"), StatusPedido.valueOf(pesquisarPor.toUpperCase())));
					}
				}
			}
		}
		return entityManager.createQuery(criteriaQuery);
	}
	
	@Override
	public List<GraficoPedidoDTO> pedidosGrafico() {
		// TODO gerar consulta para pegar os dados e montar o grafico
		
		
		return null;
	}
	
}
