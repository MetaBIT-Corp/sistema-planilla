package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoDocumento;

import java.util.List;

public interface EmpleadoDocumentoService {
    public abstract List<EmpleadoDocumento> getDocumentsByEmployee(Empleado e);
    public abstract EmpleadoDocumento createOrUpdateDocumentsEmployee(EmpleadoDocumento ed);
}
