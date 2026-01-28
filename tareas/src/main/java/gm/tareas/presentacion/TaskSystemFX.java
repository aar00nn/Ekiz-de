package gm.tareas.presentacion;

import gm.tareas.TareasApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class TaskSystemFX extends Application {
private ConfigurableApplicationContext applicationContext;

//    public static void main(String[] args) {
//        launch(args);
//    }


    public void init(){
       this.applicationContext= new SpringApplication(TareasApplication.class).run();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader= new FXMLLoader(
                TareasApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);

        Scene escena= new Scene(loader.load());
        stage.setScene(escena);
        stage.show();


        escena.getStylesheets().add(TareasApplication.class.getResource(
                "/css/style.css").toExternalForm());
    }
@Override
    public void stop(){
        applicationContext.close();
    Platform.exit();
    }
}
