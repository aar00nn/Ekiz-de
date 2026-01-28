package gm.tareas.controlador;

import gm.tareas.modelo.Tarea;
import gm.tareas.servicio.TareaServicio;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class IndexControlador implements Initializable{


    private static final Logger logger= LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private TareaServicio tareaServicio;

    @FXML
    private TableView <Tarea>tareaTabla;

    @FXML
    private TableColumn<Tarea,Integer> idColumn;

    @FXML
    private TableColumn<Tarea,String> taskColumn;

    @FXML
    private TableColumn<Tarea,String> teacherColumn;

    @FXML
    private TableColumn<Tarea,String>subjectColumn;

    @FXML
    private TableColumn<Tarea, String>observationColumn;

    @FXML
    private TableColumn<Tarea, LocalDateTime>rdColumn;

    @FXML
    private TableColumn<Tarea, LocalDateTime>odColumn;

    @FXML
    private TableColumn<Tarea,Boolean>statusColumn;

    @FXML
    private TextField taskText;
    @FXML
    private TextField teacherText;
    @FXML
    private TextField subjectText;
    @FXML
    private TextField statusText;
    @FXML
    private  TextArea commentsText;
    @FXML
    private AnchorPane contenedor;
    @FXML
    private TableColumn testColumn;

    @FXML
    private Button modifyButton;
    @FXML
    private Button deleteButton;

    private Integer internalId;

    private final ObservableList<Tarea> taskList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        tareaTabla.setItems(taskList);
        cargarDatos();






        deleteButton.disableProperty().bind(
                tareaTabla.getSelectionModel().selectedItemProperty().isNull()
        );
modifyButton.disableProperty().bind(
        tareaTabla.getSelectionModel().selectedItemProperty().isNull()
);


        contenedor.setOnMouseClicked(event->{
            if (contenedor.isHover()){
                tareaTabla.getSelectionModel().clearSelection();
            }
        });
        tareaTabla.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Tarea tarea, boolean empty) {
                super.updateItem(tarea, empty);

                if (tarea == null || empty) {
                    setStyle("");
                } else if (Boolean.TRUE.equals(tarea.getStatus())) {
                    // ENTREGADO
                    setStyle("-fx-background-color: #c8f7c5;");
                } else {
                    // PENDIENTE
                    setStyle("-fx-background-color: #cfe9ff;");
                }
            }
        });

    }

    private void configurarColumnas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTask"));

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));

        observationColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));


        rdColumn.setCellValueFactory(new PropertyValueFactory<>("recordDate"));

        statusColumn.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().getStatus())
        );

        statusColumn.setCellFactory(col->new TableCell<>() {
            private final CheckBox checkBox=new CheckBox();{
               setGraphic(checkBox);
            }
            {
                statusColumn.setCellFactory(col -> new TableCell<>() {

                    private final CheckBox checkBox = new CheckBox();
                    private boolean updating = false; // evita loops

                    {
                        checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {

                            if (updating) return;

                            Tarea tarea = getTableView().getItems().get(getIndex());

                            // INTENTA DESMARCAR (ENTREGADO -> PENDIENTE)
                            if (oldValue && !newValue) {

                                boolean confirm = mostrarMensajeConfirmacion(
                                        "Undo task",
                                        "Task will be marked as pending",
                                        "Are you sure?"
                                );

                                if (!confirm) {
                                    // 🔁 Revertir visualmente
                                    updating = true;
                                    checkBox.setSelected(true);
                                    updating = false;
                                    return;
                                }

                                // Confirmó → limpiar estado
                                tarea.setStatus(false);
                                tarea.setOutDate(null);
                            }

                            // MARCAR COMO ENTREGADO
                            if (!oldValue && newValue) {
                                tarea.setStatus(true);
                            }

                            tareaServicio.saveTask(tarea);
                            tareaTabla.refresh();
                        });
                    }


                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            updating = true;
                            checkBox.setSelected(item);
                            updating = false;
                            setGraphic(checkBox);
                        }
                    }
                });

            }


            @Override
            protected void updateItem(Boolean item, boolean empty){
                super.updateItem(item,empty);

                if (empty){
                    setGraphic(null);
                }
                else{
                    checkBox.setSelected(item!=null &&item);
                    setGraphic(checkBox);
                }
            }

        });

        odColumn.setCellValueFactory(new PropertyValueFactory<>("outDate"));

     testColumn.setCellFactory(col->new TableCell<Tarea,Boolean>(){
         private final CheckBox checkBox=new CheckBox();
         {
             checkBox.selectedProperty().addListener((obs,oldV,newv)->
                     {
                         if(oldV&&!newv){

                             mostrarMensajeConfirmacion("a","a","a");
                         }
                     }
                     );





         }
         @Override
         protected void updateItem(Boolean item, boolean empty) {
             super.updateItem(item, empty);
             if (empty) {
                 setGraphic(null);
             } else {
                 setGraphic(checkBox);
                 checkBox.setSelected(item != null && item);
             }


         }
     });


    }

    private void cargarDatos() {
        logger.info("ejecutando indexControlador");

//        taskList.addAll(tareaServicio.toListTask());
        taskList.setAll(tareaServicio.toListTask());

    }
