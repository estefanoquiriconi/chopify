# 🍻 **Chopify - Requerimientos del Sistema**  

---

## 📋 **Requerimientos Funcionales**  

1. **Registro y Gestión de Negocios**  
   - **Registro de Negocio**:  
     - El sistema deberá permitir a los vendedores crear una cuenta mediante un formulario con los siguientes campos:  
       - Nombre del negocio
       - Dirección
       - Teléfono
       - Correo electrónico
   - **Perfil de Tienda**:  
     - El sistema deberá permitir a los vendedores editar su perfil para actualizar la información básica y gestionar su catálogo de productos.

2. **Módulo de Inventario**  
   - **Control de Stock**:  
     - El sistema deberá permitir a los vendedores gestionar el inventario de cada producto.
     - El sistema deberá permitir realizar un seguimiento en tiempo real del stock disponible, actualizando la información inmediatamente después de cada transacción.
   - **Notificaciones de Inventario**:  
     - El sistema deberá permitir enviar notificaciones automáticas a los vendedores cuando el inventario de un producto esté por debajo de un nivel predefinido (configurable por el vendedor, por ejemplo, 5 unidades).

3. **Promociones y Descuentos Personalizados**  
   - **Campañas Promocionales**:  
     - El sistema deberá permitir a los vendedores crear y gestionar campañas promocionales con las siguientes características:  
       - Descuentos porcentuales o en monto fijo aplicables a productos individuales o al total de la compra.  
       - Definir la duración de la promoción (fecha de inicio y fin).

4. **Zonas de Cobertura y Horarios Flexibles**  
   - **Configuración de Zonas de Entrega**:  
     - El sistema deberá permitir que los vendedores definan una zona de entrega.
    
   - **Horarios de Entrega**:  
     - El sistema deberá permitir que los vendedores establezcan horarios personalizados para la recepción de pedidos, incluyendo horas específicas y días de la semana.  
     - El sistema deberá permitir la opción de cerrar temporalmente la tienda.

5. **Pagos Digitales y en Efectivo**  
   - **Métodos de Pago**:  
     - El sistema deberá permitir la integración de opciones de pago, incluyendo:  
       - Billeteras virtuales (MercadoPago).
       - Efectivo contra entrega.  

6. **Gestión de Pedidos**  
   - **Estado de Pedidos**:  
     - El sistema deberá permitir a los vendedores gestionar el estado de cada pedido, con las siguientes opciones:  
       - "Pendiente": pedido recibido pero no procesado.
       - "En preparación": el pedido está siendo preparado.
       - "En camino": el pedido ha sido despachado para la entrega.
       - "Entregado": el pedido ha llegado a su destino.
     - El sistema deberá permitir rechazar pedidos.

7. **Notificaciones en Tiempo Real**  
   - **Sistema de Notificaciones**:  
     - El sistema deberá permitir el envío de notificaciones en tiempo real a los vendedores a través de una interfaz de usuario para eventos como:  
       - Nuevos pedidos.
       - Alerta de stock bajo.
       - Pago recibido.

---

## ⚙️ **Requerimientos No Funcionales**  

1. **Seguridad**  
   - **Encriptación**:  
     - El sistema deberá permitir la encriptación de todos los datos sensibles (como contraseñas).

2. **Usabilidad**  
   - **Interfaz de Usuario**:  
     - El sistema deberá permitir una interfaz intuitiva y amigable.

3. **Rendimiento**  
   - **Capacidad de Respuesta**:  
     - El sistema deberá permitir el procesamiento de actualizaciones y solicitudes en tiempo real, con tiempos de respuesta no superiores a 3 segundos en la interfaz de usuario.
   - **Escalabilidad**:  
     - El sistema deberá permitir el manejo de un incremento en la carga de usuarios y transacciones sin degradar el rendimiento.
