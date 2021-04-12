package es.urjc.etsii.dad.CarSpring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class DatabaseInit {

	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private AnuncioRepository adRepo;
	@Autowired
	private ArticuloRepository artRepo;
	
	@PostConstruct
	public void init () {
		userRepo.save(new Usuario("admin@gmail.com", "admin", "admin", "Administrador de la página web", "ROLE_USER", "ROLE_ADMIN"));

		
	}

}
