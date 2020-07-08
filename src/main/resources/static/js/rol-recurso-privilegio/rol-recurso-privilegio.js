$(document).ready(function(){

    $('#errorDiv').hide();

    /* Manejador de Eventos en seleccionar todos los Privilegios*/
    $('#checkTodos').change(function() {

        var checkboxes = $(this).closest('form').find(':checkbox').not($(this));
        checkboxes.prop('checked', $(this).is(':checked'));
        var input = $("#idsPrivilegiosAsignacionInput");

        if($(this).is(':checked')){
            $("#lblCheckTodos").text("Desmarcar todos los Privilegios")
        }else{
            $("#lblCheckTodos").text("Marcar todos los Privilegios")
        }



        input.val("");

        $('.checkboxPrivilegio').each(function(i, obj) {
            if($(this).is(":checked")){
                input.val(input.val()+$(this).data("idPrivilegio")+"|");
            }else{}
        });
    });

    /* Manejadores de eventos para asignar Recursos a un Rol */

    $('#rolRecursoAsignarModal').on("show.bs.modal", function(event) {

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data("id-rol");
        var rol = link.data("rol");

        modal.find(".modal-header #rolRecursoAsignarModalTitle").append(
            "Asignar Recurso a Rol: "+ rol.toString()
        );

        modal.find("#rolRecursoAsignarForm #idRolAsignacionInput").val(idRol);

        $.ajax({

            type : "GET",
            url : "/planilla/rol-recurso-privilegio/rol-recursos/"+idRol,

            success : function(data) {

                data = $.parseJSON(data);
                var i_no_asignados = 0;
                var combobox = document.getElementById("idRecursoAsignacionInput");

                for (i=0;i<data.length;i++){

                    if(data[i].estado=="0"){
                        var option = document.createElement("option");
                        option.setAttribute("value", data[i].idRecurso);
                        option.setAttribute("label", data[i].recurso);
                        combobox.appendChild(option);
                    }

                }

            }

        });

    });

    $("#rolRecursoAsignarModal").on("hide.bs.modal", function(){
        $("#idRecursoAsignacionInput").val("");
        $("#rolRecursoAsignarModalTitle").html("");
    });

    $(".checkboxPrivilegio").change(function () {

        var input = $("#idsPrivilegiosAsignacionInput");

        input.val("");

        $('.checkboxPrivilegio').each(function(i, obj) {
            if($(this).is(":checked")){
                input.val(input.val()+$(this).data("idPrivilegio")+"|");
            }else{}
        });

    });

    $("#rolRecursoAsignarSubmitBtn").on("click",function (e) {

        e.preventDefault();
        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({
            type: "POST",
            url: url,
            data: form.serialize(),
            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#rolRecursoAsignarSubmitBtn').attr('disabled',true);
                    $('#errorDiv').hide();

                    window.location.href = document.location.origin + "/planilla/rol-recurso-privilegio/index/"+response.result.idRol+"?store_success=true";

                }else{

                    $('#errorDiv').show();
                    $("#errorDiv").attr("class","callout callout-danger")
                    var child = document.getElementById("errorUl").lastElementChild;

                    while (child) {
                        document.getElementById("errorUl").removeChild(child);
                        child = document.getElementById("errorUl").lastElementChild;
                    }

                    var li = document.createElement('li');
                    var contentLi = document.createTextNode("Debe seleccionar un recurso y asignar uno o más privilegios");
                    li.appendChild(contentLi);
                    document.getElementById("errorUl").appendChild(li);
                }

            },
        });

    });

    /* Manejadores de eventos para asignar/eliminar Privilegios a un Rol sobre un Recurso */

    /* Evento al mostrar el modal para asignar/eliminar Privilegios */
    $('#rolRecursoPrivilegiosModal').on("show.bs.modal", function(event){

        var modal = $(this);
        var link = $(event.relatedTarget);

        // Obteniendo data definida en el botón que dispara evento del mostrar modal
        var idRol = link.data("id-rol");
        var rol = link.data("rol");
        var idRecurso = link.data("id-recurso");
        var recurso = link.data("recurso");

        // Colocando el mensaje en el header del modal
        modal.find(".modal-header #rolRecursoPrivilegiosModalTitle").append(
            "<b>Privilegios</b> de rol <b>"+ rol.toString()+"</b> sobre recurso <b>"+ recurso.toString().charAt(0).toUpperCase()+recurso.toString().slice(1).toLowerCase()+"</b>"
        );

        //AJAX para recuperar y colocar los privilegios en tablas del modal
        $.ajax({

            type: "GET",
            url: "/planilla/rol-recurso-privilegio/recurso-privilegios/"+idRol+"/"+idRecurso,

            //Si petición es exitosa
            success: function (response) {

                response = $.parseJSON(response);

                var iAsignados = 0;
                var iNoAsignados = 0;

                // Iteración para recorrer respuesta obtenida y colocar elementos en tabla e inputs
                for (i=0;i<response.length;i++){

                    /* Si elemento tiene como estado "1", colocar en tabla de asignados
                     * De lo contrario colocarlo en tabla de no asignados
                     */
                    if(response[i].estado=="1"){

                        //Obteniendo tabla de asignados
                        var tabla = document.getElementById("privilegiosAsignadosTable");
                        var fila = tabla.insertRow(iAsignados);

                        // Colocando en primer celda el nombre del privilegio
                        var celda1 = fila.insertCell(0);
                        celda1.innerHTML = response[i].privilegio;

                        // Colocando en segunda celda el botón para eliminarlo
                        var celda2 = fila.insertCell(1);

                        var btn = document.createElement("button");
                        btn.id = "eliminarBtn"+i;   // Identificador de botón.
                        btn.setAttribute("class", "btn btn-danger btn-sm cambiarClassBtn");
                        btn.dataset["idRol"] = idRol.toString();  // Colocando data con ID de Rol
                        btn.dataset["idRecurso"] = idRecurso.toString();  // Colocando data con ID de Recurso
                        btn.dataset["idPrivilegio"] = response[i].idPrivilegio.toString();  // Colocando data con ID de Privilegio
                        btn.dataset["estado"] = "1";

                        // Ícono y texto de botón
                        var ic = document.createElement("i");
                        ic.setAttribute("class","fas fa-times");
                        btn.appendChild(ic);
                        btn.append(" Eliminar");
                        celda2.appendChild(btn);

                        iAsignados++;

                    }else{

                        //Obteniendo tabla de asignados
                        var tabla = document.getElementById("privilegiosNoAsignadosTable");
                        var fila = tabla.insertRow(iNoAsignados);

                        // Colocando en primer celda el nombre del privilegio
                        var celda1 = fila.insertCell(0);
                        celda1.innerHTML = response[i].privilegio;

                        // Colocando en segunda celda el botón para asignarlo
                        var celda2 = fila.insertCell(1);

                        var btn = document.createElement("button");
                        btn.id = "asignarBtn"+i;    // Identificador de botón.
                        btn.setAttribute("class", "btn btn-info btn-sm cambiarClassBtn");
                        btn.dataset["idRol"] = idRol.toString();  // Colocando data con ID de Rol
                        btn.dataset["idRecurso"] = idRecurso.toString();  // Colocando data con ID de Recurso
                        btn.dataset["idPrivilegio"] = response[i].idPrivilegio.toString();  // Colocando data con ID de Privilegio
                        btn.dataset["estado"] = "0";

                        // Ícono y texto de botón
                        var ic = document.createElement("i");
                        ic.setAttribute("class","fas fa-check");
                        btn.appendChild(ic);
                        btn.append(" Asignar");
                        celda2.appendChild(btn);

                        iNoAsignados++;

                    }

                }

                var asignadosTabla = document.getElementById("privilegiosAsignadosTable");
                var asignadosHeader = asignadosTabla.createTHead();
                var asignadosFila = asignadosHeader.insertRow(0);
                var asignadosCelda1= asignadosFila.insertCell(0).innerHTML="<b>Nombres</b>";
                var asignadosCelda2= asignadosFila.insertCell(1).innerHTML="<b>Acciones</b>";

                var noAsignadosTabla = document.getElementById("privilegiosNoAsignadosTable");
                var noAsignadosHeader = noAsignadosTabla.createTHead();
                var noAsignadosFila = noAsignadosHeader.insertRow(0);
                var noAsignadosCelda1= noAsignadosFila.insertCell(0).innerHTML="<b>Nombres</b>";
                var noAsignadosCelda2= noAsignadosFila.insertCell(1).innerHTML="<b>Acciones</b>";

            },

            //Si petición falla
            error: function (response) {}

        });

    });

    /* Evento al ocultar el modal para asignar/eliminar Privilegios */
    $("#rolRecursoPrivilegiosModal").on("hide.bs.modal", function(){

        //Borrando contenido de título de modal
        $("#rolRecursoPrivilegiosModalTitle").html("");

        //Borrando contenido de tablas
        $("#privilegiosAsignadosTable").html("");
        $("#privilegiosNoAsignadosTable").html("");

        //Borrando contenido de inputs de privilegios a asignar/eliminar
        $("#idRolInput").val("");
        $("#idRecursoInput").val("");
        $("#idPrivilegioInput").val("");
        $("#estadoInput").val("");

    });

    /* Manejadores de eventos para eliminar todos los privilegios a un Rol sobre un Recurso */

    /* Evento al mostrar el modal para eliminar Privilegios */
    $("#rolRecursoModalDestroy").on("show.bs.modal", function (event) {

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data("id-rol");
        var rol = link.data("rol");
        var idRecurso = link.data("id-recurso");
        var recurso = link.data("recurso");

        modal.find(".modal-body #destroyMessage").text("¿Está seguro que desea eliminar los privilegios de '"+rol+"' sobre el recurso '"+recurso+"'?");
        modal.find(".modal-body #destroyInfoMessage").text("Eliminará todos los privilegios asignados al Rol sobre el Recurso. Esta acción no es reversible");

        modal.find(".modal-body #idRolDestroyInput").val(idRol);
        modal.find(".modal-body #idRecursoDestroyInput").val(idRecurso);


    });

    var store = $('#store').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Recurso y privilegios asignado al Rol con éxito");
    }
    if(delet === 'true'){
        toastr.success("Recurso y privilegios eliminados del rol con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Recurso no se puede eliminar.");
    }


});

