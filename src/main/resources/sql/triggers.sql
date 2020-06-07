 /*----TRIGGER PARA CREAR LOS PERIODOS CUANDO SE CREA UN ANIO LABORAL----*/
CREATE OR REPLACE TRIGGER anio_laboral_after_insert AFTER
    INSERT ON anios_laborales
    FOR EACH ROW
DECLARE
    v_anio_actual    VARCHAR2(4);
    v_anio_periodo   VARCHAR2(4);
    v_fecha_inicio   periodos.fecha_inicio%TYPE;
    v_fecha_final    periodos.fecha_final%TYPE;
BEGIN
    SELECT
        to_char(sysdate, 'YYYY')
    INTO v_anio_actual
    FROM
        dual;

    SELECT
        trunc(sysdate, 'YEAR')
    INTO v_fecha_inicio
    FROM
        dual;

    v_anio_periodo := to_char(v_fecha_inicio, 'YYYY');
    
    IF :new.periodicidad = 30 THEN
        v_fecha_final := last_day(v_fecha_inicio);
    ELSE
        v_fecha_final := v_fecha_inicio + 14;
    END IF;

    CASE
        WHEN :new.periodicidad = 15 THEN
            WHILE v_anio_actual = v_anio_periodo LOOP
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
                    0,
                    :new.id_anio_laboral
                );

                v_fecha_inicio := v_fecha_final + 1;
                v_anio_periodo := to_char(v_fecha_inicio, 'YYYY');
                
                IF v_fecha_inicio = trunc(v_fecha_inicio, 'MONTH') THEN
                    v_fecha_final := v_fecha_inicio + 14;
                ELSE
                    v_fecha_final := last_day(v_fecha_inicio);
                END IF;

            END LOOP;
        WHEN :new.periodicidad = 30 THEN
            WHILE v_anio_actual = v_anio_periodo LOOP
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
                    0,
                    :new.id_anio_laboral
                );

                v_fecha_inicio := v_fecha_final + 1;
                v_anio_periodo := to_char(v_fecha_inicio, 'YYYY');
                v_fecha_final := last_day(v_fecha_inicio);
            END LOOP;
    END CASE;
END;
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
BEGIN
	--Obtenemos el salario base mensual del empleado
    SELECT
        salario_base_mensual
    INTO v_salario_base_mensual
    FROM
        empleados
    WHERE
        id_empleado = v_id_empleado;

	--Calculamos el salario por hora
	--Son 44 horas de trabajo semanales y 4 semanas de trabajo, 44*4=176

    v_salario_hora := v_salario_base_mensual / 176;

	--Monto Horas Extra Diurnas; Se pagan con un 100% de recargo, o sea el doble
    v_monto_horas_extra := v_salario_hora * v_horas_extras_d * 2;

	--Monto Horas Extra Nocturnas; Se pagan con un 125% de recargo, o sea el 2.25
    v_monto_horas_extra := v_monto_horas_extra + v_salario_hora * v_horas_extras_n * 2.25;

	--Actualizamos el Monto Horas Extra en la Planilla    
    :new.monto_horas_extra := v_monto_horas_extra;

END;
;;