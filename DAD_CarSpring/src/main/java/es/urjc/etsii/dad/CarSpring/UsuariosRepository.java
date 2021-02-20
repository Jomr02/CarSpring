package es.urjc.etsii.dad.CarSpring;

import java.util.Optional;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
	
	List<Usuario> findByNickAndContrasena(String nick, String contrasena);

}