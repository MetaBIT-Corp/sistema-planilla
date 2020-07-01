  /*Trigger para crear los periodos después de haber insertado un año laboral
 * Realizado por: Enrique Menjívar
 * Fecha de creación 02/06/2020
 * Ultima modificación: 02/06/2020
 * */
CREATE OR REPLACE TRIGGER CREAR_PERIODOS_AI AFTER
    INSERT ON anios_laborales
    FOR EACH ROW
DECLARE
    v_anio_actual    VARCHAR2(4);
    v_anio_periodo   VARCHAR2(4);
    v_fecha_inicio   periodos.fecha_inicio%TYPE;
    v_fecha_final    periodos.fecha_final%TYPE;
    v_activo         periodos.activo%TYPE;
BEGIN
    --Se obtiene el año actual
    SELECT
        to_char(sysdate, 'YYYY')
    INTO v_anio_actual
    FROM
        dual;

    --Se obtiene el primer dia, del primer mes del año actual para empezar a generar los periodos desde esa fecha
    SELECT
        trunc(sysdate, 'YEAR')
    INTO v_fecha_inicio
    FROM
        dual;

    --Se convierte a char la fehca en la que iniciaran los periodos
    v_anio_periodo := to_char(v_fecha_inicio, 'YYYY');
    
    --Si la periodicidad es de 30 la fecha final será el último día del mes, si no esta será el 15 del mes
    IF :new.periodicidad = 30 THEN
        v_fecha_final := last_day(v_fecha_inicio);
    ELSE
        v_fecha_final := v_fecha_inicio + 14;
    END IF;

    WHILE v_anio_actual = v_anio_periodo LOOP --Mientras el año en que se van generando los periodos sea igual al actual
        
        --Poner en activo el periodo que comprende la fecha actual
        IF TRUNC(SYSDATE) BETWEEN v_fecha_inicio AND v_fecha_final THEN
            v_activo := 1;
        ELSE
            v_activo := 0;
        END IF;
        
        CASE
            WHEN :new.periodicidad = 15 THEN --Cuando la periodicidad sea quincenal (quincenal)
            
                    --Se realiza la insersión del periodo
                    INSERT INTO periodos (
                        id_periodo,
                        fecha_inicio,
                        fecha_final,
                        activo,
                        id_anio_laboral
                    ) VALUES (
                        periodos_seq.NEXTVAL,
                        v_fecha_inicio,
                        v_fecha_final,
                        v_activo,
                        :new.id_anio_laboral
                    );
    
                    v_fecha_inicio := v_fecha_final + 1; --La fecha de inicio del próximo periodo pasa a ser la fecha final más 1 día
                    v_anio_periodo := to_char(v_fecha_inicio, 'YYYY'); --Se obtiene el año del periodo recién insertado
                    
                    /*
                    Si la fecha de inicio del periodo es igual al primer día del mes, la fecha final del próximo periodo será el día 15
                    si no la fecha final del próximo periodo será el último día del mes
                     */
                    IF v_fecha_inicio = trunc(v_fecha_inicio, 'MONTH') THEN
                        v_fecha_final := v_fecha_inicio + 14;
                    ELSE
                        v_fecha_final := last_day(v_fecha_inicio);
                    END IF;
    
            WHEN :new.periodicidad = 30 THEN --Cuando la periodicidad es de 30 (mensual)
                    --Se realiza la insersión del periodo
                    INSERT INTO periodos (
                        id_periodo,
                        fecha_inicio,
                        fecha_final,
                        activo,
                        id_anio_laboral
                    ) VALUES (
                        periodos_seq.NEXTVAL,
                        v_fecha_inicio,
                        v_fecha_final,
                        v_activo,
                        :new.id_anio_laboral
                    );
    
                    v_fecha_inicio := v_fecha_final + 1;       -- La fecha de inicio del próximo periodo será igual a la final más un día
                    v_anio_periodo := to_char(v_fecha_inicio, 'YYYY');  -- Se obtiene el año del periodo recién insertado
                    v_fecha_final := last_day(v_fecha_inicio);          -- La fecha final del próximo periodo será igual al último día del mes
                
        END CASE;
    END LOOP;
END;
;;

