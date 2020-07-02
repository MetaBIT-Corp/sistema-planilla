/* Procedimiento para actualizar los totales de ingreso y descuentos fijos de la planilla
 * @parametro: id de la planilla a la que se actualizarán los ingresos y descuentos
 * Realizado por: Enrique Menjívar
 * Fecha de creación 04/06/2020
 * Ultima modificación: 25/06/2020
 * */
CREATE OR REPLACE PROCEDURE planilla_update_movimientos (
    p_id_planilla_in IN NUMBER
) IS

    --Se obtiene el total de aquellos ingresos fijos que no son patronales para la planilla que se ha indicado
    CURSOR cur_ingreso IS
    SELECT
        nvl(SUM(monto_movimiento), 0)
    FROM
        planillas
        NATURAL JOIN planilla_movimientos
        NATURAL JOIN tipos_movimiento
    WHERE
        id_planilla = p_id_planilla_in
        AND es_patronal = 0
        AND es_fijo = 1
        AND es_descuento = 0;

    --Se obtiene el total de aquelos descuentos fijos que no son patronales para la planilla que se ha indicado
    CURSOR cur_descuento IS
    SELECT
        nvl(SUM(monto_movimiento), 0)
    FROM
        planillas
        NATURAL JOIN planilla_movimientos
        NATURAL JOIN tipos_movimiento
    WHERE
        id_planilla = p_id_planilla_in
        AND es_patronal = 0
        AND es_fijo = 1
        AND es_descuento = 1;

    --Se obtiene la periodicidad de planilla
    CURSOR cur_periodicidad IS
    SELECT 
        periodicidad 
    FROM 
        PLANILLAS 
        NATURAL JOIN PERIODOS 
        NATURAL JOIN ANIOS_LABORALES 
    WHERE id_planilla=p_id_planilla_in;

    --Declararción de variables
    v_total_ingresos     planillas.total_ingresos%TYPE;
    v_total_descuentos   planillas.total_descuentos%TYPE;
    v_periodicidad       anios_laborales.periodicidad%TYPE;
BEGIN
    --Abriendo cursores
    OPEN cur_ingreso;
    OPEN cur_descuento;
    OPEN cur_periodicidad;

    --Copiando el valor de los cursores a las variables
    FETCH cur_ingreso INTO v_total_ingresos;
    FETCH cur_descuento INTO v_total_descuentos;
    FETCH cur_periodicidad INTO v_periodicidad;
    
    --Se actualiza la planilla con el total de ingresos y descuentos
    UPDATE planillas
    SET
        total_ingresos = v_total_ingresos,
        total_descuentos = v_total_descuentos
    WHERE
        id_planilla = p_id_planilla_in;

    RECALCULAR_IMPUESTOS(p_id_planilla_in, v_periodicidad);

    --Se cierran los cursores
    CLOSE cur_ingreso;
    CLOSE cur_descuento;
    CLOSE cur_periodicidad;
    
END;
;;

/* Procedimiento para generar las planillas del periodo
 * @parametro: id del periodo al que se le quieren generar las planillas
 * Realizado por: Enrique Menjívar
 * Fecha de creación 23/06/2020
 * Ultima modificación: 23/06/2020
 * */
CREATE OR REPLACE PROCEDURE generar_planillas (
    p_id_periodo_in IN NUMBER
) IS

    v_id_periodo     periodos.id_periodo%TYPE := p_id_periodo_in; --Almacena el periodo en el que se generarán las planilals
    v_periodicidad   anios_laborales.periodicidad%TYPE; --Almacena la periodicidad del año laboral
    v_movimiento     empleados.salario_base_mensual%TYPE; --Almacena el total del movimiento
    v_es_base tipos_movimiento.monto_base%TYPE; --Servirá para verficar las planilla movimientos se crearán en base al monto base o al porcentaje

    --Se obtienen los tipos de movimientos que son fijos y que no son patronales
    CURSOR cur_tipo_movimientos IS
    SELECT *
    FROM tipos_movimiento
    WHERE es_fijo = 1;

