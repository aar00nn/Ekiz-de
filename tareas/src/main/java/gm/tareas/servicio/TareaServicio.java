package gm.tareas.servicio;

import gm.tareas.modelo.Tarea;
import gm.tareas.repositorio.TareaRepositorio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TareaServicio implements ITareasServicio {
@Autowired
    private TareaRepositorio tareaRepositorio;

    @Override
    public List<Tarea> toListTask() {

        return tareaRepositorio.findAll();
    }

    @Override
    public Tarea lookingForId(Integer idTarea) {
        return tareaRepositorio.findById(idTarea).orElse(null);
    }

    @Override
    public void saveTask(Tarea tarea) {

        // Nueva tarea
        if (tarea.getIdTask() == null) {
            tarea.setRecordDate(LocalDateTime.now());
            tarea.setStatus(false);
            tarea.setOutDate(null);
        }

        // Si se marca como ENTREGADA
        if (Boolean.TRUE.equals(tarea.getStatus())) {
            if (tarea.getOutDate() == null) {
                tarea.setOutDate(LocalDateTime.now());
            }
        }
        // 👇 SI SE DESMARCA
        else {
            tarea.setOutDate(null);
        }

        tareaRepositorio.save(tarea);
    }


    @Override
    public void deleteTask(Integer id) {
        tareaRepositorio.deleteById(id);
    }

    @Override
    public List<Tarea> orderByRecordDesc() {
        // Llama al método "mágico" del repositorio
     return tareaRepositorio.findAll(Sort.by(Sort.Direction.DESC,"recordDate"));
//        return tareaRepositorio.findAllByOrderByRecordDate();
    }

    @Override
    public List<Tarea> orderByOutDesc() {
        return this.tareaRepositorio.findAll(Sort.by(Sort.Direction.DESC,"outDate"));
    }

    @Override
    public List<Tarea> getPendingTasks() {
        return tareaRepositorio.findByStatus(false);
    }

    @Override
    public List<Tarea> orderByName(String nombre) {
        return tareaRepositorio.findByTaskNameContainingIgnoreCase(nombre);
    }

    @Override
    public List<Tarea> orderByStatus(Boolean status) {
        return tareaRepositorio.findByStatus(status);
    }

    @PostConstruct
    public void cargarDatosPrueba() {
        if (tareaRepositorio.count() == 0) {

            Tarea t1 = new Tarea(
                    null, "hola", "aa", "se", false,
                    "a", LocalDateTime.now(), null
            );

            Tarea t2 = new Tarea(
                    null, "hola 2", "bb", "se", false,
                    "b", LocalDateTime.now(), null
            );

            tareaRepositorio.save(t1);
            tareaRepositorio.save(t2);
        }
        else System.out.println("Total de registros= "+ tareaRepositorio.count());
    }


}
