/* Funcion que permite ejecutar una cuota de plan de ingreso/descuento,devuelve el monto de la cuota
 * @parametro: id del empleado
 * @parametro: periodicidad de la planilla. 15 si es quincenal, 30 si es mensual
 * @parametro: si es descuento o ingreso. 0 si es ingreso, 1 si es descuento
 * Realizado por: Edwin Palacios
 * Fecha de creación 20/06/2020
 * Ultima modificación: 29/06/2020
 * */
CREATE OR REPLACE FUNCTION obtener_plan
    --parametros
    (p_id_empleado              empleados.id_empleado%TYPE, 
     p_periodicidad_planilla    anios_laborales.periodicidad%TYPE,
     p_es_egreso                planes.es_egreso%TYPE) 
    
    RETURN  empleados.salario_base_mensual%TYPE -- return el monto de la/s cuota/s
    IS
   
    -- cursor de planes del empleado
    CURSOR cur_planes 
        (p_id_empleado  empleados.id_empleado%TYPE, 
         p_es_egreso    planes.es_egreso%TYPE) IS 
        SELECT p.*
        FROM planes p  
        WHERE (p.id_empleado = p_id_empleado AND p.es_egreso = p_es_egreso AND p.es_activo = 1); 
    
    -- declaracion de variables
    v_cuota         cuotas%rowtype;
    v_monto_plan    empleados.salario_base_mensual%TYPE := 0; 
    
BEGIN
    -- se recorre cada uno de los planes del empleado
    FOR rec_plan IN cur_planes(p_id_empleado, p_es_egreso)
    LOOP
        -- Se obtiene la primera cuota
        SELECT c.* INTO v_cuota
            FROM cuotas c 
            WHERE (c.id_plan = rec_plan.id_plan AND c.fecha_real_pago IS NULL AND ROWNUM = 1);
            
        -- Caso 1: Si el plan de cuotas tiene la misma periodicidad que el de planillas
        IF rec_plan.periodicidad_plan = p_periodicidad_planilla THEN
            UPDATE cuotas set monto_cancelado= rec_plan.monto_cuota, fecha_real_pago = SYSDATE
            WHERE id_cuota = (v_cuota.id_cuota);
            v_monto_plan := v_monto_plan + (rec_plan.monto_cuota - v_cuota.monto_cancelado);
            
        -- Caso 2: Si el plan de cuotas es de 15 de dias y el de planillas 30, en este caso se efectuan dos cuotas 
        ELSIF rec_plan.periodicidad_plan = 15 AND  p_periodicidad_planilla = 30 THEN    
            UPDATE cuotas set monto_cancelado= rec_plan.monto_cuota, fecha_real_pago = SYSDATE
            WHERE id_cuota IN (v_cuota.id_cuota, v_cuota.id_cuota + 1) AND fecha_prevista_pago <= LAST_DAY(v_cuota.fecha_prevista_pago);
            v_monto_plan := v_monto_plan + (rec_plan.monto_cuota - v_cuota.monto_cancelado);   
            
            IF(SQL%ROWCOUNT = 2) THEN
                v_monto_plan := v_monto_plan + rec_plan.monto_cuota;  
            END IF;
              
        -- Caso 3: Si el plan de cuotas es de 30 de dias y el de planillas 15     
        ELSIF rec_plan.periodicidad_plan = 30 AND  p_periodicidad_planilla = 15 THEN
            -- Si el monto cancelado es 0 significa que es primera que se efectua la cuota, caso contrario, ya se habrá efectuado la mitad de la cuota
            IF v_cuota.monto_cancelado = 0 THEN
                UPDATE cuotas set monto_cancelado= (rec_plan.monto_cuota/2)
                WHERE id_cuota = (v_cuota.id_cuota);
                v_monto_plan := v_monto_plan + (rec_plan.monto_cuota/2);
            ELSE
                UPDATE cuotas set monto_cancelado= (rec_plan.monto_cuota), fecha_real_pago = SYSDATE
                WHERE id_cuota = (v_cuota.id_cuota);
                v_monto_plan := v_monto_plan + (rec_plan.monto_cuota - v_cuota.monto_cancelado);
            END IF;     
        END IF;
        
        -- Si el plan ya no tiene cuotas se setea como un plan ya no activo
        UPDATE planes set es_activo = 0
        WHERE id_plan =  rec_plan.id_plan AND not exists (  SELECT c.id_cuota
                                                            FROM cuotas c 
                                                            WHERE (c.id_plan = rec_plan.id_plan AND c.fecha_real_pago IS NULL AND ROWNUM = 1));
    END LOOP;
    RETURN v_monto_plan;
END;
;;
;;

/* Funcion que permite calcular la renta en base al monto imponible de renta y la periodicidad
 * @parametro: p_monto
 * @parametro: p_periodicidad. 15 si es quincenal, 30 si es mensual
 * Realizado por: Edwin Palacios
 * Fecha de creación 27/06/2020
 * Ultima modificación: 27/06/2020
 * */
CREATE OR REPLACE FUNCTION calcular_renta(p_monto  planillas.renta%TYPE, p_periodicidad anios_laborales.periodicidad%TYPE)
    RETURN  empleados.salario_base_mensual%TYPE
    IS
    -- cursor de rangos
    CURSOR cur_rangos_renta 
        (p_periodicidad anios_laborales.periodicidad%TYPE)  
        IS 
        SELECT *
        FROM rangos_renta
        WHERE (periodicidad_renta = p_periodicidad); 
    -- variables
    v_renta     planillas.renta%TYPE := 0;
BEGIN
    FOR rec_rangos_renta IN cur_rangos_renta(p_periodicidad) 
    LOOP
        IF p_monto >= rec_rangos_renta.salario_min AND p_monto <= rec_rangos_renta.salario_max THEN
           v_renta :=  (p_monto - rec_rangos_renta.exceso)*(rec_rangos_renta.porcentaje_renta/100) + rec_rangos_renta.cuota_fija;    
        END IF;
    END LOOP;
    return v_renta;
END;
;;