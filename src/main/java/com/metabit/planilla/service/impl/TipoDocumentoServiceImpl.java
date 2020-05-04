package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.TipoDocumento;
import com.metabit.planilla.repository.TipoDocumentoJpaRepository;
import com.metabit.planilla.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tipoDocumentoServiceImpl")
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    @Autowired
    @Qualifier("tipoDocumentoJpaRepository")
    private TipoDocumentoJpaRepository tipoDocumentoJpaRepository;

    public List<TipoDocumento> getTiposDocumento() {
        return tipoDocumentoJpaRepository.findAll();
    }

    @Override
    public TipoDocumento getTipoDocumento(int id) {
        return tipoDocumentoJpaRepository.findById(id).get();
    }

    @Override
    public TipoDocumento storeTipoDocumento(TipoDocumento tipoDocumento) {
        return tipoDocumentoJpaRepository.save(tipoDocumento);
    }

    @Override
    public TipoDocumento updateTipoDocumento(TipoDocumento tipoDocumento) {
        return tipoDocumentoJpaRepository.save(tipoDocumento);
    }

    @Override
    public void deleteTipoDocumento(int idTipoDocumento) {
        tipoDocumentoJpaRepository.deleteById(idTipoDocumento);
    }
}
