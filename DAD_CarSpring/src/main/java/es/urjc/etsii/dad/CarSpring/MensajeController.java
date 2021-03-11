package es.urjc.etsii.dad.CarSpring;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MensajeController {

	@Autowired
	private MensajesRepository msgRepo;
	@Autowired
	private UsuariosRepository userRepo;
	@Autowired
	private ChatRepository chtRepo;




	public void init () {

	}

	@GetMapping("/chat")
	public String chatUsuarios(Model model) {

		model.addAttribute("chat", chtRepo.findAll());

		return "chat";
	}

	@GetMapping("/chat/mensajes")
	public String tablaMensajes(Model model) {

		model.addAttribute("mensaje", msgRepo.findAll());

		return "index";
	}

	@GetMapping("/chat/{id}")
	public String verChat(Model model, @PathVariable long id) {

		Optional<Chat> chat = chtRepo.findById(id);

		if(chat.isPresent()) {
			model.addAttribute("chat", chat.get());
		}

		return "ver_chat";
	}

	@GetMapping("/mensaje/{id}")
	public String verMensaje(Model model, @PathVariable long id) {

		Optional<Mensaje> mesnaje = msgRepo.findById(id);

		if(mesnaje.isPresent()) {
			model.addAttribute("chat", mesnaje.get());
		}

		return "ver_mensaje";
	}

}