package es.urjc.etsii.dad.CarSpring;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class TablonController {
	

	@Autowired
	private AnuncioRepository adRepo;
	@Autowired
	private UsuarioRepository usRepo;



	@PostConstruct
	public void init() {

		
	}

	@GetMapping("/tablon")
	public String tablon(Model model, Pageable page, HttpServletRequest request) {
		Usuario usuarioActual = usRepo.findByNick(request.getUserPrincipal().getName());
		model.addAttribute("username", usuarioActual.getNick());
		model.addAttribute("anuncios", adRepo.findAll(page));

		return "tablon";
	}

	@GetMapping("/crearAnuncio")
	public String crearAnuncio() {
		return "nuevoAnuncio_form";
	}

	@PostMapping("/anuncio/nuevo")
	public String nuevoAnuncio(Model model, @RequestParam String nombre, @RequestParam int precio, @RequestParam(required=false) String categoria, @RequestParam(defaultValue="0") int anoFabricacion, @RequestParam String comentario, @RequestParam(defaultValue="0") int kilometros, @RequestParam String nombreUser) {
		
		Anuncio anuncio = new Anuncio(new Articulo(nombre, categoria, anoFabricacion, kilometros), comentario, precio);
		Usuario user = usRepo.findByNick(nombreUser);
		user.addAnuncio(anuncio);
		adRepo.save(anuncio);
		usRepo.save(user);

		return "anuncio_guardado";

	}

	@GetMapping("/anuncio/{id}")
	public String verAnuncio(Model model, @PathVariable long id, HttpServletRequest request) {
		
		Optional<Anuncio> op = adRepo.findById(id);
		Anuncio anuncio;
		
		if(op.isPresent()) {
			anuncio = op.get();
			if(anuncio.getArticulo().getAnoFabricacion() > 0) {
				model.addAttribute("hayAno", true);
			}
			if(anuncio.getArticulo().getCategoria() != "") {
				model.addAttribute("hayCat", true);
			}
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
		}
		return "ver_anuncio";
	}
	
	@GetMapping("/borrar_anuncio/{id}")
	public String borrarAnuncio(Model model, @PathVariable long id, Pageable page, HttpServletRequest request) {

		Usuario usuarioActual = usRepo.findByNick(request.getUserPrincipal().getName());
		model.addAttribute("username", usuarioActual.getNick());
		
		Optional<Anuncio> op = adRepo.findById(id);
		if(op.isPresent()) {
			Anuncio ad = op.get();
			Usuario propietario = ad.getAnunciante();
			propietario.borrarArticulo(ad.getArticulo());
			propietario.borrarAnuncio(ad);
			adRepo.deleteById(id);
		}
		model.addAttribute("anuncios", adRepo.findAll(page));
		return "tablon";
	}

}