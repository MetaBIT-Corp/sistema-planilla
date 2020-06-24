package com.metabit.planilla.controller;

import com.metabit.planilla.entity.*;
import com.metabit.planilla.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/periodo")
public class PeriodoController {

    @Autowired
    @Qualifier("periodoServiceImpl")
    private PeriodoService periodoService;

    @Autowired
    @Qualifier("planillaServiceImpl")
    private PlanillaService planillaService;

    @Autowired
    @Qualifier("empleadoDocumentoServiceImpl")
    private EmpleadoDocumentoService empleadoDocumentoService;

    @Autowired
    @Qualifier("empleadosPuestosUnidadesServiceImpl")
    private EmpleadosPuestosUnidadesService empleadosPuestosUnidadesService;

    @Autowired
    @Qualifier("planillaDiaFestivoServiceImpl")
    private PlanillaDiaFestivoService planillaDiaFestivoService;

    @Autowired
    @Qualifier("tipoMovimientoServiceImpl")
    private TipoMovimientoService tipoMovimientoService;

    @Autowired
    @Qualifier("planillaMovimientosServiceImpl")
    private PlanillaMovimientosService planillaMovimientosService;

    private static final String INDEX_VIEW = "periodo/index";
    private static final Log LOGGER = LogFactory.getLog(ProfesionController.class);

    @GetMapping("/index/{id}")
    public ModelAndView index(@PathVariable(value = "id", required = true) int idPeriodo, Model model){

        Periodo periodo = periodoService.getByIdPeriodo(idPeriodo);
        List<Planilla> planillas = planillaService.findByPeriodo(periodo);

        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("periodo",periodo);
        modelAndView.addObject("planillas",planillas);

        return modelAndView;

    }

