package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.*;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.*;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/unidades-organizacionales")
public class UnidadOrganizacionalController {

    @Autowired
    @Qualifier("unidadOrganizacionalServiceImpl")
    private UnidadOrganizacionalService unidadOrganizacionalService;

    @Autowired
    @Qualifier("tipoUnidadOrganizacionalServiceImpl")
    private TipoUnidadOrganizacionalService tipoUnidadOrganizacionalService;

    @Autowired
    @Qualifier("centroCostoServiceImpl")
    private CentroCostoService centroCostoService;

    @Autowired
    @Qualifier("userJpaRepository")
    private UserJpaRepository userJpaRepository;

    @Autowired
    @Qualifier("rolServiceImpl")
    private RolService rolService;

    @Autowired
    @Qualifier("empleadoServiceImpl")
    private EmpleadoService empleadoService;

    @Autowired
    @Qualifier("anioLaboralServiceImpl")
    private AnioLaboralService anioLaboralService;

    @Autowired
    @Qualifier("empleadosPuestosUnidadesServiceImpl")
    private EmpleadosPuestosUnidadesService empleadosPuestosUnidadesService;

    private static final String INDEX_VIEW = "unidad-organizacional/index";
    private static final String SHOW_VIEW = "unidad-organizacional/show";
    private static final Log LOGGER = LogFactory.getLog(UnidadOrganizacionalController.class);

    //List all Unidades organizacionales
    @PreAuthorize("hasAuthority('UNIDADORGANIZACIONAL_JEFE_INDEX') or hasAuthority('UNIDADORGANIZACIONAL_INDEX') or hasAuthority('PRESUPUESTO_INDEX')")
    @GetMapping("/index")
    public ModelAndView index(Model model) {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        Usuario usuario = getUserLogueado();

        //VERIFICAR SI ESE ROL ES EL DEL JEFE DE UNIDAD ORGANIZACIONAL, ADMIN o ADMIN DE PRESUPUESTO
        Boolean jefeUnidad = false;
        Boolean create = false;
        Boolean edit = false;
        Boolean delete = false;
        Boolean show = false;
        Boolean presupuesto = false;

        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            for (RolRecursoPrivilegio rrp : rol.getRolesRecursosPrivilegios()) {

                //SE VERIFICA SI TIENE EL RECURSO JEFE UNIDAD
                if (rrp.getRecurso().getRecurso().equals("UNIDADORGANIZACIONAL_JEFE")) {
                    jefeUnidad = true;
                }

                //VERIFICACION DE PRIVILEGIOS EN BASE RECURSOS UNIDADORG_JEFE, UNIDADORGANIZACIONAL y PRESUPUESTO
                if (rrp.getRecurso().getRecurso().equals("UNIDADORGANIZACIONAL_JEFE") ||
                        rrp.getRecurso().getRecurso().equals("UNIDADORGANIZACIONAL")
                ) {
                    switch (rrp.getPrivilegio().getPrivilegio()) {
                        case "CREATE":
                            create = true;
                            break;
                        case "EDIT":
                            edit = true;
                            break;
                        case "DELETE":
                            delete = true;
                            break;
                        case "SHOW":
                            show = true;
                            break;
                    }
                }
                if (rrp.getRecurso().getRecurso().equals("PRESUPUESTO") && rrp.getPrivilegio().getPrivilegio().equals("EDIT")) {
                    presupuesto = true;
                }
            }
        }

        List<UnidadOrganizacional> unidadOrganizacionalList = null;

        if (jefeUnidad) {
            Empleado empleado = empleadoService.findByUsuario(usuario);
            unidadOrganizacionalList = empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(empleado).getUnidadOrganizacional().getSubunidades();
            mav.addObject("unidades",unidadOrganizacionalList);
        } else {
            unidadOrganizacionalList = unidadOrganizacionalService.getAllUnidadesOrganizacionales();
            mav.addObject("unidades", unidadOrganizacionalList);
        }

