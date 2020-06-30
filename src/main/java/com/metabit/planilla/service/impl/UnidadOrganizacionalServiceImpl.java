package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.repository.UnidadOrganizacionalJpaRepository;
import com.metabit.planilla.service.UnidadOrganizacionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@Service("unidadOrganizacionalServiceImpl")
public class UnidadOrganizacionalServiceImpl implements UnidadOrganizacionalService {
	
	@Autowired
    EntityManagerFactory emf;

    @Autowired
    @Qualifier("unidadOrganizacionalJpaRepository")
    private UnidadOrganizacionalJpaRepository unidadOrganizacionalJpaRepository;

    @Override
    public List<UnidadOrganizacional> getAllUnidadesOrganizacionales() {
        return unidadOrganizacionalJpaRepository.findAll();
    }

    @Override
    public UnidadOrganizacional getOneUnidadOrganizacional(int id) {
        return unidadOrganizacionalJpaRepository.findByIdUnidadOrganizacional(id);
    }

    @Override
    public List<UnidadOrganizacional> getAllHijas(UnidadOrganizacional unidad) {
        return unidadOrganizacionalJpaRepository.findByUnidadPadreIs(unidad);
    }

    @Override
    public UnidadOrganizacional addOrUpdateUnidadOrganizaional(UnidadOrganizacional unidadOrganizacional) {
        return unidadOrganizacionalJpaRepository.save(unidadOrganizacional);
    }

    @Override
    public void deleteUnidad(UnidadOrganizacional unidadOrganizacional) {
        unidadOrganizacionalJpaRepository.delete(unidadOrganizacional);
    }

	@Override
	public List<UnidadOrganizacional> getByTipoUnidad(int id_tipo) {
		// TODO Auto-generated method stub
		return unidadOrganizacionalJpaRepository.findByTipoUnidadOrganizacional(id_tipo);
	}

	@Override
	public List<UnidadOrganizacional> getAllUnidadesOrganizacionalesSinPagar() {
		List<UnidadOrganizacional> unidadesAll = unidadOrganizacionalJpaRepository.findAll();
		List<UnidadOrganizacional> unidadesSinPagar = new ArrayList<UnidadOrganizacional>();
		Boolean unidadPagada = true;
		
		for(int i = 0; i < unidadesAll.size(); i++) {
			unidadPagada = true;
			List<EmpleadosPuestosUnidades> epu = unidadesAll.get(i).getEmpleadosPuestosUnidades();
			for(int j = 0; j < epu.size(); j++) {
				List<Planilla> planillas = epu.get(j).getEmpleado().getPlanillasEmpleado();
				for(int k = 0; k < planillas.size(); k++) {
					if(planillas.get(k).getFechaEmision() == null) unidadPagada = false;
				}
			}
			if(!unidadPagada) {	
				UnidadOrganizacional unidad = new UnidadOrganizacional();
				unidad.setIdUnidadOrganizacional(unidadesAll.get(i).getIdUnidadOrganizacional());
				unidad.setUnidadOrganizacional(unidadesAll.get(i).getUnidadOrganizacional());
				unidadesSinPagar.add(unidad);
			}
		}
		return unidadesSinPagar;
	}
    
    
}
