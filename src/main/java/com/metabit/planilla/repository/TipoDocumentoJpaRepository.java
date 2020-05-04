package com.metabit.planilla.repository;

import com.metabit.planilla.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("tipoDocumentoJpaRepository")
public interface TipoDocumentoJpaRepository extends JpaRepository<TipoDocumento, Serializable> {
}