        //Recuperacion de todos los empleados que no sean jefes en alguna otra unidad organizacional
        /*List<Empleado> empleadoList= empleadoService.ge;

        mav.addObject("empleados",empleadoList);*/
        mav.addObject("create", create);
        mav.addObject("edit", edit);
        mav.addObject("delete", delete);
        mav.addObject("show", show);
        mav.addObject("presupuesto", presupuesto);
        mav.addObject("jefeUnidad", jefeUnidad);
        mav.addObject("unidad", new UnidadOrganizacional());
        mav.addObject("tipos_unidad", tipoUnidadOrganizacionalService.getByTipoUnidadOrganizacionalHabilitado(true));
        return mav;
    }

    //Get Unidad Organizacional
    @PreAuthorize("hasAuthority('UNIDADORGANIZACIONAL_JEFE_SHOW') or hasAuthority('UNIDADORGANIZACIONAL_SHOW')")
    @GetMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(SHOW_VIEW);
        UnidadOrganizacional unidad = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        AnioLaboral anioLaboral = anioLaboralService.getAnioLaboral(LocalDate.now().getYear());
        CentroCosto centroCosto = centroCostoService.findByAnioAndUnidad(anioLaboral, unidad);
        mav.addObject("unidad", unidad);
        mav.addObject("centroCosto", centroCosto);
        mav.addObject("anio", anioLaboral);
        return mav;
    }

    @GetMapping("/show-unidad/{id}")
    public @ResponseBody
    JsonResponse showUnidad(@PathVariable(value = "id", required = true) int id) {
        JsonResponse jsonResponse = new JsonResponse();
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        List<UnidadOrganizacional> unidad = new ArrayList<>();
        if(unidadOrganizacionalService.getAllHijas(uo).size()==0){
            unidad.add(uo);
            jsonResponse.setResult(unidad);
        }else{
            jsonResponse.setResult(unidadOrganizacionalService.getAllHijas(uo));
        }

        return jsonResponse;
    }

    //Save new Unidad Organizacional
    @PreAuthorize("hasAuthority('UNIDADORGANIZACIONAL_JEFE_CREATE') or hasAuthority('UNIDADORGANIZACIONAL_CREATE')")
    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam Map<String, String> allParams) {

        TipoUnidadOrganizacional tipo = tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional")));
        int idUnidadPadre = 0;

        if (allParams.get("unidadOrganizacional").isEmpty()) {
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de unidad.", HttpStatus.BAD_REQUEST);
        }

        Boolean jefeUnidad = false;
        Usuario usuario = getUserLogueado();
        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            for (RolRecursoPrivilegio rrp : rol.getRolesRecursosPrivilegios()) {
                //SE VERIFICA SI TIENE EL RECURSO JEFE UNIDAD
                if (rrp.getRecurso().getRecurso().equals("UNIDADORGANIZACIONAL_JEFE")) {
                    jefeUnidad = true;
                }
            }
        }

        //CREACION DE UNIDAD ORGANIZACIONAL
        UnidadOrganizacional uo = new UnidadOrganizacional();
        uo.setUnidadOrganizacional(allParams.get("unidadOrganizacional"));

        if (jefeUnidad) {
            Empleado empleado = empleadoService.findByUsuario(usuario);
            idUnidadPadre = empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(empleado).getUnidadOrganizacional().getIdUnidadOrganizacional();

            //Validacion de jerarquia
            if(!validarJerarquia(tipo,idUnidadPadre)){
                return new ResponseEntity<>("El tipo de unidad padre es de una jerarquia inferior o igual a la de la unidad nueva. Cambiar el tipo de unidad o unidad padre.", HttpStatus.BAD_REQUEST);
            }

            uo.setUnidadPadre(empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(empleado).getUnidadOrganizacional());
        } else {
            idUnidadPadre = Integer.parseInt(allParams.get("idUnidadPadre"));
            if (idUnidadPadre == -1) {
                uo.setUnidadPadre(null);
            } else {
                uo.setUnidadPadre(unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidadPadre));
            }

            if(!validarJerarquia(tipo,idUnidadPadre)){
                return new ResponseEntity<>("El tipo de unidad padre es de una jerarquia inferior o igual a la de la unidad nueva. Cambiar el tipo de unidad o unidad padre.", HttpStatus.BAD_REQUEST);
            }
        }


        uo.setTipoUnidadOrganizacional(tipo);
        unidadOrganizacionalService.addOrUpdateUnidadOrganizaional(uo);

        return new ResponseEntity<>("Se creo correctamente la unidad organzacional.", HttpStatus.OK);
    }

    //Edit Unidad Organizacional
    @GetMapping("/edit/{id}")
    public @ResponseBody
    JsonResponse edit(@PathVariable(value = "id", required = true) int id) {
        JsonResponse jsonResponse = new JsonResponse();
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        jsonResponse.setResult(uo);
        return jsonResponse;
    }

    //Update Unidad Organizacional
    @PreAuthorize("hasAuthority('UNIDADORGANIZACIONAL_JEFE_EDIT') or hasAuthority('UNIDADORGANIZACIONAL_EDIT')")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestParam Map<String, String> allParams) {

        TipoUnidadOrganizacional tipo = tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional")));
        int idUnidadPadre = 0;

        if (allParams.get("unidadOrganizacional").isEmpty()) {
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de unidad.", HttpStatus.BAD_REQUEST);
        }


        Boolean jefeUnidad = false;
        Usuario usuario = getUserLogueado();
        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            for (RolRecursoPrivilegio rrp : rol.getRolesRecursosPrivilegios()) {
                //SE VERIFICA SI TIENE EL RECURSO JEFE UNIDAD
                if (rrp.getRecurso().getRecurso().equals("UNIDADORGANIZACIONAL_JEFE")) {
                    jefeUnidad = true;
                }
            }
        }

        //ACTUALIZACION DE UNIDAD ORGANIZACIONAL
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(Integer.parseInt(allParams.get("idUnidadOrganizacional")));
        uo.setUnidadOrganizacional(allParams.get("unidadOrganizacional"));

        if (jefeUnidad) {
            Empleado empleado = empleadoService.findByUsuario(usuario);
            idUnidadPadre = empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(empleado).getUnidadOrganizacional().getIdUnidadOrganizacional();

            //Validacion de jerarquia
            if(!validarJerarquia(tipo,idUnidadPadre)){
                return new ResponseEntity<>("El tipo de unidad padre es de una jerarquia inferior o igual a la de la unidad nueva. Cambiar el tipo de unidad o unidad padre.", HttpStatus.BAD_REQUEST);
            }
            uo.setUnidadPadre(empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(empleado).getUnidadOrganizacional());
        } else {
            idUnidadPadre = Integer.parseInt(allParams.get("idUnidadPadre"));
            if (idUnidadPadre == -1) {
                uo.setUnidadPadre(null);
            } else {
                uo.setUnidadPadre(unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidadPadre));
            }

            if(!validarJerarquia(tipo,idUnidadPadre)){
                return new ResponseEntity<>("El tipo de unidad padre es de una jerarquia inferior o igual a la de la unidad nueva. Cambiar el tipo de unidad o unidad padre.", HttpStatus.BAD_REQUEST);
            }
        }

        int idEmpleado = Integer.parseInt(allParams.get("idEmpleado"));
        if(idEmpleado!=-1){
            uo.setEmpleadoJefe(empleadoService.findEmployeeById(idEmpleado));
        }

        uo.setTipoUnidadOrganizacional(tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional"))));
        unidadOrganizacionalService.addOrUpdateUnidadOrganizaional(uo);

        return new ResponseEntity<>("Se actualizo la unidad organzacional.", HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('UNIDADORGANIZACIONAL_JEFE_DELETE') or hasAuthority('UNIDADORGANIZACIONAL_DELETE')")
    public ResponseEntity<?> delete(@RequestParam(name = "id_unidad_delete", required = true) int id) {
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        if (uo.getSubunidades().size() != 0 || uo.getEmpleadosPuestosUnidades().size() !=0) {
            return new ResponseEntity<>("No puede ser eliminada, posee SUBUNIDADES o EMPLEADOS asignados.", HttpStatus.BAD_REQUEST);
        }
        unidadOrganizacionalService.deleteUnidad(uo);
        return new ResponseEntity<>("Se elimino la unidad correctamente.", HttpStatus.OK);
    }

    private Usuario getUserLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario usuario = userJpaRepository.findByUsername(userDetail.getUsername());
        return usuario;
    }

    public Boolean validarJerarquia(TipoUnidadOrganizacional tipo,int idPadre){
       if(idPadre==-1){
           return true;
       }
        if(tipo.getNivelJerarquico() > unidadOrganizacionalService.getOneUnidadOrganizacional(idPadre).getTipoUnidadOrganizacional().getNivelJerarquico()){
            return true;
        }
        return false;
    }
}
