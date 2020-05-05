package com.metabit.planilla.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.entity.Profesion;
import com.metabit.planilla.service.ProfesionService;

@Controller
@RequestMapping("/planilla/profesion")
public class ProfesionController {

    @Autowired
    @Qualifier("profesionServiceImpl")
    private ProfesionService profesionService;

    private static final String INDEX_VIEW = "profesion/index";
    private static final String CREATE_VIEW = "profesion/create";

    private static final Log LOGGER = LogFactory.getLog(ProfesionController.class);

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("profesiones", profesionService.getProfesiones());
        return modelAndView;
    }

    @RequestMapping(path = {"/form-profesion","/form-profesion/{id}"})
    public ModelAndView create(@PathVariable("id") Optional<Integer> id){
        ModelAndView modelAndView = new ModelAndView(CREATE_VIEW);
        if(id.isPresent()){
            Integer idProfesion = Integer.valueOf(id.get());
            Profesion profesion = profesionService.getProfesion(idProfesion);
            modelAndView.addObject("profesionEntity",profesion);
        }else{
            modelAndView.addObject("profesionEntity", new Profesion());
        }
        return modelAndView;
    }

    @PostMapping("/form-post")
    public String createUpdatePost(@Valid @ModelAttribute("profesionEntity") Profesion profesion, BindingResult bindingResult){
        LOGGER.info("PROFESION: " + profesion);
        if(bindingResult.hasErrors()){
            return "/profesion/create";
        }else{
            profesionService.storeProfesion(profesion);
            return "redirect:index";
        }

    }

    @PostMapping("/destroy")
    private String destroy(@RequestParam("idProfesionDestroy") int idProfesion) {
        profesionService.deleteProfesion(idProfesion);
        return "redirect:/planilla/profesion/index";
    }

}