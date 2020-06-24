package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.AnioLaboral;

@Repository("anioLaboralJpaRepository")
public interface AnioLaboralJpaRepository extends JpaRepository<AnioLaboral, Serializable>{

	//@Query(value = "SELECT * FROM ANIOS_LABORALES WHERE ANIO_LABORAL=?1", nativeQuery=true)
	public abstract AnioLaboral findByAnioLaboral(int anio);
	public abstract AnioLaboral findByIdAnioLaboral(int id_anio);
	
	@Query(value = "SELECT MAX(anio_laboral) FROM ANIOS_LABORALES ORDER BY ANIO_LABORAL DESC", nativeQuery = true)
	public abstract int findUltimoAnioLaboral();
}
