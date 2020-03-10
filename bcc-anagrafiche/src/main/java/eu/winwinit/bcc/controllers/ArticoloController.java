package eu.winwinit.bcc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.service.ArticoloService;

@RestController
@RequestMapping("/api/v1")
public class ArticoloController {

	@Autowired
	ArticoloService articoloService;

	@RequestMapping(value = "articolo-create", method = RequestMethod.POST)
	public ResponseEntity<String> articoloCreate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "name") String nome,
			@RequestParam(value = "descrizione", required = false) Optional<String> descrizione,
			@RequestParam(value = "quantita") Integer quantita, 
			@RequestParam(value = "fornitore") String fornitore,
			@RequestParam(value = "prezzo_acq_prod") Double prezzo_acq_prod,
			@RequestParam(value = "prezzo_vend", required = false) Optional<Double> prezzo_vend) {

		Articolo articolo = new Articolo();

		articolo.setNome(nome);
		descrizione.ifPresent(desc -> {
			articolo.setDescrizione(desc);
		});
		articolo.setQuantita(quantita);
		articolo.setFornitore(fornitore);
		articolo.setPrezzo_acq_prod(prezzo_acq_prod);
		prezzo_vend.ifPresent(prezv -> {
			articolo.setPrezzo_vend(prezv);
		});

		boolean res = articoloService.save(articolo);

		if (res)
			return ResponseEntity.ok().body("Created");
		else
			return ResponseEntity.badRequest().body("Not Created");
	}

	@RequestMapping(value = "articolo-delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> articoloDelete(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		boolean res = articoloService.deleteById(id);

		if (res)
			return ResponseEntity.ok().body("Deleted");
		else
			return ResponseEntity.badRequest().body("Not Deleted");
	}

	@RequestMapping(value = "articolo-findbyid", method = RequestMethod.POST)
	public ResponseEntity<Articolo> articoloFindById(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Articolo> res = articoloService.findById(id);

		if (res.isPresent())
			return new ResponseEntity<>(res.get(), HttpStatus.FOUND);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "articolo-findall", method = RequestMethod.POST)
	public ResponseEntity<List<Articolo>> articoloFindAll(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken) {

		List<Articolo> articoli = articoloService.findAll();

		if (articoli.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(articoli, HttpStatus.FOUND);
	}
	
	@RequestMapping(value = "articolo-findbyname", method = RequestMethod.POST)
	public ResponseEntity<List<Articolo>> articoloFindByName(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "name") String name) {

		Optional<List<Articolo>> res = articoloService.findByName(name);

		if (res.isPresent())
			return new ResponseEntity<>(res.get(), HttpStatus.FOUND);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "articolo-update", method = RequestMethod.PUT)
	public ResponseEntity<String> articoloUpdate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "name") String name) {
	
		boolean res = articoloService.updateName(id, name);

		if (res)
			return ResponseEntity.ok().body("Updated");
		else
			return ResponseEntity.badRequest().body("Not Updated");
	}
}
