package es.urjc.etsii.dad.CarSpring;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;



@Controller
public class AnuncioController {
	
	//Si se abre la URL http://127.0.0.1:8080/h2-console y se configura
	//la URL JDBC con el valor jdbc:h2:mem:testdb se puede acceder a la 
	//base de datos de la aplicación 
	//Green: Voy a utilizar esto para crear un pedido de la misma forma que hacemos el anuncio a usuario.

	@Autowired
	private AnuncioRepository adRepo;
	@Autowired
	private UsuarioRepository usRepo;
	@Autowired
	private ArticuloRepository artRepo;
	

	@GetMapping("/tablon")
	public String tablon(Model model, Pageable page, HttpServletRequest request) {
		Usuario usuarioActual = usRepo.findByNick(request.getUserPrincipal().getName());

		model.addAttribute("username", usuarioActual.getNick());
		model.addAttribute("anuncios", adRepo.findAll(page));

		return "tablon";
	}
	
	@GetMapping("/crearAnuncio")
	public String crearAnuncio(Model model, HttpServletRequest request) {
		model.addAttribute("username", request.getUserPrincipal().getName());
		return "nuevoAnuncio_form";
	}
	
	@CacheEvict(cacheNames="anuncios", allEntries=true)
	@PostMapping("/anuncio/nuevo")
	public String nuevoAnuncio(Model model, @RequestParam String nombre, @RequestParam int precio, @RequestParam(required=false) String categoria, @RequestParam(defaultValue="0") int anoFabricacion, @RequestParam String comentario, HttpServletRequest request) {
		Usuario usuarioActual = usRepo.findByNick(request.getUserPrincipal().getName());
		Articulo art = new Articulo(nombre, categoria, anoFabricacion);
		artRepo.save(art);
		Anuncio anuncio = new Anuncio(art, comentario, precio);
		usuarioActual.addAnuncio(anuncio);
		adRepo.save(anuncio);
		
		//Comunicación por REST
		RestTemplate rest = new RestTemplate();
		String anuncios_link = "http://lbsi/email/anuncio";
		HttpEntity<Anuncio> mailRequest= new HttpEntity<>(anuncio);
	    rest.exchange(anuncios_link, HttpMethod.POST,mailRequest,Void.class);
	    
		model.addAttribute("username", request.getUserPrincipal().getName());
		return "anuncio_guardado";

	}

	@GetMapping("/anuncio/{id}")
	public String verAnuncio(Model model, @PathVariable long id, HttpServletRequest request) {
		
		String username = usRepo.findByNick(request.getUserPrincipal().getName()).getNick();
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
			
			model.addAttribute("anuncio", anuncio);
			model.addAttribute("enVenta", !anuncio.isVendido());
		}
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		model.addAttribute("username", request.getUserPrincipal().getName());
		return "ver_anuncio";
	}
	
	@Transactional  // Hace falta anotarlo con Transactional para que no me de un Foreing Key Constraint Error al intentar borrarlo, porque se supone que anuncio es una entidad "padre" de articulo
	@GetMapping("/borrar_anuncio/{id}")
	public String borrarAnuncio(Model model, @PathVariable long id, Pageable page, HttpServletRequest request) {
		
		Optional<Anuncio> op = adRepo.findById(id);
		if(op.isPresent()) {
			Anuncio ad = op.get();
			Usuario anunciante = ad.getAnunciante();
			
			anunciante.borrarAnuncio(ad);	//Se borra el anuncio de la lista de anuncios del que lo publicó
			
			// El artículo solo se va a eliminar tambien si no ha sido vendido: en ese caso su dueño ahora sería el comprador y él no querría perder la referencia a su nuevo articulo
			if(!ad.isVendido()) {
				Articulo art = ad.getArticulo();
				anunciante.borrarArticulo(art);  // Lo quito de la lista de articulos del usuario
				artRepo.delete(art);			 // Borro el articulo de su repo
			}
			adRepo.deleteById(id);				 //
		}
		
		model.addAttribute("username", request.getUserPrincipal().getName());
		model.addAttribute("anuncios", adRepo.findAll(page));
		return "tablon";
	}
	

}