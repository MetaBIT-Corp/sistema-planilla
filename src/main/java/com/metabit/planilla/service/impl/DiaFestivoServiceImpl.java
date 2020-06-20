package com.metabit.planilla.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.DiaFestivo;
import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.repository.DiaFestivoJpaRepository;
import com.metabit.planilla.service.DiaFestivoService;
import com.metabit.planilla.service.PeriodoService;

@Service("diaFestivoServiceImpl")
public class DiaFestivoServiceImpl implements DiaFestivoService{
	
	@Autowired
	@Qualifier("diaFestivoJpaRepository")
	private DiaFestivoJpaRepository diaFestivoJpaRepository;

	@Autowired
	@Qualifier("periodoServiceImpl")
	private PeriodoService periodoService;
	
	@Override
	public List<DiaFestivo> getDiasFestivos() {
		return diaFestivoJpaRepository.findAll();
	}

	@Override
	public DiaFestivo storeDiaFestivo(DiaFestivo diaFestivo) {
		return diaFestivoJpaRepository.save(diaFestivo);
	}

	@Override
	public DiaFestivo updateDiaFestivo(DiaFestivo diaFestivo) {
		return diaFestivoJpaRepository.save(diaFestivo);
	}

	@Override
	public void deleteDiaFestivo(int idDiaFestivo) {
		diaFestivoJpaRepository.deleteById(idDiaFestivo);
	}

	@Override
	public DiaFestivo getDiaFestivo(int id) {
		return diaFestivoJpaRepository.findById(id).get();
	}

	@Override
	public List<DiaFestivo> getDiasFestivosDelPeriodoActivo() {
		
		//Obtenemos el Periodo Activo
		Periodo periodoActivo = periodoService.getPeriodoActivo();
		
		//Obtenemos el entero que representa el anio
		int anioActual = periodoActivo.getAnioLaboral().getAnioLaboral();
		
		//Ahora obtenemos todos los dias Festivos que estan registrados en la BD
		List<DiaFestivo> diasFestivos = diaFestivoJpaRepository.findAll();
		
		//Creamos la lista de Dias Festivos que vamos a retornar, aqui solo iran 
		//Dias Festivos que caen dentro del Periodo Actual, ya que son los posibles dias
		//que los empleados trabajen de forma extraordinaria, por lo cual se les debe
		//Retribuir
		List<DiaFestivo> diasFestivosDelPeridoActivo = new ArrayList<DiaFestivo>();
		
		//Procedemos a recorrer todos los dias Festivos, si el dia festivo esta dentro del rango de 
		//fechas de Periodo Activo (Fecha Inicio y Fecha Fin), entonces lo agregamos a la lista
		for (DiaFestivo diaFestivo : diasFestivos) {
			
			//String con formato dd/mm/yyyy
			String dateStr = diaFestivo.getDia() + "/" + diaFestivo.getMes() + "/" + anioActual;
			
			try {
				
				//Convertimos String a Date
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
				
				//Convertimos Date a LocalDate
				LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
				
				//Si el dia festivo no cae antes de la fecha de inicio o no cae despues de la fecha fin
				//Significa que esta en el rango de fechas, por lo que se agrega a la lista
				if(!(localDate.isBefore(periodoActivo.getFechaInicio()) || localDate.isAfter(periodoActivo.getFechaFinal()))) {
					
					//Agregamos a la lista el Dia Festivo, ya que cumplio condicion
					diasFestivosDelPeridoActivo.add(diaFestivo);
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		//Retornamos la lista
		return diasFestivosDelPeridoActivo;
	}

	
}
