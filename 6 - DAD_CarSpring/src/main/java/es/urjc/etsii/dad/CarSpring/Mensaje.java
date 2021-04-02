package es.urjc.etsii.dad.CarSpring;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mensaje {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String cuerpo;
	//private SimpleDateFormat fecha = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	
	protected Mensaje(){
		
	}
	
	public Mensaje(String s) {
		this.cuerpo = s;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}



	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", cuerpo=" + cuerpo + "]";
	}	
	
}
