package com.metabit.planilla.controller;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/planilla/empleado")
public class EmpleadoController {

    @Autowired
    @Qualifier("userJpaRepository")
    private UserJpaRepository userJpaRepository;

    @Autowired
    @Qualifier("empleadoServiceImpl")
    private EmpleadoService empleadoService;

    private static final String INDEX_VIEW = "empleado/index";
    private static final String EDIT_VIEW = "empleado/edit";
    private static final String CREATE_VIEW = "empleado/create";

    //List all employees
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        mav.addObject("empleados",empleadoService.getAllEmployees());
        return mav;
    }

    //Form to create employee
    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView(CREATE_VIEW);
        return mav;
    }

    @PostMapping("/store")
    private String store() {
        return "";
    }

    //Form to edit employee by Id
    @GetMapping("/edit")
    public ModelAndView edit() {
        ModelAndView mav = new ModelAndView(EDIT_VIEW);
        return mav;
    }

    @PostMapping("/update")
    private String update() {
        return "";
    }

    @GetMapping("/status")
    private String disable(@RequestParam("id") int id) {
        Empleado e = empleadoService.findEmployeeById(id);
        String cadena="habilito";

        //FALTA HABILITAR O INHABILITAR USUARIO
        if(e.getEmpleadoHabilitado()){
            e.setEmpleadoHabilitado(false);
            cadena="deshabilito";
        }else{
            e.setEmpleadoHabilitado(true);
        }
        empleadoService.updateEmployee(e);
        return "redirect:/planilla/empleado/index";
    }
}
