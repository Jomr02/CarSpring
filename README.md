# CarSpring
Plataforma de compra-venta de vehiculos
## Entidades principales
- **Usuario**: Tiene su nombre de usuario y contraseña, puede ver sus datos de contacto, su lista de vehiculos en vehiculos en venta, mensajes enviados y recibidos, listas de vehiculos comprados/vendidos, lista de deseados, y sus valoraciones como usuario.
- **Administrador**: Puede eliminar anuncios o mensajes que vayan contra las normas del sistema. Además de poder eliminar el acceso a usuarios.
- **Anuncio**: Tipo (Venta/Intercambio) Contiene la descripción del vehiculo, usuario vendedor, precio, precio negociable o no.
- **Artículo**: Clasificación (Coche, furgoneta etc), Estado (Nuevo, seminuevo, km0), Marca y Modelo, Descripción, Fotos o Vídeos.
- **Compra**: Asocia un anuncio a su vendedor y su comprador. Se añade a sus respectivas listas de vendidos/comprados y se les deja valorar al vendedor/comprador respectivamente

## Servicio interno
- **Alertas por correo electrónico:** Recibir correo electrónico cuando uno de tus vehiculos ha sido comprado.

