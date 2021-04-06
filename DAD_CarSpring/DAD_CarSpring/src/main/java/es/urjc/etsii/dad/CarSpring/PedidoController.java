package es.urjc.etsii.dad.CarSpring;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;



@Controller
public class PedidoController {

	@Autowired
	private AnuncioRepository adRepo;
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private ArticuloRepository artRepo;
	@Autowired
	private PedidoRepository peRepo; 

	
	
	@GetMapping("/hacerPedido/{id}")
	public String hacerPedido(Model model, @PathVariable long id, HttpServletRequest request) {
		
		Usuario comprador = userRepo.findByNick(request.getUserPrincipal().getName());
		Anuncio anuncio = adRepo.findById(id).get();
		Usuario vendedor = anuncio.getAnunciante();
		Pedido pedido = new Pedido(comprador, anuncio); // Creo una instancia de Pedido
//		peRepo.save(pedido);
//		comprador.addPedido(pedido);
	
//////// Este bloque de código asigna un nuevo dueño al articulo. Saca el articulo de la lista de posesiones del vendedor, y lo mete en la del comprador. El anuncio se marca como vendido
		Articulo aux = anuncio.getArticulo();
		Articulo newArt = new Articulo(aux.getNombre(), aux.getCategoria(), aux.isPublico(), aux.getAnoFabricacion()); //Hago una copia del articulo, ID distinto
		artRepo.save(newArt);
		vendedor.borrarArticulo(aux);   	// Le quito el objeto de su lista al vendedor, pero no lo borro de la base de datos para que lo pueda ver en su historial
		comprador.addArticulo(newArt);		// Añado el articulo a los objetos del comprador
		anuncio.setVendido();				// El anuncio se marca como vendido (luego aparecerá en una lista de anuncios vendidos en el perfil del vendedor).  
////////////////		
		
		userRepo.save(vendedor);
		userRepo.save(comprador);
		model.addAttribute("userActual", comprador);
		model.addAttribute("username", comprador.getNick());
		
		//Comunicación por REST
		//String url = PARTIDOS_URL;
		HttpEntity<Pedido> pedidoRequest= new HttpEntity<>(pedido);
		RestTemplate.exchange(url, HttpMethod.GET,pedidoRequest,Void.class);
	    
	    
		return "pedido_realizado";
		
	}

}