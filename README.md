# Ekiz-de
LOL
WASO
Para usar esta cosa yo recomiendo usar porque te deja descargar los plugins de las herramientas que son necesarias para reucir la cantidad de codigo que se escribe en el proyecto.
Esta cosa usa spring que basicamente te genera un documento en donde automaticamente te inyecta archivos necesarios para poder hacer aplicaciones web usando el servidor de tomcat por defectoo aplicaciones de escritorio (https://start.spring.io/). Este mini proyecto usa conexion jdbc con la base de datos.
En este proyecto como se usa spring solo realizando unas configuraciones. En el archivo .properties ingresas los datos escenciales de labase de datos y se conecta automaticamente. posteriormente TareaRepositorio implemtenano extendiendo de la interfaz de JpaRepository es lo que trauce el codigo de java a comenados SQL, soloo estendiendo de esta interfaz genera automaticamente el CRUD para sql, por lo que los metodos para guardar la tarea, eliminarla etc... se generan automaticmente y se le asignan a la clase principal que en este caso es Tarea. 
Por ejemplo, solo hace falta crear una instancia de TareaRepositorio en el (IndexControlador)(Ques es lo que controla la iterfaz grafica en este caso JAVAFX) para que automaticamente si quieres guardar una tarea o guardar los cambios simplemene indicas el nombre de la instancia y la accion que deseas realizar, por ejemplo
tareaServicioInstancia.saveTarea();. Despues sigo explicanco porque voy a comer lol
