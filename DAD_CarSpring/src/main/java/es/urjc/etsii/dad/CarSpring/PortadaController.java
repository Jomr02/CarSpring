package es.urjc.etsii.dad.CarSpring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PortadaController {
	
	
	@Autowired
	private UsuariosRepository repository;
	
	@PostConstruct
	//Son solo ejemplos
	public void init () {
		repository.save(new Usuario("Chema", "essolodeprueba", "Vendo Oper Corsa"));
		repository.save(new Usuario("Cassi", "essolodeprueba2", "Hola!! Soy Cassi, encantada de conocerte =)"));
	}
	
	
	@GetMapping("/")
	public String tablaUsuarios(Model model) {

		model.addAttribute("usuario", repository.findAll());

		return "registro";
	}
	
	@PostMapping("/usuario/nuevo")
	public String nuevoUsuario(Model model, Usuario usuario) {

		repository.save(usuario);

		return "usuario_guardado";

	}

}
