package com.metabit.planilla.controller;

import com.metabit.planilla.entity.*;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.AnioLaboralService;
import com.metabit.planilla.service.AsignacionPresupuestoService;
import com.metabit.planilla.service.CentroCostoService;
import com.metabit.planilla.service.UnidadOrganizacionalService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/presupuesto")
public class PresupuestoController {

    @Autowired
    @Qualifier("unidadOrganizacionalServiceImpl")
    private UnidadOrganizacionalService unidadOrganizacionalService;

    @Autowired
    @Qualifier("centroCostoServiceImpl")
    private CentroCostoService centroCostoService;

    @Autowired
    @Qualifier("anioLaboralServiceImpl")
    private AnioLaboralService anioLaboralService;

    @Autowired
    @Qualifier("asignacionPresupuestoServiceImpl")
    private AsignacionPresupuestoService asignacionPresupuestoService;

    @Autowired
    @Qualifier("userJpaRepository")
    private UserJpaRepository userJpaRepository;

    private static final String EDIT_VIEW = "presupuesto/edit";
    private static final String SHOW_CENTRO_COSTO = "presupuesto/centro-costo";
    private static final Log LOGGER = LogFactory.getLog(PresupuestoController.class);

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('PRESUPUESTO_EDIT')")
    public ModelAndView edit(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(EDIT_VIEW);
        UnidadOrganizacional unit = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        AnioLaboral anioLaboral = anioLaboralService.getAnioLaboral(LocalDate.now().getYear());
        CentroCosto centroCosto = centroCostoService.findByAnioAndUnidad(anioLaboral, unit);

        mav.addObject("unidad", unit);
        if (centroCosto == null) {
            mav.addObject("adventencia", "No hay año laboral creado. Se podra asignar presupuesto hasta que exista un año laboral activo.");
            mav.addObject("anio_error", true);
            return mav;
        }
        if(unit.getUnidadPadre()!=null){
            CentroCosto centroCostoPadre = centroCostoService.findByAnioAndUnidad(anioLaboral, unit.getUnidadPadre());
            double presupuestoPadreDisponible = centroCostoPadre.getPresupuestoAsignado() - centroCostoPadre.getPresupuestoDevengado();
            mav.addObject("presupuestoPadreDisponible",presupuestoPadreDisponible);
        }else{
            mav.addObject("presupuestoPadreDisponible",-1);
        }
        mav.addObject("presupuestoUnidadDisponible",centroCosto.getPresupuestoAsignado()-centroCosto.getPresupuestoDevengado());
        mav.addObject("anio_error", false);
        mav.addObject("centroCosto", centroCosto);
        mav.addObject("anio", anioLaboral);
        mav.addObject("uo",unit);
        return mav;
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam Map<String, String> allParams) {
        Map<String, String> mensajes = new HashMap<String, String>();
        AnioLaboral anioLaboral = anioLaboralService.getAnioLaboral(LocalDate.now().getYear());
        double mas = 0.0;
        double menos = 0.0;
        double nuevoPresupuesto = 0.0;
        Boolean esIncremento = false;

        if (allParams.get("mas").isEmpty() && allParams.get("menos").isEmpty()) {
            mensajes.put("empty", "Debe de ingresar los datos de al menos un campo.");
        } else {
            if (!allParams.get("mas").isEmpty()){
                mas = Double.parseDouble(allParams.get("mas"));
            }
            if (!allParams.get("menos").isEmpty()){
                menos = Double.parseDouble(allParams.get("menos"));
            }

            if (mas < 0 || menos < 0) {
                mensajes.put("negativo", "No se admiten nuemeros negativos.");
            } else {
                // Recuperacion del centro de costo a modificar
                CentroCosto centroCosto = centroCostoService.getOneCentroCosto(Integer.parseInt(allParams.get("idCentroCosto")));

                //Nuevo presupuesto
                double montoAsignacion = mas - menos;
                nuevoPresupuesto = centroCosto.getPresupuestoAsignado() + montoAsignacion;
                LOGGER.info("Nuevo presu " + nuevoPresupuesto + "---------------------");
                LOGGER.info("Mas " + mas + "---------------------");
                LOGGER.info("Menos " + menos + "---------------------");
                LOGGER.info("Monto Asignacion " + montoAsignacion + "---------------------");
                if (nuevoPresupuesto < 0) {
                    mensajes.put("presupuestoNegativo", "El nuevo presupuesto no puede ser negativo.");
                    return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
                }

                //Unidad Padre de la unidad organizacional a la que se le aplicara el incremento o disminucion
                UnidadOrganizacional unidadPadre = centroCosto.getUnidadOrganizacional().getUnidadPadre();

                //Verifica si posee unidad padre
                if (unidadPadre != null) {
                    CentroCosto centroCostoPadre = centroCostoService.findByAnioAndUnidad(anioLaboral, unidadPadre);
                    double presupuestoPadreDisponible = centroCostoPadre.getPresupuestoAsignado() - centroCosto.getPresupuestoDevengado();

                    // Validacion de que si el presupuesto nuevo excede las capacidades del disponible del padre
                    if (montoAsignacion > presupuestoPadreDisponible) {
                        LOGGER.info(nuevoPresupuesto + "---------------------");
                        LOGGER.info(presupuestoPadreDisponible + "---------------------");
                        mensajes.put("errorPadre", "La unidad organizacional padre, no posee los fondos suficientes.");
                        return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
                    }
                }
                //Verificando que no se quite mas fondos de los que se poseen actualmente
                if (montoAsignacion < 0 && (montoAsignacion * -1) > (centroCosto.getPresupuestoAsignado() - centroCosto.getPresupuestoDevengado())) {
                    mensajes.put("errorHijo", "No se pueden quitar mas fondos de los que se tienen disponibles en la unidad.");
                    return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
                }
                //Verificacion de si es incremento o decremento en el presupuesto
                if (montoAsignacion > 0) {
                    esIncremento = true;
                } else{
                    montoAsignacion = montoAsignacion * -1;
                }

                if(montoAsignacion!=0){
                    //Se registra la asignacion de presupuesto
                    AsignacionPresupuesto asignacionPresupuesto = new AsignacionPresupuesto(
                            montoAsignacion,
                            esIncremento,
                            null,
                            centroCosto,
                            getUserLogueado()
                    );
                    asignacionPresupuestoService.addAsignacion(asignacionPresupuesto);
                }

                mensajes.put("success","El presupuesto se modifico correctamente");
                return new ResponseEntity<>(mensajes, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/centro-costo/{id}")
    public ModelAndView showCentroCosto(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(SHOW_CENTRO_COSTO);
        UnidadOrganizacional unit = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        AnioLaboral anioLaboral = anioLaboralService.getAnioLaboral(LocalDate.now().getYear());
        CentroCosto centroCosto = centroCostoService.findByAnioAndUnidad(anioLaboral, unit);
        mav.addObject("asignaciones",centroCosto.getAsignacionesPresupuesto());
        mav.addObject("uo",unit);
        mav.addObject("anio",anioLaboral);
        return mav;
    }

    private Usuario getUserLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario usuario = userJpaRepository.findByUsername(userDetail.getUsername());
        return usuario;
    }
}
