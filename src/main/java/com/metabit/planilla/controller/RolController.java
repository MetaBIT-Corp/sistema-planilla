package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolRecursoPrivilegio;
import com.metabit.planilla.service.RolRecursoPrivilegioService;
import com.metabit.planilla.service.RolService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    @Qualifier("rolServiceImpl")
    private RolService rolService;

    @Autowired
    @Qualifier("rolRecursoPrivilegioServiceImpl")
    private RolRecursoPrivilegioService rolRecursoPrivilegioService;

    private static final String INDEX_VIEW = "rol/index";
    private static final Log LOGGER = LogFactory.getLog(RolController.class);

    @GetMapping("/index")
    public ModelAndView index(
            Model model,
            @RequestParam(name="store_success", required=false)
                    String store_success,
            @RequestParam(name="update_success", required=false)
                    String update_success,
            @RequestParam(name="enable_success", required=false)
                    String enable_success,
            @RequestParam(name="disable_success", required=false)
                    String disable_success,
            @RequestParam(name="delete_success", required=false)
                    String delete_success){

        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("roles", rolService.getAllRoles());
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("enable_success", enable_success);
        model.addAttribute("disable_success", disable_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("rolEntity", new Rol());
        return modelAndView;
    }

    @PostMapping("/store")
    public @ResponseBody JsonResponse store(@ModelAttribute(name="rolEntity") Rol rol, BindingResult bindingResult){

        JsonResponse jsonResponse = new JsonResponse();

        ValidationUtils.rejectIfEmpty(bindingResult,"authority","Ingrese el nombre del rol.");

        if(!bindingResult.hasErrors()){
            if(rol.getIdRol() == null) {
                rolService.storeRol(rol);
                jsonResponse.setStatus("SUCCESS");
                jsonResponse.setResult(rol);
            }else{
                int asignaciones = rolRecursoPrivilegioService.findByRol(rol).size();
                if (!(asignaciones>0)){
                    rolService.updateRol(rol);
                    jsonResponse.setStatus("SUCCESS");
                    jsonResponse.setResult(rol);
                }else{
                    jsonResponse.setStatus("NOEDITABLE");
                    jsonResponse.setResult(rol);
                }
            }

        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(bindingResult.getAllErrors());
        }

        return jsonResponse;

    }

    @PostMapping("/destroy")
    public String destroy(@RequestParam("idRolDestroy") int idRol) {

        Rol rol = rolService.getByIdRol(idRol);
        if(rolService.getAllUsedRoles().contains(rol)){
            return "redirect:/"+INDEX_VIEW+"?delete_success=false";
        }
        else{

            List<RolRecursoPrivilegio> listaPrivilegiosRecursos = rolRecursoPrivilegioService.findByRol(rol);

            for(RolRecursoPrivilegio rolRecursoPrivilegio:listaPrivilegiosRecursos){
                rolRecursoPrivilegioService.deleteRolRecursoPrivilegio(rolRecursoPrivilegio);
            }

            rolService.deleteRol(idRol);
            return "redirect:/"+INDEX_VIEW+"?delete_success=true";
        }

    }
}