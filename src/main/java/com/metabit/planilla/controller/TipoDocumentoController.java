package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.TipoDocumento;
import com.metabit.planilla.service.EmpleadoDocumentoService;
import com.metabit.planilla.service.TipoDocumentoService;

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
@RequestMapping("/tipo-documento")
public class TipoDocumentoController {

    @Autowired
    @Qualifier("tipoDocumentoServiceImpl")
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    @Qualifier("empleadoDocumentoServiceImpl")
    private EmpleadoDocumentoService empleadoDocumentoService;

    private static final String INDEX_VIEW = "tipo-documento/index";
    private static final Log LOGGER = LogFactory.getLog(TipoDocumentoController.class);

    @GetMapping("/index")
    public ModelAndView index(Model model,
                              @RequestParam(name="store_success", required=false) String store_success,
                              @RequestParam(name="update_success", required=false) String update_success,
                              @RequestParam(name="enable_success", required=false) String enable_success,
                              @RequestParam(name="disable_success", required=false) String disable_success,
                              @RequestParam(name="delete_success", required=false) String delete_success){
        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("tiposDocumento",tipoDocumentoService.getTiposDocumento());
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("enable_success", enable_success);
        model.addAttribute("disable_success", disable_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("tipoDocumentoEntity", new TipoDocumento());
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('TIPODOCUMENTO_CREATE')")
    @PostMapping("/store")
    public @ResponseBody JsonResponse store(@ModelAttribute(name="tipoDocumentoEntity") TipoDocumento tipoDocumento, BindingResult bindingResult){

        JsonResponse jsonResponse = new JsonResponse();

        ValidationUtils.rejectIfEmpty(bindingResult, "tipoDocumento", "Ingrese el nombre del tipo de documento.");
        ValidationUtils.rejectIfEmpty(bindingResult, "formato", "Ingrese el formato del tipo de documento.");

        if(!bindingResult.hasErrors()){
            if(tipoDocumento.getIdTipoDocumento()==0){
                tipoDocumento.setTipoDocumentoHabilitado(true);
                tipoDocumentoService.storeTipoDocumento(tipoDocumento);
            }else{
                tipoDocumentoService.updateTipoDocumento(tipoDocumento);
            }
            jsonResponse.setStatus("SUCCESS");
            jsonResponse.setResult(tipoDocumento);
        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(bindingResult.getAllErrors());
        }

        return jsonResponse;

    }

    @PreAuthorize("hasAuthority('TIPODOCUMENTO_EDIT')")
    @PostMapping("/update")
    public String update(@ModelAttribute(name="tipoDocumentoEntity") TipoDocumento tipoDocumento){
        tipoDocumentoService.updateTipoDocumento(tipoDocumento);
        return "redirect:/"+INDEX_VIEW+"?update_success=true";
    }

    @PreAuthorize("hasAuthority('TIPODOCUMENTO_DELETE')")
    @PostMapping("/disable")
    public String disable(@RequestParam("idTipoDocumentoDisable") int idTipoDocumento) {
        TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumento(idTipoDocumento);
        if(tipoDocumento.getTipoDocumentoHabilitado()){
            tipoDocumento.setTipoDocumentoHabilitado(false);
            tipoDocumentoService.updateTipoDocumento(tipoDocumento);
            return "redirect:/"+INDEX_VIEW+"?disable_success=true";
        }else{
            tipoDocumento.setTipoDocumentoHabilitado(true);
            tipoDocumentoService.updateTipoDocumento(tipoDocumento);
            return "redirect:/"+INDEX_VIEW+"?enable_success=true";
        }
    }

    @PreAuthorize("hasAuthority('TIPODOCUMENTO_DELETE')")
    @PostMapping("/destroy")
    public String destroy(@RequestParam("idTipoDocumentoDestroy") int idTipoDocumento) {

        TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumento(idTipoDocumento);

        int asignaciones = empleadoDocumentoService.findByTipoDocumento(tipoDocumento).size();

        if (asignaciones>0){
            return "redirect:/"+INDEX_VIEW+"?delete_success=false";
        }else{
            tipoDocumentoService.deleteTipoDocumento(tipoDocumento.getIdTipoDocumento());
            return "redirect:/"+INDEX_VIEW+"?delete_success=true";
        }

    }

}