# 🍻 **Chopify - Requerimientos del Sistema**  

## 📋 **Requerimientos Funcionales**  

1. **Registro y Gestión de Negocios**  
   - **Registro de Negocio**:  
     - Los vendedores podrán crear una cuenta mediante un formulario que requerirá los siguientes campos:  
       - Nombre del negocio
       - Dirección
       - Teléfono
       - Correo electrónico
       - Logo del negocio
   - **Perfil de Tienda**:  
     - Los vendedores podrán editar su perfil para actualizar la información básica y gestionar su catálogo de productos.

2. **Módulo de Inventario**  
   - **Control de Stock**:  
     - El sistema permitirá a los vendedores gestionar el inventario de cada producto
     - El sistema realizará un seguimiento en tiempo real del stock disponible, actualizando la información inmediatamente después de cada transacción.
   - **Notificaciones de Inventario**:  
     - Se enviarán notificaciones automáticas a los vendedores cuando el inventario de un producto esté por debajo de un nivel predefinido (configurable por el vendedor, por ejemplo, 5 unidades).

3. **Promociones y Descuentos Personalizados**  
   - **Campañas Promocionales**:  
     - Los vendedores podrán crear y gestionar campañas promocionales con las siguientes características:  
       - Descuentos porcentuales o en monto fijo aplicables a productos individuales o al total de la compra.
       - Cupones promocionales que los clientes pueden utilizar al momento de pagar.  
       - Duración de la promoción (fecha de inicio y fin).

4. **Zonas de Cobertura y Horarios Flexibles**  
   - **Configuración de Zonas de Entrega**:  
     - Los vendedores podrán definir múltiples zonas de entrega, cada una con tarifas específicas basadas en la distancia.  
     - Se permitirá la integración con mapas para calcular automáticamente las distancias y sugerir tarifas.
   - **Horarios de Entrega**:  
     - Los vendedores podrán establecer horarios personalizados para la recepción de pedidos, incluyendo horas específicas y días de la semana.  
     - Se ofrecerá la opción de cerrar temporalmente la tienda.

5. **Pagos Digitales y en Efectivo**  
   - **Métodos de Pago**:  
     - El sistema deberá integrar opciones de pago, incluyendo:  
       - Billeteras virtuales (MercadoPago).
       - Efectivo contra entrega.  

6. **Gestión de Pedidos**  
   - **Estado de Pedidos**:  
     - Los vendedores podrán gestionar el estado de cada pedido, con las siguientes opciones:  
       - "Pendiente": pedido recibido pero no procesado.
       - "En preparación": el pedido está siendo preparado.
       - "En camino": el pedido ha sido despachado para la entrega.
       - "Entregado": el pedido ha llegado a su destino.
     - Se permitirá la opción de cancelar un pedido si aún no ha sido procesado.

7. **Notificaciones en Tiempo Real**  
   - **Sistema de Notificaciones**:  
     - El sistema enviará notificaciones en tiempo real a los vendedores a través de una interfaz de usuario:  
       - Nuevos pedidos.
       - Alerta de stock bajo.
       - Pago recibido.
---

## ⚙️ **Requerimientos No Funcionales**  

1. **Seguridad**  
   - **Encriptación**:  
     - El sistema asegurará que todos los datos sensibles (como contraseñas y datos de tarjetas de crédito) se mantengan encriptados.

2. **Usabilidad**  
   - **Interfaz de Usuario**:  
     - El sistema ofrecerá una interfaz intuitiva y amigable. 

3. **Rendimiento**  
   - **Capacidad de Respuesta**:  
     - El sistema deberá procesar actualizaciones y solicitudes en tiempo real, con tiempos de respuesta no superiores a 3 segundos en la interfaz de usuario.
   - **Escalabilidad**:  
     - El sistema deberá ser capaz de manejar un incremento en la carga de usuarios y transacciones sin degradar el rendimiento.