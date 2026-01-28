package gm.tareas;

import gm.tareas.presentacion.TaskSystemFX;
import jakarta.persistence.Entity;
import javafx.application.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication


public class TareasApplication {
//VERIFICAR SI SE PUEDE AGREGAR UNACOLUMNA EN BD PARA COMENTARIOS DE
// LATAREAY TAMBIEN CUANDO SE CREA LA COLUMNA DE ESTATUS EN JAVA FX VER SI SEPUEDE
// ESCOJER UN MENUDESPLEGABLEY NO DEJAR QUE ELUSUARIO INGRESE ELE ESTATUS
	public static void main(String[] args) {
//		SpringApplication.run(TareasApplication.class, args);
		Application.launch(TaskSystemFX.class,args);
	}

}
