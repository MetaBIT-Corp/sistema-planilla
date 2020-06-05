/*-----PROCEDIMIENTO PARA BORRAR TODOS LOS SEQUENCES (AGREGAR SUS SEQUENCES AL ARRAY)---------*/
DECLARE
   TYPE array_sequences IS VARRAY(3) OF VARCHAR2(30);
   ARRAY array_sequences := array_sequences('periodos_seq', 'cuotas_seq');
BEGIN
  FOR i IN 1..ARRAY.COUNT
    Loop
        EXECUTE IMMEDIATE 'DROP SEQUENCE '|| ARRAY(i) ||'';
    End Loop;
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
End;
;;

/*---SEQUENCE PARA EL ID DE LA TABLA PERIODOS*/
CREATE SEQUENCE periodos_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;;

/*---SEQUENCE PARA EL ID DE LA TABLA CUOTAS*/
CREATE SEQUENCE cuotas_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;;