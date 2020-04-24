package eu.winwinit.bcc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Carrello;
import eu.winwinit.bcc.entities.Ordine;
import eu.winwinit.bcc.service.ArticoloService;
import eu.winwinit.bcc.service.CarrelloService;
import eu.winwinit.bcc.service.OrdineService;

@RestController
@RequestMapping("/api/v1")
public class CarrelloController {

	@Autowired
	OrdineService ordineService;

	@Autowired
	ArticoloService articoloService;

	@Autowired
	CarrelloService carrelloService;

	@RequestMapping(value = "carrello-create", method = RequestMethod.POST)
	public ResponseEntity<String> carrelloCreate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "articoli") Integer[] articoli,
			@RequestParam(value = "quantita") Integer[] quantita) {

		boolean res = true;
		Ordine ordine = new Ordine();
		res = ordineService.save(ordine);
//		List<Carrello> carrello = new ArrayList<Carrello>();
		Optional<Articolo> optional = articoloService.findById(articoli[0]);
		Carrello c = new Carrello();
//		carrello.add(c);
		c.setArticolo(optional.get());
		c.setOrdine(ordine);
		c.setQuantita(quantita[0]);

		res = carrelloService.save(c);
		if (res)
			return ResponseEntity.ok().body("Success. Created");
		else
			return ResponseEntity.badRequest().body("Error. Cart not saves. Order delete.");
	}

	@RequestMapping(value = "carrello-findall", method = RequestMethod.POST)
	public ResponseEntity<?> carrelloFindAll(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken) {

//		Optional<Ordine> ordine = ordineService.findById(363);
//		
//		if(ordine.isPresent()) {
//			return new ResponseEntity<>(ordine.get().getCarrello(), HttpStatus.NOT_FOUND); 
//		}

		List<Carrello> res = carrelloService.findAll();

		if (res.isEmpty())
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(res, HttpStatus.FOUND);
	}

	@RequestMapping(value = "carrello-findbyid", method = RequestMethod.POST)
	public ResponseEntity<?> carrelloFindById(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Carrello> res = carrelloService.findById(id);

		if (res.isPresent()) {
			return new ResponseEntity<>(res.get(), HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		// Optional<Carrello> res = carrelloService.findById(366);

//		if (res.isPresent())
//			return new ResponseEntity<>(res.get(), HttpStatus.FOUND);
//		else
	}
	
	@RequestMapping(value = "carrello-findbyidordine", method = RequestMethod.POST)
	public ResponseEntity<?> carrelloFindByIdOrdine(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		List<Carrello> res = carrelloService.findByIdOrdine(id);

		if (!res.isEmpty()) {
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
