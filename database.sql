CREATE SCHEMA chopify_db;

USE chopify_db;

CREATE TABLE direcciones (a
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    direccion VARCHAR(255),
    latitud DECIMAL(9, 6),
    longitud DECIMAL(9, 6)
);

CREATE TABLE negocios ( 
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    cuit VARCHAR(20) UNIQUE,
    nombre VARCHAR(255),
    telefono VARCHAR(20),
    direccion_id BIGINT,
    estado TINYINT DEFAULT 1,
    CONSTRAINT fk_negocio_direccion FOREIGN KEY (direccion_id) REFERENCES direcciones (id)
);

CREATE TABLE clientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    direccion_id BIGINT,
    CONSTRAINT fk_cliente_direccion FOREIGN KEY (direccion_id) REFERENCES direcciones (id)
);

CREATE TABLE categorias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE productos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(12, 2) NOT NULL CHECK (precio >= 0),
    stock INT NOT NULL CHECK (stock >= 0),
    negocio_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT fk_producto_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (id),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias (id)
);

CREATE TABLE repartidores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    dni VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);

CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    negocio_id BIGINT,
    repartidor_id BIGINT,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT NOW(),
    estado VARCHAR(50) NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id),
    CONSTRAINT fk_pedido_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (id),
    CONSTRAINT fk_pedido_repartidor FOREIGN KEY (repartidor_id) REFERENCES repartidores (id)
);

CREATE TABLE seguimiento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_seguimiento_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id)
);

CREATE TABLE pedido_detalle (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(12, 2) NOT NULL CHECK (precio_unitario >= 0),
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos (id)
);

CREATE TABLE horarios_negocio (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    negocio_id BIGINT NOT NULL,
    dia_semana VARCHAR(20) NOT NULL,
    hora_apertura TIME NOT NULL,
    hora_cierre TIME NOT NULL,
    CONSTRAINT fk_horario_negocio FOREIGN KEY (negocio_id) REFERENCES negocios (id) ON DELETE CASCADE
);

CREATE TABLE promociones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    descuento DECIMAL(5, 2) NOT NULL CHECK (descuento BETWEEN 0 AND 100),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    activo SMALLINT NOT NULL DEFAULT 1
);

CREATE TABLE producto_promocion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    producto_id BIGINT NOT NULL,
    promocion_id BIGINT NOT NULL,
    CONSTRAINT fk_producto_promocion_producto FOREIGN KEY (producto_id) REFERENCES productos (id) ON DELETE CASCADE,
    CONSTRAINT fk_producto_promocion_promocion FOREIGN KEY (promocion_id) REFERENCES promociones (id) ON DELETE CASCADE
);