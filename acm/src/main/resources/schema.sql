drop table if exists prestamo_libro;
drop table if exists libro;
drop table if exists autor;
drop table if exists categoria;
drop table if exists prestamo;
drop table if exists cliente;
drop table if exists usuario;


create table autor (
    idAutor SERIAL primary key,
    nombre varchar(50) not null,
    paisOrigen varchar(50) not null
);

create table categoria (
    idCategoria SERIAL primary key,
    nombreCategoria varchar(50) not null,
    descripcion varchar(255) not null
);

create table libro (
    idLibro SERIAL primary key,
    titulo varchar(50) not null,
    anioPublicacion date not null,
    disponibilidad boolean not null,
    descripcion varchar(255) not null,
    idAutorFK int not null,
    idCategoriaFK int not null,
    foreign key (idAutorFK) references autor(idAutor),
    foreign key (idCategoriaFK) references categoria(idCategoria)
);

create table cliente (
    idCliente int primary key,
    nombre varchar(50) not null,
    correo varchar(50) not null,
    telefono varchar(10) not null,
    estadoCuenta boolean not null,
    idUsuarioFK int not null
);

create table usuario (
    idUsuario int primary key,
    username varchar(50) not null,
    password varchar(255) not null,
    rol varchar(50) not null
);

create table prestamo (
    idPrestamo SERIAL primary key,
    fechaInicioPrestamo date not null,
    fechaFinPrestamo date not null,
    idClientefk int not null,
    foreign key (idClientefk) references cliente(idCliente)
);
create table prestamo_libro (
    idPrestamo int not null,
    idLibro int not null
);
alter table prestamo_libro add foreign key (idPrestamo) references prestamo(idPrestamo);
alter table prestamo_libro add foreign key (idLibro) references libro(idLibro);