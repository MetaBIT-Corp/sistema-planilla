package com.metabit.planilla.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.entity.Profesion;
import com.metabit.planilla.service.ProfesionService;

@Controller
@RequestMapping("/profesion")
public class ProfesionController {

    @Autowired
    @Qualifier("profesionServiceImpl")
    private ProfesionService profesionService;

    private static final String INDEX_VIEW = "profesion/index";
    private static final String CREATE_VIEW = "profesion/create";

    private static final Log LOGGER = LogFactory.getLog(ProfesionController.class);

    @PreAuthorize("hasAuthority('PROFESION_INDEX')")
    @GetMapping("/index")
    public ModelAndView index(Model model,
            @RequestParam(name="store_success", required=false) String store_success,
            @RequestParam(name="update_success", required=false) String update_success,
            @RequestParam(name="delete_success", required=false) String delete_success) {
        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("profesiones", profesionService.getProfesiones());
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("profesionEntity", new Profesion());

        return modelAndView;
    }

    @PreAuthorize("hasAuthority('PROFESION_CREATE')")
    @PostMapping("/store")
    public String store(@ModelAttribute(name="profesionEntity") Profesion profesion){
        if(profesion.getIdProfesion()==0){
            profesionService.storeProfesion(profesion);
        }else{
            profesionService.updateProfesion(profesion);
            return "redirect:/profesion/index?update_success=true";
        }
        return "redirect:/profesion/index?store_success=true";
    }

    @PreAuthorize("hasAuthority('PROFESION_EDIT')")
    @PostMapping("/update")
    public String update(@ModelAttribute(name="profesionEntity") Profesion profesion){
        profesionService.updateProfesion(profesion);
        return "redirect:/profesion/index?update_success=true";
    }

    @PreAuthorize("hasAuthority('PROFESION_DELETE')")
    @PostMapping("/destroy")
    public String destroy(@RequestParam("idProfesionDestroy") int idProfesion) {
        LOGGER.info("PROFESION: "+idProfesion);
        profesionService.deleteProfesion(idProfesion);
        return "redirect:/profesion/index?delete_success=true";
    }




}