/*Trigger para actualizar le salario máximo del nivel mayor de un rango de renta en base a la una periodicidad
 * Realizado por: Enrique Menjívar
 * Fecha de creación 18/06/2020
 * Ultima modificación: 18/06/2020
 * */
CREATE OR REPLACE TRIGGER ACTUALIZAR_SALARIO_MAX_BI BEFORE
    INSERT ON rangos_renta
    FOR EACH ROW
DECLARE
    v_salario_min rangos_renta.salario_min%TYPE;
BEGIN
    --Se obtiene el salario mínimos más alto en base a una periodicidad
    SELECT
        MAX(salario_min)
    INTO v_salario_min
    FROM
        rangos_renta renta
    WHERE
        periodicidad_renta = :new.periodicidad_renta;

    --Si el salario mínimos del nuevo rango es mayor que el salario mínimo más alto
    IF :new.salario_min > v_salario_min THEN
        --Se actualiza el salario máximo del registro, poniendolo igual al salario mínimo del rango superior, menos un centavo
        UPDATE rangos_renta
        SET
            salario_max = :new.salario_min - 0.01
        WHERE
            salario_min = v_salario_min;

    END IF;

END;
;;

/*Trigger generarle la planilla al empelado que se insertó
 * Realizado por: Enrique Menjívar
 * Fecha de creación 27/06/2020
 * Ultima modificación: 27/06/2020
 * */
CREATE OR REPLACE TRIGGER crear_planilla_ai_compound FOR INSERT ON empleados COMPOUND TRIGGER

    v_id_periodo periodos.id_periodo%TYPE; --Guardará el periodo activo
    v_periodicidad   anios_laborales.periodicidad%TYPE; --Almacena la periodicidad del año laboral
    v_movimiento     empleados.salario_base_mensual%TYPE; --Almacena el total del movimiento
    v_planillas_number NUMBER; --Servirá para controlar si el periodo tiene planillas
    v_es_base tipos_movimiento.monto_base%TYPE; --Servirá para verficar las planilla movimientos se crearán en base al monto base o al porcentaje

    --Se obtienen los tipos de movimientos que son fijos y que no son patronales
    CURSOR cur_tipo_movimientos IS
    SELECT *
    FROM tipos_movimiento
    WHERE es_fijo = 1 AND es_patronal = 0;

    --Ejecutar después de cada fila afectada en la tabla empleados
    AFTER EACH ROW IS BEGIN
        --Se obtiene el periodo activo
        SELECT id_periodo INTO v_id_periodo FROM periodos WHERE activo = 1;

        --Obtiene el número de planillas que existen en el periodo
        SELECT COUNT(ROWNUM) INTO v_planillas_number FROM PLANILLAS WHERE ID_PERIODO = v_id_periodo;
        
        --Se obtiene la periodicidad del año laboral
        SELECT periodicidad INTO v_periodicidad FROM periodos NATURAL JOIN anios_laborales WHERE id_periodo = v_id_periodo;

        --Si ya fueron creadas las planillas del periodo
        IF v_planillas_number > 0 THEN
            --Se crea la planilla con el empleado recién insertado en el periodo activo
            INSERT INTO planillas (
                id_planilla,
                id_empleado,
                id_periodo
            ) VALUES (
                planillas_seq.NEXTVAL,
                :new.id_empleado,
                v_id_periodo
            );
            
            --Por cada tipo de movimiento se calcula el monto total y se almacena en la tabla planilla_movimientos
            FOR v_tm IN cur_tipo_movimientos LOOP
                SELECT nvl(monto_base,0) INTO v_es_base FROM tipos_movimiento WHERE id_movimiento=v_tm.id_movimiento;
                
                --Si la periodicidad es mensual
                IF v_periodicidad = 30 THEN

                    IF v_es_base = 0 THEN --si no posee monto base el movimiento se calcula multiplicando el porcentaje del movimeinto por el salario del empleado
                        v_movimiento := ( v_tm.porcentaje_movimiento / 100 ) * :new.salario_base_mensual;
                    ELSE
                        v_movimiento := v_tm.monto_base;
                    END IF;

                ELSE

                    IF v_es_base = 0 THEN
                        v_movimiento := (( v_tm.porcentaje_movimiento / 100 ) * :new.salario_base_mensual ) / 2;
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
--v_movimiento := ( v_tm.monto_base + ( v_tm.porcentaje_movimiento / 100 ) * :new.salario_base_mensual ) / 2;
            END LOOP;
        END IF;

        --Si no encuentra periodos activos
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                v_id_periodo := NULL;

    END AFTER EACH ROW;

    --Ejecutar después de la consulta DML
    AFTER STATEMENT IS BEGIN
        IF v_planillas_number > 0 THEN
            planilla_update_movimientos(planillas_seq.currval);
        END IF;
    END AFTER STATEMENT;

