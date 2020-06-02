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

    private static final String INDEX_VIEW = "unidad-organizacional/index";
    private static final String EDIT_VIEW = "unidad-organizacional/edit";
    private static final String SHOW_VIEW = "unidad-organizacional/show";
    private static final Log LOGGER = LogFactory.getLog(UnidadOrganizacionalController.class);

    //List all Unidades organizacionales
    //@PreAuthorize("hasAuthority('UNIDAD_INDEX')")
    @GetMapping("/index")
    public ModelAndView index(Model model) {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        Usuario usuario = getUserLogueado();

        //VERIFICAR SI ESE ROL ES EL DEL JEFE DE UNIDAD ORGANIZACIONAL, ADMIN o ADMIN DE PRESUPUESTO
        Boolean rolGerente = false;
        Boolean rolAdmin = false;
        Boolean rolPresupuesto = false;

        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            if (rol.getAuthority().equals("ROLE_JEFEUNIDAD")) {
                rolGerente = true;
            }
            if (rol.getAuthority().equals("ROLE_ADMIN")) {
                rolAdmin = true;
            }
            if (rol.getAuthority().equals("ROLE_PRESUPUESTO")) {
                rolPresupuesto = true;
            }
        }

        if (rolGerente) {
            Empleado empleado = empleadoService.findByUsuario(usuario);
            mav.addObject("unidades",
                    empleado.getEmpleadosPuestosUnidades().getUnidadOrganizacional().getSubunidades()
            );
        } else {
            mav.addObject("unidades", unidadOrganizacionalService.getAllUnidadesOrganizacionales());
        }

        mav.addObject("rolGerente", rolGerente);
        mav.addObject("rolAdmin", rolAdmin);
        mav.addObject("rolPresupuesto", rolPresupuesto);
        mav.addObject("unidad", new UnidadOrganizacional());
        mav.addObject("tipos_unidad", tipoUnidadOrganizacionalService.getAll());
        return mav;
    }

    //Get Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(SHOW_VIEW);
        return mav;
    }

    //Save new Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam Map<String, String> allParams) {

        if (allParams.get("unidadOrganizacional").isEmpty()) {
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de unidad.", HttpStatus.BAD_REQUEST);
        }

        //CREACION DE UNIDAD ORGANIZACIONAL
        Boolean rolGerente = false;
        Usuario usuario = getUserLogueado();
        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            if (rol.getAuthority().equals("ROLE_JEFEUNIDAD")) {
                rolGerente = true;
            }
        }

        UnidadOrganizacional uo = new UnidadOrganizacional();
        uo.setUnidadOrganizacional(allParams.get("unidadOrganizacional"));

        if(rolGerente){
            Empleado empleado = empleadoService.findByUsuario(usuario);
            uo.setUnidadPadre(empleado.getEmpleadosPuestosUnidades().getUnidadOrganizacional());
        }else{
            int idUnidadPadre = Integer.parseInt(allParams.get("idUnidadPadre"));
            if (idUnidadPadre == -1) {
                uo.setUnidadPadre(null);
            } else {
                uo.setUnidadPadre(unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidadPadre));
            }
        }

        uo.setTipoUnidadOrganizacional(tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional"))));
        unidadOrganizacionalService.addOrUpdateUnidadOrganizaional(uo);

        //CREACION DE CENTRO DE COSTOS
        CentroCosto centroCosto = new CentroCosto();
        //centroCosto.setAnioLaboral();
        //centroCosto.setUnidadOrganizacional(uo);
        //centroCostoService.creatOrUpdate(centroCosto);

        return new ResponseEntity<>("Se creo correctamente la unidad organzacional.", HttpStatus.OK);
    }

    //Edit Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/edit/{id}")
    public @ResponseBody
    JsonResponse edit(@PathVariable(value = "id", required = true) int id) {
        JsonResponse jsonResponse = new JsonResponse();
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        jsonResponse.setResult(uo);
        return jsonResponse;
    }

    //Update Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestParam Map<String, String> allParams) {

        if (allParams.get("unidadOrganizacional").isEmpty()) {
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de unidad.", HttpStatus.BAD_REQUEST);
        }

        Boolean rolGerente = false;
        Usuario usuario = getUserLogueado();
        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            if (rol.getAuthority().equals("ROLE_JEFEUNIDAD")) {
                rolGerente = true;
            }
        }

        //ACTUALIZACION DE UNIDAD ORGANIZACIONAL
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(Integer.parseInt(allParams.get("idUnidadOrganizacional")));
        uo.setUnidadOrganizacional(allParams.get("unidadOrganizacional"));

        if(rolGerente){
            Empleado empleado = empleadoService.findByUsuario(usuario);
            uo.setUnidadPadre(empleado.getEmpleadosPuestosUnidades().getUnidadOrganizacional());
        }else{
            int idUnidadPadre = Integer.parseInt(allParams.get("idUnidadPadre"));

            if (idUnidadPadre == -1) {
                uo.setUnidadPadre(null);
            } else {
                uo.setUnidadPadre(unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidadPadre));
            }
        }

        uo.setTipoUnidadOrganizacional(tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional"))));
        unidadOrganizacionalService.addOrUpdateUnidadOrganizaional(uo);

        return new ResponseEntity<>("Se actualizo la unidad organzacional.", HttpStatus.OK);
    }

    private Usuario getUserLogueado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario usuario = userJpaRepository.findByUsername(userDetail.getUsername());
        return usuario;
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id_unidad_delete", required = true) int id) {
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        if(uo.getSubunidades().size()!=0){
           return  new ResponseEntity<>("No puede ser eliminada, posee SUBUNIDADES.",HttpStatus.BAD_REQUEST);
        }else{
            unidadOrganizacionalService.deleteUnidad(uo);
        }
        return new ResponseEntity<>("Se elimino la unidad correctamente.", HttpStatus.OK);
    }

}
