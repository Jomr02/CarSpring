package es.urjc.etsii.dad.CarSpring;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UsuarioController {
	
	
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private AnuncioRepository adRepo;	
	@Autowired
	private ArticuloRepository artRepo;

	
	@PostConstruct
	//Son solo ejemplos
	public void init () {

	}
	
	
	public String inicio(Model model) {
		return "index";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "inicio_sesion";
	}
	
	@PostMapping("/loggedIn")
	public String loggedIn(Model model, @RequestParam String nick, @RequestParam String contrasena) {
		
		Optional<Usuario> user = userRepo.findByNickAndContrasena(nick, contrasena);
		if(user.isPresent()) {
			//userActual = user.get();
			model.addAttribute("nick", nick);
			model.addAttribute("loggedIn", true);
			return "index";
		}
		else {
			return "usuarioNoExiste";
		}
	}
	
	@GetMapping("/loginerror")
	public String loginerror() {
		return "usuarioNoExiste";
	}

	@GetMapping("/register")
	public String registrarUsuario() {
		return "registro_form";
	}

	@PostMapping("/registerOK")
	public String nuevoUsuario(Model model, @RequestParam String nick, @RequestParam String contrasena, @RequestParam String biografia) {
		userRepo.save(new Usuario(nick, contrasena, biografia));
		
		return "usuario_guardado";
	}
	
	
	@GetMapping("/usuario/{userId}")
	public String verPerfil(Model model, @PathVariable Long userId, HttpServletRequest request) {
	
		Optional<Usuario> op = userRepo.findById(userId);
		if(op.isPresent()) {
			model.addAttribute("usuario", op.get());
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
		}

		return "perfil_usuario";
	}
	
	@GetMapping("/usuario/{userId}/edit")
	public String usuarioEdit(Model model, @PathVariable Long userId) {
		Optional<Usuario> op = userRepo.findById(userId);
		if(op.isPresent()) {
			model.addAttribute("usuario", op.get());
		}

		return "perfil_usuario_edit";
	}
	
	@PostMapping("/usuario/{userId}/guardar")
	public String usuarioEditGuardar(Model model, @PathVariable Long userId, @RequestParam Optional<String> contrasena, @RequestParam Optional<String> bio) {
		Optional<Usuario> op = userRepo.findById(userId);
		if(op.isPresent()) {
			Usuario usuario = op.get();
			if(contrasena.isPresent()) {usuario.setContrasena(contrasena.get());}
			if(bio.isPresent()) {usuario.setBio(bio.get());}
			userRepo.save(usuario);
		}

		return "usuario_guardado";
	}
	
	@Transactional
	@GetMapping("/borrar_usuario/{userId}")
	public String borrarUsuario(Model model, @PathVariable long userId, Pageable page) {
		
		Optional<Usuario> op = userRepo.findById(userId);
		if(op.isPresent()) {
			Usuario user = op.get();
			List<Articulo> articulos = user.getArticulos(); //Se saca la referencia a todos los articulos que aun son de este usuario
			user.borrarTodosAnuncios();				// Borramos la referencia que tiene el usuario de sus anuncios y artículos
			user.borrarTodosArticulos();
			artRepo.deleteInBatch(articulos);		// Borramos todas las instancias de sus articulos del repositorio
			adRepo.deleteByAnunciante_Id(userId);	// Borramos todas las instancias de sus anuncios
			userRepo.deleteById(userId);			// Borramos el usuario de su repo
		}
		return "usuario_borrado";
	}
}