END crear_planilla_ai_compound;
;;

/*-----------Trigger para Crear Cuotas luego de crear un nuevo Plan -------------*/
CREATE OR REPLACE TRIGGER crearcuotas AFTER
    INSERT ON planes
    FOR EACH ROW
DECLARE
    v_id_plan              planes.id_plan%TYPE := :new.id_plan;
    v_peridicidad_plan     planes.periodicidad_plan%TYPE := :new.periodicidad_plan;
    v_plazo_plan           planes.plazo%TYPE := :new.plazo;
    v_fecha_prevista_pago  cuotas.fecha_prevista_pago%TYPE;
    v_numero_cuota         cuotas.numero_cuota%TYPE;
BEGIN
    --Si es Mensual
    IF v_peridicidad_plan = 30 THEN
        FOR i IN 1..v_plazo_plan LOOP
            v_numero_cuota := i;
            IF i = 1 THEN
                v_fecha_prevista_pago := last_day(sysdate);
            ELSE
                v_fecha_prevista_pago := last_day(add_months(v_fecha_prevista_pago, 1));
            END IF;

            INSERT INTO cuotas (
                id_cuota,
                fecha_prevista_pago,
                fecha_real_pago,
                monto_cancelado,
                numero_cuota,
                id_plan
            ) VALUES (
                cuotas_seq.NEXTVAL,
                v_fecha_prevista_pago,
                NULL,
                0.0,
                v_numero_cuota,
                v_id_plan
            );

        END LOOP;

    ELSE
        FOR i IN 1..v_plazo_plan LOOP
            v_numero_cuota := i;
            IF i = 1 THEN
                IF ( sysdate - ( add_months(last_day(sysdate), -1) + 1 ) ) < 15 THEN
                    v_fecha_prevista_pago := ( add_months(last_day(sysdate), -1) + 1 ) + 14;

                ELSE
                    v_fecha_prevista_pago := last_day(sysdate);
                END IF;

            ELSE
                IF last_day(v_fecha_prevista_pago) = v_fecha_prevista_pago THEN
                    v_fecha_prevista_pago := v_fecha_prevista_pago + 15;
                ELSE
                    v_fecha_prevista_pago := last_day(v_fecha_prevista_pago);
                END IF;
            END IF;

            INSERT INTO cuotas (
                id_cuota,
                fecha_prevista_pago,
                fecha_real_pago,
                monto_cancelado,
                numero_cuota,
                id_plan
            ) VALUES (
                cuotas_seq.NEXTVAL,
                v_fecha_prevista_pago,
                NULL,
                0.0,
                v_numero_cuota,
                v_id_plan
            );

        END LOOP;
    END IF;
END;
;;

/*--------- Trigger para Eliminar Cuotas antes de eliminar Plan, Eliminacion en Cascada ---------*/
CREATE OR REPLACE TRIGGER eliminarcuotas BEFORE
    DELETE ON planes
    FOR EACH ROW
DECLARE
    v_id_plan planes.id_plan%TYPE := :old.id_plan;
BEGIN
    DELETE FROM cuotas
    WHERE
        id_plan = v_id_plan;

END;
;;

/*--------- Trigger para Calcular el Monto de las Horas Extra ---------*/
CREATE OR REPLACE TRIGGER calcularmontohorasextra BEFORE
    UPDATE OF horas_extra_diurnas, horas_extra_nocturnas ON planillas
    FOR EACH ROW
