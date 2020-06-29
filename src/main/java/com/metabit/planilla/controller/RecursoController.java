package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.service.RecursoService;
import com.metabit.planilla.service.RolRecursoPrivilegioService;

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

@Controller
@RequestMapping("/recurso")
public class RecursoController {

    @Autowired
    @Qualifier("recursoServiceImpl")
    private RecursoService recursoService;

    @Autowired
    @Qualifier("rolRecursoPrivilegioServiceImpl")
    private RolRecursoPrivilegioService rolRecursoPrivilegioService;

    private static final String INDEX_VIEW = "recurso/index";
    private static final Log LOGGER = LogFactory.getLog(RecursoController.class);

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
        modelAndView.addObject("recursos", recursoService.getRecursos());
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("enable_success", enable_success);
        model.addAttribute("disable_success", disable_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("recursoEntity", new Recurso());
        return modelAndView;
    }

    // Almacenar/Editar Privilegios
    @PostMapping("/store")
    public @ResponseBody JsonResponse store(@ModelAttribute(name="recursoEntity") Recurso recurso, BindingResult bindingResult){

        JsonResponse jsonResponse = new JsonResponse();

        ValidationUtils.rejectIfEmpty(bindingResult,"recurso","Ingrese el nombre del recurso.");

        // Si no hay errores, almacena o actualiza
        if(!bindingResult.hasErrors()){
            // Si la entidad enviada desde la vista no posee un id, almacena una nueva.
            if(recurso.getIdRecurso() == null) {
                recursoService.storeRecurso(recurso);
                jsonResponse.setStatus("SUCCESS");
                jsonResponse.setResult(recurso);
            }else{
                // Si la entidad enviada desde la vista posee un id, actualiza el registro correspondiente en la base
                // Antes verificamos que no haya sido asignado a un RolRecursoPrivilegio
                // Obtenemos la cantidad de veces que el recurso aparece en la taba de Roles_Recursos_Privilegios
                // Si no hay ninguna aparición, actualiza, de lo contrario envia un status de NOEDITABLE en el JSON
                int asignaciones = rolRecursoPrivilegioService.findByRecurso(recurso).size();
                if (!(asignaciones>0)){
                    recursoService.updateRecurso(recurso);
                    jsonResponse.setStatus("SUCCESS");
                    jsonResponse.setResult(recurso);
                }else{
                    jsonResponse.setStatus("NOEDITABLE");
                    jsonResponse.setResult(recurso);
                }
            }
        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(bindingResult.getAllErrors());
        }

        return jsonResponse;

    }

    @PostMapping("/destroy")
    public String destroy(@RequestParam("idRecursoDestroy") int idRecurso) {

        Recurso recurso = recursoService.getRecurso(idRecurso);

        int asignaciones = rolRecursoPrivilegioService.findByRecurso(recurso).size();
        if (asignaciones>0){
            return "redirect:/"+INDEX_VIEW+"?delete_success=false";
        }else{
            recursoService.deleteRecurso(idRecurso);
            return "redirect:/"+INDEX_VIEW+"?delete_success=true";
        }
    }

}