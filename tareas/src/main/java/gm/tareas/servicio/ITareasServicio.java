package gm.tareas.servicio;

import gm.tareas.modelo.Tarea;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public interface ITareasServicio  {

    public List<Tarea> toListTask();

    public Tarea lookingForId(Integer idTarea);

    public void saveTask(Tarea tarea);

    public void deleteTask(Integer id);

    public List<Tarea> orderByRecordDesc( );

    public List<Tarea> orderByOutDesc( );

    public List<Tarea> getPendingTasks();

    public List<Tarea> orderByName(String nombre);

    public List<Tarea> orderByStatus(Boolean status);

}