DECLARE
    v_horas_extras_d        planillas.horas_extra_diurnas%TYPE := :new.horas_extra_diurnas;
    v_horas_extras_n        planillas.horas_extra_nocturnas%TYPE := :new.horas_extra_nocturnas;
    v_id_planilla           planillas.id_planilla%TYPE := :old.id_planilla;
    v_monto_horas_extra     planillas.monto_horas_extra%TYPE;
    v_id_empleado           planillas.id_empleado%TYPE := :old.id_empleado;
    v_salario_base_mensual  empleados.salario_base_mensual%TYPE;
    v_salario_hora          FLOAT(126);
    v_horas_trabajo_diarias empleados.horas_trabajo%TYPE;
BEGIN
	--Obtenemos el salario base mensual del empleado
    SELECT
        salario_base_mensual,
        horas_trabajo
    INTO v_salario_base_mensual,
         v_horas_trabajo_diarias
    FROM
        empleados
    WHERE
        id_empleado = v_id_empleado;

	--Calculamos el salario por hora
	--5: dias de trabajo a la semana, 4: semanas del mes:
    v_salario_hora := v_salario_base_mensual / ( v_horas_trabajo_diarias * 5 * 4 ); 

	--Monto Horas Extra Diurnas; Se pagan con un 100% de recargo, o sea el doble
    v_monto_horas_extra := v_salario_hora * v_horas_extras_d * 2;

	--Monto Horas Extra Nocturnas; Se pagan con un 125% de recargo, o sea el 2.25
    v_monto_horas_extra := v_monto_horas_extra + v_salario_hora * v_horas_extras_n * 2.25;

	--Actualizamos el Monto Horas Extra en la Planilla    
    :new.monto_horas_extra := v_monto_horas_extra;

END calcularmontohorasextra;
;;

/*--------- Trigger para Calcular el Monto Comisión ---------*/
CREATE OR REPLACE TRIGGER calcularmontocomision BEFORE 
	UPDATE OF monto_ventas ON planillas
	FOR EACH ROW

DECLARE

CURSOR cur_rangos_comision IS
	SELECT venta_min, venta_max, tasa_comision
	FROM rangos_comision
	WHERE rango_comision_habilitado = 1;

v_monto_ventas planillas.monto_ventas%TYPE := :new.monto_ventas;
v_monto_comision planillas.monto_comision%TYPE DEFAULT 0;

BEGIN

FOR rec_rango_comision IN cur_rangos_comision
LOOP
	IF v_monto_ventas BETWEEN rec_rango_comision.venta_min AND rec_rango_comision.venta_max THEN

		v_monto_comision := v_monto_ventas * rec_rango_comision.tasa_comision; 
		:new.monto_comision := v_monto_comision;

	END IF;

	EXIT WHEN v_monto_comision > 0;

END LOOP;

END;
;;

/* Trigger para la creacion de centro de costo por cada unidad organizaciona que se inserta.
 * Realizado por: Ricardo Estupinian
 * Fecha de creación 17/06/2020
 * Ultima modificación: 17/06/2020
 * */
CREATE OR REPLACE TRIGGER create_centro_costo AFTER
    INSERT ON unidades_organizacionales
    FOR EACH ROW
DECLARE
    v_anio_id anios_laborales.id_anio_laboral%TYPE;
BEGIN
    --Recuperacion del año laboral actual, segun fecha actual
    SELECT id_anio_laboral INTO v_anio_id FROM anios_laborales
    WHERE anio_laboral = TO_CHAR(SYSDATE,'yyyy');

    -- Insercion de centro costo por unidad y por año actual
    INSERT INTO centros_costos(
        id_centro_costo,
        presupuesto_anterior,
        presupuesto_asignado,
        presupuesto_devengado,
        id_anio,
        id_unidad_organizacional
    )
    VALUES(centro_costo_seq.NEXTVAL,0.0,0.0,0.0,v_anio_id,:NEW.id_unidad_organizacional);
END;
;;

/* Trigger para la asignacion de presupuesto a unidades organizacionales.
 * Realizado por: Ricardo Estupinian
 * Fecha de creación 17/06/2020
 * Ultima modificación: 17/06/2020
 * */
CREATE OR REPLACE TRIGGER asignacion_presupuesto_before_insert BEFORE
    INSERT ON planilla.asignaciones_presupuestos
    FOR EACH ROW
