package gm.tareas.repositorio;

import gm.tareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepositorio extends JpaRepository<Tarea,Integer> {
//    List<Tarea> findAllByEstatus(Boolean estatus);
//    La capa de repositorio nos ayuda a conectarnos a la BD sin necesidad de escribir sql

    List<Tarea> findByStatus(Boolean status);

    List<Tarea>findByTaskNameContainingIgnoreCase(String taskName);

    // Spring busca 'recordDate' y ordena
    List<Tarea> findAllByOrderByRecordDate();

  //  List<Tarea> getPendingTasks();

    // Spring busca 'outDate' y ordena
    List<Tarea> findAllByOrderByOutDate();
}
