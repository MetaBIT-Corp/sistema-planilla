package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("privilegioJpaRepository")
public interface PrivilegioJpaRepository extends JpaRepository<Privilegio, Serializable> {

    /*
     * René Martínez
     * Método para recuperar privilegios asignados a recurso asignado a un rol específico.
     */
    @Query(value = "SELECT ID_PRIVILEGIO, PRIVILEGIO FROM PRIVILEGIOS WHERE (ID_PRIVILEGIO IN (SELECT ID_PRIVILEGIO FROM ROLES_RECURSOS_PRIVILEGIOS WHERE ID_ROL=?1 AND ID_RECURSO=?2))", nativeQuery = true)
    public abstract List<Privilegio> getRolRecursoPrivilegios(Integer idRol, Integer idRecurso);
}
