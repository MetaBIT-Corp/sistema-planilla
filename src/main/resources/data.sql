/* PUESTOS */
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (1, 'Descripción del puesto', 'Administrador Presupuestario', 5000, 1200, 1);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (2, 'Descripción del puesto', 'Gerente de Departamento', 4000, 800, 1);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (3, 'Descripción del puesto', 'Jefe de planilla', 1000, 360, 1);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (4, 'Descripción del puesto', 'Analista Financiero', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (5, 'Descripción del puesto', 'Analista de Sistemas', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (6, 'Descripción del puesto', 'Auxiliar de planilla', 1000, 360, 1);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (7, 'Descripción del puesto', 'Asesor Legal', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (8, 'Descripción del puesto', 'Contador', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (9, 'Descripción del puesto', 'Auxiliar Contable', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (10, 'Descripción del puesto', 'Secretaria', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (11, 'Descripción del puesto', 'Programador', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (12, 'Descripción del puesto', 'Soporte técnico', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (13, 'Descripción del puesto', 'Técnico de Redes', 1000, 360, 0);
INSERT INTO puestos(id_puesto, descripcion, puesto, salario_maximo, salario_minimo, usuario_requerido)
	   VALUES      (14, 'Descripción del puesto', 'Administrador de Bases de Datos', 1000, 360, 0);
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

insert into direcciones (id_direccion,calle,complemento,numero_casa,urbanizacion,id_municipio) values (1, 'Campos', 'Condominio San Francisco', '4-A', null, 3);

insert into empresas (id_empresa,correo_empresa,empresa,nic_empresa,nit_empresa,page,pagina_empresa,telefono,id_direccion) values(1,'metabit@gmail.com','MetaBIT','000001','000002','@MetaBIT','www.metabit.com','2237-2828',1);

--Empleados de prueba
insert into empleados values(1,null,'Lopez','Estupinian','EL16002','el16002@ues.edu.sv',1,0,'28-JUN-97',2,'Ricardo','Bladimir',2000,null,null,null);

insert into empleados values(2,null,'Lopez','Estupinian','EL14002','el14002@ues.edu.sv',0,0,'17-MAR-95',2,'Estefany','Lizeida',2000,null,null,null);

