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
import eu.winwinit.bcc.service.CarrelloService;
import eu.winwinit.bcc.service.OrdineService;

@RestController
@RequestMapping("/api/v1")
public class ArticoloController {

	@Autowired
	OrdineService ordineService;

	@Autowired
	ArticoloService articoloService;

	@Autowired
	CarrelloService carrelloService;

	@RequestMapping(value = "articolo-create", method = RequestMethod.POST)
	public ResponseEntity<?> articoloCreate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "name") String nome,
			@RequestParam(value = "descrizione", required = false) Optional<String> descrizione,
			@RequestParam(value = "quantita") Integer quantita, @RequestParam(value = "fornitore") String fornitore,
			@RequestParam(value = "prezzo_acq_prod") Double prezzo_acq_prod,
			@RequestParam(value = "prezzo_vend", required = false) Optional<Double> prezzo_vend) {

		Articolo articolo = new Articolo();

		// controllo valori nulli
		boolean checkNome = nome.matches(".*[a-zA-Z]+.*");
		boolean checkQuantita = (quantita != null) ? true : false;
		// TODO quando creo fornitore.class cambiare controllo
		boolean checkFornitore = fornitore.matches(".*[a-zA-Z]+.*");
		boolean checkPrezzo_acq_prod = (prezzo_acq_prod != null) ? true : false;
		boolean checkPrezzo_vend = (prezzo_vend.isPresent()) ? true : false;

		// controllo valori che siano accettabili
		checkQuantita = (checkQuantita && quantita >= 0) ? true : false;
		checkPrezzo_acq_prod = (checkPrezzo_acq_prod && prezzo_acq_prod > 0) ? true : false;
		checkPrezzo_vend = (checkPrezzo_vend) ? ((prezzo_vend.get() > 0) ? true : false) : true;

		boolean res = true;

		if (checkNome && checkQuantita && checkFornitore && checkPrezzo_acq_prod && checkPrezzo_vend) {

			// controllo articoli doppi not works
//			Optional<List<Articolo>> optional = articoloService.findByName(nome);
//			if (optional.isPresent()) {
//				for (Articolo art : optional.get()) {
//					if (fornitore.equals(art.getFornitore()) && prezzo_acq_prod == art.getPrezzo_acq_prod())
//						res = false;
//					// Ricorda: articolo è generico, se cambia ad esempio il modello lo si gestisce
//					// quando si creerà articolo specifico
//				}
//			}

			// se valori insetiti tutti ok inserisco prodotto
			if (res) {
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

				res = articoloService.save(articolo);

				if (res)
					return ResponseEntity.ok().body("Success. Created.");
				else
					return ResponseEntity.badRequest().body("Error. Not Created.");
			} else
				return ResponseEntity.badRequest().body("Error. Article already exist.");

		} else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");
	}

	@RequestMapping(value = "articolo-delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> articoloDelete(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Articolo> optional;
		boolean res = true;

		// controllo id che sia valido
		if (id != null)
			optional = articoloService.findById(id);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		// se articolo esiste controllo se è ordinato
		if (optional.isPresent()) {
			res = carrelloService.articleIsOrdinated(optional.get());
		} else
			return ResponseEntity.badRequest().body("Error. Article not exist.");

		// se articolo è ordinato i dati mi servono
		if (res) {
			// se articolo NON ordinato
			res = articoloService.deleteById(id);
			if (res)
				return ResponseEntity.ok().body("Success. Deleted.");
			else
				return ResponseEntity.badRequest().body("Error. Not Deleted.");
		} else { // se presente ordinato
			Articolo articolo = optional.get();
			articolo.setQuantita(0);
			res = articoloService.save(articolo);
			if (res)
				return ResponseEntity.ok().body("Quantity set to zero. Non-cancellable article.");
			else
				return ResponseEntity.badRequest().body("Error. Not save new quatity.");
		}
	}

	@RequestMapping(value = "articolo-findbyid", method = RequestMethod.POST)
	public ResponseEntity<?> articoloFindById(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Articolo> res;

		// controllo id che sia valido
		if (id != null)
			res = articoloService.findById(id);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

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
	public ResponseEntity<?> articoloFindByName(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "name") String nome) {

		boolean checkNome = nome.matches(".*[a-zA-Z]+.*");
		Optional<List<Articolo>> res;

		if (checkNome)
			res = articoloService.findByName(nome);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		if (res.isPresent())
			return new ResponseEntity<>(res.get(), HttpStatus.FOUND);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "articolo-update", method = RequestMethod.PUT)
	public ResponseEntity<String> articoloUpdate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String nome) {
		
		boolean res = nome.matches(".*[a-zA-Z]+.*");
		
		if (res && id != null)
			res = articoloService.updateName(id, nome);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		if (res)
			return ResponseEntity.ok().body("Updated");
		else
			return ResponseEntity.badRequest().body("Not Updated");
	}

	@RequestMapping(value = "articolo-update2", method = RequestMethod.PUT)
	public ResponseEntity<String> articoloUpdate2(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String nome) {
		
		boolean res = nome.matches(".*[a-zA-Z]+.*");
		
		if (res && id != null)
			res = articoloService.updateName2(id, nome);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");
		if (res)
			return ResponseEntity.ok().body("Updated");
		else
			return ResponseEntity.badRequest().body("Not Updated");
	}
}