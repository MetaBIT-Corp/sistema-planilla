package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.EmpleadoProfesion;
import com.metabit.planilla.entity.Profesion;
import com.metabit.planilla.service.EmpleadoProfesionService;
import com.metabit.planilla.service.ProfesionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profesion")
public class ProfesionController {

    @Autowired
    @Qualifier("profesionServiceImpl")
    private ProfesionService profesionService;

    @Autowired
    @Qualifier("empleadoProfesionServiceImpl")
    private EmpleadoProfesionService empleadoProfesionService;

    private static final String INDEX_VIEW = "profesion/index";
    private static final Log LOGGER = LogFactory.getLog(ProfesionController.class);

    @PreAuthorize("hasAuthority('PROFESION_INDEX')")
    @GetMapping("/index")
    public ModelAndView index(Model model,
                              @RequestParam(name="store_success", required=false) String store_success,
                              @RequestParam(name="update_success", required=false) String update_success,
                              @RequestParam(name="enable_success", required=false) String enable_success,
                              @RequestParam(name="disable_success", required=false) String disable_success,
                              @RequestParam(name="delete_success", required=false) String delete_success) {
        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("profesiones", profesionService.getProfesiones());
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("enable_success", enable_success);
        model.addAttribute("disable_success", disable_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("profesionEntity", new Profesion());

        return modelAndView;
    }

    @PreAuthorize("hasAuthority('PROFESION_CREATE')")
    @PostMapping("/store")
    public @ResponseBody JsonResponse store(@ModelAttribute(name="profesionEntity") Profesion profesion, BindingResult bindingResult){

        JsonResponse jsonResponse = new JsonResponse();

        if(profesion.getEsProfesion()){
            ValidationUtils.rejectIfEmpty(bindingResult,"profesion","Ingrese el nombre de la profesión.");
        }else{
            ValidationUtils.rejectIfEmpty(bindingResult,"profesion","Ingrese el nombre del oficio.");
        }

        if(!bindingResult.hasErrors()){
            if(profesion.getIdProfesion() == 0) {
                profesion.setProfesionHabilitada(true);
                profesionService.storeProfesion(profesion);
            }else{
                profesionService.updateProfesion(profesion);
            }
            jsonResponse.setStatus("SUCCESS");
            jsonResponse.setResult(profesion);
        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(bindingResult.getAllErrors());
        }

        return jsonResponse;

    }

    @PreAuthorize("hasAuthority('PROFESION_EDIT')")
    @PostMapping("/update")
    public String update(@ModelAttribute(name="profesionEntity") Profesion profesion){
        profesionService.updateProfesion(profesion);
        return "redirect:/profesion/index?update_success=true";
    }

    @PreAuthorize("hasAuthority('PROFESION_DELETE')")
    @PostMapping("/disable")
    public String disable(@RequestParam("idProfesionDisable") int idProfesion){
        Profesion profesion = profesionService.getProfesion(idProfesion);
        if(profesion.getProfesionHabilitada()){
            profesion.setProfesionHabilitada(false);
            profesionService.updateProfesion(profesion);
            return "redirect:/"+INDEX_VIEW+"?disable_success=true";
        }else{
            profesion.setProfesionHabilitada(true);
            profesionService.updateProfesion(profesion);
            return "redirect:/"+INDEX_VIEW+"?enable_success=true";
        }
    }

    @PreAuthorize("hasAuthority('PROFESION_DELETE')")
    @PostMapping("/destroy")
    public String destroy(@RequestParam("idProfesionDestroy") int idProfesion) {

        Profesion profesion = profesionService.getProfesion(idProfesion);

        int asignaciones = empleadoProfesionService.findByProfesion(profesion).size();
        if (asignaciones>0){
            return "redirect:/"+INDEX_VIEW+"?delete_success=false";
        }else{
            profesionService.deleteProfesion(profesion.getIdProfesion());
            return "redirect:/"+INDEX_VIEW+"?delete_success=true";
        }
    }

}