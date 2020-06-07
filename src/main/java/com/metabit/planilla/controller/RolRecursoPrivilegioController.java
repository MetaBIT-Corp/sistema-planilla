package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolRecursoPrivilegio;
import com.metabit.planilla.service.PrivilegioService;
import com.metabit.planilla.service.RecursoService;
import com.metabit.planilla.service.RolRecursoPrivilegioService;
import com.metabit.planilla.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rol-recurso-privilegio")
public class RolRecursoPrivilegioController extends BaseController{

    @Autowired
    @Qualifier("rolRecursoPrivilegioServiceImpl")
    private RolRecursoPrivilegioService rolRecursoPrivilegioService;

    @Autowired
    @Qualifier("rolServiceImpl")
    private RolService rolService;

    @Autowired
    @Qualifier("recursoServiceImpl")
    private RecursoService recursoService;

    @Autowired
    @Qualifier("privilegioServiceImpl")
    private PrivilegioService privilegioService;

    private static final String INDEX_VIEW = "rol-recurso-privilegio/index";

    @GetMapping("/index/{id}")
    public ModelAndView index(@PathVariable(value = "id", required = true) int idRol, Model model){

        Rol rol = rolService.getByIdRol(idRol);
        List<Recurso> recursos = recursoService.getRolRecursos(idRol);
        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("recursos", recursos);
        modelAndView.addObject("rol", rol);

        return modelAndView;
    }

    @GetMapping("/privilegios/{idrol}/{idrecurso}")
    public @ResponseBody ArrayList<String> privilegios(@PathVariable(value = "idrol", required = true)int idRol, @PathVariable(value = "idrecurso", required = true)int idRecurso){

        ArrayList<String> nombres = new ArrayList<>();

        List<Privilegio> privilegios = privilegioService.getRolRecursoPrivilegios(idRol,idRecurso);

        for(int i=0; i<privilegios.size();i++){
            nombres.add(privilegios.get(i).getPrivilegio());
        }

        return nombres;
    }

}
