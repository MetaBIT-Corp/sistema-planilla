package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoDocumento;
import com.metabit.planilla.repository.EmpleadoDocumentoJpaRepository;
import com.metabit.planilla.service.EmpleadoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("empleadoDocumentoServiceImpl")
public class EmpleadoDocumentoServiceImpl implements EmpleadoDocumentoService {

    @Autowired
    @Qualifier("empleadoDocumentoJpaRepository")
    private EmpleadoDocumentoJpaRepository empleadoDocumentoJpaRepository;

    @Override
    public List<EmpleadoDocumento> getDocumentsByEmployee(Empleado e) {
        return empleadoDocumentoJpaRepository.findByEmpleado(e);
    }

    @Override
    public Boolean createOrUpdateDocumentsEmployee(List<EmpleadoDocumento> ed) {
        Iterable<EmpleadoDocumento> i = ed;
        return !empleadoDocumentoJpaRepository.saveAll(i).isEmpty();
    }
}
