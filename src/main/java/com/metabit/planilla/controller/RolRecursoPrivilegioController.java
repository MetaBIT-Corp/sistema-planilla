package com.metabit.planilla.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolRecursoPrivilegio;
import com.metabit.planilla.service.PrivilegioService;
import com.metabit.planilla.service.RecursoService;
import com.metabit.planilla.service.RolRecursoPrivilegioService;
import com.metabit.planilla.service.RolService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        List<Privilegio> privilegios = privilegioService.getPrivilegios();

        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("rol", rol);
        modelAndView.addObject("recursos", recursos);
        modelAndView.addObject("privilegios", privilegios);

        model.addAttribute("rolRecursoPrivilegioEntity", new RolRecursoPrivilegio());

        return modelAndView;

    }

    @GetMapping("/privilegios/{idrol}/{idrecurso}")
    public @ResponseBody String privilegios(@PathVariable(value = "idrol", required = true)int idRol, @PathVariable(value = "idrecurso", required = true)int idRecurso) throws JSONException {

        List<Privilegio> recursoPrivilegios = privilegioService.getRolRecursoPrivilegios(idRol,idRecurso);
        List<Privilegio> privilegios = privilegioService.getPrivilegios();

        JSONArray jsonArray = new JSONArray();

        for(Privilegio privilegio:privilegios){

            if(recursoPrivilegios.contains(privilegio)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("estado","1");
                jsonObject.put("idPrivilegio", privilegio.getIdPrivilegio());
                jsonObject.put("privilegio", privilegio.getPrivilegio());
                jsonArray.put(jsonObject);
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("estado","0");
                jsonObject.put("idPrivilegio", privilegio.getIdPrivilegio());
                jsonObject.put("privilegio", privilegio.getPrivilegio());
                jsonArray.put(jsonObject);
            }

        }

        return jsonArray.toString();

    }

    @GetMapping("/recursos/{idrol}")
    public @ResponseBody String recursos(@PathVariable(value = "idrol", required = true)int idRol) throws JSONException {

        List<Recurso> rolRecursos = recursoService.getRolRecursos(idRol);
        List<Recurso> recursos = recursoService.getRecursos();

        JSONArray jsonArray = new JSONArray();

        for(Recurso recurso:recursos){

            if(rolRecursos.contains(recurso)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("estado","1");
                jsonObject.put("idRecurso", recurso.getIdRecurso());
                jsonObject.put("recurso", recurso.getRecurso());
                jsonArray.put(jsonObject);
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("estado","0");
                jsonObject.put("idRecurso", recurso.getIdRecurso());
                jsonObject.put("recurso", recurso.getRecurso());
                jsonArray.put(jsonObject);
            }

        }

        return jsonArray.toString();

    }

    @PostMapping("/asignar")
    public String asignar(@RequestParam("idRol") int idRol, @RequestParam("idRecurso") int idRecurso, @RequestParam("idPrivilegio") int idPrivilegio) {

        Rol rol = rolService.getByIdRol(idRol);
        Recurso recurso = recursoService.getRecurso(idRecurso);
        Privilegio privilegio = privilegioService.getPrivilegio(idPrivilegio);

        RolRecursoPrivilegio rolRecursoPrivilegio = new RolRecursoPrivilegio();
        rolRecursoPrivilegio.setRol(rol);
        rolRecursoPrivilegio.setRecurso(recurso);
        rolRecursoPrivilegio.setPrivilegio(privilegio);

        rolRecursoPrivilegioService.storeRolRecursoPrivilegio(rolRecursoPrivilegio);

        return "redirect:/"+INDEX_VIEW+"/"+idRol;

    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idRolEliminar") int idRol, @RequestParam("idRecursoEliminar") int idRecurso, @RequestParam("idPrivilegioEliminar") int idPrivilegio) {

        Rol rol = rolService.getByIdRol(idRol);
        Recurso recurso = recursoService.getRecurso(idRecurso);
        Privilegio privilegio = privilegioService.getPrivilegio(idPrivilegio);

        RolRecursoPrivilegio rolRecursoPrivilegio = rolRecursoPrivilegioService.findByRolAndRecursoAndPrivilegio(rol,recurso,privilegio);

        rolRecursoPrivilegioService.deleteRolRecursoPrivilegio(rolRecursoPrivilegio);

        return "redirect:/"+INDEX_VIEW+"/"+idRol;

    }

    @PostMapping("/asignar-recurso")
    public String asignarRecursp(@RequestParam("idRolAsignacion") int idRol, @RequestParam("idRecursoAsignacion") int idRecurso, @RequestParam("idsPrivilegiosoAsignacion") String idsPrivilegios) {

        Rol rol = rolService.getByIdRol(idRol);
        Recurso recurso = recursoService.getRecurso(idRecurso);

        String[] ids = idsPrivilegios.split("[|]");

        for(String id:ids){

            int idR = Integer.parseInt(id);

            Privilegio privilegio = privilegioService.getPrivilegio(idR);

            RolRecursoPrivilegio rolRecursoPrivilegio = new RolRecursoPrivilegio();
            rolRecursoPrivilegio.setRol(rol);
            rolRecursoPrivilegio.setRecurso(recurso);
            rolRecursoPrivilegio.setPrivilegio(privilegio);

            rolRecursoPrivilegioService.storeRolRecursoPrivilegio(rolRecursoPrivilegio);

        }






        return "redirect:/"+INDEX_VIEW+"/"+idRol;

    }

}