DECLARE
    v_id_unidad_org planilla.unidades_organizacionales.id_unidad_organizacional%TYPE;
    v_id_unidad_org_padre planilla.unidades_organizacionales.id_unidad_organizacional%TYPE DEFAULT NULL;
    v_id_anio_laboral planilla.anios_laborales.id_anio_laboral%TYPE;
    v_monto planilla.asignaciones_presupuestos.monto_asignacion%TYPE;
    rec_centro_costo_padre planilla.centros_costos%ROWTYPE;
    rec_centro_costo planilla.centros_costos%ROWTYPE;
BEGIN

    --Asignamos la fecha actual a la asignacion de presupuesto
    :NEW.fecha_asignacion := SYSDATE;

    --Recuperacion de anio actual
    SELECT id_anio_laboral INTO v_id_anio_laboral
    FROM planilla.anios_laborales
    WHERE anio_laboral = TO_CHAR(SYSDATE,'yyyy');

    -- Recuperacion de centro de costo para asignacion de presupuesto
    SELECT * INTO rec_centro_costo
    FROM planilla.centros_costos
    WHERE id_centro_costo = :NEW.id_centro_costo;

    -- Recuperacion de unidad organizacional padre para poder recuperar el centro de costo de la unidad padre en año actual
    SELECT id_unidad_organizacional_padre INTO v_id_unidad_org_padre
    FROM planilla.unidades_organizacionales
    WHERE id_unidad_organizacional = rec_centro_costo.id_unidad_organizacional;


    -- Verificacion de si posee unidad padre
    IF v_id_unidad_org_padre IS NOT NULL THEN
        -- Recuperacion de centro de costo padre, para efectuarle el respectivo incremento o decremento
        SELECT * INTO rec_centro_costo_padre
        FROM planilla.centros_costos
        WHERE id_unidad_organizacional = v_id_unidad_org_padre AND id_anio = v_id_anio_laboral;

        -- Modificamos el monto devengado en la unidad padre
        IF :NEW.es_incremento = 1 THEN
            rec_centro_costo_padre.presupuesto_devengado := rec_centro_costo_padre.presupuesto_devengado + :NEW.monto_asignacion;
        ELSE
            rec_centro_costo_padre.presupuesto_devengado := rec_centro_costo_padre.presupuesto_devengado - :NEW.monto_asignacion;
        END IF;

        -- Persistimos los datos de la unidad organizacional a la que se le efectua la asignacion
        UPDATE planilla.centros_costos SET ROW = rec_centro_costo_padre WHERE id_centro_costo = rec_centro_costo_padre.id_centro_costo;
    END IF;

    -- Efectuamos la asignacion de presupuesto en la unidad correspondiente
    IF :NEW.es_incremento = 1 THEN
        rec_centro_costo.presupuesto_asignado := rec_centro_costo.presupuesto_asignado + :NEW.monto_asignacion;
    ELSE
        rec_centro_costo.presupuesto_asignado := rec_centro_costo.presupuesto_asignado - :NEW.monto_asignacion;
    END IF;

    -- Persistimos los datos de la unidad organizacional a la que se le efectua la asignacion
    UPDATE planilla.centros_costos SET ROW = rec_centro_costo WHERE id_centro_costo = rec_centro_costo.id_centro_costo;

END;
;;

/* Trigger para la fecha de inicio y de finalizacion de un empleado en un puesto dentro de una unidad organizaciona.
 * Realizado por: Ricardo Estupinian
 * Fecha de creación 18/06/2020
 * Ultima modificación: 18/06/2020
 * */
CREATE OR REPLACE TRIGGER  empleado_puesto_unidad_before_insert BEFORE
    INSERT ON planilla.empleados_puestos_unidades
    FOR EACH ROW
DECLARE
    -- Variable que almacenara el id del empleado_puesto_unidad vigente del empleado requerido
    v_id_epu_old planilla.empleados_puestos_unidades.id_empleado_puesto_unidad%TYPE;
