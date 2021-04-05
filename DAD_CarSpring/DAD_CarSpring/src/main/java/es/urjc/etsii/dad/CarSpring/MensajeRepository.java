package es.urjc.etsii.dad.CarSpring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

	List<Mensaje> findAllByDestinatario(Usuario d);
	
}