BEGIN
    --Se obtiene la periodicidad del año laboral
    SELECT periodicidad
    INTO v_periodicidad
    FROM periodos NATURAL JOIN anios_laborales
    WHERE id_periodo = v_id_periodo;

    --Para cada empleado se crea su planilla se generan sus movimientos fijos
    FOR v_empleado IN (SELECT id_empleado, salario_base_mensual FROM empleados) LOOP
        INSERT INTO planillas (
            id_planilla,
            id_empleado,
            id_periodo
        ) VALUES (
            planillas_seq.NEXTVAL,
            v_empleado.id_empleado,
            v_id_periodo
        );

        --Por cada tipo de movimiento se calcula el monto total y se almacena en la tabla planilla_movimientos
        FOR v_tm IN cur_tipo_movimientos LOOP
            SELECT nvl(monto_base,0) INTO v_es_base FROM tipos_movimiento WHERE id_movimiento=v_tm.id_movimiento;

            --Si la peridicidad es mensual
            IF v_periodicidad = 30 THEN
                
                IF v_es_base = 0 THEN --si no posee monto base el movimiento se calcula multiplicando el porcentaje del movimeinto por el salario del empleado
                    v_movimiento := ( v_tm.porcentaje_movimiento / 100 ) * v_empleado.salario_base_mensual;
                ELSE
                    v_movimiento := v_tm.monto_base;
                END IF;

            --Si la periodicidad es quincenal el monto se divide entre 2
            ELSE

                IF v_es_base = 0 THEN
                    v_movimiento := (( v_tm.porcentaje_movimiento / 100 ) * v_empleado.salario_base_mensual ) / 2;
                ELSE
                    v_movimiento := v_tm.monto_base / 2;
                END IF;

            END IF;

            INSERT INTO planilla_movimientos (
                id_planilla_movimiento,
                monto_movimiento,
                id_planilla,
                id_movimiento
            ) VALUES (
                planilla_movimientos_seq.NEXTVAL,
                v_movimiento,
                planillas_seq.CURRVAL,
                v_tm.id_movimiento
            );

        END LOOP;
        
        --Llamada al procedimiento para actualizar los totales de ingresos y descuentos fijos en la planilla
        PLANILLA_UPDATE_MOVIMIENTOS(planillas_seq.CURRVAL);

    END LOOP;

END;
;;

