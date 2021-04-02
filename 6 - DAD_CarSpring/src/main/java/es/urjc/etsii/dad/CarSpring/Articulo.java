package es.urjc.etsii.dad.CarSpring;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;



@Entity
public class Articulo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String categoria;
	private boolean publico = true;
	private int anoFabricacion;//Solo el año
	private int kilometros;
	private String urlFoto;
	
	public Articulo() {
		
	}
	
	public Articulo(String nombre) {

		this.nombre = nombre;
		this.categoria = "";
		this.urlFoto = "imagenprueba.jpg";
	}
	
	public Articulo(String nombre, int anoFabricacion) {
		this.nombre = nombre;
		this.categoria = "";
		if(anoFabricacion > 0) {
			this.anoFabricacion = anoFabricacion;
		} else this.anoFabricacion = 0;
		this.urlFoto = "imagenprueba.jpg";
	}
	
	public Articulo(String nombre, String categoria, int anoFabricacion) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		if(anoFabricacion > 0) {
			this.anoFabricacion = anoFabricacion;
		} else this.anoFabricacion = 0;
		this.urlFoto = "imagenprueba.jpg";
	}
	
	public Articulo(String nombre, String categoria, boolean publico) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.publico = publico;
		this.anoFabricacion = 0;
		this.urlFoto = "imagenprueba.jpg";
	}
			
			
	public Articulo(String nombre, String categoria, boolean publico, int anoFabricacion) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.publico = publico;
		if(anoFabricacion > 0) {
			this.anoFabricacion = anoFabricacion;
		}
		else this.anoFabricacion = 0;
		
		this.urlFoto = "imagenprueba.jpg";
	}
	
	public Articulo(String nombre, String categoria, int anoFabricacion, int kilometros) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		if(anoFabricacion > 0) {
			this.anoFabricacion = anoFabricacion;
		}
		else this.anoFabricacion = 0;
		this.kilometros = kilometros;
		this.urlFoto = "imagenprueba.jpg";
	}
	
	public Articulo(String nombre, String categoria, boolean publico, int anoFabricacion, int kilometros) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.publico = publico;
		if(anoFabricacion > 0) {
			this.anoFabricacion = anoFabricacion;
		}
		else this.anoFabricacion = 0;
		this.kilometros = kilometros;
		this.urlFoto = "imagenprueba.jpg";
	}

	public String getNombre() {
		return nombre;
	}

	public long getId() {
		return id;
	}

	public String getUrlFoto() {
		return urlFoto;
	}


	public String getCategoria() {
		return categoria;
	}

	public boolean isPublico() {
		return publico;
	}

	public int getAnoFabricacion() {
		return anoFabricacion;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUrlFoto(String uri) {
		this.urlFoto = uri;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public void setAnoFabricacion(int anoFabricacion) {
		this.anoFabricacion = anoFabricacion;
	}

	@Override
	public String toString() {
		return "Articulo [id=" + id + ", foto=" + nombre + ", categoria=" + categoria + ", publico=" + publico
				+ ", anoFabricacion=" + anoFabricacion + "]";
	}
	
	
	
}
