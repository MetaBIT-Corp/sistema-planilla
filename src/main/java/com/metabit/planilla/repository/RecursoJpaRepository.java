package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import com.metabit.planilla.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("recursoJpaRepository")
public interface RecursoJpaRepository extends JpaRepository<Recurso, Serializable> {

    /*
     * René Martínez
     * Método para recuperar recursos asignados a un rol.
     */
    @Query(value = "SELECT ID_RECURSO, RECURSO FROM RECURSOS WHERE ID_RECURSO IN (SELECT ID_RECURSO FROM ROLES_RECURSOS_PRIVILEGIOS WHERE ID_ROL=?1)", nativeQuery = true)
    public abstract List<Recurso> getRolRecursos(Integer idRol);

}
