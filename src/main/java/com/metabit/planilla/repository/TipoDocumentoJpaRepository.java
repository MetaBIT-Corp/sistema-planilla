package com.metabit.planilla.repository;

import com.metabit.planilla.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("tipoDocumentoJpaRepository")
public interface TipoDocumentoJpaRepository extends JpaRepository<TipoDocumento, Serializable> {
    public abstract List<TipoDocumento> getAllByTipoDocumentoHabilitadoIsTrue();
}
