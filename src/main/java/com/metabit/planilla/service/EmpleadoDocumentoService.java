package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoDocumento;

import java.util.List;

public interface EmpleadoDocumentoService {
    public abstract EmpleadoDocumento getDocumentEmployee(int id);
    public abstract EmpleadoDocumento createOrUpdateDocumentsEmployee(EmpleadoDocumento ed);
    public abstract void deleteDocumentEmployee(int id);
    public abstract List<EmpleadoDocumento> findByEmpleado(Empleado e);
}
