package com.metabit.planilla.controller;


import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.*;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.DireccionService;
import com.metabit.planilla.service.RolService;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    @Qualifier("userJpaRepository")
    private UserJpaRepository userJpaRepository;

    @Autowired
    @Qualifier("departamentoServiceImpl")
    private DepartamentoService departamentoService;

    @Autowired
    @Qualifier("rolServiceImpl")
    private RolService rolService;

    @Autowired
    @Qualifier("direccionServiceImpl")
    private DireccionService direccionService;

    private static final String INDEX_VIEW = "departamento/index";

    @PreAuthorize("hasAuthority('DEPARTAMENTO_INDEX')")
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);

        //Verificando si el usuario logueado posee los permisos para acceder a las funcionalidades (para mostrar botones)
        String recurso = "DEPARTAMENTO";
        Boolean create = verifyResourcePrivileges("CREATE",recurso);
        Boolean edit = verifyResourcePrivileges("EDIT",recurso);
        Boolean delete = verifyResourcePrivileges("DELETE",recurso);

        mav.addObject("departamentos", departamentoService.getAllDepartamentos());
        mav.addObject("depto", new Departamento());
        mav.addObject("create",create);
        mav.addObject("edit",edit);
        mav.addObject("delete",delete);
        return mav;
    }

    //Edit Unidad Organizacional
    @GetMapping("/edit/{id}")
    public @ResponseBody
    JsonResponse edit(@PathVariable(value = "id", required = true) int id) {
        JsonResponse jsonResponse = new JsonResponse();
        Departamento depto = departamentoService.getDepartamento(id);
        jsonResponse.setResult(depto);
        return jsonResponse;
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam Map<String, String> allParams) {
        if (allParams.get("departamento").isEmpty()) {
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de departamento.", HttpStatus.BAD_REQUEST);
        }
        // Crear Departamento
        Departamento depto = new Departamento();
        depto.setDepartamento(allParams.get("departamento"));
        departamentoService.createOrUpdateDepartamento(depto);
        return new ResponseEntity<>("Se creo correctamente el departamento.", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestParam Map<String, String> allParams) {
        if (allParams.get("departamento").isEmpty()) {
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de departamento.", HttpStatus.BAD_REQUEST);
        }
        //Departamento
        Departamento depto = departamentoService.getDepartamento(Integer.parseInt(allParams.get("idDepartamento")));
        depto.setDepartamento(allParams.get("departamento"));
        departamentoService.createOrUpdateDepartamento(depto);
        return new ResponseEntity<>("Se actualizo correctamente el departamento.", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Map<String, String> allParams) {
        Departamento depto = departamentoService.getDepartamento(Integer.parseInt(allParams.get("idDepartamento")));
        List<Direccion> direcciones = direccionService.getAllDirecciones();
        for (Direccion d: direcciones) {
            if(depto.getMunicipios().contains(d.getMunicipio())){
                return new ResponseEntity<>("No puede ser eliminado. Es usado en la direccion de algun empleado", HttpStatus.BAD_REQUEST);
            }
        }
        departamentoService.deleteDepartamento(depto);
        return new ResponseEntity<>("Se elimino correctamente el departamento.", HttpStatus.OK);
    }

    private Usuario getUserLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario usuario = userJpaRepository.findByUsername(userDetail.getUsername());
        return usuario;
    }

    private Boolean verifyResourcePrivileges(String privilegio, String recurso){
        Usuario usuario = getUserLogueado();
        for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
            for (RolRecursoPrivilegio rrp : rol.getRolesRecursosPrivilegios()) {
                //SE VERIFICA SI TIENE EL RECURSO Y EL PRIVILEGIO REQUERIDO
                if (rrp.getRecurso().getRecurso().equals(recurso)&&rrp.getPrivilegio().getPrivilegio().equals(privilegio)) {
                    return true;
                }
            }
        }
        return false;
    }
}
