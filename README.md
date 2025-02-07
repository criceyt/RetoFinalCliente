MÉTODO DE DESPLIEGUE:
- El método de despliegue elegido es el Modelo 2, en el cual el WAR crea las tablas y el script inserta los datos (Nombre: concesionariodb.sql).

COSAS A TENER EN CUENTA:

Cuando despliegues esta aplicación, vendrá con datos predeterminados, los cuales contendrán usuarios y trabajadores.

Para acceder como usuario, podrás bien registrarte o bien utilizar un usuario predeterminado.

Con el rol de trabajador, en cambio, tendrás que iniciar sesión con uno de los administradores ya insertados.

Aquí te dejamos dos usuarios, uno de cada tipo:

USUARIO: 
- Correo: juan@example.com
- Password: Abcd*1234


TRABAJADOR: 
- Correo: admin1@example.com
- Password: Abcd*1234

URL SERVIDOR:

Ubicacion = Paquete entidades


TESTS:
Para que los tests se ejecuten de manera correcta, es necesario borrar los datos de la tabla hasta que se visualicen correctamente. (Los tests no pueden utilizar la barra de desplazamiento, por lo que la tabla debe ser completamente visible).

IMPORTANTE: En la tabla de vehículos no se debe borrar el vehículo 20, ya que en los tests de mantenimiento se hace referencia a dicho vehículo.