/* Evento al hacer clic en botón con clase 'cambiarClassBtn' */
$(document).on('click', '.cambiarClassBtn', function() {

    /* Obteniendo data del privilegio según el botón presionado */
    var idRol = $(this).data("id-rol");
    var idRecurso = $(this).data("id-recurso");
    var idPrivilegio = $(this).data("id-privilegio");
    var estado = $(this).data("estado");

    /* Asignando valores a inputs para enviar datos a controller */
    $("#idRolInput").val(idRol);
    $("#idRecursoInput").val(idRecurso);
    $("#idPrivilegioInput").val(idPrivilegio);
    $("#estadoInput").val(estado);

    /* Haciendo clic sobre el botón del formulario para cambiar el estado del privilegio */
    $("#cambiarSubmitBtn").click();

});

/* Evento al para guardar cambios de cambio de privilegio y para mostrar la tabla de asignados y no asignados' */
$(document).on('click', '#cambiarSubmitBtn', function(e) {

    e.preventDefault();

    /* Obteniendo data del privilegio según el botón presionado */
    var idRol = $("#idRolInput").val();
    var idRecurso = $("#idRecursoInput").val();

    var form = $(this).parents('form');
    var url = form.attr('action');

    /* AJAX para enviar data al controller de cambio de privilegio */
    $.ajax({

        type: "POST",
        url: url,
        data: form.serialize(),
        dataType: "json",

        success: function (data) {

            $("#privilegiosAsignadosTable").html("");
            $("#privilegiosNoAsignadosTable").html("");

            /* AJAX para recuperar y colocar los privilegios en tablas del modal */
            $.ajax({

                type: "GET",
                url: "/planilla/rol-recurso-privilegio/recurso-privilegios/"+idRol+"/"+idRecurso,

                //Si petición es exitosa
                success: function (response) {

                    response = $.parseJSON(response);

                    var iAsignados = 0;
                    var iNoAsignados = 0;

                    // Iteración para recorrer respuesta obtenida y colocar elementos en tabla e inputs
                    for (i=0;i<response.length;i++){

                        /* Si elemento tiene como estado "1", colocar en tabla de asignados
                         * De lo contrario colocarlo en tabla de no asignados
                         */
                        if(response[i].estado=="1"){

                            //Obteniendo tabla de asignados
                            var tabla = document.getElementById("privilegiosAsignadosTable");
                            var fila = tabla.insertRow(iAsignados);

                            // Colocando en primer celda el nombre del privilegio
                            var celda1 = fila.insertCell(0);
                            celda1.innerHTML = response[i].privilegio;

                            // Colocando en segunda celda el botón para eliminarlo
                            var celda2 = fila.insertCell(1);

                            var btn = document.createElement("button");
                            btn.id = "eliminarBtn"+i;   // Identificador de botón.
                            btn.setAttribute("class", "btn btn-danger btn-sm cambiarClassBtn");
                            btn.dataset["idRol"] = idRol.toString();  // Colocando data con ID de Rol
                            btn.dataset["idRecurso"] = idRecurso.toString();  // Colocando data con ID de Recurso
                            btn.dataset["idPrivilegio"] = response[i].idPrivilegio.toString();  // Colocando data con ID de Privilegio
                            btn.dataset["estado"] = "1";

                            // Ícono y texto de botón
                            var ic = document.createElement("i");
                            ic.setAttribute("class","fas fa-times");
                            btn.appendChild(ic);
                            btn.append(" Eliminar");
                            celda2.appendChild(btn);

                            iAsignados++;

                        }else{

                            //Obteniendo tabla de asignados
                            var tabla = document.getElementById("privilegiosNoAsignadosTable");
                            var fila = tabla.insertRow(iNoAsignados);

                            // Colocando en primer celda el nombre del privilegio
                            var celda1 = fila.insertCell(0);
                            celda1.innerHTML = response[i].privilegio;

                            // Colocando en segunda celda el botón para asignarlo
                            var celda2 = fila.insertCell(1);

                            var btn = document.createElement("button");
                            btn.id = "asignarBtn"+i;    // Identificador de botón.
                            btn.setAttribute("class", "btn btn-info btn-sm cambiarClassBtn");
                            btn.dataset["idRol"] = idRol.toString();  // Colocando data con ID de Rol
                            btn.dataset["idRecurso"] = idRecurso.toString();  // Colocando data con ID de Recurso
                            btn.dataset["idPrivilegio"] = response[i].idPrivilegio.toString();  // Colocando data con ID de Privilegio
                            btn.dataset["estado"] = "0";

                            // Ícono y texto de botón
                            var ic = document.createElement("i");
                            ic.setAttribute("class","fas fa-check");
                            btn.appendChild(ic);
                            btn.append(" Asignar");
                            celda2.appendChild(btn);

                            iNoAsignados++;

                        }

                    }

                    var asignadosTabla = document.getElementById("privilegiosAsignadosTable");
                    var asignadosHeader = asignadosTabla.createTHead();
                    var asignadosFila = asignadosHeader.insertRow(0);
                    var asignadosCelda1= asignadosFila.insertCell(0).innerHTML="<b>Nombres</b>";
                    var asignadosCelda2= asignadosFila.insertCell(1).innerHTML="<b>Acciones</b>";

                    var noAsignadosTabla = document.getElementById("privilegiosNoAsignadosTable");
                    var noAsignadosHeader = noAsignadosTabla.createTHead();
                    var noAsignadosFila = noAsignadosHeader.insertRow(0);
                    var noAsignadosCelda1= noAsignadosFila.insertCell(0).innerHTML="<b>Nombres</b>";
                    var noAsignadosCelda2= noAsignadosFila.insertCell(1).innerHTML="<b>Acciones</b>";

                },

                //Si petición falla
                error: function (response) {}

            });


        },

        //Si petición falla
        error: function (response) {}

    });

});
