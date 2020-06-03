package com.metabit.planilla.controller;

import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.service.RecursoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/recurso")
public class RecursoController {

    @Autowired
    @Qualifier("recursoServiceImpl")
    private RecursoService recursoService;

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

}
