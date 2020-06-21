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
    e_monto_bajo_exception    EXCEPTION; 
BEGIN
    p_message_completo := 'Hola, Soy un procedimiento de prueba: ' 
                          || p_message 
                          || ' Fecha: ' 
                          || p_message_date;
    --RAISE e_monto_bajo_exception;
EXCEPTION
    WHEN e_monto_bajo_exception THEN
       RAISE_APPLICATION_ERROR(-20001, 'Error: El presupuesto no es suficiente'); 
END;
;;

/* Procedimiento para realizar el pago de planilla
 * @parametro: id de la unidad a pagar planilla (Parametro de entrada)
 * @parametro: mensaje que indica el resultado de la ejecución del procedimiento (Parametro de salida)
 * Realizado por: Edwin Palacios
 * Fecha de creación 18/06/2020
 * Ultima modificación: 21/06/2020
 * */

CREATE OR REPLACE PROCEDURE PAGO_PLANILLA
    (p_id_unidad IN NUMBER, p_message OUT varchar2) -- parametros
    IS
    
    -- obtenemos las planillas sobre las que se calculará el salario neto 
    CURSOR cur_planillas (p_id_unidad NUMBER) IS  
        SELECT p.*, e.salario_base_mensual, e.horas_trabajo
        FROM unidades_organizacionales u 
        JOIN empleados_puestos_unidades epu ON (u.id_unidad_organizacional = epu.id_unidad_organizacional)
        JOIN empleados e ON (epu.id_empleado=e.id_empleado)
        JOIN planillas p ON (e.id_empleado=p.id_empleado)
        WHERE (u.id_unidad_organizacional= p_id_unidad AND e.empleado_habilitado = 1 AND epu.fecha_fin IS NULL);  
    
    -- periodo actual y siguiente     
    TYPE rec_periodo IS RECORD (
        v_periodo_actual      periodos%rowtype,
        v_periodo_siguiente      periodos%rowtype
    );
    
    -- declaracion de variables
    
    v_periodos_rec          rec_periodo;    -- registro para manejar los periodos (actual para cerrarlo y siguiente para abrirlo)
    v_periodicidad          anios_laborales.periodicidad%TYPE;  -- el tipo de periodicidad: si es mensual(30) o quincenal (15)
    v_presupuesto_unidad    centros_costos.presupuesto_asignado%type := 0;  -- presupuesto actual de la unidad
    
    -- Por empleado
    v_salario_base          empleados.salario_base_mensual%type := 0; -- Salario base con el que cuenta el empleado
    v_plan_ingreso          empleados.salario_base_mensual%type := 0; -- monto de plan de ingreso (si cuenta con uno)
    v_plan_descuento        empleados.salario_base_mensual%type := 0; -- monto de plan descuento (si cuenta con uno)
    v_salario_devengado     empleados.salario_base_mensual%type := 0; -- salario base + ingresos(ingresos comunes, horas extra, comision, dias festivos, plan de ingreso, etc)
    v_aportacion_patronal   empleados.salario_base_mensual%type := 0; -- sumatorioa de isss patronal, afp patronal y otros movimientos patronales  
    v_descuentos_empleado   empleados.salario_base_mensual%type := 0; -- sumatoria de isss + afp + renta + plan de descuentos + otros descuentos  
    v_valor_neto_a_pagar    empleados.salario_base_mensual%type := 0; -- salario devengado - descuentos de empleado
    
    -- Global
    v_total_pago_planilla   empleados.salario_base_mensual%type := 0; -- Es el gasto patronal: (valor neto a pagar + aportacion patronal) por cada empleado
BEGIN

    -- Obtenemos el periodo actual
        SELECT p.* 
        INTO v_periodos_rec.v_periodo_actual
        FROM anios_laborales a
             JOIN periodos p ON (a.id_anio_laboral = p.id_anio_laboral)
        WHERE p.activo = 1; 
    
    -- Obtenemos el presupuesto actual de la unidad y la periodicidad 
    SELECT (c.presupuesto_anterior + c.presupuesto_asignado - c.presupuesto_devengado) as "presupuesto_actual", a.periodicidad
    INTO v_presupuesto_unidad, v_periodicidad
    FROM unidades_organizacionales u
        JOIN centros_costos c ON (u.id_unidad_organizacional = c.id_unidad_organizacional)
        JOIN anios_laborales a ON (c.id_anio = a.id_anio_laboral)
    WHERE u.id_unidad_organizacional = p_id_unidad AND a.anio_laboral = TO_CHAR(SYSDATE,'yyyy');
    
    -- recorremos las planillas para verificar si el presupuesto es suficiente 
    FOR rec_planilla IN cur_planillas(p_id_unidad)
    LOOP
        -- definimos el salario base según periodicidad
        IF (v_periodicidad = 30) THEN
            v_salario_base := rec_planilla.salario_base_mensual;
        ELSE
            v_salario_base := rec_planilla.salario_base_mensual/2;
        END IF;
        
        v_plan_ingreso := obtener_plan(rec_planilla.id_empleado,v_periodicidad ,0); 
        v_plan_descuento := obtener_plan(rec_planilla.id_empleado,v_periodicidad ,1);       
        v_salario_devengado :=  + v_salario_base  
                                + v_plan_ingreso
                                + rec_planilla.monto_comision
                                + rec_planilla.monto_horas_extra
                                + rec_planilla.monto_dias_festivos
                                + rec_planilla.total_ingresos;
                                
        v_aportacion_patronal := 0; -- pendiente 
        
        v_descuentos_empleado := rec_planilla.renta 
                                + (rec_planilla.total_descuentos-v_aportacion_patronal); -- pendiente
        
        v_valor_neto_a_pagar := v_salario_devengado - v_descuentos_empleado; 
                               
        v_total_pago_planilla := v_total_pago_planilla 
                                + v_valor_neto_a_pagar
                                + v_aportacion_patronal;
                                
        DBMS_OUTPUT.PUT_LINE(rec_planilla.id_planilla 
                         || ' ' 
                         ||rec_planilla.salario_neto
                         || ' Periodicidad: ' 
                         || v_periodicidad
                         || ' Presupuesto: ' 
                         || v_presupuesto_unidad
                         || ' Salario base: ' 
                         || v_salario_base
                         || ' Total pago Planilla: ' 
                         || v_total_pago_planilla
                         );   
                         
        -- Si el presupuesto no es suficiente se sale del ciclo
        EXIT WHEN (v_total_pago_planilla > v_presupuesto_unidad);
    END LOOP;
    
     -- Se valida el presupuesto es suficiente para pagar planilla
    IF (v_total_pago_planilla > v_presupuesto_unidad) THEN
        p_message := 'El presupuesto no es suficiente. El presupuesto actual es de $'
                     || v_presupuesto_unidad 
                     || ' y el pago requerido consta de $'
                     || v_total_pago_planilla;
        ROLLBACK;
    ELSE
        -- Obtenemos el periodo siguiente 
        SELECT p.* 
        INTO v_periodos_rec.v_periodo_siguiente
        FROM periodos p 
        WHERE p.id_periodo = (v_periodos_rec.v_periodo_actual.id_periodo + 1); -- EXISTS
        
        p_message := 'Sigue';
        COMMIT;
    END IF;
END;
;;