$(document).ready(function(){

    $('#rolRecursoPrivilegiosModal').on('show.bs.modal', function(event) {

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data('id-rol');
        var idRecurso = link.data('id-recurso')

        $.ajax({

            type : 'GET',
            url : '/rol-recurso-privilegio/privilegios/'+idRol+'/'+idRecurso,
            dataType: "json",
            success : function(data) {

                for (i=0;i<data.length;i++){

                    $('#tbody-privilegios').append(
                        '<tr>' +
                            '<td>'+data[i].privilegio+'</td>'+
                            '<td>'+
                            '<a class="btn btn-danger btn-sm"\n' +
                                'href="#"\n' +
                                'title="Quitar privilegio del recurso"\n' +
                                'data-toggle="modal"\n' +
                                'data-target="#"\n' +
                                'data-id-privilegio='+data[i].idPrivilegio+'\n' +
                                'data-privilegio='+data[i].privilegio+'>'+'\n' +
                                '<i class="far fa-times-circle"></i>'+
                            '</a>'+
                            '</td>'+
                        '</tr>'
                    );

                }
            }


        });


    });

    $("#rolRecursoPrivilegiosModal").on('hide.bs.modal', function(){
        $('#tbody-privilegios').html("");
    });

});