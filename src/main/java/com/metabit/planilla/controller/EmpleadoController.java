package com.metabit.planilla.controller;

import com.metabit.planilla.entity.*;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/planilla/empleado")
public class EmpleadoController {

    @Autowired
    @Qualifier("userJpaRepository")
    private UserJpaRepository userJpaRepository;

    @Autowired
    @Qualifier("empleadoServiceImpl")
    private EmpleadoService empleadoService;

    @Autowired
    @Qualifier("departamentoServiceImpl")
    private DepartamentoService departamentoService;

    @Autowired
    @Qualifier("generoServiceImpl")
    private GeneroService generoService;

    @Autowired
    @Qualifier("profesionServiceImpl")
    private ProfesionService  profesionService;

    @Autowired
    @Qualifier("tipoDocumentoServiceImpl")
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    @Qualifier("estadoCivilServiceImpl")
    private EstadoCivilService estadoCivilService;

    @Autowired
    @Qualifier("puestoServiceImpl")
    private PuestoService puestoService;

    @Autowired
    @Qualifier("municipioServiceImpl")
    private MunicipioService municipioService;

    @Autowired
    @Qualifier("direccionServiceImpl")
    private DireccionService direccionService;

    @Autowired
    @Qualifier("empleadoProfesionServiceImpl")
    private EmpleadoProfesionService empleadoProfesionService;

    @Autowired
    @Qualifier("empleadoDocumentoServiceImpl")
    private EmpleadoDocumentoService empleadoDocumentoService;

    private static final String INDEX_VIEW = "empleado/index";
    private static final String EDIT_VIEW = "empleado/edit";
    private static final String CREATE_VIEW = "empleado/create";

    private static final Log LOGGER = LogFactory.getLog(EmpleadoController.class);

