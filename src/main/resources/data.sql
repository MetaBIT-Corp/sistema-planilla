/* PUESTOS */
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (1, 'Descripción del puesto', 1 , 'Administrador Presupuestario', 1, 5000, 1200, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (2, 'Descripción del puesto', 1 , 'Gerente de Departamento', 1 , 4000, 800, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (3, 'Descripción del puesto', 1 , 'Jefe de planilla', 1 , 1000, 360, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (4, 'Descripción del puesto', 1 , 'Analista Financiero', 0 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (5, 'Descripción del puesto', 0 , 'Analista de Sistemas', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (6, 'Descripción del puesto', 1 , 'Auxiliar de planilla', 1 , 1000, 360, 1);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (7, 'Descripción del puesto', 0 , 'Asesor Legal', 0 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (8, 'Descripción del puesto', 1 , 'Contador', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (9, 'Descripción del puesto', 1 , 'Auxiliar Contable', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (10, 'Descripción del puesto', 1 , 'Secretaria', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (11, 'Descripción del puesto', 0 , 'Programador', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (12, 'Descripción del puesto', 1 , 'Soporte técnico', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (13, 'Descripción del puesto', 1 , 'Técnico de Redes', 1 , 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, es_administrativo, puesto, puesto_habilitado, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (14, 'Descripción del puesto', 1 , 'Administrador de Bases de Datos', 1 , 1000, 360, 0);

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
values(300,null,'Lopez','Estupinian','EL16002','el16002@metabit.tech.sv','el16002@gmail.com',1,0,'28-JUN-97',20,'Ricardo','Bladimir',2000,78,1,1,5);

insert into empleados(ID_EMPLEADO,APELLIDO_CASADA,APELLIDO_MATERNO,APELLIDO_PATERNO,CODIGO,CORREO_INSTITUCIONAL,CORREO_PERSONAL,EMPLEADO_HABILITADO,ES_ADMINISTRATIVO,FECHA_NACIMIENTO,HORAS_TRABAJO,NOMBRE_PRIMERO,NOMBRE_SEGUNDO,SALARIO_BASE_MENSUAL,ID_DIRECCION,ID_ESTADO_CIVIL,ID_GENERO,ID_USUARIO) 
values(301,null,'Palacios','Amaya','EP16014','el16007@metabit.tech.sv','el16007@hotmail.com',1,0,'28-JUN-97',20,'Edwin','Joel',2000,78,1,1,null);

insert into empleados_documentos(ID_EMPLEADO_DOCUMENTO, CODIGO_DOCUMENTO, ID_EMPLEADO, ID_TIPO_DOCUMENTO) values (101,'15840523-1',300,1);
insert into empleados_documentos(ID_EMPLEADO_DOCUMENTO, CODIGO_DOCUMENTO, ID_EMPLEADO, ID_TIPO_DOCUMENTO) values (102,'0810-123321-100-4',301,2);

insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(101,300,1);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(102,300,4);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(103,300,6);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(104,301,7);
insert into empleados_profesiones(ID_EMPLEADO_PROFESION, ID_EMPLEADO, ID_PROFESION)values(105,301,10);


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

--Unidades organizacionales
insert into unidades_organizacionales values(500,100,'Gerencia General',null);
insert into unidades_organizacionales values(501,101,'Departamento de RRHH',500);
insert into unidades_organizacionales values(502,101,'Departamento de Ventas',500);
insert into unidades_organizacionales values(503,101,'Departamento de Informatica',500);
insert into unidades_organizacionales values(504,101,'Departamento de Administrativo',500);
insert into unidades_organizacionales values(505,101,'Departamento Financiero',500);
insert into unidades_organizacionales values(506,102,'Area de Tecnologias de Informacion',503);
insert into unidades_organizacionales values(507,102,'Area de Desarrollo de Software',503);
insert into unidades_organizacionales values(508,102,'Area de Redes Informaticas',503);
insert into unidades_organizacionales values(509,103,'Seccion de Testing/QA',507);
insert into unidades_organizacionales values(510,103,'Seccion de Desarrolladores Backend',507);
insert into unidades_organizacionales values(511,103,'Seccion de Desarrolladores Frontend',507);*/

--Empleado Puesto Unidad
/*insert into empleados_puestos_unidades	(id_empleado_puesto_unidad, id_empleado, id_puesto,id_unidad_organizacional)
								 values (100, 300, 1,501);
insert into empleados_puestos_unidades	(id_empleado_puesto_unidad, id_empleado, id_puesto,id_unidad_organizacional)
								 values (101, 301, 2,502);

/*---------------------TIPO UNIDAD ORGANIZACIONAL------------------*/
INSERT ALL
  INTO TIPOS_UNIDAD_ORGANIZACIONAL VALUES(100, 1, 'Departamento', 1)
  INTO TIPOS_UNIDAD_ORGANIZACIONAL VALUES(101, 2, 'Área', 1)
  INTO TIPOS_UNIDAD_ORGANIZACIONAL VALUES(102, 3, 'Sección', 1)
SELECT * FROM DUAL;

/*----------------------ANIO LABORAL----------------------*/
INSERT INTO anios_laborales VALUES(100, 2020, 30);

/*----------------------PERIODO----------------------*/
INSERT ALL 
  INTO PERIODOS VALUES(100, 0, '01/01/20', '30/01/20', 100)
  INTO PERIODOS VALUES(101, 0, '01/02/20', '28/02/20', 100)
  INTO PERIODOS VALUES(102, 0, '01/03/20', '30/03/20', 100)
  INTO PERIODOS VALUES(103, 0, '01/04/20', '30/04/20', 100)
  INTO PERIODOS VALUES(104, 0, '01/05/20', '30/05/20', 100)
  INTO PERIODOS VALUES(105, 1, '01/06/20', '30/06/20', 100)
  INTO PERIODOS VALUES(106, 0, '01/07/20', '30/07/20', 100)
  INTO PERIODOS VALUES(107, 0, '01/08/20', '30/08/20', 100)
  INTO PERIODOS VALUES(108, 0, '01/09/20', '30/09/20', 100)
  INTO PERIODOS VALUES(109, 0, '01/10/20', '30/10/20', 100)
  INTO PERIODOS VALUES(110, 0, '01/11/20', '30/11/20', 100)
  INTO PERIODOS VALUES(111, 0, '01/12/20', '30/12/20', 100)
SELECT * FROM DUAL;

/*----------------------PLANILLA----------------------*/
INSERT ALL
  INTO PLANILLAS VALUES(100, '20/06/20', 2, 3, 100, 100, 100, 100, 900, 100, 100, 300, 105)
  INTO PLANILLAS VALUES(101, '20/06/20', 2, 3, 100, 100, 100, 100, 900, 100, 100, 301, 105)
SELECT * FROM DUAL;

/*----------------------PLANILLA MOVIMIENTOS----------------------*/
INSERT ALL
  INTO PLANILLA_MOVIMIENTOS VALUES(100, 100, 1)
  INTO PLANILLA_MOVIMIENTOS VALUES(101, 100, 2)
  INTO PLANILLA_MOVIMIENTOS VALUES(102, 100, 3)
  INTO PLANILLA_MOVIMIENTOS VALUES(103, 100, 4)
  INTO PLANILLA_MOVIMIENTOS VALUES(104, 100, 5)
  INTO PLANILLA_MOVIMIENTOS VALUES(105, 100, 1)
  INTO PLANILLA_MOVIMIENTOS VALUES(106, 100, 2)
  INTO PLANILLA_MOVIMIENTOS VALUES(107, 100, 3)
  INTO PLANILLA_MOVIMIENTOS VALUES(108, 100, 4)
  INTO PLANILLA_MOVIMIENTOS VALUES(109, 100, 5)
SELECT * FROM DUAL;

/*----------------------RANGOS RENTA----------------------*/
INSERT ALL
  INTO RANGOS_RENTA VALUES(100, 0, 0, 30, 0, 1, 0.01, 472.00)
  INTO RANGOS_RENTA VALUES(101, 17.67, 472.00, 30, 10, 1, 472.01, 895.24)
  INTO RANGOS_RENTA VALUES(102, 60.00, 895.24, 30, 20, 1, 895.25, 2038.10)
  INTO RANGOS_RENTA VALUES(103, 288.57, 2038.10, 30, 30, 1, 2038.11, 100000)

  INTO RANGOS_RENTA VALUES(104, 0, 0, 15, 0, 1, 0.01, 236.00)
  INTO RANGOS_RENTA VALUES(105, 8.83, 236, 15, 10, 1, 236.01, 447.62)
  INTO RANGOS_RENTA VALUES(106, 30.00, 446.62, 15, 20, 1, 447.63, 1019.05)
  INTO RANGOS_RENTA VALUES(107, 144.28, 1019.05, 15, 30, 1, 1019.06, 100000)

  INTO RANGOS_RENTA VALUES(108, 0, 0, 7, 0, 1, 0.01, 118.00)
  INTO RANGOS_RENTA VALUES(109, 4.42, 118.00, 7, 10, 1, 118.01, 223.81)
  INTO RANGOS_RENTA VALUES(110, 15.00, 223.81, 7, 20, 1, 223.82, 509.52)
  INTO RANGOS_RENTA VALUES(111, 72.14, 509.52, 7, 30, 1, 509.52, 100000)
SELECT * FROM DUAL;