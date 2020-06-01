package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.CentroCosto;
import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.service.CentroCostoService;
import com.metabit.planilla.service.TipoUnidadOrganizacionalService;
import com.metabit.planilla.service.UnidadOrganizacionalService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

@Controller
@RequestMapping("/unidades-organizacionales")
public class UnidadOrganizacionalController {

    @Autowired
    @Qualifier("unidadOrganizacionalServiceImpl")
    private UnidadOrganizacionalService unidadOrganizacionalService;

    @Autowired
    @Qualifier("tipoUnidadOrganizacionalServiceImpl")
    private TipoUnidadOrganizacionalService tipoUnidadOrganizacionalService;

    @Autowired
    @Qualifier("centroCostoServiceImpl")
    private CentroCostoService centroCostoService;

    private static final String INDEX_VIEW = "unidad-organizacional/index";
    private static final String EDIT_VIEW = "unidad-organizacional/edit";
    private static final String SHOW_VIEW = "unidad-organizacional/show";
    private static final Log LOGGER = LogFactory.getLog(UnidadOrganizacionalController.class);

    //List all Unidades organizacionales
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/index")
    public ModelAndView index(Model model){
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        mav.addObject("unidades",unidadOrganizacionalService.getAllUnidadesOrganizacionales());
        mav.addObject("unidad", new UnidadOrganizacional());
        mav.addObject("tipos_unidad",tipoUnidadOrganizacionalService.getAll());
        return mav;
    }

    //Get Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id", required = true) int id){
        ModelAndView mav = new ModelAndView(SHOW_VIEW);
        return mav;
    }

    //Save new Unidad Organizacional
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam Map<String, String> allParams){

        if(allParams.get("unidadOrganizacional").isEmpty()){
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de unidad.", HttpStatus.BAD_REQUEST);
        }

        //CREACION DE UNIDAD ORGANIZACIONAL
        UnidadOrganizacional uo = new UnidadOrganizacional();
        uo.setUnidadOrganizacional(allParams.get("unidadOrganizacional"));
        int idUnidadPadre = Integer.parseInt(allParams.get("idUnidadPadre"));

        if(idUnidadPadre == -1){
            uo.setUnidadPadre(null);
        }else{
            uo.setUnidadPadre(unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidadPadre));
        }

        uo.setTipoUnidadOrganizacional(tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional"))));
        unidadOrganizacionalService.addOrUpdateUnidadOrganizaional(uo);

        //CREACION DE CENTRO DE COSTOS
        CentroCosto centroCosto = new CentroCosto();
        //centroCosto.setAnioLaboral();
        //centroCosto.setUnidadOrganizacional(uo);
        //centroCostoService.creatOrUpdate(centroCosto);

        return new ResponseEntity<>("Se creo correctamente la unidad organzacional.",HttpStatus.OK);
    }

    //Edit Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @GetMapping("/edit/{id}")
    public @ResponseBody JsonResponse edit(@PathVariable(value = "id", required = true) int id){
        JsonResponse jsonResponse = new JsonResponse();
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(id);
        jsonResponse.setResult(uo);
        return jsonResponse;
    }

    //Update Unidad Organizacional
    //@PreAuthorize("hasAuthority('')")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestParam Map<String, String> allParams){

        if(allParams.get("unidadOrganizacional").isEmpty()){
            return new ResponseEntity<>("Campo requerido. Ingrese nombre de unidad.", HttpStatus.BAD_REQUEST);
        }

        //ACTUALIZACION DE UNIDAD ORGANIZACIONAL
        UnidadOrganizacional uo = unidadOrganizacionalService.getOneUnidadOrganizacional(Integer.parseInt(allParams.get("idUnidadOrganizacional")));
        uo.setUnidadOrganizacional(allParams.get("unidadOrganizacional"));
        int idUnidadPadre = Integer.parseInt(allParams.get("idUnidadPadre"));

        if(idUnidadPadre == -1){
            uo.setUnidadPadre(null);
        }else{
            uo.setUnidadPadre(unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidadPadre));
        }

        uo.setTipoUnidadOrganizacional(tipoUnidadOrganizacionalService.getOne(Integer.parseInt(allParams.get("idTipoUnidadOrganizacional"))));
        unidadOrganizacionalService.addOrUpdateUnidadOrganizaional(uo);

        return new ResponseEntity<>("Se actualizo la unidad organzacional.",HttpStatus.OK);
    }
}
