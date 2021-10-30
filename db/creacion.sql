CREATE TABLE "TipoAtraccion" (
	"Tipo"	TEXT,
	PRIMARY KEY("Tipo")
);

CREATE TABLE "Atracciones" (
	"Nombre"	TEXT NOT NULL,
	"Costo"		INTEGER NOT NULL,
	"Tiempo"	REAL NOT NULL,
	"Cupo"		INTEGER NOT NULL,
	"Tipo"		TEXT NOT NULL,
	PRIMARY KEY("Nombre"),
	FOREIGN KEY("Tipo") REFERENCES TipoAtraccion("Tipo")
);

CREATE TABLE "Promociones" (
	"Nombre"			TEXT,
	"Tipo_atraccion"	TEXT,
	
	PRIMARY KEY("Nombre"),
	FOREIGN KEY("Tipo_atraccion") REFERENCES TipoAtraccion("Tipo")
	);
	
	CREATE TABLE "Porcentual" (
	"Nombre"		TEXT,
	"Descuento"		REAL,
	
	FOREIGN KEY("Nombre") REFERENCES Promociones("Nombre")
	);
	
	CREATE TABLE "AXB" (
	"Nombre"			TEXT,
	"Atraccion_gratis"	TEXT,
	

	FOREIGN KEY("Nombre") REFERENCES Promociones("Nombre")
	FOREIGN KEY("Atraccion_gratis") REFERENCES Atracciones("Nombre")
	
	);
	
	CREATE TABLE "Absoluta" (
	"Nombre"		TEXT,
	"Precio"		INTEGER,
	

	FOREIGN KEY("Nombre") REFERENCES Promociones("Nombre")
	
	);
	
	CREATE TABLE "Promocion_Atraccion" (
	"Nombre_promocion"	TEXT,
	"Nombre_atraccion"	TEXT,
	
	PRIMARY KEY ("Nombre_promocion","Nombre_atraccion"),
	FOREIGN KEY("Nombre_promocion") REFERENCES Promociones("Nombre")
	FOREIGN KEY("Nombre_atraccion") REFERENCES Atracciones("Nombre")
	
	);
	
	CREATE TABLE "Usuarios" (
	"Nombre"				TEXT,
	"Atraccion_preferida"	TEXT,
	"Presupuesto" 			INTEGER,
	"Tiempo" 				REAL,
	"id_usuario"   			INTEGER,
	
	PRIMARY KEY ("id_usuario","Nombre"),
	FOREIGN KEY("Atraccion_preferida") REFERENCES TipoAtraccion("Tipo")
	);

	CREATE TABLE "Itinerario" (
	"id_itinerario"		INTEGER ,
	"Usuario_nombre"	TEXT,
	"Atraccion_nombre"	TEXT,
	"Promocion_nombre"  TEXT,
	
	PRIMARY KEY ("id_itinerario" AUTOINCREMENT)
	FOREIGN KEY("Usuario_nombre") REFERENCES Usuarios("Nombre")
	FOREIGN KEY("Atraccion_nombre") REFERENCES Atracciones("Nombre")
	FOREIGN KEY("Promocion_nombre") REFERENCES Promociones("Nombre")
	
	);
	
insert into TipoAtraccion (Tipo) values ("Paisaje");
insert into TipoAtraccion (Tipo) values ("Degustacion");
insert into TipoAtraccion (Tipo) values ("Aventura");
	
	
	
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Moria",10,2,6,"Aventura");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Minas Tirith",5,2.5,25,"Paisaje");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("La Comarca",3,6.5,150,"Degustacion");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Mordor",25,3,4,"Aventura");

insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Abismo de Helm",5,2,15,"Paisaje");

insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Lothlórien",35,1,30,"Degustacion");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Erebor",12,3,32,"Paisaje");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Bosque Negro",3,4,12,"Aventura");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Parque De La Costa",30,3.5,80,"Aventura");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Parque San Carlos",50,5,6,"Paisaje");
insert into Atracciones (Nombre,Costo,Tiempo,Cupo,Tipo) values ("Bodega Rutini",10,2,15,"Degustacion");

insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Eowyn","Aventura",10 , 8,1);
insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Gandalf","Paisaje",100 , 5,2);

insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Sam","Degustacion",36 , 8,3);
insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Galadriel","Paisaje",120 , 6,4);
 insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Eze","Degustacion",80 , 10,5);
 insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Nahue","Aventura",20 , 12,6);
 insert into Usuarios (Nombre,Atraccion_preferida,Presupuesto,Tiempo,id_usuario) values ("Eowyn","Aventura",80 , 8,7);
 
 
----------------------pack1-----------------------

 INSERT into Promociones (Nombre, Tipo_atraccion) values ("Pack1","Aventura");
 insert into Porcentual (Nombre,Descuento) values ("Pack1",20);

 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack1","Bosque Negro");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack1","Mordor");
 
 -------------------------------------------
 ---------------------pack2--------------------------
 INSERT into Promociones (Nombre, Tipo_atraccion) values ("Pack2","Degustacion");
 insert into Absoluta (Nombre,Precio) values ("Pack2",36);

 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack2","Lothlórien");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack2","La Comarca");
 -----------------------------------------------
 
  ---------------------pack3--------------------------
 INSERT into Promociones (Nombre, Tipo_atraccion) values ("Pack3","Paisaje");
 insert into AXB (Nombre,Atraccion_gratis) values ("Pack3","Erebor");

 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack3","Minas Tirith");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack3","Abismo de Helm");
 -----------------------------------------------
 
   ---------------------pack4--------------------------
 INSERT into Promociones (Nombre, Tipo_atraccion) values ("Pack4","Paisaje");
 insert into Porcentual (Nombre,Descuento) values ("Pack4",15);

 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack4","Parque San Carlos");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack4","Abismo de Helm");
 -----------------------------------------------
 
   ---------------------pack5--------------------------
 INSERT into Promociones (Nombre, Tipo_atraccion) values ("Pack5","Paisaje");
 insert into Absoluta (Nombre,Precio) values ("Pack5",38);

 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack5","Parque De La Costa");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack5","Moria");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack5","Bosque Negro");
 -----------------------------------------------
 
  ---------------------pack6--------------------------
 INSERT into Promociones (Nombre, Tipo_atraccion) values ("Pack6","Degustacion");
 insert into AXB (Nombre,Atraccion_gratis) values ("Pack6","La Comarca");

 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack6","Bodega Rutini");
 INSERT INTO Promocion_Atraccion ("Nombre_promocion","Nombre_atraccion") VALUES ("Pack6","Lothlórien");
 -----------------------------------------------
 
 SELECT * FROM Usuarios;