package com.metabit.planilla.repository;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("empleadoDocumentoJpaRepository")
public interface EmpleadoDocumentoJpaRepository  extends JpaRepository<EmpleadoDocumento, Serializable> {
    public abstract List<EmpleadoDocumento> findByEmpleado(Empleado e);
}
