INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (1, 'Descripción del puesto', 'Gerente', 5000, 1200, 1);

INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (2, 'Descripción del puesto', 'Jefe de Departamento', 4000, 800, 1);

INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (3, 'Descripción del puesto', 'Secretaria', 1000, 360, 0);

INSERT ALL INTO PRIVILEGIOS VALUES(1, 'READ')
           INTO PRIVILEGIOS VALUES(2, 'WRITE') 
           INTO PRIVILEGIOS VALUES(3, 'CREATE') 
           INTO PRIVILEGIOS VALUES(4, 'DELETE')
SELECT * FROM DUAL;

INSERT ALL INTO RECURSOS VALUES(1, 'PROFESION')
		   INTO	RECURSOS VALUES(2, 'GENERO')
		   INTO	RECURSOS VALUES(3, 'PUESTO')
		   INTO	RECURSOS VALUES(4, 'EMPLEADO')
SELECT * FROM DUAL;

INSERT ALL INTO ROLES VALUES(1, 'ROLE_ADMIN')
		   INTO ROLES VALUES(2, 'ROLE_USER')
SELECT * FROM DUAL;

INSERT ALL INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(1,1,1,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(2,2,2,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(3,3,3,2)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(4,4,4,2)
SELECT * FROM DUAL;

INSERT ALL INTO USUARIOS VALUES(1,1,1,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'admin')
           INTO USUARIOS VALUES(2,1,1,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'user')
SELECT * FROM DUAL;

INSERT ALL INTO USUARIOS_ROLES VALUES(1,1)
  		   INTO USUARIOS_ROLES VALUES(2,2)
SELECT * FROM DUAL;