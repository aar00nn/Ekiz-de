package gm.tareas.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tarea {
//    AGREGAR COLUMNAS DE REGISTRAR RESPONSABLE, COLUMNA PARA COMENTARIOS EN
//    LA TABLA SOBRE LA TAREA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTask;
    private String taskName;
    private String creator;
    private String subject;
    private Boolean status=false;
    private String comments;

    private LocalDateTime recordDate;
    private LocalDateTime outDate;


}
