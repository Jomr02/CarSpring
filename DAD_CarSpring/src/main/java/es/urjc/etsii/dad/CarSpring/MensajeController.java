package es.urjc.etsii.dad.CarSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MensajeController {
	
	@Autowired
	private MensajesRepository msgRepo;

}
