/*---PROCEDIMIENTO PARA ACTUALIZAR LOS TOTALES DE INGRESO Y DESCUENTO EN LA PLANILLA-----*/
CREATE OR REPLACE PROCEDURE planilla_update_movimientos (
    p_id_planilla_in IN NUMBER
) IS

    CURSOR cur_ingreso IS
    SELECT
        nvl(SUM(monto_movimiento), 0)
    FROM
        planillas
        NATURAL JOIN planilla_movimientos
        NATURAL JOIN tipos_movimiento
    WHERE
        id_planilla = p_id_planilla_in
        AND es_fijo = 1
        AND es_descuento = 0;

    CURSOR cur_descuento IS
    SELECT
        nvl(SUM(monto_movimiento), 0)
    FROM
        planillas
        NATURAL JOIN planilla_movimientos
        NATURAL JOIN tipos_movimiento
    WHERE
        id_planilla = p_id_planilla_in
        AND es_fijo = 1
        AND es_descuento = 1;

    v_total_ingresos     planillas.total_ingresos%TYPE;
    v_total_descuentos   planillas.total_descuentos%TYPE;
BEGIN
    OPEN cur_ingreso;
    OPEN cur_descuento;
    FETCH cur_ingreso INTO v_total_ingresos;
    FETCH cur_descuento INTO v_total_descuentos;
    
    UPDATE planillas
    SET
        total_ingresos = v_total_ingresos,
        total_descuentos = v_total_descuentos
    WHERE
        id_planilla = p_id_planilla_in;

    CLOSE cur_ingreso;
    CLOSE cur_descuento;
    COMMIT;
END;
;;

/*---PROCEDIMIENTO DE PRUEBA -----*/
CREATE OR REPLACE PROCEDURE SHOW_MENSAJE_PROCEDURE
    (p_message IN varchar2, p_message_completo OUT varchar2) -- parametros
    IS
    p_message_date  DATE := sysdate;
BEGIN
    p_message_completo := 'Hola, Soy un procedimiento de prueba: ' 
                          || p_message 
                          || ' Fecha: ' 
                          || p_message_date;
END;
;;