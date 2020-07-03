package com.metabit.planilla.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.metabit.planilla.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.DireccionService;
import com.metabit.planilla.service.EmailService;
import com.metabit.planilla.service.EmpleadoDocumentoService;
import com.metabit.planilla.service.EmpleadoProfesionService;
import com.metabit.planilla.service.EmpleadoService;
import com.metabit.planilla.service.EmpleadosPuestosUnidadesService;
import com.metabit.planilla.service.EstadoCivilService;
import com.metabit.planilla.service.GeneroService;
import com.metabit.planilla.service.MunicipioService;
import com.metabit.planilla.service.ProfesionService;
import com.metabit.planilla.service.PuestoService;
import com.metabit.planilla.service.RolService;
import com.metabit.planilla.service.TipoDocumentoService;
import com.metabit.planilla.service.UnidadOrganizacionalService;
import com.metabit.planilla.service.UsuarioService;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/empleado")
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
    private ProfesionService profesionService;

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

    @Autowired
    @Qualifier("unidadOrganizacionalServiceImpl")
    private UnidadOrganizacionalService unidadOrganizacionalService;

    @Autowired
    @Qualifier("empleadosPuestosUnidadesServiceImpl")
    private EmpleadosPuestosUnidadesService empleadosPuestosUnidadesService;

    @Autowired
	@Qualifier("rolServiceImpl")
	private RolService rolService;

    @Autowired
	@Qualifier("emailServiceImpl")
	private EmailService emailService;
    
    @Autowired
    @Qualifier("usuarioServiceImpl")
    private UsuarioService usuarioService;

    private static final String INDEX_VIEW = "empleado/index";
    private static final String EDIT_VIEW = "empleado/edit";
    private static final String CREATE_VIEW = "empleado/create";
    private static final String SHOW_VIEW = "empleado/show";
    private static final String EDIT_EMP_DOC = "empleado/edit_emp_docs";
    private static final String EDIT_EMP_PROF = "empleado/edit_emp_prof";

    private static final Log LOGGER = LogFactory.getLog(EmpleadoController.class);

    //List all employees
    @PreAuthorize("hasAuthority('EMPLEADO_INDEX')")
    @GetMapping("/index")
    public ModelAndView index(Model model,
                              @RequestParam(name = "lock_success", required = false) String lock_success,
                              @RequestParam(name = "unlock_success", required = false) String unlock_success,
                              @RequestParam(name = "enable", required = false) String enable,
                              @RequestParam(name = "create", required = false) String create,
                              @RequestParam(name = "edit", required = false) String edit) {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        mav.addObject("empleados", empleadoService.getAllEmployees());
        model.addAttribute("lock_success", lock_success);
        model.addAttribute("unlock_success", unlock_success);
        model.addAttribute("enable", enable);
        model.addAttribute("create", create);
        model.addAttribute("edit", edit);
        return mav;
    }

    //Form to create employee
    @PreAuthorize("hasAuthority('EMPLEADO_CREATE')")
    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView(CREATE_VIEW);
        mav.addObject("empleado", new Empleado());
        mav.addObject("direccion", new Direccion());
        mav.addObject("user", new Usuario());
        mav.addObject("puestos", puestoService.getPuestosEnable());
        mav.addObject("unidades", unidadOrganizacionalService.getAllUnidadesOrganizacionales());
        mav.addObject("estadosCiviles", estadoCivilService.getAllCivilStates());
        mav.addObject("profesiones", profesionService.getProfesionesEnable());
        mav.addObject("documentos", tipoDocumentoService.getTipoDocHabilitado());
        mav.addObject("generos", generoService.getAllGeneros());
        mav.addObject("municipios", departamentoService.getAllDepartamentos().get(0).getMunicipios());
        mav.addObject("departamentos", departamentoService.getAllDepartamentos());

        /*-------------------------CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
        mav.addObject("roles", rolService.getAllRoles());
        /*------------------FIN DEL CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/

        return mav;
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestParam(name = "profesiones_seleccion[]", required = false, defaultValue = "0") List<Integer> profesiones, @RequestParam Map<String, String> allParams, @RequestParam(name = "roles[]",required = false) List<Integer> roles) throws ParseException {
        //VALIDACIONES
        Map<String, String> mensajes = validationEmptyFields(allParams, profesiones);

        //VALIDACION DE CAMPOS REQUERIDO DE DOCUMENTOS DE EMPLEADO
        List<TipoDocumento> tipoDocumentos = new ArrayList<>();
        List<String> datoDocumentos = new ArrayList<>();
        for (String key : allParams.keySet()) {
            if (!allParams.get(key).isEmpty() && key.split("_")[0].equals("documento")) {
                tipoDocumentos.add(tipoDocumentoService.getTipoDocumento(Integer.parseInt(key.split("_")[1])));
                datoDocumentos.add(allParams.get(key));
            }
        }
        if (tipoDocumentos.size() == 0) {
            mensajes.put("error_sec2", "Error en la seccion Documentos de Empleado. Debe llenar al menos un documento.");
        }

        //Controlando que errores de datos obligatorios sean resueltos
        if (mensajes.size() > 0) {
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //VALIDACION DE DOCUMENTOS DE EMPLEADO
        List<String> patronesTipoDocumentos = getPatternByTipoDocumento(tipoDocumentos);
        for (int i = 0; i < datoDocumentos.size(); i++) {
            Pattern patron = Pattern.compile(patronesTipoDocumentos.get(i));
            Matcher matcher = patron.matcher(datoDocumentos.get(i));
            //Validando si se cumple el patron
            if (!matcher.matches()) {
                mensajes.put("error" + tipoDocumentos.get(i).getTipoDocumento(), "Error de formato en documento " + tipoDocumentos.get(i).getTipoDocumento());
            }
        }

        //Validacion de usuario campos requeridos
        Puesto puesto = puestoService.getPuesto(Integer.parseInt(allParams.get("idPuesto")));
        if (puesto.isUsuarioRequerido()) {
            if (allParams.get("username").isEmpty() || allParams.get("password").isEmpty()) {
                mensajes.put("error_user", "Error en la seccion Usuario de Empleado. Llenar campos requeridos.");
            }else{
                if(userJpaRepository.findByUsername(allParams.get("username"))!=null){
                    mensajes.put("error_user", "Error en la seccion Usuario de Empleado. Usuario ya EXISTE.");
                }
            }
        }

        //Controlando que errores sean resueltos en su totalidad
        if (mensajes.size() > 0) {
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //DATOS NECESARIOS PARA REGISTRAR EMPLEADO
        Municipio municipio = municipioService.getMunicipio(Integer.parseInt(allParams.get("idMunicipio")));
        EstadoCivil estadoCivil = estadoCivilService.getCivilState(Integer.parseInt(allParams.get("idEstadoCivil")));
        Genero genero = generoService.getGenero(Integer.parseInt(allParams.get("idGenero")));

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
                LocalDate.parse(allParams.get("fechaNacimiento")),
                allParams.get("correoPersonal"),
                allParams.get("correoInstitucional"),
                Double.parseDouble(allParams.get("salarioBaseMensual")),
                Integer.parseInt(allParams.get("horasTrabajo")),
                true,
                null,
                null,
                estadoCivil,
                direccion,
                null,
                genero,
                null
        );
        empleado = empleadoService.addEmployee(empleado);

        //REGISTRAR EMPLEADO_PROFESION
        for (Integer i : profesiones) {
            EmpleadoProfesion ep = new EmpleadoProfesion(
                    empleado,
                    profesionService.getProfesion(i)
            );
            empleadoProfesionService.createOrUpdateProfessionsEmployee(ep);
        }

        //REGISTRAR EMPLEADO_DOCUMENTO
        for (int i = 0; i < tipoDocumentos.size(); i++) {
            EmpleadoDocumento ed = new EmpleadoDocumento(
                    empleado,
                    tipoDocumentos.get(i),
                    datoDocumentos.get(i)
            );
            empleadoDocumentoService.createOrUpdateDocumentsEmployee(ed);
        }

        //Recuperando Unidad organizacional seleccionada
        UnidadOrganizacional unidadOrganizacional = unidadOrganizacionalService.getOneUnidadOrganizacional(Integer.parseInt(allParams.get("idUnidadOrganizacional")));

        //Creado Empleado puesto unidad
        EmpleadosPuestosUnidades epu = new EmpleadosPuestosUnidades(
                empleado,
                puesto,
                unidadOrganizacional
        );
        empleadosPuestosUnidadesService.createOrUpdate(epu);

        //Registro de usuario
        if (puesto.isUsuarioRequerido()) {
            BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
            Usuario usuario = new Usuario(
                    allParams.get("username"),
                    pe.encode(allParams.get("password")),
                    Boolean.parseBoolean(allParams.get("enabled")) ? false : true,
                    false,
                    false,
                    false,
                    0,
                    null
            );

            /*-------------------------CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
        	List<Rol> rolesList = new ArrayList<Rol>();
            if(roles != null) {
            	for (int idRol: roles) {
                	Rol rol = rolService.getByIdRol(idRol);
                	rolesList.add(rol);
                }
            }
            usuario.setRoles(rolesList);
            /*------------------FIN DEL CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/

            userJpaRepository.save(usuario);

            //Relacionando usuario con Empleado
            empleado.setUsuario(usuario);
            empleadoService.updateEmployee(empleado);
        }

        mensajes.put("success", "Empleado Registrado correctamente");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    //Form to edit employee by Id
    @PreAuthorize("hasAuthority('EMPLEADO_EDIT')")
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(EDIT_VIEW);
        Empleado e = empleadoService.findEmployeeById(id);

        /*-------------------------CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
        List<Rol> user_roles = new ArrayList();
        List<Rol> available_roles = new ArrayList();
        /*------------------FIN DEL CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/

        //Si tiene usuario cargarlo sino crear uno por si el puesto lo requiere
        if(e.getUsuario()!=null){
            mav.addObject("user",e.getUsuario());
            /*---------------------------------Codigo KIKE-------------------------------------*/
            user_roles = rolService.getUserRoles(e.getUsuario().getIdUsuario());
            available_roles = rolService.getAvailableRoles(e.getUsuario().getIdUsuario());
            mav.addObject("available_roles", available_roles);
            mav.addObject("user_roles", user_roles);
        }else{
            mav.addObject("user",new Usuario());
            mav.addObject("roles", rolService.getAllRoles());
        }

        mav.addObject("empleado", e);
        mav.addObject("direccion", e.getDireccion());
        mav.addObject("unidades", unidadOrganizacionalService.getAllUnidadesOrganizacionales());
        mav.addObject("puestos", puestoService.getPuestosEnable());
        mav.addObject("estadosCiviles", estadoCivilService.getAllCivilStates());
        mav.addObject("generos", generoService.getAllGeneros());
        mav.addObject("municipios", municipioService.getMunicipiosByDepartamento(e.getDireccion().getMunicipio().getDepartamento()));
        mav.addObject("departamentos", departamentoService.getAllDepartamentos());
        mav.addObject("epu",empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(e));

        return mav;
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestParam Map<String, String> allParams, @RequestParam(name = "roles[]", required = false) List<Integer> roles) {
        //VALIDACIONES
        Map<String, String> mensajes = validationEmptyFields(allParams, null);

        Puesto puesto = puestoService.getPuesto(Integer.parseInt(allParams.get("idPuesto")));
        Empleado empleado = empleadoService.findEmployeeById(Integer.parseInt(allParams.get("idEmpleado")));
        EmpleadosPuestosUnidades epu = empleadosPuestosUnidadesService.getByEmpleadoAndFechaFinIsNull(empleado);
        UnidadOrganizacional unit = unidadOrganizacionalService.getOneUnidadOrganizacional(Integer.parseInt(allParams.get("idUnidadOrganizacional")));

        //Verificamos si es jefe de una unidad
        if(empleado.equals(epu.getUnidadOrganizacional().getEmpleadoJefe())&&!unit.equals(epu.getUnidadOrganizacional())){
            mensajes.put("error_jefe", "ERROR. El empleado actualmente es JEFE DE UNIDAD, para poder cambiarlo de unidad organizacional debe de asignar un nuevo jefe y luego realizr el cambio.");
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //Validacion de usuario campos requeridos por si no posee usuario
        if(puesto.isUsuarioRequerido() && empleado.getUsuario() == null) {
            if (allParams.get("username").isEmpty() || allParams.get("password").isEmpty()) {
                mensajes.put("error_user", "Error en la seccion Usuario de Empleado. Llenar campos requeridos.");
            }else{
                if(userJpaRepository.findByUsername(allParams.get("username"))!=null){
                    mensajes.put("error_user", "Error en la seccion Usuario de Empleado. Usuario ya EXISTE.");
                }
            }
        }

        //Controlando que errores de datos obligatorios sean resueltos
        if (mensajes.size() > 0) {
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }
        //DATOS NECESARIOS PARA REGISTRAR EMPLEADO
        Municipio municipio = municipioService.getMunicipio(Integer.parseInt(allParams.get("idMunicipio")));
        EstadoCivil estadoCivil = estadoCivilService.getCivilState(Integer.parseInt(allParams.get("idEstadoCivil")));
        Genero genero = generoService.getGenero(Integer.parseInt(allParams.get("idGenero")));

        empleado.setCodigo(allParams.get("codigo"));
        empleado.setNombrePrimero(allParams.get("nombrePrimero"));
        empleado.setNombreSegundo(allParams.get("nombreSegundo"));
        empleado.setApellidoMaterno(allParams.get("apellidoMaterno"));
        empleado.setApellidoPaterno(allParams.get("apellidoPaterno"));
        empleado.setApellidoCasada(allParams.get("apellidoCasada"));
        empleado.setFechaNacimiento(LocalDate.parse(allParams.get("fechaNacimiento")));
        empleado.setCorreoPersonal(allParams.get("correoPersonal"));
        empleado.setCorreoInstitucional(allParams.get("correoInstitucional"));
        empleado.setSalarioBaseMensual(Double.parseDouble(allParams.get("salarioBaseMensual")));
        empleado.setHorasTrabajo(Integer.parseInt(allParams.get("horasTrabajo")));
        empleado.setEstadoCivil(estadoCivil);
        empleado.setGenero(genero);

        //EDITANDO DIRECCION
        Direccion direccion = empleado.getDireccion();
        direccion.setUrbanizacion(allParams.get("urbanizacion"));
        direccion.setCalle(allParams.get("calle"));
        direccion.setNumeroCasa(allParams.get("numeroCasa"));
        direccion.setComplemento(allParams.get("complemento"));
        direccion.setMunicipio(municipio);

        //ACTUALIZANDO DE DIRECCION
        empleado.setDireccion(direccion);

        //ACTUALIZANDO PUESTO UNIDAD EMPLEADO
        if(puesto != epu.getPuesto() || epu.getUnidadOrganizacional() != unit){
            EmpleadosPuestosUnidades epuNew = new EmpleadosPuestosUnidades(
                    empleado,
                    puesto,
                    unit
            );
            empleadosPuestosUnidadesService.createOrUpdate(epuNew);
        }

        //Actualizando usuario
        if(puesto.isUsuarioRequerido()){
        	/*-------------------------CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
        	List<Rol> rolesList = new ArrayList<Rol>();
            if(roles != null) {
            	for (int idRol: roles) {
                	Rol rol = rolService.getByIdRol(idRol);
                	rolesList.add(rol);
                }
            }
            /*------------------FIN DEL CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
            
            if(empleado.getUsuario()==null){
                BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
                Usuario usuario = new Usuario(
                        allParams.get("username"),
                        pe.encode(allParams.get("password")),
                        Boolean.parseBoolean(allParams.get("enabled")) ? false : true,
                        false,
                        false,
                        false,
                        0,
                        null
                );
                
                usuario.setRoles(rolesList);
                userJpaRepository.save(usuario);
                empleado.setUsuario(usuario);
            }else{
                empleado.getUsuario().setEnabled(Boolean.parseBoolean(allParams.get("enabled")) ? true : false);
                
                /*-------------------------CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
            	Usuario usuario = empleado.getUsuario();
                usuario.setRoles(rolesList);
                userJpaRepository.save(usuario);
                /*------------------FIN DEL CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
            }
        }

        //Si el empleado tien ya un usuario y lo cambian a un puesto sin usuario requerido solo se deshabilita
        if(!puesto.isUsuarioRequerido()&&empleado.getUsuario()!=null){
        	/*-------------------------CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
        	List<Rol> rolesList = new ArrayList<Rol>();
        	Usuario usuario = empleado.getUsuario();
        	usuario.setRoles(rolesList);
        	userJpaRepository.save(usuario);
        	/*------------------FIN DEL CÓDIGO PARA AGREGAR ROLES AL EMPLEADO-------------------*/
        	empleado.getUsuario().setEnabled(false);
        }

        empleadoService.updateEmployee(empleado);

        mensajes.put("success", "Editado Registrado correctamente");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    @GetMapping("/edit-documentos/{id}")
    public ModelAndView editDocumentos(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(EDIT_EMP_DOC);
        Empleado e = empleadoService.findEmployeeById(id);

        //List de documentos que pueden ser agregados al empleado
        List<TipoDocumento> documentos = tipoDocumentoService.getTipoDocHabilitado();
        List<EmpleadoDocumento> empleadoDocumentos = e.getDocumentosEmpleado();
        for (EmpleadoDocumento ed : empleadoDocumentos) {
            documentos.remove(ed.getTipoDocumento());
        }
        mav.addObject("empleado", e);
        mav.addObject("documentos_empleado", empleadoDocumentos);
        mav.addObject("documentos", documentos);
        return mav;
    }

    @PostMapping("/update-documentos")
    public ResponseEntity<?> updateDocumentos(@RequestParam Map<String, String> allParams) {
        Map<String, String> mensajes = new HashMap<String, String>();

        //VALIDACION DE CAMPOS REQUERIDO DE DOCUMENTOS DE EMPLEADO
        List<EmpleadoDocumento> empDoc = new ArrayList<>();
        List<String> datoDocumentos = new ArrayList<>();

        for (String key : allParams.keySet()) {
            if (!allParams.get(key).isEmpty() && key.split("_")[0].equals("empDoc")) {
                empDoc.add(empleadoDocumentoService.getDocumentEmployee(Integer.parseInt(key.split("_")[1])));
                datoDocumentos.add(allParams.get(key));
            }
        }

        if (empDoc.size() == 0) {
            mensajes.put("error_sec2", "El codigo del documento esta vacio.");
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //VALIDACION DE DOCUMENTOS DE EMPLEADO
        List<String> patronesTipoDocumentos = getPatternByTipoDocumento(Arrays.asList(empDoc.get(0).getTipoDocumento()));

        Pattern patron = Pattern.compile(patronesTipoDocumentos.get(0));
        Matcher matcher = patron.matcher(datoDocumentos.get(0));
        //Validando si se cumple el patron
        if (!matcher.matches()) {
            mensajes.put("error" + empDoc.get(0).getTipoDocumento().getTipoDocumento(), "Error de formato en documento " + empDoc.get(0).getTipoDocumento().getTipoDocumento());
        }

        //Controlando que errores sean resueltos en su totalidad
        if (mensajes.size() > 0) {
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        EmpleadoDocumento empleadoDocumento = empDoc.get(0);
        empleadoDocumento.setCodigoDocumento(datoDocumentos.get(0));

        //Guardando Cambios
        empleadoDocumentoService.createOrUpdateDocumentsEmployee(empleadoDocumento);
        mensajes.put("success", "Se actualizo el documento correctamente");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    @PostMapping("/delete-documentos")
    public ResponseEntity<?> deleteDocumentos(@RequestParam(name = "id") int id) {
        Map<String, String> mensajes = new HashMap<String, String>();
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        empleadoDocumentoService.deleteDocumentEmployee(id);
        mensajes.put("success", "Se elimino el documento correctamente");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    @PostMapping("/add-documentos")
    public ResponseEntity<?> addDocumentos(@RequestParam(name = "idEmpleado") int idEmpleado, @RequestParam Map<String, String> allParams) {
        //VALIDACIONES
        Map<String, String> mensajes = new HashMap<String, String>();

        //VALIDACION DE CAMPOS REQUERIDO DE DOCUMENTOS DE EMPLEADO
        List<TipoDocumento> tipoDocumentos = new ArrayList<>();
        List<String> datoDocumentos = new ArrayList<>();
        for (String key : allParams.keySet()) {
            if (!allParams.get(key).isEmpty() && key.split("_")[0].equals("documento")) {
                tipoDocumentos.add(tipoDocumentoService.getTipoDocumento(Integer.parseInt(key.split("_")[1])));
                datoDocumentos.add(allParams.get(key));
            }
        }

        if (tipoDocumentos.size() == 0) {
            mensajes.put("success", "Ningun documentos agregado");
            return new ResponseEntity<>(mensajes, HttpStatus.OK);
        }

        //VALIDACION DE DOCUMENTOS DE EMPLEADO
        List<String> patronesTipoDocumentos = getPatternByTipoDocumento(tipoDocumentos);
        for (int i = 0; i < datoDocumentos.size(); i++) {
            Pattern patron = Pattern.compile(patronesTipoDocumentos.get(i));
            Matcher matcher = patron.matcher(datoDocumentos.get(i));
            //Validando si se cumple el patron
            if (!matcher.matches()) {
                mensajes.put("error" + tipoDocumentos.get(i).getTipoDocumento(), "Error de formato en documento " + tipoDocumentos.get(i).getTipoDocumento());
            }
        }

        //Controlando que errores sean resueltos en su totalidad
        if (mensajes.size() > 0) {
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        Empleado empleado = empleadoService.findEmployeeById(idEmpleado);

        //REGISTRAR EMPLEADO_DOCUMENTO
        for (int i = 0; i < tipoDocumentos.size(); i++) {
            EmpleadoDocumento ed = new EmpleadoDocumento(
                    empleado,
                    tipoDocumentos.get(i),
                    datoDocumentos.get(i)
            );
            empleadoDocumentoService.createOrUpdateDocumentsEmployee(ed);
        }

        mensajes.put("success", "Documentos agregados correctamente");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    @GetMapping("/edit-profesiones/{id}")
    public ModelAndView editProfesiones(@PathVariable(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView(EDIT_EMP_PROF);
        Empleado e = empleadoService.findEmployeeById(id);

        //List de profesiones que pueden ser agregados al empleado
        List<Profesion> profesiones = profesionService.getProfesionesEnable();
        List<EmpleadoProfesion> empleadoProfesiones = e.getProfesionesEmpleado();
        int contador = 0;
        for (EmpleadoProfesion ep : empleadoProfesiones) {
            profesiones.remove(ep.getProfesion());
        }
        mav.addObject("empleado", e);
        mav.addObject("profesiones_empleado", empleadoProfesiones);
        mav.addObject("profesiones", profesiones);
        return mav;
    }

    @PostMapping("/delete-profesiones")
    public ResponseEntity<?> deleteProfesiones(@RequestParam(name = "profesiones_seleccion[]", required = false, defaultValue = "0") List<Integer> profesiones, @RequestParam(name = "idEmpleado") int idEmpleado) {
        //VALIDACIONES
        Map<String, String> mensajes = new HashMap<String, String>();
        if (profesiones.size() == 1 && profesiones.get(0) == 0) {
            mensajes.put("success", "No se elimino ninguna profesion/oficio.");
            return new ResponseEntity<>(mensajes, HttpStatus.OK);
        }

        //VALIDANDO QUE NO ELIMINE TODAS LAS PROFESIONES DEL EMPLEADO
        Empleado e = empleadoService.findEmployeeById(idEmpleado);
        if (profesiones.size() == e.getProfesionesEmpleado().size()) {
            mensajes.put("error", "No se pueden eliminar todos, al menos debe de quedar una profesion/oficio.");
            return new ResponseEntity<>(mensajes, HttpStatus.BAD_REQUEST);
        }

        //ELIMINAR EMPLEADO_PROFESION
        for (Integer i : profesiones) {
            empleadoProfesionService.deleteProfesionEmpleado(i);
        }
        mensajes.put("success", "Se ha eliminado correctamente.");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    @PostMapping("/add-profesiones")
    public ResponseEntity<?> addProfesiones(@RequestParam(name = "profesiones_seleccion[]", required = false, defaultValue = "0") List<Integer> profesiones, @RequestParam(name = "idEmpleado") int idEmpleado) {
        //VALIDACIONES
        Map<String, String> mensajes = new HashMap<String, String>();
        if (profesiones.size() == 1) {
            mensajes.put("success", "No se agrego ninguna profesion/oficio.");
            return new ResponseEntity<>(mensajes, HttpStatus.OK);
        }

        Empleado e = empleadoService.findEmployeeById(idEmpleado);

        //AGREGAR EMPLEADO_PROFESION
        for (Integer i : profesiones) {
            EmpleadoProfesion ep = new EmpleadoProfesion(
                    e,
                    profesionService.getProfesion(i)
            );
            empleadoProfesionService.createOrUpdateProfessionsEmployee(ep);
        }
        mensajes.put("success", "Se ha agregaron correctamente.");
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    private List<String> getPatternByTipoDocumento(List<TipoDocumento> tipoDocumentos) {
        //Base de construccion[A-Za-z]{2}[0-9]{3} -->Patron Carnet UES
        String letras = "[A-Za-z]";
        String numeros = "[0-9]";
        List<String> patronesTipoDocumentos = new ArrayList<>();

        //CONTRUCCION DE PATRONES POR TIPO DOCUMENTO
        for (int i = 0; i < tipoDocumentos.size(); i++) {

            //Recuperamos el formato del tipo documento y lo dividimos por bloques usando el guion como separador
            String[] bloques = tipoDocumentos.get(i).getFormato().split("-");
            String patronTipo = "";
            int contadorBloque = 0;

            //Recorremos cada bloque de caracteres separados
            for (String bloque : bloques) {
                //Eliminado espacios en blancos en el bloque
                bloque.trim();

                int contadorLetras = 0;
                int contadorNumeros = 0;

                //Se recorre caracter por caracter en la subcadena
                for (Character c : bloque.toCharArray()) {
                    if (c.equals('a')) {
                        if (contadorNumeros > 0) {
                            patronTipo += numeros + "{" + contadorNumeros + "}";
                            contadorNumeros = 0;
                        }
                        contadorLetras++;
                    }
                    if (c.equals('0')) {
                        if (contadorLetras > 0) {
                            patronTipo += letras + "{" + contadorLetras + "}";
                            contadorLetras = 0;
                        }
                        contadorNumeros++;
                    }
                }
                if (contadorLetras == bloque.toCharArray().length || contadorLetras > 0) {
                    patronTipo += letras + "{" + contadorLetras + "}";
                }
                if (contadorNumeros == bloque.toCharArray().length || contadorNumeros > 0) {
                    patronTipo += numeros + "{" + contadorNumeros + "}";
                }

                contadorBloque++;
                if (contadorBloque < bloques.length) {
                    //Agregamos un guion al salir de un bloque
                    patronTipo += "-";
                }
            }
            //Agregamos patron al listado
            patronesTipoDocumentos.add(patronTipo);
        }
        return patronesTipoDocumentos;
    }

    private Map<String, String> validationEmptyFields(Map<String, String> allParams, List<Integer> profesiones) {
        //VALIDACION DE CAMPOS REQUERIDOS EN SECCION PERSONAL
        Map<String, String> mensajes = new HashMap<String, String>();
        if (allParams.get("nombrePrimero").isEmpty()  || allParams.get("fechaNacimiento").isEmpty() || allParams.get("apellidoMaterno").isEmpty()) {
            mensajes.put("error_sec1", "Error en la seccion Informacion Personal. Llenar todos los campos requeridos.");
        }else{
            //Fecha de nacimiento
            if (LocalDate.parse(allParams.get("fechaNacimiento")).isAfter(LocalDate.now())) {
                mensajes.put("error_fecha_mayor", "Error en fecha de nacimiento. La fecha debe de ser menor a la actual.");
            } else {
                if ((LocalDate.now().getYear() - LocalDate.parse(allParams.get("fechaNacimiento")).getYear()) < 18) {
                    mensajes.put("error_fecha_nacimiento", "Error en fecha de nacimiento.El empleado debe de ser MAYOR DE EDAD.");
                }
            }
        }

        //VALIDACION DE CAMPOS REQUERIDOS PARA SECCION DIRECCION EMPLEADO
        if (allParams.get("urbanizacion").isEmpty()) {
            mensajes.put("error_sec4", "Error en la seccion Direccion de Empleado. Llenar todos los campos requeridos.");
        }

        if (profesiones != null) {
            //VALIDACION DE CAMPOS REQUERIDOS PARA SECCION INFORMACION PROFESIONAL
            if (allParams.get("codigo").isEmpty() || allParams.get("correoPersonal").isEmpty()|| allParams.get("correoInstitucional").isEmpty() || allParams.get("salarioBaseMensual").isEmpty() || allParams.get("horasTrabajo").isEmpty() || profesiones.get(0) == 0) {
                mensajes.put("error_sec3", "Error en la seccion Informacion Profesional. Llenar todos los campos requeridos.");
            }
            //Otras validaciones
            mensajes = validacionUniqueAndOthers(allParams, mensajes, 0);
        } else {
            //VALIDACION DE CAMPOS REQUERIDOS PARA SECCION INFORMACION PROFESIONAL
            if (allParams.get("codigo").isEmpty() || allParams.get("correoPersonal").isEmpty() || allParams.get("correoInstitucional").isEmpty() || allParams.get("salarioBaseMensual").isEmpty() || allParams.get("horasTrabajo").isEmpty()) {
                mensajes.put("error_sec3", "Error en la seccion Informacion Profesional. Llenar todos los campos requeridos.");
            } else {
                mensajes = validacionUniqueAndOthers(allParams, mensajes, Integer.parseInt(allParams.get("idEmpleado")));
            }
        }
        return mensajes;
    }

    private Map<String, String> validacionUniqueAndOthers(Map<String, String> allParams, Map<String, String> mensajes, int idEmp) {
        if (empleadoService.existEmployeeCode(allParams.get("codigo"), idEmp)) {
            mensajes.put("error_unique_code", "Codigo de empleado ya existe. Debe de ser unico.");
        }
        Pattern pat1 = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Pattern pat2 = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@metabit.tech.sv");
        Matcher matcher1 = pat1.matcher(allParams.get("correoPersonal"));
        Matcher matcher2 = pat2.matcher(allParams.get("correoInstitucional"));

        //Validacion de correos en registro
        if (!matcher2.matches()) {
            mensajes.put("error_correo_ins", "Correo institucional, mal formado (todo en minusculas). Dominio debe ser @metabit.tech.sv");
        } else {
            if (empleadoService.existInstitucionalEmail(allParams.get("correoInstitucional"), idEmp)) {
                mensajes.put("error_unique_email_inst", "Correo institucional ya existe. Debe de ser unico.");
            }
        }
        if (!allParams.get("correoPersonal").isEmpty() && !matcher1.matches()) {
            mensajes.put("error_correo_per", "Correo personal, mal formado (todo en minusculas).Verifique.");
        } else {
            if (empleadoService.existPersonalEmail(allParams.get("correoPersonal"), idEmp)) {
                mensajes.put("error_unique_email_pers", "Correo personal ya existe. Debe de ser unico.");
            }
        }
        if (Double.parseDouble(allParams.get("salarioBaseMensual")) < 300) {
            mensajes.put("error_min_salary", "El salario debe de ser mayor al minimo ( $300.00 ).");
        }
        if (Double.parseDouble(allParams.get("horasTrabajo")) < 1) {
            mensajes.put("error_horas", "Horas de trabajo deben de ser mayor que cero.");
        }

        return mensajes;
    }

    @GetMapping("/status")
    public String disable(@RequestParam("id") int id) {
        Empleado e = empleadoService.findEmployeeById(id);
        String mensaje = "false";

        if (e.getEmpleadoHabilitado()) {
            e.setEmpleadoHabilitado(false);

            if (e.getUsuario() != null) {
                Usuario usuario = e.getUsuario();
                usuario.setEnabled(false);
                userJpaRepository.save(usuario);
            }

        } else {
            e.setEmpleadoHabilitado(true);
            mensaje = "true";
        }
        empleadoService.updateEmployee(e);
        return "redirect:/empleado/index?enable="+mensaje;
    }

    @PreAuthorize("hasAuthority('EMPLEADO_SHOW')")
    @GetMapping("/show")
    public String show(Model model, @RequestParam(value = "id", required = true) int id) {
        Empleado e = empleadoService.findEmployeeById(id);
        model.addAttribute("documents", empleadoDocumentoService.findByEmpleado(e));
        model.addAttribute("professions", empleadoProfesionService.getAllProfessionsEmployee(e));
        model.addAttribute("empleado", e);
        return SHOW_VIEW;
    }


    /**
     * Metodo que permite bloquear el usuario del empleado seleccionado
     *
     * @param id: id del empleado
     * @return String
     * @author Edwin Palacios
     */

    @PostMapping("/lock-user")
    public String lockUser(@RequestParam("idEmpleado") int id) {
        Empleado empleado = empleadoService.findEmployeeById(id);
        if (empleado.getUsuario() != null) {
            Usuario usuario = empleado.getUsuario();
            usuario.setEnabled(false);
            userJpaRepository.save(usuario);
        } else {
            return "redirect:/empleado/index?lock_success=false";
        }
        return "redirect:/empleado/index?lock_success=true";
    }

    /**
     * Metodo que permite desbloquear el usuario del empleado seleccionado
     *
     * @param id: id del empleado
     * @return String
     * @author Edwin Palacios
     */

    @PostMapping("/unlock-user")
    public String unlockUser(@RequestParam("idEmpleado") int id) {
        Empleado empleado = empleadoService.findEmployeeById(id);
        if (empleado.getUsuario() != null) {
            Usuario usuario = empleado.getUsuario();
            usuario.setEnabled(true);
            usuario.setIntentos(0);
            userJpaRepository.save(usuario);
        } else {
            return "redirect:/empleado/index?unlock_success=false";
        }
        return "redirect:/empleado/index?unlock_success=true";
    }
    
    @PostMapping("/send-unlock-email")
    public String sendUnlockEmail(HttpServletRequest request, @RequestParam("username") String username, RedirectAttributes redirAttrs) {
    	
    	Email email = new Email();
        Map<String, Object> model = new HashMap<>();
    	
    	HttpSession session = request.getSession(true);
    	session.setAttribute("user_attemps", 0);
    	
        Usuario usuario = userJpaRepository.findByUsername(username);
        Empleado empleado = empleadoService.findByUsuario(usuario);
        
        if(usuario != null) {
        	List<Usuario> admin_users = usuarioService.getAdminUsers();
        	
        	String email_personal = empleado.getCorreoPersonal();
            String email_institucional = empleado.getCorreoInstitucional();
            String nombre = empleado.getNombrePrimero() + " " +
            				empleado.getNombreSegundo() + " " +
            				empleado.getApellidoPaterno() + " " +
            				empleado.getApellidoMaterno();
            
            
        	String  url_base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); 
            String 	url_unlock = url_base + "/empleado/unlock-user";
        	
        	model.put("email_per", email_personal);
        	model.put("email_ins", email_institucional);
        	model.put("nombre", nombre);
        	model.put("username", username);
        	model.put("id",empleado.getIdEmpleado());
    		model.put("url_unlock", url_unlock);
    		model.put("url_base", url_base);

        	for(Usuario user : admin_users) {
        		Empleado e = empleadoService.findByUsuario(user);
        		email.setFrom("metabitCorp@gmail.com");
                email.setTo(e.getCorreoPersonal());
                email.setSubject("Desbloqueo de usario");
                
                email.setModel(model);
                emailService.sendEmail(email);
        	}
        	
        	redirAttrs.addFlashAttribute("email_success", "success");
        }
        
        return "redirect:/login";
        
    }
}
