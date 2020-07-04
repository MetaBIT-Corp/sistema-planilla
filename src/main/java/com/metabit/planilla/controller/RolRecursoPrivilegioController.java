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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
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

    @PreAuthorize("hasAuthority('PERMISO_INDEX')")
    @GetMapping("/index/{id}")
    public ModelAndView index(@PathVariable(value = "id", required = true) int idRol, Model model,
                              @RequestParam(name="store_success", required=false) String store_success,
                              @RequestParam(name="delete_success", required=false) String delete_success){

        Rol rol = rolService.getByIdRol(idRol);
        List<Recurso> recursos = recursoService.getRolRecursos(idRol);
        List<Privilegio> privilegios = privilegioService.getPrivilegios();

        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("rol", rol);
        modelAndView.addObject("recursos", recursos);
        modelAndView.addObject("privilegios", privilegios);

        model.addAttribute("store_success", store_success);
        model.addAttribute("delete_success", delete_success);

        model.addAttribute("rolRecursoPrivilegioEntity", new RolRecursoPrivilegio());

        return modelAndView;

    }

    @PreAuthorize("hasAuthority('PERMISO_CREATE')")
    @PostMapping("/asignar-recurso")
    public @ResponseBody JsonResponse asignarRecurso(@RequestParam("idRolAsignacion") String idRol, @RequestParam("idRecursoAsignacion") String idRecurso, @RequestParam("idsPrivilegiosAsignacion") String idsPrivilegios) throws JSONException {

        JsonResponse jsonResponse = new JsonResponse();

        if(idsPrivilegios!="" && idRecurso!="" && idRol!=""){

            Rol rol = rolService.getByIdRol(Integer.parseInt(idRol));
            Recurso recurso = recursoService.getRecurso(Integer.parseInt(idRecurso));
            String[] ids = idsPrivilegios.split("[|]");

            for(String id:ids){

                int idR = Integer.parseInt(id);

                Privilegio privilegio = privilegioService.getPrivilegio(idR);

                RolRecursoPrivilegio rolRecursoPrivilegiox = new RolRecursoPrivilegio();
                rolRecursoPrivilegiox.setRol(rol);
                rolRecursoPrivilegiox.setRecurso(recurso);
                rolRecursoPrivilegiox.setPrivilegio(privilegio);

                rolRecursoPrivilegioService.storeRolRecursoPrivilegio(rolRecursoPrivilegiox);

            }

            jsonResponse.setStatus("SUCCESS");
            jsonResponse.setResult(rol);

        }else{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","Ingrese los privilegios a asignar.");

            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(jsonObject.toString());
        }

        return jsonResponse;

    }

    @PreAuthorize("hasAuthority('PERMISO_INDEX')")
    @GetMapping("/recurso-privilegios/{idrol}/{idrecurso}")
    public @ResponseBody String recursoPrivilegios(@PathVariable(value = "idrol", required = true)int idRol, @PathVariable(value = "idrecurso", required = true)int idRecurso) throws JSONException {

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

    @PreAuthorize("hasAuthority('PERMISO_INDEX')")
    @GetMapping("/rol-recursos/{idrol}")
    public @ResponseBody String rolRecursos(@PathVariable(value = "idrol", required = true)int idRol) throws JSONException {

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

    @PreAuthorize("hasAuthority('PERMISO_EDIT')")
    @PostMapping("/cambiar-privilegio")
    public @ResponseBody JsonResponse cambiarPrivilegio(@RequestParam("idRol") int idRol, @RequestParam("idRecurso") int idRecurso, @RequestParam("idPrivilegio") int idPrivilegio, @RequestParam("estado") int estado){

        JsonResponse jsonResponse = new JsonResponse();

        Rol rol = rolService.getByIdRol(idRol);
        Recurso recurso = recursoService.getRecurso(idRecurso);
        Privilegio privilegio = privilegioService.getPrivilegio(idPrivilegio);

        if(estado==0){

            // Verificamos existencia del objeto
            RolRecursoPrivilegio rolRecursoPrivilegioExistente= rolRecursoPrivilegioService.getRolRecursoPrivilegioByRolAndRecursoAndPrivilegio(rol,recurso,privilegio);

            // Si el objeto no existe entonces lo crea, de lo contrario no hace nada
            if (rolRecursoPrivilegioExistente == null) {

                RolRecursoPrivilegio rolRecursoPrivilegio = new RolRecursoPrivilegio();
                rolRecursoPrivilegio.setRol(rol);
                rolRecursoPrivilegio.setRecurso(recurso);
                rolRecursoPrivilegio.setPrivilegio(privilegio);

                rolRecursoPrivilegioService.storeRolRecursoPrivilegio(rolRecursoPrivilegio);

                jsonResponse.setStatus("SUCCESS");
                jsonResponse.setResult(rolRecursoPrivilegio);

            }

        }else{

            // Verificamos existencia del objeto
            RolRecursoPrivilegio rolRecursoPrivilegioExistente= rolRecursoPrivilegioService.getRolRecursoPrivilegioByRolAndRecursoAndPrivilegio(rol,recurso,privilegio);

            // Si el objeto existe entonces lo elimina, de lo contrario no hace nada
            if (rolRecursoPrivilegioExistente != null) {
                rolRecursoPrivilegioService.deleteRolRecursoPrivilegio(rolRecursoPrivilegioExistente);
                jsonResponse.setStatus("SUCCESS");
                jsonResponse.setResult(rol);
            }

        }

        return jsonResponse;

    }

    @PreAuthorize("hasAuthority('PERMISO_DELETE')")
    @PostMapping("/destroy")
    public String destroy(@RequestParam("idRolDestroy") int idRol, @RequestParam("idRecursoDestroy") int idRecurso) {

        Rol rol = rolService.getByIdRol(idRol);
        Recurso recurso = recursoService.getRecurso(idRecurso);

        List<RolRecursoPrivilegio> lista = rolRecursoPrivilegioService.findByRolAndRecurso(rol,recurso);

        for(RolRecursoPrivilegio rolRecursoPrivilegio:lista){
            rolRecursoPrivilegioService.deleteRolRecursoPrivilegio(rolRecursoPrivilegio);
        }

        return "redirect:/"+INDEX_VIEW+"/"+idRol+"?delete_success=true";

    }


}
