package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.entity.Municipio;
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.MunicipioService;
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
@RequestMapping("/municipio")
public class MunicipioController {

    @Autowired
    @Qualifier("municipioServiceImpl")
    private MunicipioService municipioService;

    @Autowired
    @Qualifier("departamentoServiceImpl")
    private DepartamentoService departamentoService;

    private static final String INDEX_VIEW = "municipio/index";

    @GetMapping("/index/{id}")
    public ModelAndView index(@PathVariable(value = "id", required = true) int idDepartamento, Model model,
                              @RequestParam(name="store_success", required=false) String store_success,
                              @RequestParam(name="update_success", required=false) String update_success,
                              @RequestParam(name="delete_success", required=false) String delete_success){

        Departamento departamento = departamentoService.getDepartamento(idDepartamento);

        List<Municipio> municipios = municipioService.getMunicipiosByDepartamento(departamento);

        ModelAndView modelAndView = new ModelAndView(INDEX_VIEW);
        modelAndView.addObject("departamento", departamento);
        modelAndView.addObject("municipios", municipios);
        model.addAttribute("store_success", store_success);
        model.addAttribute("update_success", update_success);
        model.addAttribute("delete_success", delete_success);
        model.addAttribute("municipioEntity", new Municipio());

        return modelAndView;

    }

    @PostMapping("/store")
    public @ResponseBody
    JsonResponse store(@ModelAttribute(name="municipioEntity") Municipio municipio, @RequestParam("idDepartamento") String idDepartamento, BindingResult bindingResult){

        Departamento departamento = departamentoService.getDepartamento(Integer.parseInt(idDepartamento));

        JsonResponse jsonResponse = new JsonResponse();

        ValidationUtils.rejectIfEmpty(bindingResult,"municipio","Ingrese el nombre del Municipio.");

        if(!bindingResult.hasErrors()){
            if(municipio.getIdMunicipio() == 0) {
                municipio.setDepartamento(departamento);
                municipioService.storeMunicipio(municipio);
            }else{
                municipio.setDepartamento(departamento);
                municipioService.updateMunicipio(municipio);
            }
            jsonResponse.setStatus("SUCCESS");
            jsonResponse.setResult(municipio);
        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(bindingResult.getAllErrors());
        }

        return jsonResponse;

    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name="municipioEntity") Municipio municipio){
        municipioService.updateMunicipio(municipio);
        return "redirect:/municipio/index?update_success=true";
    }

    @PostMapping("/destroy")
    public String destroy(@RequestParam("idMunicipioDestroy") int idMunicipio, @RequestParam("idDepartamentoDestroy") int idDepartamento) {

        municipioService.deleteMunicipio(idMunicipio);
        return "redirect:/"+INDEX_VIEW+"/"+idDepartamento+"?delete_success=true";

    }
}
