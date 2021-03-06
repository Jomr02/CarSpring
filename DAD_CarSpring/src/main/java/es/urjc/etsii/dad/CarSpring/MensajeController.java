package es.urjc.etsii.dad.CarSpring;

import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import antlr.collections.List;

@Controller
public class MensajeController {

	@Autowired
	private MensajesRepository msgRepo;
	@Autowired
	private UsuariosRepository userRepo;
	@Autowired
	private ChatRepository chtRepo;



	@PostConstruct
	public void init () {

		
		Usuario u1 = new Usuario ("chema", "chema", "Hola, soy Chema");
		userRepo.save(u1);
		Usuario u2 = new Usuario ("jorge", "jorge", "Hola, soy Jorge");
		userRepo.save(u2);
		Chat c1 = new Chat(u1,u2); //u1 es remitente, u2 es destinatario
		Chat c2 = new Chat(u2,u1); 
		chtRepo.save(c1);
		chtRepo.save(c2);

		Mensaje m1 = new Mensaje ("Prueba de que hay mensajes dentro del chat");
		Mensaje m2 = new Mensaje ("Prueba de que hay una contestación");
		c1.addMensaje(m1);
		c2.addMensaje(m2);
		chtRepo.save(c1); 
		chtRepo.save(c2); 

	}

	@GetMapping("/bandeja_entrada")
	public String chatUsuarios(Model model) {

		model.addAttribute("chats", chtRepo.findAll());

		return "bandeja_entrada";
	}

	@GetMapping("/chat/mensajes")
	public String tablaMensajes(Model model) {

		model.addAttribute("mensaje", msgRepo.findAll());

		return "mensaje_enviado";
	}

	@GetMapping("/chat/{toId}")
	public String verChat(Model model, @PathVariable long toId) {
		
		
		/*
		//Esto va a haber que cambiarlo para buscar el remitente cuando haya inicios de sesión
		java.util.List<Chat> listachats = chtRepo.findByDestinatario_Id(toId);
		model.addAttribute("hayChat", false);
		
		model.addAttribute("destinatario", userRepo.findByNick("uPrueba1"));
		
		if(!listachats.isEmpty()) {
			model.addAttribute("chat", listachats.get(0));
			model.addAttribute("hayChat", true);
		}
		 */
		return "ver_chat";
		
	}
	
	@PostMapping("chat/mensaje/nuevo/{id}")
	public String nuevoMensaje(Model model, @RequestParam(defaultValue="") String cuerpo, @RequestParam String destinatario, @RequestParam String remitente) {

		/*
		Optional<Chat> op = chtRepo.findById(id);
		
		
		if(op.isPresent()) {

			Chat chat = op.get();
			chat.insertarMensaje(cuerpo);
			model.addAttribute("chat", chat);

		}
		*/
		
		Chat c1 = new Chat(userRepo.findByNick(remitente), userRepo.findByNick(destinatario)); //u1 es remitente, u2 es destinatario
		chtRepo.save(c1);

		Mensaje m1 = new Mensaje (cuerpo);
		c1.addMensaje(m1);
		chtRepo.save(c1); 
		 
		
		return "mensaje_enviado";
	}

	@GetMapping("/mensaje/{id}")
	public String verMensaje(Model model, @PathVariable long id) {

		Optional<Mensaje> mesnaje = msgRepo.findById(id);

		if(mesnaje.isPresent()) {
			model.addAttribute("chat", mesnaje.get());
		}

		return "mensaje_enviado";
	}

}