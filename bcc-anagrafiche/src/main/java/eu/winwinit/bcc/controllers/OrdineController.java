package eu.winwinit.bcc.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import eu.winwinit.bcc.entities.Ordine;
import eu.winwinit.bcc.service.ArticoloService;
import eu.winwinit.bcc.service.OrdineService;

@RestController
@RequestMapping("/api/v1")
public class OrdineController {
	
	/*
	 * test per git 2
	 * */

	@Autowired
	OrdineService ordineService;

	@Autowired
	ArticoloService articoloService;

	@RequestMapping(value = "ordine-create", method = RequestMethod.POST)
	public ResponseEntity<String> ordineCreate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "quantita") Integer[] quantita, 
			@RequestParam(value = "articoli") Integer[] articoli) {

		boolean res = true;
		Ordine ordine = new Ordine();
		Set<Articolo> carrello = new HashSet<Articolo>();

		if (quantita.length == articoli.length) { // controllo che mi ha inserito lo stesso numero di articoli e
													// quantita
			int i = 0;
			while (res && i < articoli.length) {
				Optional<Articolo> optional = articoloService.findById(articoli[i]);
				if (optional.isPresent() && quantita[i] > 0) {
					carrello.add(optional.get());
					ordine.setQuantita(quantita[i]);
					i++;
				} else {
					res = false;
					return ResponseEntity.badRequest().body("No. art o q");
				}
			}

			if (res) {
				ordine.setArticoli(carrello);
				res = ordineService.save(ordine);
			}
		} else {
			res = false;
			return ResponseEntity.badRequest().body("No. lenght");
		}

		if (res)
			return ResponseEntity.ok().body("Created");
		else
			return ResponseEntity.badRequest().body("Not Created");
	}

	@RequestMapping(value = "ordine-delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> articoloDelete(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		boolean res = ordineService.deleteById(id);

		if (res)
			return ResponseEntity.ok().body("Deleted");
		else
			return ResponseEntity.badRequest().body("Not Deleted");

	}

	@RequestMapping(value = "ordine-findall", method = RequestMethod.POST)
	public ResponseEntity<List<Ordine>> articoloFindAll(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken) {

		List<Ordine> ordini = ordineService.findAll();

		if (ordini.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(ordini, HttpStatus.FOUND);
	}
	
	@RequestMapping(value = "ordine-findbyid", method = RequestMethod.POST)
	public ResponseEntity<Ordine> articoloFindById(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Ordine> ordini = ordineService.findById(id);

		if (ordini.isPresent())
			return new ResponseEntity<>(ordini.get(), HttpStatus.FOUND);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}