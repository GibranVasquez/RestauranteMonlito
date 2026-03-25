# INSTRUCCIONES PARA COMPAÑERO - FASES 5, 6, 7

Tu compañero debe hacer exactamente lo siguiente. Usa este como checklist.

---

## 📋 FASE 5: SERVICIO PEDIDOS (Compañero)

### a) Crear estructura de carpetas (similar a lo que hicimos antes):
```
servicio-pedidos/
├── src/main/java/com/restaurante/pedidos/
│   ├── PedidosApplication.java
│   ├── model/
│   │   └── Pedido.java
│   ├── repository/
│   │   └── PedidoRepository.java
│   ├── service/
│   │   └── PedidoService.java
│   └── controller/
│       └── PedidoController.java
└── src/main/resources/
    └── application.properties (YA EXISTE)
```

### b) Crear PedidosApplication.java:
```java
package com.restaurante.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PedidosApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedidosApplication.class, args);
    }
}
```

### c) Crear Pedido.java (model):
```java
package com.restaurante.pedidos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cliente_id")
    private Long clienteId;

    private LocalDateTime fecha;
    private String estado = "PENDIENTE";

    @ElementCollection
    @CollectionTable(name="pedido_producto", joinColumns=@JoinColumn(name="pedido_id"))
    @Column(name="producto_id")
    private List<Long> productoIds;

    public Pedido(){
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public List<Long> getProductoIds() { return productoIds; }
    public void setProductoIds(List<Long> productoIds) { this.productoIds = productoIds; }
}
```

### d) PedidoRepository.java:
```java
package com.restaurante.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
```

### e) PedidoService.java:
```java
package com.restaurante.pedidos.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.restaurante.pedidos.repository.PedidoRepository;
import com.restaurante.pedidos.model.Pedido;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repo;

    public List<Pedido> listar(){
        return repo.findAll();
    }

    public Pedido guardar(Pedido p){
        return repo.save(p);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public Pedido obtenerPorId(Long id){
        return repo.findById(id).orElse(null);
    }
}
```

### f) PedidoController.java:
```java
package com.restaurante.pedidos.controller;

import com.restaurante.pedidos.model.Pedido;
import com.restaurante.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar(){
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable Long id){
        return pedidoService.obtenerPorId(id);
    }

    @PostMapping
    public Pedido guardar(@RequestBody Pedido p){
        return pedidoService.guardar(p);
    }

    @PutMapping("/{id}")
    public Pedido actualizar(@PathVariable Long id,@RequestBody Pedido p){
        p.setId(id);
        return pedidoService.guardar(p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        pedidoService.eliminar(id);
    }
}
```

### g) application.properties para servicio-pedidos:
```properties
spring.application.name=servicio-pedidos
server.port=8083

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_pedidos
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Eureka Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

---

## 📋 FASE 6: SERVICIO PAGOS (Compañero)

### a) Crear estructura de carpetas:
```
servicio-pagos/
├── src/main/java/com/restaurante/pagos/
│   ├── PagosApplication.java
│   ├── model/
│   │   └── MetodoDePago.java
│   ├── repository/
│   │   └── MetodoDePagoRepository.java
│   ├── service/
│   │   └── MetodoDePagoService.java
│   └── controller/
│       └── MetodoDePagoController.java
└── src/main/resources/
    └── application.properties (YA EXISTE)