BEGIN
    --Recuperamos el empleado_puesto_unidad vigente del empleado requerido
    SELECT id_empleado_puesto_unidad INTO v_id_epu_old
    FROM planilla.empleados_puestos_unidades
    WHERE id_empleado = :NEW.id_empleado AND fecha_fin IS NULL;

    -- Se agrega la fecha actual en fecha inicio de nuevo registro
    :NEW.fecha_inicio := SYSDATE;

    -- Realizamos el update al puesto anterior
    UPDATE planilla.empleados_puestos_unidades
    SET fecha_fin = SYSDATE
    WHERE id_empleado_puesto_unidad = v_id_epu_old;

    -- Controlando excepcion de NOT_DATA FOUND
    EXCEPTION
        WHEN no_data_found THEN
        :NEW.fecha_inicio := SYSDATE;
END;
;;

/*----------------------Trigger para Actualizar Monto por Dias Festivos Trabajados ------------------*/
CREATE OR REPLACE TRIGGER actualizar_monto_dias_festivos FOR
    INSERT OR DELETE ON planillas_dias_festivos
COMPOUND TRIGGER
    v_monto_dia_festivo      planillas.monto_dias_festivos%TYPE;
    v_planilla_id            planillas.id_planilla%TYPE;
    v_empleado_id            empleados.id_empleado%TYPE;
    v_salario_base_mensual   empleados.salario_base_mensual%TYPE;
    v_horas_trabajo_diarias  empleados.horas_trabajo%TYPE;
    v_salario_base_hora      FLOAT(126);
    AFTER EACH ROW IS BEGIN
        --Obtenemos el id de planilla a la que se le agrego o elimino un dia festivo 
	    CASE
            --En caso de Insercion
	    	WHEN INSERTING THEN
        		v_planilla_id := :new.id_planilla;
            --En caso de Eliminar    
        	WHEN DELETING THEN
        		v_planilla_id := :old.id_planilla;
        END CASE;
        
        --Ahora obtenemos el Empleado (id_empleado) al que pertenece la Planilla
        SELECT
            id_empleado
        INTO v_empleado_id
        FROM
            planillas
        WHERE
            id_planilla = v_planilla_id;
              
        --De Este empleado obtenemos el salario base mensual y las horas de trabajo diarias
        --Esto con el fin de calcular el Salario base por Hora
        SELECT
            salario_base_mensual,
            horas_trabajo
        INTO
            v_salario_base_mensual,
            v_horas_trabajo_diarias
        FROM
            empleados
        WHERE
            id_empleado = v_empleado_id;
        
        --Procedemos a calcular el Salario base por hora    
        --5: dias de trabajo a la semana, 4: semanas del mes.
        v_salario_base_hora := v_salario_base_mensual / ( v_horas_trabajo_diarias * 5 * 4 ); 
        
        --Ahora calculamos cuanto es el monto por un dia festivo trabajado por el Empleado
        --Este es el monto que se sumara o restara al monto de dias festivos de Planilla, ya que
        --esto se va a ejecutar por cada INSERT o DELETE
        v_monto_dia_festivo := v_horas_trabajo_diarias * v_salario_base_hora;
        
        CASE
            --En el caso de INSERT este monto se suma al monto total de Dias Festivos
        	WHEN INSERTING THEN
		        UPDATE planillas
		        SET
		            monto_dias_festivos = monto_dias_festivos + v_monto_dia_festivo
		        WHERE
		            id_planilla = v_planilla_id;
            --En el caso de DELETE este monto se resta al monto total de Dias Festivos        
		    WHEN DELETING THEN
		    	UPDATE planillas
		        SET
		            monto_dias_festivos = monto_dias_festivos - v_monto_dia_festivo
		        WHERE
		            id_planilla = v_planilla_id;
        END CASE;    

    END AFTER EACH ROW;
END actualizar_monto_dias_festivos;
;;

/* Paquete que contiene la variable public donde se almacena el id de año pasado, este se utiliza para crear centros de costo
 * en un TRIGGER AFTER INSERT ON ANIOS_LABORALES
 * Realizado por: Ricardo Estupinian
 * Fecha de creación 27/06/2020
 * Ultima modificación: 27/06/2020
 * */
CREATE OR REPLACE PACKAGE planilla.pkg_anio_laboral IS
    v_id_anio_anterior planilla.anios_laborales.id_anio_laboral%TYPE;
END;
;;

/* Trigger BEFORE INSERT en la tabla ANIOS_LABORALES, que asigna un valor a la variable public del paquete PKG_ANIO_LABORAL.
 * Realizado por: Ricardo Estupinian
 * Fecha de creación 27/06/2020
 * Ultima modificación: 27/06/2020
 * */
