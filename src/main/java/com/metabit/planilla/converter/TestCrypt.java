package com.metabit.planilla.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCrypt {

	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("planilla"));
		

		/*int id_empleado;
		int id_planilla;
		int id_periodo;
		int horas_extra_diurnas;
		int horas_extra_nocturnas;
		double monto_comision;
		double monto_dias_festivos;
		double monto_horas_extra;
		double monto_venta;
		double renta;
		double salario_neto;
		double total_descuentos;
		double total_ingresos;
		String fecha_emision;
		String[] mes_final= {"'31/01/19'", "'28/02/19'", "'31/03/19'", "'30/04/19'", "'31/05/19'", "'30/06/19'", "'31/07/19'", "'31/08/19'", "'30/09/19'", "'31/10/19'", "'30/11/19'", "'31/12/19'"};
		
		
		for(int i=0; i<120; i++) {
			horas_extra_diurnas = (int)(Math.random() * (10));
			horas_extra_nocturnas = (int)(Math.random() * (10));
			monto_comision = new BigDecimal(Math.random()*100).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			monto_dias_festivos = new BigDecimal(Math.random()*50).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			monto_horas_extra = new BigDecimal(Math.random()*50).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			monto_venta = new BigDecimal(Math.random()*100).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			renta = new BigDecimal(Math.random()*100).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			salario_neto = 300 + (int)(Math.random() * (3500));
			total_descuentos = new BigDecimal(Math.random()*50).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			total_ingresos = new BigDecimal(Math.random()*100).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
			id_empleado = 500 + i;
			id_periodo = 511;
			id_planilla = 6320 + i;
			fecha_emision = mes_final[11];
			
			System.out.println("INSERT INTO PLANILLAS(ID_PLANILLA, FECHA_EMISION, HORAS_EXTRA_DIURNAS, HORAS_EXTRA_NOCTURNAS, MONTO_COMISION, MONTO_DIAS_FESTIVOS, MONTO_HORAS_EXTRA, MONTO_VENTAS, RENTA, SALARIO_NETO, TOTAL_DESCUENTOS, TOTAL_INGRESOS, ID_EMPLEADO, ID_PERIODO) VALUES("+id_planilla+", "+fecha_emision+", "+horas_extra_diurnas+", "+horas_extra_nocturnas+", "+monto_comision+", "+monto_dias_festivos+", "+monto_horas_extra+", "+monto_venta+", "+renta+", "+salario_neto+", "+total_descuentos+", "+total_ingresos+", "+id_empleado+", "+id_periodo+");;");
		}*/
		
	}

}
