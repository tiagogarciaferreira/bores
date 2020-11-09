package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.repository.helper.estado.EstadoQueries;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>, EstadoQueries {

	
}
