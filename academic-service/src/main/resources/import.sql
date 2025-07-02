INSERT INTO areas (nombre) VALUES ('Matematicas');
INSERT INTO areas (nombre) VALUES ('Letras');
INSERT INTO areas (nombre) VALUES ('Ciencias');
INSERT INTO areas (nombre) VALUES ('Artes');
INSERT INTO areas (nombre) VALUES ('Atletismo');

insert into materias (id_area,nombre) values (1,'Aritmetica'    );
insert into materias (id_area,nombre) values (1,'Algebra'       );
insert into materias (id_area,nombre) values (1,'Razonamiento Matematico');
insert into materias (id_area,nombre) values (1,'Geometria'     );
insert into materias (id_area,nombre) values (1,'Trigonometria' );

insert into materias (id_area,nombre) values (2,'Literatura'    );
insert into materias (id_area,nombre) values (2,'Geografia'     );
insert into materias (id_area,nombre) values (2,'Historia'      );
insert into materias (id_area,nombre) values (2,'Ingles'        );
insert into materias (id_area,nombre) values (2,'Lenguage'      );
insert into materias (id_area,nombre) values (2,'Razonamiento Verbal');
insert into materias (id_area,nombre) values (2,'Educiacion Civica');

insert into materias (id_area,nombre) values (4,'Danza'         );
insert into materias (id_area,nombre) values (4,'Religion'      );
insert into materias (id_area,nombre) values (4,'Dibujo'        );
insert into materias (id_area,nombre) values (4,'Oratoria'      );
insert into materias (id_area,nombre) values (4,'Musica'        );

insert into materias (id_area,nombre) values (5,'Educacion Fisica');


INSERT INTO grados(nivel, sub_nivel) VALUES ('INICIAL',3);
INSERT INTO grados(nivel, sub_nivel) VALUES ('INICIAL',4);
INSERT INTO grados(nivel, sub_nivel) VALUES ('INICIAL',5);

INSERT INTO grados(nivel, sub_nivel) VALUES ('PRIMARIA',1);
INSERT INTO grados(nivel, sub_nivel) VALUES ('PRIMARIA',2);
INSERT INTO grados(nivel, sub_nivel) VALUES ('PRIMARIA',3);
INSERT INTO grados(nivel, sub_nivel) VALUES ('PRIMARIA',4);
INSERT INTO grados(nivel, sub_nivel) VALUES ('PRIMARIA',5);
INSERT INTO grados(nivel, sub_nivel) VALUES ('PRIMARIA',6);

INSERT INTO grados(nivel, sub_nivel) VALUES ('SECUNDARIA',1);
INSERT INTO grados(nivel, sub_nivel) VALUES ('SECUNDARIA',2);
INSERT INTO grados(nivel, sub_nivel) VALUES ('SECUNDARIA',3);
INSERT INTO grados(nivel, sub_nivel) VALUES ('SECUNDARIA',4);
INSERT INTO grados(nivel, sub_nivel) VALUES ('SECUNDARIA',5);

ALTER TABLE bimestres MODIFY COLUMN orden TINYINT;
ALTER TABLE bimestres MODIFY COLUMN nota1 TINYINT;
ALTER TABLE bimestres MODIFY COLUMN nota2 TINYINT;
ALTER TABLE bimestres MODIFY COLUMN nota3 TINYINT;
ALTER TABLE bimestres MODIFY COLUMN nota4 TINYINT;