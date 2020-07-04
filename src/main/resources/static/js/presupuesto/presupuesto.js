$(document).ready(function () {
    var idUnidad = $("#unidadId").data("id");
    $("#btn_registrar").on("click", function () {
        $(this).attr("disabled","disabled");
        $.ajax({
            type: "POST",
            url: "/planilla/presupuesto/store",
            data: $("#presupuesto_form").serialize(),
            dataType: "json",
            success: function (data) {
                $("#errors").attr("hidden", "hidden");
                toastr.success(data.success);
                setTimeout(function () {
                    location.reload();
                }, 2000);
            },
            error: function (data) {
                $("#errors").removeAttr("hidden");
                $("#btn_registrar").removeAttr("disabled");
                var m = "";
                $.each(data.responseJSON, function (key, value) {
                    m += "<i class='icon fas fa-ban mr-2' style='color:red;'></i><span style='text-size=4px'>" + value + "</span>";
                });
                document.getElementById("error_message").innerHTML = m;

                //Para mover al inicio de la pagina el control
                $('html, body').animate({
                    scrollTop: 0
                }, 'slow');

            }
        });
    });
    $.ajax({
        type: "GET",
        url: "/planilla/unidades-organizacionales/show-unidad/"+idUnidad,
        dataType: "json",
        success: function (data) {
            if (data.result.length == 0) {
                $("#mensaje").text("No posee sub-unidades");
            }else{
                function ruta_imagen(tipo) {
                    switch (tipo) {
                        case "Gerencias": return "/planilla/primitives/samples/images/photos/g.png";break;
                        case "Departamentos": return "/planilla/primitives/samples/images/photos/d.png";break;
                        case "Secciones": return "/planilla/primitives/samples/images/photos/s.png";break;
                        case "Areas": return "/planilla/primitives/samples/images/photos/a.png";break;
                        default: return "/planilla/primitives/samples/images/photos/o.png";
                    }

                }
                var options = new primitives.orgdiagram.Config();
                if(data.result[0].unidadPadre.unidadPadre == null){
                    var items = [
                        new primitives.orgdiagram.ItemConfig({
                            id: 0,
                            parent: null,
                            title: data.result[0].unidadPadre.unidadOrganizacional,
                            description: "Es parte de "+data.result[0].unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional + " de la empresa METABIT CORP SA de CV",
                            groupTitle: data.result[0].unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional,
                            link: data.result[0].unidadPadre.idUnidadOrganizacional,
                            image : ruta_imagen(data.result[0].unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional)
                        })
                    ];

                    for (var i = 0; i < data.result.length; i++) {
                        items.push(new primitives.orgdiagram.ItemConfig({
                            id: i + 1,
                            parent: 0,
                            title: data.result[i].unidadOrganizacional,
                            groupTitle: data.result[i].tipoUnidadOrganizacional.tipoUnidadOrganizacional,
                            description: "Es parte de "+ data.result[i].tipoUnidadOrganizacional.tipoUnidadOrganizacional + " de la empresa METABIT CORP SA de CV",
                            itemTitleColor: primitives.common.Colors.Green,
                            image: ruta_imagen(data.result[i].tipoUnidadOrganizacional.tipoUnidadOrganizacional),
                            link: data.result[i].idUnidadOrganizacional
                        }));
                    }

                }else{
                    var items = [
                        new primitives.orgdiagram.ItemConfig({
                            id: 0,
                            parent: null,
                            title: data.result[0].unidadPadre.unidadPadre.unidadOrganizacional,
                            description: "Es parte de "+data.result[0].unidadPadre.unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional + " de la empresa METABIT CORP SA de CV",
                            groupTitle: data.result[0].unidadPadre.unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional,
                            link: data.result[0].unidadPadre.unidadPadre.idUnidadOrganizacional,
                            image : ruta_imagen(data.result[0].unidadPadre.unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional)
                        }),
                        new primitives.orgdiagram.ItemConfig({
                            id: 1,
                            parent: 0,
                            title: data.result[0].unidadPadre.unidadOrganizacional,
                            description: "Es parte de "+data.result[0].unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional + " de la empresa METABIT CORP SA de CV",
                            groupTitle: data.result[0].unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional,
                            link: data.result[0].unidadPadre.idUnidadOrganizacional,
                            image : ruta_imagen(data.result[0].unidadPadre.tipoUnidadOrganizacional.tipoUnidadOrganizacional)
                        })
                    ];

                    for (var i = 0; i < data.result.length; i++) {
                        items.push(new primitives.orgdiagram.ItemConfig({
                            id: i + 2,
                            parent: 1,
                            title: data.result[i].unidadOrganizacional,
                            groupTitle: data.result[i].tipoUnidadOrganizacional.tipoUnidadOrganizacional,
                            description: "Es parte de "+ data.result[i].tipoUnidadOrganizacional.tipoUnidadOrganizacional + " de la empresa METABIT CORP SA de CV",
                            itemTitleColor: primitives.common.Colors.Green,
                            image: ruta_imagen(data.result[i].tipoUnidadOrganizacional.tipoUnidadOrganizacional),
                            link: data.result[i].idUnidadOrganizacional
                        }));
                    }
                }

                options.items = items;
                options.pageFitMode = primitives.common.PageFitMode.None;
                options.hasSelectorCheckbox = primitives.common.Enabled.False;
                options.onCursorChanged = function (e, d) {
                    window.location.href = document.location.origin + "/planilla/presupuesto/edit/" + d.context.link;
                };
                options.cursorItem = 0;

                jQuery("#basicdiagram").orgDiagram(options);
            }
        },
        error: function (data) {
            console.log(data);
        }
    })
});