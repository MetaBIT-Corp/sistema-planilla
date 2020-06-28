package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;

@Repository("planillaJpaRepository")
public interface PlanillaJpaRepository extends JpaRepository<Planilla, Serializable>{

	@Procedure(procedureName = "PLANILLA_UPDATE_MOVIMIENTOS")
	public abstract void planillaUpdateMovimientos(int id_planilla);
	
	@Procedure(procedureName = "GENERAR_PLANILLAS")
	public abstract void generarPlanillas(int id_periodo);
	
	public abstract List<Planilla> findByPeriodo(Periodo periodo);
	
	@Query(value = "SELECT * FROM planillas\n" + 
			"WHERE id_empleado IN (\n" + 
			"    SELECT id_empleado FROM tipos_unidad_organizacional\n" + 
			"        NATURAL JOIN unidades_organizacionales\n" + 
			"        NATURAL JOIN empleados_puestos_unidades\n" + 
			"        NATURAL JOIN empleados\n" + 
			"        NATURAL JOIN planillas\n" + 
			"    WHERE id_unidad_organizacional = ?1\n" + 
			")\n" + 
			"AND id_periodo = ?2", nativeQuery = true)
	public abstract List<Planilla> findPlanillasUnidad(int id_unidad, int id_periodo);

	@Procedure(name = "pagoPlanilla")
	String pagarPlanilla(@Param("p_id_unidad") int idUnidadOrganizacional);
	
	@Procedure(name = "recalcularImpuestos")
	void recalcularImpuestos(@Param("p_id_planilla") int idPlanilla, @Param("p_periodicidad") int periodicidad);
}
