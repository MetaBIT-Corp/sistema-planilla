package com.metabit.planilla.controller;

import com.metabit.planilla.domain.JsonResponse;
import com.metabit.planilla.entity.*;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.RolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.metabit.planilla.service.RangoComisionService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/rango-comision")
public class RangoComisionController {

	private static String INDEX_VIEW = "rango-comision/index";
	private static final Log LOGGER = LogFactory.getLog(RangoComisionController.class);
	
	@Autowired
	@Qualifier("rangoComisionServiceImpl")
	private RangoComisionService rangoComisionService;

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;

	@Autowired
	@Qualifier("rolServiceImpl")
	private RolService rolService;
	
	@GetMapping("/index")
	public String index(Model model, @RequestParam(name="delete_success", required=false) String delete_success) {
		
		//Recibiendo el posible param de Exito en la eliminacion
		model.addAttribute("delete_success", delete_success);
		
		//Agregando al modelo el listado de objetos
		model.addAttribute("rangosComision", rangoComisionService.getAllRangoComision());

		/*Agregado por Ricardo*/
		Boolean create = false;
		Boolean edit = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Usuario usuario = userJpaRepository.findByUsername(userDetail.getUsername());

		for (Rol rol : rolService.getUserRoles(usuario.getIdUsuario())) {
			for (RolRecursoPrivilegio rrp : rol.getRolesRecursosPrivilegios()) {
				if (rrp.getRecurso().getRecurso().equals("RANGO_COMISION") && rrp.getPrivilegio().getPrivilegio().equals("CREATE")) {
					create = true;
				}
				if (rrp.getRecurso().getRecurso().equals("RANGO_COMISION") && rrp.getPrivilegio().getPrivilegio().equals("EDIT")) {
					edit = true;
				}
			}
		}
		model.addAttribute("create",create);
		model.addAttribute("edit",edit);
		model.addAttribute("rango",new RangoComision());

		/*FIN*/

		//System.out.println("Recibiendo: " + rangoComisionService.getAllRangoComision().size());
		return INDEX_VIEW;
	}

	@GetMapping("/edit/{id}")
	public @ResponseBody
	JsonResponse edit(@PathVariable(value = "id", required = true) int id) {
		JsonResponse jsonResponse = new JsonResponse();
		RangoComision rangoComision = rangoComisionService.getOneRango(id);
		jsonResponse.setResult(rangoComision);
		return jsonResponse;
	}

	@PostMapping("/store")
	public ResponseEntity<?> store(@RequestParam Map<String, String> allParams) {

		if (allParams.get("ventaMin").equals("")||allParams.get("ventaMax").equals("")||allParams.get("tasaComision").equals("")) {
			return new ResponseEntity<>("Campos requeridos vacios.", HttpStatus.BAD_REQUEST);
		}
		float ventaMin = Float.parseFloat(allParams.get("ventaMin"));
		float ventaMax = Float.parseFloat(allParams.get("ventaMax"));
		float tasaComision = Float.parseFloat(allParams.get("tasaComision"));

		if (ventaMax<=0||ventaMin<=0||tasaComision<=0) {
			return new ResponseEntity<>("No se admiten valores negativos o iguales a cero.", HttpStatus.BAD_REQUEST);
		}else{
			if(tasaComision>1){
				return new ResponseEntity<>("La tasa comision debe estar entre 0.01 y 1", HttpStatus.BAD_REQUEST);
			}
		}
		if (ventaMax<ventaMin) {
			return new ResponseEntity<>("El monto de venta maxima no puede ser menor que el monto minimo.", HttpStatus.BAD_REQUEST);
		}

		Boolean traslape = false;
		for(RangoComision rc:rangoComisionService.getAllRangoComision()){

			if((ventaMin <= rc.getVentaMin() && ventaMin < rc.getVentaMax()) && (ventaMax > rc.getVentaMin() && ventaMax >= rc.getVentaMax())){
				traslape = true;
			}else{
				if((ventaMin >= rc.getVentaMin() && ventaMin <= rc.getVentaMax())||(ventaMax>= rc.getVentaMin() && ventaMax<= rc.getVentaMax())){
					traslape = true;
				}
			}
		}
		if(traslape) {
			return new ResponseEntity<>("El rango comision ingresado esta contenido por otro rango ya existente. (Rangos traslapados) ", HttpStatus.BAD_REQUEST);
		}
		RangoComision rangoComision;
		String mensaje;
		if(allParams.get("id").equals("0")){
			rangoComision = new RangoComision();
			mensaje="Se creo el rango comision correctamente";
		}else {
			rangoComision = rangoComisionService.getOneRango(Integer.parseInt(allParams.get("id")));
			mensaje="Se actualizo el rango comision correctamente";
		}
		rangoComision.setTasaComision(tasaComision);
		rangoComision.setVentaMax(ventaMax);
		rangoComision.setVentaMin(ventaMin);
		rangoComision.setHabilitado(Boolean.parseBoolean(allParams.get("habilitado")) ? true : false);
		rangoComisionService.createOrUpdate(rangoComision);
		return new ResponseEntity<>(mensaje, HttpStatus.OK);
	}
	
	@PostMapping("/destroy")
	public String destroy(@RequestParam("idRangoComisionDestroy") int idRangoComision) {
		
		rangoComisionService.deleteRangoComision(idRangoComision);
		
		return "redirect:/rango-comision/index?delete_success";
	}



}