CREATE OR REPLACE TRIGGER planilla.GET_ANIO_ANTERIOR BEFORE
    INSERT ON planilla.anios_laborales
    FOR EACH ROW
BEGIN
    -- Buscamos el anio laboral anterior al nuevo y lo almacenamos en la variable publica dentro que esta en el paguete
    SELECT id_anio_laboral INTO planilla.pkg_anio_laboral.v_id_anio_anterior
    FROM planilla.anios_laborales
    WHERE anio_laboral = TO_CHAR(SYSDATE-366,'yyyy'); -- Se usa 366 por si es anio bisiesto

    -- Manejo de excepcion por si no encuentra un año anterior
    -- Este caso se dara solo en la primera vez que se cree un anio laboral
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
        planilla.pkg_anio_laboral.v_id_anio_anterior := NULL;
END;
;;

/* Trigger AFTER INSERT en la tabla ANIOS_LABORALES, para crear los nuevos CENTROS DE COSTOS, cuando se crea un nuevo año laboral.
 * Realizado por: Ricardo Estupinian
 * Fecha de creación 27/06/2020
 * Ultima modificación: 27/06/2020
 * */
CREATE OR REPLACE TRIGGER planilla.CREATE_CC_AFTER_INSERT_ANIO AFTER
    INSERT ON planilla.anios_laborales
    FOR EACH ROW
DECLARE
    -- Variables
    v_monto_asignacion planilla.centros_costos.presupuesto_anterior%TYPE;
    v_id_anio_anteorior planilla.anios_laborales.id_anio_laboral%TYPE;

    -- Registro
    rec_nuevo_cc planilla.centros_costos%ROWTYPE;
BEGIN
    -- Referenciamos la variable global para obtener el id del año anterior
    v_id_anio_anteorior := planilla.pkg_anio_laboral.v_id_anio_anterior;

    IF v_id_anio_anteorior IS NOT NULL THEN
        -- Se usa un FOR para recorrer un cursor implicito usando un registro implicito
        -- El dato se recupera usando la variable global almacenada en el paquete
        FOR rec_centro_costo IN (   SELECT *
                                    FROM planilla.centros_costos
                                    WHERE id_anio = v_id_anio_anteorior)
        LOOP
            -- Resta de presupuesto asignado y devengado del centro de costos del anio pasado que sera la nueva asignacion del nuevo cc.
            v_monto_asignacion := rec_centro_costo.presupuesto_asignado - rec_centro_costo.presupuesto_devengado;

            -- Asignacion de valores al registro que se insertara como nuevo centro de costos en el anio nuevo
            rec_nuevo_cc.id_centro_costo := planilla.centro_costo_seq.NEXTVAL;
            rec_nuevo_cc.presupuesto_anterior := v_monto_asignacion;
            rec_nuevo_cc.presupuesto_asignado := v_monto_asignacion;
            rec_nuevo_cc.presupuesto_devengado := 0.00;
            rec_nuevo_cc.id_anio := :NEW.id_anio_laboral;
            rec_nuevo_cc.id_unidad_organizacional := rec_centro_costo.id_unidad_organizacional;

            -- Guardamos el registro
             INSERT INTO planilla.centros_costos VALUES rec_nuevo_cc;
        END LOOP;
        -- FIN LOOP
    ELSE
        -- Recorremos un cursor explicito con un FOR utilizando un registro implicito
        FOR rec_unidad IN ( SELECT *
                            FROM planilla.unidades_organizacionales )
        LOOP
            rec_nuevo_cc.id_centro_costo := centro_costo_seq.NEXTVAL;
            rec_nuevo_cc.presupuesto_anterior := 0.00;
            rec_nuevo_cc.presupuesto_asignado := 0.00;
            rec_nuevo_cc.presupuesto_devengado := 0.00;
            rec_nuevo_cc.id_anio := :NEW.id_anio_laboral;
            rec_nuevo_cc.id_unidad_organizacional := rec_unidad.id_unidad_organizacional;

            -- Guardamos el registro
            INSERT INTO planilla.centros_costos VALUES rec_nuevo_cc;
        END LOOP;
        -- FIN LOOP
    END IF;
END;
;;