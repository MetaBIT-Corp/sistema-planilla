$(document).ready(function(){

    $('#rolRecursoPrivilegiosModal').on('show.bs.modal', function(event) {

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data('id-rol');
        var idRecurso = link.data('id-recurso')
        //AJAX Call
        $.ajax({
            type : 'GET',
            url : '/rol-recurso-privilegio/privilegios/'+idRol+'/'+idRecurso,
            success : function(data) {

                alert(data.length);

            }
        });
    });

});