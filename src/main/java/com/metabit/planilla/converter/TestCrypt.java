package com.metabit.planilla.converter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCrypt {

	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("planilla"));
		System.out.println("----------"+LocalDate.now());
		int current_year = Calendar.getInstance().get(Calendar.YEAR);
		int anio=2020;
		int periodicidad=30;
		List<String> error = new ArrayList<String>();
		
		if(anio != current_year || periodicidad != 15 && periodicidad != 30) {
			if(periodicidad != current_year) {
				error.add("El año laboral debe ser del periodo actual");
				System.out.println("------------1");
			}
			if(periodicidad != 15 && periodicidad != 30) {
				error.add("La periodicidad seleccionada no es válida");
				System.out.println("------------2");
			}
			System.out.println("------------3");
		}
		System.out.println("------------4");
	}

}
