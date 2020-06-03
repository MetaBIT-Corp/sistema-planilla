package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.service.PrivilegioService;
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
@RequestMapping("/privilegio")
public class PrivilegioController {

    @Autowired
    @Qualifier("privilegioServiceImpl")
    private PrivilegioService privilegioService;

    @Autowired
    @Qualifier("rolRecursoPrivilegioServiceImpl")
    private RolRecursoPrivilegioService rolRecursoPrivilegioService;

    private static final String INDEX_VIEW = "privilegio/index";
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
        modelAndView.addObject("privilegios", privilegioService.getPrivilegios());
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("enable_success", enable_success);
        model.addAttribute("disable_success", disable_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("privilegioEntity", new Privilegio());
        return modelAndView;
    }

    @PostMapping("/store")
    public @ResponseBody
    JsonResponse store(@ModelAttribute(name="privilegioEntity") Privilegio privilegio, BindingResult bindingResult){

        JsonResponse jsonResponse = new JsonResponse();

        ValidationUtils.rejectIfEmpty(bindingResult,"privilegio","Ingrese el nombre del privilegio.");

        if(!bindingResult.hasErrors()){
            if(privilegio.getIdPrivilegio() == null) {
                privilegioService.storePrivilegio(privilegio);
            }else{
                privilegioService.updatePrivilegio(privilegio);
            }
            jsonResponse.setStatus("SUCCESS");
            jsonResponse.setResult(privilegio);
        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(bindingResult.getAllErrors());
        }

        return jsonResponse;

    }

    @PostMapping("/destroy")
    public String destroy(@RequestParam("idPrivilegioDestroy") int idPrivilegio) {

        Privilegio privilegio = privilegioService.getPrivilegio(idPrivilegio);

        int asignaciones = rolRecursoPrivilegioService.findByPrivilegio(privilegio).size();
        if (asignaciones>0){
            return "redirect:/"+INDEX_VIEW+"?delete_success=false";
        }else{
            privilegioService.deletePrivilegio(idPrivilegio);
            return "redirect:/"+INDEX_VIEW+"?delete_success=true";
        }
    }
}