/* Procedimiento para realizar el pago de planilla
 * @parametro: id de la unidad a pagar planilla (Parametro de entrada)
 * @parametro: mensaje que indica el resultado de la ejecución del procedimiento (Parametro de salida)
 * Realizado por: Edwin Palacios
 * Fecha de creación 18/06/2020
 * Ultima modificación: 29/06/2020
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
        JOIN periodos  pe ON (p.id_periodo = pe.id_periodo)
        WHERE (u.id_unidad_organizacional= p_id_unidad AND e.empleado_habilitado = 1 AND epu.fecha_fin IS NULL AND pe.activo = 1 AND p.fecha_emision IS NULL);
    
    -- declaracion de variables
    
    v_periodos_rec          periodos%rowtype;    -- registro para manejar los periodos (actual para cerrarlo y siguiente para abrirlo)
    v_periodicidad          anios_laborales.periodicidad%TYPE;  -- el tipo de periodicidad: si es mensual(30) o quincenal (15)
    v_presupuesto_unidad    centros_costos.presupuesto_asignado%TYPE := 0;  -- presupuesto actual de la unidad
    v_id_centro_costo       centros_costos.id_centro_costo%TYPE;
    v_num_unidades_faltantes    number;
    
    -- Por empleado
    v_salario_base          empleados.salario_base_mensual%type := 0; -- Salario base con el que cuenta el empleado
    v_plan_ingreso          empleados.salario_base_mensual%type := 0; -- monto de plan de ingreso (si cuenta con uno)
    v_plan_descuento        empleados.salario_base_mensual%type := 0; -- monto de plan descuento (si cuenta con uno)
    v_salario_devengado     empleados.salario_base_mensual%type := 0; -- salario base + ingresos(ingresos comunes, horas extra, comision, dias festivos, plan de ingreso, etc)
    v_aportacion_patronal   empleados.salario_base_mensual%type := 0; -- sumatoria de isss patronal, afp patronal y otros movimientos patronales  
    v_descuentos_empleado   empleados.salario_base_mensual%type := 0; -- sumatoria de isss + afp + renta + plan de descuentos + otros descuentos  
    v_valor_neto_a_pagar    empleados.salario_base_mensual%type := 0; -- salario devengado - descuentos de empleado
    
    -- Global
    v_total_pago_planilla   empleados.salario_base_mensual%type := 0; -- Es el gasto patronal: (valor neto a pagar + aportacion patronal) por cada empleado
BEGIN

    -- Obtenemos el periodo actual
    SELECT p.* 
    INTO v_periodos_rec
    FROM anios_laborales a
         JOIN periodos p ON (a.id_anio_laboral = p.id_anio_laboral)
    WHERE p.activo = 1; 
    -- Si existe periodo
    IF (SQL%ROWCOUNT > 0) THEN
        -- Obtenemos el presupuesto actual de la unidad y la periodicidad 
        SELECT (c.presupuesto_asignado - c.presupuesto_devengado) as "presupuesto_actual", a.periodicidad, c.id_centro_costo
        INTO v_presupuesto_unidad, v_periodicidad, v_id_centro_costo
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
                                    
            -- consultamos aportes patronales
            SELECT nvl(SUM(pm.monto_movimiento),0) 
            INTO v_aportacion_patronal
            FROM planilla_movimientos pm
            JOIN tipos_movimiento tm ON (pm.id_movimiento = tm.id_movimiento)
            Where pm.id_planilla = rec_planilla.id_planilla AND tm.es_patronal = 1;                        
            
            v_descuentos_empleado := rec_planilla.renta 
                                    + rec_planilla.total_descuentos
                                    + v_plan_descuento;
            
            v_valor_neto_a_pagar := v_salario_devengado - v_descuentos_empleado; 
                                   
            v_total_pago_planilla := v_total_pago_planilla 
                                    + v_salario_devengado
                                    + v_aportacion_patronal;
                                    
            -- actualizamos la planilla del empleado
            UPDATE planillas SET fecha_emision = SYSDATE, salario_neto = v_valor_neto_a_pagar
            WHERE id_planilla = rec_planilla.id_planilla;  
                             
            -- Si el presupuesto no es suficiente se sale del ciclo
            EXIT WHEN (v_total_pago_planilla > v_presupuesto_unidad);
        END LOOP;

         -- Despues de recorrer todas las planillas se valida si el presupuesto fue suficiente para pagar planilla
        IF (v_total_pago_planilla > v_presupuesto_unidad) THEN
            p_message := 'Presupuesto no suficiente. El presupuesto actual es de '
                         || to_char(v_presupuesto_unidad,'$99,999.99') 
                         || ' y el pago requerido es de '
                         || to_char(v_total_pago_planilla,'$99,999.99'); 
            ROLLBACK;
        ELSE
            -- actualizamos el centro de costo de la planilla
            UPDATE centros_costos SET presupuesto_devengado= presupuesto_devengado + v_total_pago_planilla
            WHERE id_centro_costo = v_id_centro_costo;
            
            -- Consultamos si existe una unidad mas que falte de pagar
            SELECT COUNT(*)
            INTO v_num_unidades_faltantes
            FROM unidades_organizacionales uo
            NATURAL JOIN empleados_puestos_unidades epu
            NATURAL JOIN empleados e
            NATURAL JOIN planillas p
            WHERE epu.fecha_fin IS NULL AND e.empleado_habilitado = 1 AND p.fecha_emision IS NULL;
            
            -- si no hay mas unidades que falten por pagar
            IF(v_num_unidades_faltantes = 0) THEN
                -- Cerramos el periodo actual
                UPDATE periodos SET activo = 0
                WHERE id_periodo = v_periodos_rec.id_periodo;
                -- Abrimos el periodo siguiente 
                UPDATE periodos SET activo = 1
                WHERE id_periodo = (v_periodos_rec.id_periodo + 1);
            END IF;
            
            p_message := 'Pago realizado de manera exitosa. El presupuesto actual es de '
                         || to_char((v_presupuesto_unidad - v_total_pago_planilla),'$99,999.99')
                         || ' El cobro realizado fue de '
                         || to_char(v_total_pago_planilla,'$99,999.99');
                         
            COMMIT;
        END IF;
    ELSE
        p_message := 'No existe un periodo activo. Por favor revisar';
    END IF;
END;
;;

/* Procedimiento que permite recalcular isss, afp y renta de una planilla
 * @parametro: p_id_planilla
 * @parametro: p_periodicidad. 15 si es quincenal, 30 si es mensual
 * Realizado por: Edwin Palacios
 * Fecha de creación 27/06/2020
 * Ultima modificación: 27/06/2020
 * */