//    Si quieres, el siguiente paso natural es explicarte cómo hacer que la tabla se actualice
//    automáticamente al guardar o eliminar sin volver a llamar a cargarDatos(), eso ya sería nivel
//    intermedio-avanzado

    public void addTaskUi() {
        if (this.taskText.getText().isEmpty() || this.taskText.getText().equals("")) {
            mostrarMensaje("Validation error.", " A name task must be asiggn");
            this.taskText.requestFocus();
            return;
        } else {
            var task = new Tarea();
            recolectDataForm(task);
            task.setIdTask(null);
            tareaServicio.saveTask(task);
            this.taskList.add(task);
            this.mostrarMensaje("Advice", "Task has been added succesfully!!");
            cleanForm();

        }

    }

    public void modifyTask(){
if (internalId==null)
    mostrarMensaje("Warning","A client must be selected to be modified");
        if(this.taskText.getText().isEmpty()){
            mostrarMensaje("WARNING VALIDATION","A NAME TASK MUST BE ASIGNED ON TASK FIELD");
        }
       var selectedTask=tareaTabla.getSelectionModel().getSelectedItem();
        recolectDataForm(selectedTask);
        tareaServicio.saveTask(selectedTask);
        tareaTabla.refresh();
        cleanForm();
        mostrarMensaje("Succes","Task has been modified succesfully");

    }


    public void loadOnForm(){
       var tarea=tareaTabla.getSelectionModel().getSelectedItem();
       if(tarea!=null){
        internalId=tarea.getIdTask();
        this.taskText.setText(tarea.getTaskName());
        this.teacherText.setText(tarea.getCreator());
        this.subjectText.setText(tarea.getSubject());
        this.commentsText.setText(tarea.getComments());
       }
    }

    public void deleteButton() {

        Tarea taskSelected = tareaTabla.getSelectionModel().getSelectedItem();

        if (taskSelected == null) {
            mostrarMensaje("Warning", "You must select a task to delete");
            return;
        }
      boolean confirm=  mostrarMensajeConfirmacion(
                "Delete","Youre about to delete thiss task",
                "Are you sure you want to delete it?");

        if (!confirm){
            return;
        }
        else {
            tareaServicio.deleteTask(taskSelected.getIdTask());
            taskList.remove(taskSelected);
            cleanForm();
            mostrarMensaje("Success", "Task has been deleted successfully");
            tareaTabla.getSelectionModel().clearSelection();
        }
        tareaTabla.getSelectionModel().clearSelection();
    }


    public void cleanForm() {
        this.internalId=null;
        this.taskText.clear();
        this.teacherText.clear();
        this.subjectText.clear();
        this.commentsText.clear();
        tareaTabla.getSelectionModel().clearSelection();
    }

    private void recolectDataForm(Tarea tarea) {
        if(internalId!=null)
            tarea.setIdTask(internalId);
        tarea.setTaskName(this.taskText.getText());
        tarea.setCreator(this.teacherText.getText());
        tarea.setSubject(this.subjectText.getText());
        tarea.setComments(this.commentsText.getText());

    }

    private boolean mostrarMensajeConfirmacion(String title, String header, String text){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

        ButtonType buttonYes=new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonNo=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonYes,buttonNo);

        return alert.showAndWait().orElse(buttonNo)==buttonYes;
    }

    private void mostrarMensaje(String titulo,String mensaje) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
