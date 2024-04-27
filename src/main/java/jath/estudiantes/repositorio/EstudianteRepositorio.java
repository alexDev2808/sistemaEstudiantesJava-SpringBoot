package jath.estudiantes.repositorio;

import jath.estudiantes.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

//                                           se indica el modelo, y el tipo de dato del id
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {

}
