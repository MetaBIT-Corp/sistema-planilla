package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;

@Repository("planillaJpaRepository")
public interface PlanillaJpaRepository extends JpaRepository<Planilla, Serializable>{

	@Procedure(procedureName = "PLANILLA_UPDATE_MOVIMIENTOS")
	public abstract void planillaUpdateMovimientos(int id_planilla);
	public abstract List<Planilla> findByPeriodo(Periodo periodo);
	
	@Procedure(name = "show")
	String showMessage(@Param("p_message") String p_message);
}
