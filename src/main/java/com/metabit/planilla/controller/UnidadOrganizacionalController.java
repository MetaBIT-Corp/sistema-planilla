package com.metabit.planilla.controller;

import com.metabit.planilla.service.UnidadOrganizacionalService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/unidades-organizacionales")
public class UnidadOrganizacionalController {

    @Autowired
    @Qualifier("unidadOrganizacionalServiceImpl")
    private UnidadOrganizacionalService unidadOrganizacionalService;

    private static final String INDEX_VIEW = "unidad-organizacional/index";
    private static final String EDIT_VIEW = "unidad-organizacional/edit";
    private static final String CREATE_VIEW = "unidad-organizacional/create";
    private static final String SHOW_VIEW = "unidad-organizacional/show";
    private static final Log LOGGER = LogFactory.getLog(UnidadOrganizacionalController.class);

    //List all Unidades organizacionales
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/index")
    public ModelAndView index(Model model){
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        mav.addObject("unidades",unidadOrganizacionalService.getAllUnidadesOrganizacionales());
        return mav;
    }

    //Get Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id", required = true) int id){
        ModelAndView mav = new ModelAndView(SHOW_VIEW);
        return mav;
    }

    //Get Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView mav = new ModelAndView(CREATE_VIEW);
        return mav;
    }

    //Save new Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @PostMapping("/store")
    public String store(){
       return "redirect:/unidades-organizacionales/index";
    }

    //Edit Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id", required = true) int id){
        ModelAndView mav = new ModelAndView(EDIT_VIEW);
        return mav;
    }

    //Update Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @PostMapping("/update")
    public String update(){
        return "redirect:/unidades-organizacionales/index";
    }
}