    //List all employees
    //@PreAuthorize("hasAuthority('EMPLEADO_INDEX')")
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
        mav.addObject("empleado",new Empleado());
        mav.addObject("direccion",new Direccion());
        //mav.addObject("user",)
        mav.addObject("puestos",puestoService.getPuestos());
        mav.addObject("estadosCiviles",estadoCivilService.getAllCivilStates());
        mav.addObject("profesiones",profesionService.getProfesiones());
        mav.addObject("documentos",tipoDocumentoService.getTipoDocHabilitado());
        mav.addObject("generos",generoService.getAllGeneros());
        mav.addObject("municipios", departamentoService.getAllDepartamentos().get(0).getMunicipios());
        mav.addObject("departamentos", departamentoService.getAllDepartamentos());
        return mav;
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam(name="profesiones_seleccion[]",required = false,defaultValue= "0") List<Integer> profesiones, @RequestParam Map<String,String> allParams) throws ParseException {

        LOGGER.info(allParams);
        //VALIDACIONES
        Map<String,String> mensajes = validationEmptyFields(allParams,profesiones);

        //VALIDACION DE CAMPOS REQUERIDO DE DOCUMENTOS DE EMPLEADO
        List<TipoDocumento> tipoDocumentos=new ArrayList<>();
        List<String> datoDocumentos=new ArrayList<>();
        for (String key:allParams.keySet()){
            if(!allParams.get(key).isEmpty()&&key.split("_")[0].equals("documento")){
                tipoDocumentos.add(tipoDocumentoService.getTipoDocumento(Integer.parseInt(key.split("_")[1])));
                datoDocumentos.add(allParams.get(key));
            }
        }
        if(tipoDocumentos.size()==0){
            mensajes.put("error_sec2","Error en la seccion Documentos de Empleado. Debe llenar al menos un documento.");
        }

        //Controlando que errores de datos obligatorios sean resueltos
        if(mensajes.size()>0){
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //VALIDACION DE DOCUMENTOS DE EMPLEADO
        List<String> patronesTipoDocumentos=getPatternByTipoDocumento(tipoDocumentos);
        for (int i=0;i<datoDocumentos.size();i++){
            LOGGER.info("Patron"+patronesTipoDocumentos.get(i));
            Pattern patron=Pattern.compile(patronesTipoDocumentos.get(i));
            Matcher matcher=patron.matcher(datoDocumentos.get(i));
            //Validando si se cumple el patron
            if(!matcher.matches()){
                mensajes.put("error"+tipoDocumentos.get(i).getTipoDocumento(),"Error de formato en documento "+tipoDocumentos.get(i).getTipoDocumento());
            }
        }

        //Controlando que errores sean resueltos en su totalidad
        if(mensajes.size()>0){
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //DATOS NECESARIOS PARA REGISTRAR EMPLEADO
        Municipio municipio=municipioService.getMunicipio(Integer.parseInt(allParams.get("idMunicipio")));
        EstadoCivil estadoCivil=estadoCivilService.getCivilState(Integer.parseInt(allParams.get("idEstadoCivil")));

        Direccion direccion = new Direccion(
                allParams.get("urbanizacion"),
                allParams.get("calle"),
                allParams.get("numeroCasa"),
                allParams.get("complemento"),
                municipio
        );

        //REGISTRO DE DIRECCION
        direccionService.addDirection(direccion);

        Empleado empleado = new Empleado(allParams.get("codigo"),
                allParams.get("nombrePrimero"),
                allParams.get("nombreSegundo"),
                allParams.get("apellidoPaterno"),
                allParams.get("apellidoMaterno"),
                allParams.get("apellidoCasada"),
                new SimpleDateFormat("dd-MM-yyyy").parse(allParams.get("fechaNacimiento")),
                allParams.get("correoPersonal"),
                allParams.get("correoInstitucional"),
                Double.parseDouble(allParams.get("salarioBaseMensual")),
                Integer.parseInt(allParams.get("horasTrabajo")),
                false,
                true,
                null,
                null,
                estadoCivil,
                direccion,
                null
        );
        empleado=empleadoService.addEmployee(empleado);

        //REGISTRAR EMPLEADO_PROFESION
        for (Integer i:profesiones) {
            EmpleadoProfesion ep=new EmpleadoProfesion(
                    empleado,
                    profesionService.getProfesion(i)
            );
            empleadoProfesionService.createOrUpdateProfessionsEmployee(ep);
        }

        //REGISTRAR EMPLEADO_DOCUMENTO
        for (int i=0;i<tipoDocumentos.size();i++) {
            EmpleadoDocumento ed=new EmpleadoDocumento(
                    empleado,
                    tipoDocumentos.get(i),
                    datoDocumentos.get(i)
            );
            empleadoDocumentoService.createOrUpdateDocumentsEmployee(ed);
        }

        mensajes.put("success","Empleado Registrado correctamente");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    private List<String> getPatternByTipoDocumento(List<TipoDocumento> tipoDocumentos){
        //Base de construccion[A-Za-z]{2}[0-9]{3} -->Patron Carnet UES
        String letras="[A-Za-z]";
        String numeros="[0-9]";
        List<String> patronesTipoDocumentos=new ArrayList<>();

        //CONTRUCCION DE PATRONES POR TIPO DOCUMENTO
        for (int i=0;i<tipoDocumentos.size();i++){

            //Recuperamos el formato del tipo documento y lo dividimos por bloques usando el guion como separador
            String [] bloques=tipoDocumentos.get(i).getFormato().split("-");
            String patronTipo="";
            int contadorBloque=0;

            //Recorremos cada bloque de caracteres separados
            for (String bloque:bloques) {
                //Eliminado espacios en blancos en el bloque
                bloque.trim();

                int contadorLetras=0;
                int contadorNumeros=0;

                //Se recorre caracter por caracter en la subcadena
                for (Character c:bloque.toCharArray()) {
                    if(c.equals('a')){
                        if(contadorNumeros>0){
                            patronTipo+=numeros+"{"+contadorNumeros+"}";
                            contadorNumeros=0;
                        }
                        contadorLetras++;
                    }
                    if(c.equals('0')){
                        if(contadorLetras>0){
                            patronTipo+=letras+"{"+contadorLetras+"}";
                            contadorLetras=0;
                        }
                        contadorNumeros++;
                    }
                }
                if(contadorLetras==bloque.toCharArray().length||contadorLetras>0){
                    patronTipo+=letras+"{"+contadorLetras+"}";
                }
                if(contadorNumeros==bloque.toCharArray().length||contadorNumeros>0){
                    patronTipo+=numeros+"{"+contadorNumeros+"}";
                }

                contadorBloque++;
                if(contadorBloque<bloques.length){
                    //Agregamos un guion al salir de un bloque
                    patronTipo+="-";
                }
            }
            //Agregamos patron al listado
            patronesTipoDocumentos.add(patronTipo);
        }
        return patronesTipoDocumentos;
    }

    private Map<String,String> validationEmptyFields(Map<String,String> allParams,List<Integer>profesiones){
        //VALIDACION DE CAMPOS REQUERIDOS EN SECCION PERSONAL
        Map<String,String> mensajes = new HashMap<String,String>();
        if(allParams.get("nombrePrimero").isEmpty()|| allParams.get("nombreSegundo").isEmpty()||allParams.get("fechaNacimiento").isEmpty()){
            mensajes.put("error_sec1","Error en la seccion Informacion Personal. Llenar todos los campos requeridos.");
        }

        //VALIDACION DE CAMPOS REQUERIDOS PARA SECCION DIRECCION EMPLEADO
        if(allParams.get("urbanizacion").isEmpty()){
            mensajes.put("error_sec4","Error en la seccion Direccion de Empleado. Llenar todos los campos requeridos.");
        }

        //VALIDACION DE CAMPOS REQUERIDOS PARA SECCION INFORMACION PROFESIONAL
        if(allParams.get("codigo").isEmpty()||allParams.get("correoInstitucional").isEmpty()||allParams.get("salarioBaseMensual").isEmpty()||allParams.get("horasTrabajo").isEmpty()||profesiones.get(0)==0){
            mensajes.put("error_sec3","Error en la seccion Informacion Profesional. Llenar todos los campos requeridos.");
        }
        return mensajes;
    }

    //Form to edit employee by Id
    @GetMapping("/edit")
    public ModelAndView edit() {
        ModelAndView mav = new ModelAndView(EDIT_VIEW);
        return mav;
    }

    @PostMapping("/update")
    public String update() {
        return "";
    }

    @GetMapping("/status")
    public String disable(@RequestParam("id") int id) {
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