```

### b) PagosApplication.java:
```java
package com.restaurante.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PagosApplication {
    public static void main(String[] args) {
        SpringApplication.run(PagosApplication.class, args);
    }
}
```

### c) MetodoDePago.java (model):
```java
package com.restaurante.pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class MetodoDePago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    private String descripcion;
    private boolean activo = true;

    public MetodoDePago() {}
    
    public MetodoDePago(Long id, String nombre, String descripcion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
```

### d) MetodoDePagoRepository.java:
```java
package com.restaurante.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.pagos.model.MetodoDePago;

public interface MetodoDePagoRepository extends JpaRepository<MetodoDePago, Long> {
}
```

### e) MetodoDePagoService.java:
```java
package com.restaurante.pagos.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.restaurante.pagos.repository.MetodoDePagoRepository;
import com.restaurante.pagos.model.MetodoDePago;

@Service
public class MetodoDePagoService {
    @Autowired
    private MetodoDePagoRepository repo;

    public List<MetodoDePago> listar(){
        return repo.findAll();
    }

    public MetodoDePago guardar(MetodoDePago m){
        return repo.save(m);
    }

    public MetodoDePago actualizar(Long id, MetodoDePago m){
        m.setId(id);
        return repo.save(m);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public MetodoDePago obtenerPorId(Long id){
        return repo.findById(id).orElse(null);
    }
}
```

### f) MetodoDePagoController.java:
```java
package com.restaurante.pagos.controller;

import com.restaurante.pagos.model.MetodoDePago;
import com.restaurante.pagos.service.MetodoDePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/metodos-pago")
public class MetodoDePagoController {
    @Autowired
    private MetodoDePagoService metodoDePagoService;

    @GetMapping
    public List<MetodoDePago> listar() {
        return metodoDePagoService.listar();
    }

    @GetMapping("/{id}")
    public MetodoDePago obtener(@PathVariable Long id) {
        return metodoDePagoService.obtenerPorId(id);
    }

    @PostMapping
    public MetodoDePago guardar(@RequestBody MetodoDePago metodoDePago) {
        return metodoDePagoService.guardar(metodoDePago);
    }

    @PutMapping("/{id}")
    public MetodoDePago actualizar(@PathVariable Long id, @RequestBody MetodoDePago metodoDePago) {
        return metodoDePagoService.actualizar(id, metodoDePago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        metodoDePagoService.eliminar(id);
    }
}
```

### g) application.properties para servicio-pagos:
```properties
spring.application.name=servicio-pagos
server.port=8084

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_pagos
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Eureka Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

---

## 📋 FASE 7: SERVICIO ENTREGAS (Compañero)

### a) Crear estructura de carpetas:
```
servicio-entregas/
├── src/main/java/com/restaurante/entregas/
│   ├── EntregasApplication.java
│   ├── model/
│   │   └── Entrega.java
│   ├── repository/
│   │   └── EntregaRepository.java
│   ├── service/
│   │   └── EntregaService.java
│   └── controller/
│       └── EntregaController.java
└── src/main/resources/
    └── application.properties (YA EXISTE)
```

### b) EntregasApplication.java:
```java
package com.restaurante.entregas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EntregasApplication {
    public static void main(String[] args) {
        SpringApplication.run(EntregasApplication.class, args);
    }
}
```

### c) Entrega.java (model - NUEVO):
```java
package com.restaurante.entregas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="pedido_id")
    private Long pedidoId;

    private String estado = "PENDIENTE"; // PENDIENTE, EN_TRANSITO, ENTREGADO

    private LocalDateTime fechaEntrega;

    private String direccion;

    public Entrega(){}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
```

### d) EntregaRepository.java:
```java
package com.restaurante.entregas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.entregas.model.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
```

### e) EntregaService.java:
```java
package com.restaurante.entregas.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.restaurante.entregas.repository.EntregaRepository;
import com.restaurante.entregas.model.Entrega;

@Service
public class EntregaService {
    @Autowired
    private EntregaRepository repo;

    public List<Entrega> listar(){
        return repo.findAll();
    }

    public Entrega guardar(Entrega e){
        return repo.save(e);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public Entrega obtenerPorId(Long id){
        return repo.findById(id).orElse(null);
    }
}
```

### f) EntregaController.java:
```java
package com.restaurante.entregas.controller;

import com.restaurante.entregas.model.Entrega;
import com.restaurante.entregas.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/entregas")
public class EntregaController {
    @Autowired
    private EntregaService entregaService;

    @GetMapping
    public List<Entrega> listar() {
        return entregaService.listar();
    }

    @GetMapping("/{id}")
    public Entrega obtener(@PathVariable Long id) {
        return entregaService.obtenerPorId(id);
    }

    @PostMapping
    public Entrega guardar(@RequestBody Entrega entrega) {
        return entregaService.guardar(entrega);
    }

    @PutMapping("/{id}")
    public Entrega actualizar(@PathVariable Long id, @RequestBody Entrega entrega) {
        entrega.setId(id);
        return entregaService.guardar(entrega);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        entregaService.eliminar(id);
    }
}
```

### g) application.properties para servicio-entregas:
```properties
spring.application.name=servicio-entregas
server.port=8085

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_entregas
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Eureka Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

---

## ✅ CHECKLIST FINAL PARA COMPAÑERO

- [ ] Fase 5: Servicio Pedidos creado y funcionando en puerto 8083
- [ ] Fase 6: Servicio Pagos creado y funcionando en puerto 8084
- [ ] Fase 7: Servicio Entregas creado y funcionando en puerto 8085
- [ ] Cada servicio tiene su propia base de datos (restaurante_pedidos, restaurante_pagos, restaurante_entregas)
- [ ] Eureka Server está corriendo en http://localhost:8761
- [ ] Todos los servicios se registran en Eureka
- [ ] API Gateway está funcionando en puerto 8080

---

## 🔗 PUERTOS FINALES

| Servicio | Puerto | 
|----------|--------|
| API Gateway | 8080 |
| Servicio Clientes | 8081 |
| Servicio Productos | 8082 |
| Servicio Pedidos | 8083 |
| Servicio Pagos | 8084 |
| Servicio Entregas | 8085 |
| Eureka Server | 8761 |
