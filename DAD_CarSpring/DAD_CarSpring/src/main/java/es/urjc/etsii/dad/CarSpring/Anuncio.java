 package es.urjc.etsii.dad.CarSpring;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Anuncio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String comentario;
	private int precio;
	private boolean vendido;
	
	
	@OneToOne
	private Articulo articulo;
	@ManyToOne
	private Usuario anunciante;

	public Anuncio() {}
	
	public Anuncio(Articulo articulo, String comentario, int precio) {
		this.comentario = comentario;
		this.precio = precio;
		this.articulo = articulo;
		this.vendido = false;
	}
	
	public Anuncio(String nombre, String comentario, int precio) {
		super();
		this.articulo = new Articulo(nombre);
		this.comentario = comentario;
		this.precio = precio;
		this.vendido = false;
	}

	public long getId() {
		return id;
	}

	public String getComentario() {
		return comentario;
	}

	public int getPrecio() {
		return precio;
	}

	public Usuario getAnunciante() {
		return this.anunciante;
	}


	
	public Articulo getArticulo() {
		return this.articulo;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public void setPrecio(int precio) {
		this.precio = precio;
	} 
	
	public void setAnunciante(Usuario u){
		this.anunciante = u;
	}
	
	public boolean isVendido() {
		return this.vendido;
	}

	public void deleteArticulo() {
		this.articulo = null;
	}


	public void setVendido() {
		this.vendido = true;
	}
	
	public void setArticulo(Articulo art) {
		this.articulo = art;
	}
	
	@Override
	public String toString() {
		return "Anuncio [id=" + id + ", comentario=" + comentario + ", precio=" + precio + ", articulo=" + articulo
				+ ", anunciante=" + anunciante + "]";
	}



}

