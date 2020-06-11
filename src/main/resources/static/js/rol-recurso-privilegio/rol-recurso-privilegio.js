$(document).ready(function(){

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
            url : "/rol-recurso-privilegio/recursos/"+idRol,

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
        $("#idRecursoAsignacionInput").html("");
        $("#rolRecursoAsignarModalTitle").html("");
    });

    $(".checkboxPrivilegio").change(function () {

        var input = $("#idsPrivilegiosoAsignacionInput");

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

        $(".checkboxPrivilegio").attr("disabled", true);

        $.ajax({
            type: "POST",
            url: url,
            data: form.serialize(),
            dataType: "json",
            success: function (data) {
                window.location.href = document.location.origin + "/rol-recurso-privilegio/index/1";
            },
            error: conexionError()
        });

    });

    /* Manejadores de eventos para asignar/eliminar Privilegios a un Rol sobre un Recurso */

    $('#rolRecursoPrivilegiosModal').on("show.bs.modal", function(event) {

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data("id-rol");
        var idRecurso = link.data("id-recurso");
        var recurso = link.data("recurso");

        modal.find(".modal-header #rolRecursoPrivilegiosModalTitle").append(
            "Privilegios del Rol sobre el Recurso: "+
            recurso.toString().charAt(0).toUpperCase()+recurso.toString().slice(1).toLowerCase()
        );

        modal.find("#asignarForm #idRolInput").val(idRol);
        modal.find("#asignarForm #idRecursoInput").val(idRecurso);

        modal.find("#eliminarForm #idRolEliminarInput").val(idRol);
        modal.find("#eliminarForm #idRecursoEliminarInput").val(idRecurso);

        $.ajax({

            type : "GET",
            url : "/rol-recurso-privilegio/privilegios/"+idRol+"/"+idRecurso,

            success : function(data) {

                data = $.parseJSON(data);

                var i_asignados = 0;
                var i_no_asignados = 0;

                for (i=0;i<data.length;i++){

                    if(data[i].estado=="1"){

                        var tabla = document.getElementById("tablaAsignados");
                        var fila = tabla.insertRow(i_asignados);
                        i_asignados++;

                        var celda1 = fila.insertCell(0);
                        celda1.innerHTML = data[i].privilegio;

                        var celda2 = fila.insertCell(1);
                        var btn = document.createElement("button");
                        btn.id = "btnQuitar"+i;
                        btn.setAttribute("class", "btn btn-danger btn-sm btnQuitarClass");
                        btn.dataset["idPrivilegio"] = data[i].idPrivilegio.toString();
                        btn.onclick = function(){
                            var privilegioInput = document.getElementById("idPrivilegioEliminarInput");
                            privilegioInput.setAttribute("value",this.dataset["idPrivilegio"]);
                            document.getElementById("btnSubmitEliminar").click();

                            setTimeout(function(){
                                $("#rolRecursoPrivilegiosModal").modal("hide");
                                document.getElementById("btnRecurso"+idRecurso).click();
                            },300);

                            return false;
                        };
                        var ic = document.createElement("i");
                        ic.setAttribute("class","fas fa-times");
                        celda2.appendChild(btn);
                        btn.appendChild(ic);
                        btn.append(" Eliminar");
                    }else{
                        var tabla = document.getElementById("tablaNoAsignados");
                        var fila = tabla.insertRow(i_no_asignados);
                        i_no_asignados++;

                        var celda1 = fila.insertCell(0);
                        celda1.innerHTML = data[i].privilegio;

                        var celda2 = fila.insertCell(1);
                        var btn = document.createElement("button");
                        btn.id = "btnAgregar"+i;
                        btn.setAttribute("class", "btn btn-info btn-sm btnAgregarClass");
                        btn.dataset["idPrivilegio"] = data[i].idPrivilegio.toString();
                        btn.onclick = function(){
                            var privilegioInput = document.getElementById("idPrivilegioInput");
                            privilegioInput.setAttribute("value",this.dataset["idPrivilegio"]);
                            document.getElementById("btnSubmitAsignar").click();
                            setTimeout(function(){
                                $("#rolRecursoPrivilegiosModal").modal("hide");
                                document.getElementById("btnRecurso"+idRecurso).click();
                            },300);
                            return false;
                        };

                        var ic = document.createElement("i");
                        ic.setAttribute("class","fas fa-check");
                        celda2.appendChild(btn);
                        btn.appendChild(ic);
                        btn.append(" Asignar");
                    }

                }

                var tablaAsignados = document.getElementById("tablaAsignados");
                var headerAsignados = tablaAsignados.createTHead();
                var filaAsignados = headerAsignados.insertRow(0);
                var celda1Asignados= filaAsignados.insertCell(0).innerHTML="<b>Nombres</b>";
                var celda2Asignados= filaAsignados.insertCell(1).innerHTML="<b>Accione</b>";

                var tablaNoAsignados = document.getElementById("tablaNoAsignados");
                var headerNoAsignados = tablaNoAsignados.createTHead();
                var filaNoAsignados = headerNoAsignados.insertRow(0);
                var celda1NoAsignados= filaNoAsignados.insertCell(0).innerHTML="<b>Nombres</b>";
                var celda2NoAsignados= filaNoAsignados.insertCell(1).innerHTML="<b>Accione</b>";

            }

        });

    });

    $("#rolRecursoPrivilegiosModal").on("hide.bs.modal", function(){
        $("#tablaAsignados").html("");
        $("#tablaNoAsignados").html("");
        $("#rolRecursoPrivilegiosModalTitle").html("");
    });

    $("#btnSubmitEliminar").on("click",function (e) {

        e.preventDefault();
        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({
            type: "POST",
            url: url,
            data: form.serialize(),
            dataType: "json",
            success: function (data) {}
        });

    });

    $("#btnSubmitAsignar").on("click",function (e) {

        e.preventDefault();
        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({
            type: "POST",
            url: url,
            data: form.serialize(),
            dataType: "json",
            success: function (data) {}
        });

    });



});