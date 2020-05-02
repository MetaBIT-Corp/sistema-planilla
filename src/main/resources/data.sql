INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (1, 'Descripción del puesto', 'Gerente', 5000, 1200, 1);

INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (2, 'Descripción del puesto', 'Jefe de Departamento', 4000, 800, 1);

INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (3, 'Descripción del puesto', 'Secretaria', 1000, 360, 0);

/*PROFESIONES*/

INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (1, 'Ingeniero en Sistemas Informáticos', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (2, 'Ingeniero en Redes Informáticas', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (3, 'Ingeniero Electricista', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (4, 'Ingeniero Industrial', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (5, 'Licenciado en Administración de Empresas', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (6, 'Licenciado en Contaduría Pública', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (7, 'Licenciado en Economía', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (8, 'Técnico en Sistemas Informáticos', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (9, 'Técnico en Base de Datos', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (10, 'Técnico en Redes Computacionales', 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion) VALUES (11, 'Técnico en Hardware Computacional', 1);

/*TIPOS DE DOCUMENTO*/

INSERT INTO tipos_documento(id_tipo_documento, tipo_documento, formato, tipo_documento_habilitado) VALUES (1, 'DUI','00000000-0',1);
INSERT INTO tipos_documento(id_tipo_documento, tipo_documento, formato, tipo_documento_habilitado) VALUES (2, 'NIT','0000-000000-000-0',1);
INSERT INTO tipos_documento(id_tipo_documento, tipo_documento, formato, tipo_documento_habilitado) VALUES (3, 'NUP','000000000000',1);
INSERT INTO tipos_documento(id_tipo_documento, tipo_documento, formato, tipo_documento_habilitado) VALUES (4, 'ISSS','000000000',1);
INSERT INTO tipos_documento(id_tipo_documento, tipo_documento, formato, tipo_documento_habilitado) VALUES (5, 'Licencia de Conducir','0000-000000-000-0',1);

/*----------------------PRIVILEGIOS, RECURSOS, ROLES, USUARIO--------------------------*/
INSERT ALL INTO PRIVILEGIOS VALUES(1, 'INDEX')
           INTO PRIVILEGIOS VALUES(2, 'EDIT') 
           INTO PRIVILEGIOS VALUES(3, 'CREATE') 
           INTO PRIVILEGIOS VALUES(4, 'DELETE')
           INTO PRIVILEGIOS VALUES(5, 'SHOW')
SELECT * FROM DUAL;

INSERT ALL INTO RECURSOS VALUES(1, 'EMPLEADO')
		   INTO	RECURSOS VALUES(2, 'EMPRESA')
		   INTO	RECURSOS VALUES(3, 'GENERO')
		   INTO	RECURSOS VALUES(4, 'TIPODOCUMENTO')
SELECT * FROM DUAL;

INSERT ALL INTO ROLES VALUES(1, 'ROLE_ADMIN')
		   INTO ROLES VALUES(2, 'ROLE_JEFE')
		   INTO ROLES VALUES(3, 'ROLE_AUXILIAR')
		   INTO ROLES VALUES(4, 'ROLE_PRESUPUESTO')
SELECT * FROM DUAL;

INSERT ALL INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(1,1,1,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(2,2,2,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(3,3,3,2)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(4,4,4,2)
SELECT * FROM DUAL;

INSERT ALL INTO USUARIOS VALUES(1,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'admin')
           INTO USUARIOS VALUES(2,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'user')
SELECT * FROM DUAL;

INSERT ALL INTO USUARIOS_ROLES VALUES(1,1)
  		   INTO USUARIOS_ROLES VALUES(2,2)
SELECT * FROM DUAL;

/*-------------------------------------------------------------------------------------*/
