/* PUESTOS */
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (1, 'Descripción del puesto', 1 , 'Administrador Presupuestario', 5000, 1200, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (2, 'Descripción del puesto', 1 , 'Gerente de Departamento', 4000, 800, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (3, 'Descripción del puesto', 1 , 'Jefe de planilla', 1000, 360, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (4, 'Descripción del puesto', 1 , 'Analista Financiero', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (5, 'Descripción del puesto', 0 , 'Analista de Sistemas', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (6, 'Descripción del puesto', 1 , 'Auxiliar de planilla', 1000, 360, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (7, 'Descripción del puesto', 0 , 'Asesor Legal', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (8, 'Descripción del puesto', 1 , 'Contador', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (9, 'Descripción del puesto', 1 , 'Auxiliar Contable', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (10, 'Descripción del puesto', 1 , 'Secretaria', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (11, 'Descripción del puesto', 0 , 'Programador', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (12, 'Descripción del puesto', 1 , 'Soporte técnico', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (13, 'Descripción del puesto', 1 , 'Técnico de Redes', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (14, 'Descripción del puesto', 1 , 'Administrador de Bases de Datos', 1000, 360, 0);

/*PROFESIONES*/
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (1, 'Ingeniero en Sistemas Informáticos', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (2, 'Ingeniero en Redes Informáticas', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (3, 'Ingeniero Electricista', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (4, 'Ingeniero Industrial', 1, 0);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (5, 'Licenciado en Administración de Empresas', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (6, 'Licenciado en Contaduría Pública', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (7, 'Licenciado en Economía', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (8, 'Técnico en Sistemas Informáticos', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (9, 'Técnico en Base de Datos', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (10, 'Técnico en Redes Computacionales', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (11, 'Técnico en Hardware Computacional', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (12, 'Plomero', 0, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (13, 'Chofer', 0, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (14, 'Pintor', 0, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (15, 'Licenciado en Psicología', 1, 1);
INSERT INTO profesiones(id_profesion, profesion, es_profesion, profesion_habilitada) VALUES (16, 'Licenciado en Ciencias Jurídicas', 1, 1);


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
		   INTO	RECURSOS VALUES(5, 'PUESTO')
		   INTO	RECURSOS VALUES(6, 'PROFESION')
		   INTO	RECURSOS VALUES(7, 'TIPOMOVIMIENTO')
SELECT * FROM DUAL;

INSERT ALL INTO ROLES VALUES(1, 'ROLE_ADMIN')
		   INTO ROLES VALUES(2, 'ROLE_JEFE')
		   INTO ROLES VALUES(3, 'ROLE_AUXILIAR')
		   INTO ROLES VALUES(4, 'ROLE_PRESUPUESTO')
		   INTO ROLES VALUES(5, 'ROLE_USER')
SELECT * FROM DUAL;

INSERT ALL INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(1,1,1,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(2,2,1,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(3,3,1,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(4,4,1,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(5,5,1,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(6,1,2,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(7,2,2,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(8,3,2,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(9,4,2,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(10,5,2,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(11,1,3,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(12,2,3,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(13,3,3,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(14,4,3,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(15,5,3,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(16,1,4,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(17,2,4,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(18,3,4,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(19,4,4,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(20,5,4,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(21,1,5,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(22,2,5,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(23,3,5,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(24,4,5,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(25,5,5,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(26,1,6,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(27,2,6,1)
		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(28,3,6,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(29,4,6,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(30,5,6,1)

  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(31,1,1,5)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(32,1,2,5)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(33,1,3,5)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(34,1,4,5)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(35,1,5,5)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(36,1,6,5)
  		   
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(37,1,7,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(38,2,7,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(39,3,7,1)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(40,4,7,1)
  		   
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(41,1,7,2)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(42,2,7,2)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(43,3,7,2)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(44,4,7,2)
  		   
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(45,1,7,3)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(46,2,7,3)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(47,3,7,3)
  		   INTO ROLES_RECURSOS_PRIVILEGIOS VALUES(48,4,7,3)
SELECT * FROM DUAL;

INSERT ALL INTO USUARIOS VALUES(1,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'admin')
           INTO USUARIOS VALUES(2,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'jefe')
           INTO USUARIOS VALUES(3,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'auxiliar')
           INTO USUARIOS VALUES(4,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'presupuestario')
           INTO USUARIOS VALUES(5,0,0,1,0,'$2a$10$.wYsFS0/zPkY3.LnN8F/reqimatDtOS71.5Uzh/RzlF718/aFO7oS',1,'user')
SELECT * FROM DUAL;

INSERT ALL INTO USUARIOS_ROLES VALUES(1,1)
  		   INTO USUARIOS_ROLES VALUES(2,2)
  		   INTO USUARIOS_ROLES VALUES(3,3)
  		   INTO USUARIOS_ROLES VALUES(4,4)
  		   INTO USUARIOS_ROLES VALUES(5,5)
SELECT * FROM DUAL;

/*-------------------------------------------------------------------------------------*/
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(1, 'Hombre');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(2, 'Mujer');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(3, 'Lesbiana');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(4, 'Gay');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(5, 'Bisexual');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(6, 'Transexual');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(7, 'Intersexual');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(8, 'Queer');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(9, 'Pansexual');
INSERT INTO GENEROS (ID_GENERO, GENERO) VALUES(10, 'Asexual');

--Direcciones
insert into departamentos (id_departamento,departamento) values (1, 'San Salvador');
insert into departamentos (id_departamento,departamento) values (2, 'Usulutan');

insert into municipios (id_municipio,municipio,id_departamento) values (1, 'San Salvador', 1);
insert into municipios (id_municipio,municipio,id_departamento) values (2, 'San Martin', 1);
insert into municipios (id_municipio,municipio,id_departamento) values (3, 'Alegria', 2);
insert into municipios (id_municipio,municipio,id_departamento) values (4, 'California', 2);

insert into direcciones (id_direccion,calle,complemento,numero_casa,urbanizacion,id_municipio) values (78, 'Campos', 'Condominio San Francisco', '4-A', 'La loma', 3);

insert into empresas (id_empresa,correo_empresa,empresa,nic_empresa,nit_empresa,page,pagina_empresa,telefono,id_direccion) values(1,'metabit@gmail.com','MetaBIT','000001','000002','@MetaBIT','www.metabit.com','2237-2828',78);


--Estado Civil
insert into estados_civiles values(1,'Soltero/a');
insert into estados_civiles values(2,'Casado/a');
insert into estados_civiles values(3,'Divorciado/a');
insert into estados_civiles values(4,'Viudo/a');
insert into estados_civiles values(5,'Union Libre');


--Empleados

insert into empleados(ID_EMPLEADO,APELLIDO_CASADA,APELLIDO_MATERNO,APELLIDO_PATERNO,CODIGO,CORREO_INSTITUCIONAL,CORREO_PERSONAL,EMPLEADO_HABILITADO,ES_ADMINISTRATIVO,FECHA_NACIMIENTO,HORAS_TRABAJO,NOMBRE_PRIMERO,NOMBRE_SEGUNDO,SALARIO_BASE_MENSUAL,ID_DIRECCION,ID_ESTADO_CIVIL,ID_GENERO,ID_USUARIO) 
values(1,null,'Lopez','Estupinian','EL16002','el16002@ues.edu.sv','el16002@gmail.com',1,0,'28-JUN-97',20,'Ricardo','Bladimir',2000,78,1,1,5);

insert into empleados(ID_EMPLEADO,APELLIDO_CASADA,APELLIDO_MATERNO,APELLIDO_PATERNO,CODIGO,CORREO_INSTITUCIONAL,CORREO_PERSONAL,EMPLEADO_HABILITADO,ES_ADMINISTRATIVO,FECHA_NACIMIENTO,HORAS_TRABAJO,NOMBRE_PRIMERO,NOMBRE_SEGUNDO,SALARIO_BASE_MENSUAL,ID_DIRECCION,ID_ESTADO_CIVIL,ID_GENERO,ID_USUARIO) 
values(2,null,'Lopez','Estupinian','EP16002','el16007@ues.edu.sv','el16007@gmail.com',1,0,'28-JUN-97',20,'Ricardo','Bladimir',2000,78,1,1,null);

insert into empleados_documentos(ID_EMPLEADO_DOCUMENTO, CODIGO_DOCUMENTO, ID_EMPLEADO, ID_TIPO_DOCUMENTO) values (1,'15840523-1',1,1);
insert into empleados_documentos(ID_EMPLEADO_DOCUMENTO, CODIGO_DOCUMENTO, ID_EMPLEADO, ID_TIPO_DOCUMENTO) values (2,'0810-123321-100-4',1,2);

insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(1,1,1);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(2,1,4);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(3,1,6);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(4,1,7);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(5,1,10);

--insert into empleados values(2,null,'Lopez','Estupinian','EL14002','el14002@ues.edu.sv',0,0,'17-MAR-95',2,'Estefany','Lizeida',2000,null,null,null);

--Empleado Puesto Unidad
insert into empleados_puestos_unidades	(id_empleado_puesto_unidad, id_empleado, id_puesto) 
								 values (1, 1, 1);
insert into empleados_puestos_unidades	(id_empleado_puesto_unidad, id_empleado, id_puesto) 
								 values (2, 2, 2);
										
--Tipos Movimiento
insert into tipos_movimiento	(id_movimiento, es_descuento, es_fijo, monto_base, movimiento, porcentaje_movimiento, tipo_movimiento_habilitado)
						values	(1, 1, 1, 0.00, 'Impuesto sobre la Renta', 0, 1);
insert into tipos_movimiento	(id_movimiento, es_descuento, es_fijo, monto_base, movimiento, porcentaje_movimiento, tipo_movimiento_habilitado)
						values	(2, 1, 1, 0.00, 'ISSS', 7.25 , 1);
insert into tipos_movimiento	(id_movimiento, es_descuento, es_fijo, monto_base, movimiento, porcentaje_movimiento, tipo_movimiento_habilitado)
						values	(3, 1, 1, 0.00, 'AFP', 3 , 1);
insert into tipos_movimiento	(id_movimiento, es_descuento, es_fijo, monto_base, movimiento, porcentaje_movimiento, tipo_movimiento_habilitado)
						values	(4, 0, 0, 0.00, 'Aguinaldo', 70 , 1);
insert into tipos_movimiento	(id_movimiento, es_descuento, es_fijo, monto_base, movimiento, porcentaje_movimiento, tipo_movimiento_habilitado)
						values	(5, 0, 0, 500.00 , 'Bono', 0, 1);
*/