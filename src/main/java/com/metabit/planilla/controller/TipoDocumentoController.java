package com.metabit.planilla.controller;

import com.metabit.planilla.entity.TipoDocumento;
import com.metabit.planilla.service.TipoDocumentoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/planilla/tipo-documento")
public class TipoDocumentoController {

    @Autowired
    @Qualifier("tipoDocumentoServiceImpl")
    private TipoDocumentoService tipoDocumentoService;

    private static final String INDEX_VIEW = "tipo-documento/index";
    private static final String CREATE_VIEW = "tipo-documento/create";

    private static final Log LOGGER = LogFactory.getLog(TipoDocumentoController.class);

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("tiposDocumento",tipoDocumentoService.getTiposDocumento());
        return modelAndView;
    }

    @RequestMapping(path = {"/form-tipo-documento", "/form-tipo-documento/{id}"})
    public ModelAndView create(@PathVariable("id") Optional<Integer> id){
        ModelAndView modelAndView = new ModelAndView(CREATE_VIEW);
        if(id.isPresent()){
            Integer idTipoDocumento = Integer.valueOf(id.get());
            TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumento(idTipoDocumento);
            modelAndView.addObject("tipoDocumentoEntity", tipoDocumento);
        }else{
            modelAndView.addObject("tipoDocumentoEntity", new TipoDocumento());
        }
        return modelAndView;
    }

    @PostMapping("/form-post")
    public String createUpdatePost(@Valid @ModelAttribute("tipoDocumentoEntity") TipoDocumento tipoDocumento, BindingResult bindingResult){
        LOGGER.info("TIPO DOCUMENTO: "+ tipoDocumento);
        if(bindingResult.hasErrors()){
            return "/tipo-documento/create";
        }else{
            tipoDocumentoService.storeTipoDocumento(tipoDocumento);
            return "redirect:index";
        }
    }

    @PostMapping("/disable")
    private String disable(@RequestParam("idTipoDocumentoDisable") int idTipoDocumento) {
        TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumento(idTipoDocumento);
        if(tipoDocumento.getTipoDocumentoHabilitado()){
            tipoDocumento.setTipoDocumentoHabilitado(false);
        }else{
            tipoDocumento.setTipoDocumentoHabilitado(true);
        }
        tipoDocumentoService.updateTipoDocumento(tipoDocumento);
        return "redirect:/planilla/tipo-documento/index";
    }

}