    @GetMapping("/boleta/{idplanilla}")
    public ModelAndView boleta(@PathVariable(value = "idplanilla", required = true) int idPlanilla, Model model){

        Planilla planilla = planillaService.getPlanilla(idPlanilla);
        Periodo periodo = planilla.getPeriodo();
        Empleado empleado = planilla.getEmpleado();

        // Obteniendo edad del empleado
        String edad = String.valueOf(Period.between(empleado.getFechaNacimiento(), LocalDate.from(LocalDateTime.now())).getYears());

        // Obteniendo todos los documentos del empleado
        List<EmpleadoDocumento> documentos = empleadoDocumentoService.findByEmpleado(empleado);

        String dui = "";
        String nit = "";
        String nup = "";
        String isss = "";

        for (EmpleadoDocumento documento:documentos) {
            //Obteniendo valor de DUI del empleado (TipoDocumento de DUI = 1)
            if(documento.getTipoDocumento().getIdTipoDocumento()==1){
                dui = documento.getCodigoDocumento();
            }
            //Obteniendo valor de NIT del empleado (TipoDocumento de NIT = 2)
            if(documento.getTipoDocumento().getIdTipoDocumento()==2){
                nit = documento.getCodigoDocumento();
            }
            //Obteniendo valor de NUP del empleado (TipoDocumento de NUP = 3)
            if(documento.getTipoDocumento().getIdTipoDocumento()==3){
                nup = documento.getCodigoDocumento();
            }
            //Obteniendo valor de ISSS del empleado (TipoDocumento de ISSS = 4)
            if(documento.getTipoDocumento().getIdTipoDocumento()==4){
                isss = documento.getCodigoDocumento();
            }
        }

        // Obteniendo Puestos y Unidades del Empleado
        List<EmpleadosPuestosUnidades> puestosUnidades = empleadosPuestosUnidadesService.getByEmployee(empleado);

        // Obteniendo el Puesto actual del Empleado, obtenemos el último de la lista
        Puesto puesto=puestosUnidades.get(puestosUnidades.size()-1).getPuesto();

        // Obteniendo la Unidad actual del Empleado, obtenemos la última de la lista
        String unidad = puestosUnidades.get(puestosUnidades.size()-1).getUnidadOrganizacional().getUnidadOrganizacional().toString();

        // Obteniendo al jefe de la unidad organizacional a la que pertenece el empleado.
        Empleado jefe = puestosUnidades.get(puestosUnidades.size()-1).getUnidadOrganizacional().getEmpleadoJefe();
        // Si el jefe de la Unidad Organizacional del empleado resulta ser el mismo, obtenemos el jefe de la unidad organizacional padre
        if(jefe==empleado){
            jefe = puestosUnidades.get(puestosUnidades.size()-1).getUnidadOrganizacional().getUnidadPadre().getEmpleadoJefe();
        }

        // Obteniendo los días festivos en los que laboró el empleado según la planilla
        List<PlanillaDiaFestivo> festivosPlanilla = planillaDiaFestivoService.findByPlanilla(planilla);

        // Obteniendo todos los movimientos asignados a la planilla (Tanto descuentos como ingresos)
        List<PlanillaMovimiento> movimientosPlanilla = planillaMovimientosService.getPlanillaMovimientosByPlanilla(planilla);
        // Array para almacenar Ingresos
        ArrayList<PlanillaMovimiento> movimientosIngreso = new ArrayList<>();
        // Array para almacenar Descuentos Fijos
        ArrayList<PlanillaMovimiento> movimientosDescuentoFijo = new ArrayList<>();
        // Array para almacenar Descuentos no Fijos
        ArrayList<PlanillaMovimiento> movimientosDescuento = new ArrayList<>();
        // Variable para almacenar valor del total de ingresos
        Double totalMovimientosIngreso = 0.00;
        // Variable para almacenar valor del total de descuentos fijos
        Double totalMovimientosDescuentoFijo = 0.00;
        // Variable para almacenar valor del total de descuentos no fijos
        Double totalMovimientosDescuento = 0.00;

        // Recorriendo todos los movimientos
        for (PlanillaMovimiento movimiento:movimientosPlanilla){
            // Almacenando el movimiento si este no es descuento
            if(!movimiento.getTipoMovimiento().isEsDescuento()){
                movimientosIngreso.add(movimiento);
                totalMovimientosIngreso+=movimiento.getMontoMovimiento();
            }else{

                // Si el movimiento es fijo se almacena en 'movimientosDescuentoFijo' de lo contrario en 'totalMovimientosDescuento'
                if(movimiento.getTipoMovimiento().isEsFijo()){
                    movimientosDescuentoFijo.add(movimiento);
                    totalMovimientosDescuentoFijo+=movimiento.getMontoMovimiento();
                }else{
                    movimientosDescuento.add(movimiento);
                    totalMovimientosDescuento+=movimiento.getMontoMovimiento();
                }
            }
        }

        float totalIngresos = (float) (
                 planilla.getMontoDiasFestivos()
                +planilla.getMontoHorasExtra()
                +planilla.getMontoComision()
                +empleado.getSalarioBaseMensual()
                +totalMovimientosIngreso);

        float totalDescuentos = (float) (
                planilla.getRenta()+totalMovimientosDescuentoFijo+totalMovimientosDescuento);

        ModelAndView modelAndView = new ModelAndView("periodo/boleta");
        modelAndView.addObject("periodo",periodo);
        modelAndView.addObject("planilla",planilla);
        modelAndView.addObject("empleado",empleado);
        modelAndView.addObject("dui",dui);
        modelAndView.addObject("nit",nit);
        modelAndView.addObject("nup",nup);
        modelAndView.addObject("isss",isss);
        modelAndView.addObject("edad",edad);
        modelAndView.addObject("puesto",puesto);
        modelAndView.addObject("unidad",unidad);
        modelAndView.addObject("jefe",jefe);
        modelAndView.addObject("festivosPlanilla",festivosPlanilla);

        modelAndView.addObject("movimientosPlanilla",movimientosPlanilla);
        modelAndView.addObject("movimientosIngreso",movimientosIngreso);

        modelAndView.addObject("movimientosDescuentoFijo",movimientosDescuentoFijo);
        modelAndView.addObject("movimientosDescuento",movimientosDescuento);

        modelAndView.addObject("totalMovimientosIngreso",totalMovimientosIngreso);
        modelAndView.addObject("totalMovimientosDescuentoFijo",totalMovimientosDescuentoFijo);
        modelAndView.addObject("totalMovimientosDescuento",totalMovimientosDescuento);

        modelAndView.addObject("totalIngresos",totalIngresos);
        modelAndView.addObject("totalDescuentos",totalDescuentos);

        return modelAndView;

    }
}
