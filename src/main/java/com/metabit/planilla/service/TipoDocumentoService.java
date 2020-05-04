package com.metabit.planilla.service;

import com.metabit.planilla.entity.TipoDocumento;

import java.util.List;

public interface TipoDocumentoService {
    public abstract List<TipoDocumento> getTiposDocumento();
    public abstract TipoDocumento getTipoDocumento(int id);
    public abstract TipoDocumento storeTipoDocumento(TipoDocumento tipoDocumento);
    public abstract TipoDocumento updateTipoDocumento(TipoDocumento tipoDocumento);
    public abstract void deleteTipoDocumento(int idTipoDocumento);
}
