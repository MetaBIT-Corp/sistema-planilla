$(document).ready(function(){

    //DataTable1
    $("#example1").DataTable({
        "responsive": true,
        "autoWidth": false,
    });

    $(".exampledt1").DataTable({
        "responsive": true,
        "autoWidth": false,
    });

    //DataTable2
    $('#example2').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
    });

});