package com.metabit.planilla.repository;

import java.io.Serializable;
import com.metabit.planilla.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("direccionJpaRepository")
public interface DireccionJpaRepository extends JpaRepository<Direccion, Serializable> {

}