CREATE OR REPLACE PROCEDURE RECALCULAR_IMPUESTOS(p_id_planilla  planillas.id_planilla%TYPE, p_periodicidad anios_laborales.periodicidad%TYPE) 
    IS
    -- cursor de impuestos
    CURSOR cur_impuestos 
        (p_id_planilla  planillas.id_planilla%TYPE)  
        IS 
        SELECT pm.id_planilla_movimiento, tm.*
        FROM planillas p
            JOIN planilla_movimientos pm ON (p.id_planilla = pm.id_planilla) 
            JOIN tipos_movimiento tm ON (pm.id_movimiento = tm.id_movimiento)
        WHERE (tm.tipo_movimiento_habilitado = 1 
                AND tm.es_descuento = 1 
                AND tm.id_movimiento IN (500,501,502,503)
                AND p.id_planilla = p_id_planilla); 
    
    -- declaracion de variables
    v_planilla_empleado     planillas%ROWTYPE;  
    v_salario_base          empleados.salario_base_mensual%TYPE := 0;
    v_salario_devengado     planillas.salario_neto%TYPE := 0;
    v_impuesto_isss_afp     planilla_movimientos.monto_movimiento%TYPE := 0;
    v_salario_base_renta    planillas.renta%TYPE := 0;
    v_renta                 planillas.renta%TYPE := 0;
    
    
BEGIN
    -- Se obtiene salario base
    SELECT e.salario_base_mensual 
    INTO v_salario_base
    FROM empleados e
    JOIN planillas p ON (e.id_empleado = p.id_empleado)
    WHERE id_planilla = p_id_planilla;
    
    -- Se obtiene la planilla 
    SELECT p.* 
    INTO v_planilla_empleado
    FROM planillas p
    WHERE id_planilla = p_id_planilla;
    
    IF (SQL%ROWCOUNT > 0) THEN
    
        IF p_periodicidad = 15 THEN
           v_salario_base := v_salario_base/2;
        END IF;
        
        -- obtenemos salario devengado
        v_salario_devengado :=  v_salario_base  
                                + v_planilla_empleado.monto_comision
                                + v_planilla_empleado.monto_horas_extra
                                + v_planilla_empleado.monto_dias_festivos
                                + v_planilla_empleado.total_ingresos;
        
        -- se recorre cada uno de los impuestos
        FOR rec_impuestos IN cur_impuestos(p_id_planilla)
        LOOP
            IF v_salario_devengado > rec_impuestos.monto_maximo THEN
                IF rec_impuestos.movimiento IN ('ISSS', 'AFP') THEN
                    v_impuesto_isss_afp := v_impuesto_isss_afp + ((rec_impuestos.monto_maximo*rec_impuestos.porcentaje_movimiento)/100);
                END IF;
                UPDATE planilla_movimientos 
                SET monto_movimiento = ((rec_impuestos.monto_maximo*rec_impuestos.porcentaje_movimiento)/100)
                WHERE id_planilla_movimiento = rec_impuestos.id_planilla_movimiento;
            ELSE
                IF rec_impuestos.movimiento IN ('ISSS', 'AFP') THEN
                    v_impuesto_isss_afp := v_impuesto_isss_afp + ((v_salario_devengado*rec_impuestos.porcentaje_movimiento)/100);
                END IF;
                UPDATE planilla_movimientos 
                SET monto_movimiento = ((v_salario_devengado*rec_impuestos.porcentaje_movimiento)/100)
            WHERE id_planilla_movimiento = rec_impuestos.id_planilla_movimiento;
           END IF;
        END LOOP;
        
        -- calculo de renta
        v_salario_base_renta := v_salario_devengado - v_impuesto_isss_afp;
        
        update planillas set renta = calcular_renta(v_salario_base_renta, p_periodicidad)
        WHERE id_planilla = p_id_planilla;
        
    END IF;
    --COMMIT;
END;
;;