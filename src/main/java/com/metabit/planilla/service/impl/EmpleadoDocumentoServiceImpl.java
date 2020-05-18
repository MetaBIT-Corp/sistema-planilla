package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoDocumento;
import com.metabit.planilla.entity.TipoDocumento;
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
    public EmpleadoDocumento getDocumentEmployee(int id) {
        return empleadoDocumentoJpaRepository.getOne(id);
    }

    @Override
    public EmpleadoDocumento createOrUpdateDocumentsEmployee(EmpleadoDocumento ed) {
        return empleadoDocumentoJpaRepository.save(ed);
    }

    @Override
    public void deleteDocumentEmployee(int id) {
        empleadoDocumentoJpaRepository.delete(empleadoDocumentoJpaRepository.getOne(id));
    }

	@Override
	public List<EmpleadoDocumento> findByEmpleado(Empleado e) {
		return empleadoDocumentoJpaRepository.findByEmpleado(e);
	}

    @Override
    public List<EmpleadoDocumento> findByTipoDocumento(TipoDocumento tipoDocumento) {
        return empleadoDocumentoJpaRepository.findByTipoDocumento(tipoDocumento);
    }
}
