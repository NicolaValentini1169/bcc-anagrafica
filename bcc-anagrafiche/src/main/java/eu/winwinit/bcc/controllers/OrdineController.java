package eu.winwinit.bcc.controllers;

import java.util.ArrayList;
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
import eu.winwinit.bcc.entities.Carrello;
import eu.winwinit.bcc.entities.Ordine;
import eu.winwinit.bcc.service.ArticoloService;
import eu.winwinit.bcc.service.CarrelloService;
import eu.winwinit.bcc.service.OrdineService;

@RestController
@RequestMapping("/api/v1")
public class OrdineController {

	@Autowired
	OrdineService ordineService;

	@Autowired
	ArticoloService articoloService;

	@Autowired
	CarrelloService carrelloService;

	@RequestMapping(value = "ordine-create", method = RequestMethod.POST)
	public ResponseEntity<?> ordineCreate(@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "quantita") Integer[] quantita, @RequestParam(value = "articoli") Integer[] articoli,
			@RequestParam(value = "datiCompratore") String datiCompratore,
			@RequestParam(value = "datiVenditore") String datiVenditore,
			@RequestParam(value = "datiSpedizione") String datiSpedizione) {

		// manca creazione di un metodo apposito synchronized per la modifica della
		// quantita
		// per ora mi limito a modificarla solo se l'ordine va a buon fine
		// quindi per ora non gestisco se ricevo due ordini in contemporanea
		// soluzione temporanea sarebbe toglierla immediatamente e rimetterla se
		// ordinazione fallisce

		boolean res = true;
		Ordine ordine = new Ordine();
		List<Carrello> carrello = new ArrayList<Carrello>();

		// controllo valori nulli e che mi ha inserito lo stesso numero di articoli e
		// quantita e che non siano zero
		boolean checkArticoliQuantita = (quantita.length == articoli.length && articoli.length != 0) ? true : false;
		// TODO quando creo le opportune classi cambiare controllo
		boolean checkDatiCompratore = datiCompratore.matches(".*[a-zA-Z]+.*");
		boolean checkDatiVenditore = datiVenditore.matches(".*[a-zA-Z]+.*");
		boolean checkDatiSpedizione = datiSpedizione.matches(".*[a-zA-Z]+.*");

		if (checkArticoliQuantita && checkDatiCompratore && checkDatiVenditore && checkDatiSpedizione) {

			// controllo che gli articoli esistano e siano ordinabili in base alla quantita
			// richiesta
			int i = 0; // i è un contatore che evita nullPointerException
			while (res && i < articoli.length) {
				Articolo articolo = articoloService.articleIsOrdinable(articoli[i], quantita[i]);
				if (articolo != null && articolo.getPrezzo_vend() != null) {
					carrello.add(new Carrello(articolo, ordine, quantita[i]));
					i++;
				} else {
					res = false;
				}
			}
		} else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered. Data.");

		// se articoli e quantita esistono controllo valori dati che esistano
		if (res)
			res = ordine.setOrdine(datiCompratore, datiVenditore, datiSpedizione, carrello);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered. Articles or quantities.");

		// se valori dati ok salvo ordine
		if (res)
			res = ordineService.save(ordine);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered. Data.");

		// se ordine salvato correttamente posso salvare carrello
		if (res)
			res = carrelloService.save(carrello);
		else
			return ResponseEntity.badRequest().body("Error. Order not saves.");

		// se carrello salvato correttamente posso togliere quantita da magazzino se no
		// annullo tutto
		if (res) {
			articoloService.removeQuantita(carrello);
		} else {
			res = ordineService.deleteById(ordine.getId());
			if (res) {
				// ordinazione annullata con successo
				return ResponseEntity.badRequest().body("Error. Cart not saves. Order delete.");
			} else {
				// vuol dire che ho un ordine senza carrello
				return ResponseEntity.badRequest()
						.body("Error. Cart not saves. Order not delete. Id: " + ordine.getId());
			}
		}

		// se modifica alla quantita salvata correttamente esco con successo se no
		// annullo tutto
		if (res)
			return ResponseEntity.ok().body("Success. Created");
		else {
			// TODO manca da verificare le varie cancellazioni res = ...
			articoloService.addQuantita(carrello);
			for (Carrello c : carrello) {
				res = carrelloService.deleteById(c.getId());
			}
			res = ordineService.deleteById(ordine.getId());
			return ResponseEntity.badRequest().body("Error. Order failed. Unchecked deleting.");
		}
	}

	@RequestMapping(value = "ordine-delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> ordineDelete(@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Ordine> optional;
		boolean res = true;

		// controllo id che sia valido
		if (id != null)
			optional = ordineService.findById(id);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		// se ordine esiste lo cancello
		if (optional.isPresent()) {

			// cerco articoli dentro quel ordine
			List<Carrello> carrello = carrelloService.findByIdOrdine(optional.get().getId());

			// cancello modifiche alla quantita di quegli articoli
			articoloService.addQuantita(carrello);

			// cancello carrello altrimenti blocco tutto
			// ! senza carrello poi non potrei piu sistemare quantita correttamente !
			// quindi prima sistemo quantita e poi cancello carrello
			for (Carrello c : carrello) {
				if (res)
					// cancello le righe di carrello
					res = carrelloService.deleteById(c.getId());
			}

			// se carrello cancellato correttamente cancello ordine
			if (res) {
				// cancello riga di ordine
				res = ordineService.deleteById(id);
			} else {
				articoloService.removeQuantita(carrello);
				return ResponseEntity.badRequest().body("Error. Cart not deleted.");
			}

			// se anche ordine cancellatto correttamente ritorno con successo
			if (res)
				return ResponseEntity.ok().body("Deleted");
			else {
				articoloService.removeQuantita(carrello);
				carrelloService.save(carrello);
				return ResponseEntity.badRequest().body("Not Deleted");
			}
		} else
			return ResponseEntity.badRequest().body("Error. Order not exist.");
	}

	@RequestMapping(value = "ordine-findall", method = RequestMethod.POST)
	public ResponseEntity<?> ordineFindAll(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken) {

		List<Ordine> ordini = ordineService.findAll();

		if (ordini.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(ordini, HttpStatus.FOUND);
	}

	@RequestMapping(value = "ordine-findbyid", method = RequestMethod.POST)
	public ResponseEntity<?> ordineFindById(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "id") Integer id) {

		Optional<Ordine> optional;

		// controllo id che sia valido
		if (id != null)
			optional = ordineService.findById(id);
		else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		if (optional.isPresent()) {
			// cerco articoli dentro quel ordine
			List<Carrello> carrello = carrelloService.findByIdOrdine(optional.get().getId());

			// creo stringa con dati ordine e dati di tutti gli articoli
			String res = "Ordine:\n" + optional.get().toString() + "\nArticoli:";
			for (Carrello c : carrello) {
				res += "\n" + c.getArticolo().toString() + " [ quantita : " + c.getQuantita() + "]";
			}
			return new ResponseEntity<>(res, HttpStatus.FOUND);
		} else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@SuppressWarnings({ "null", "unused" })
	@RequestMapping(value = "ordine-update", method = RequestMethod.PUT)
	public ResponseEntity<?> ordineUpdateArticolo(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "idOrdine") Integer idOrdine, @RequestParam(value = "idArticolo") Integer idArticolo,
			@RequestParam(value = "quantita") Integer quantita) {

		Optional<Ordine> optionalOrdine;
		Optional<Articolo> optionalArticolo;

		boolean checkQuantita = (quantita != null) ? true : false;

		// controllo che id e quantita != null
		if (idOrdine != null && idArticolo != null && checkQuantita) {
			optionalOrdine = ordineService.findById(idOrdine);
			optionalArticolo = articoloService.findById(idArticolo);
		} else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		// controllo che ordine, articolo e quantita da inserire esistano
		if (optionalOrdine.isPresent() && optionalArticolo.isPresent() && quantita > 0) {

			// salvo ordine, articolo e carrello in variabili piu comode
			Ordine ordine = optionalOrdine.get();
			Articolo articolo = optionalArticolo.get();
			List<Carrello> carrello = carrelloService.findByIdOrdine(ordine.getId());

			// contiene lista con nuova riga di carrello che ha quantita aggiornata
			Carrello newCarrello;
			// contiene lista con vecchia riga di carrello che ha quantita vecchia
			Carrello oldCarrello = null;

			// se articolo è gia presente in order lo salvo su oldCarrello
			for (Carrello c : carrello) {
				if (c.getArticolo() == articolo) {
					oldCarrello = c;
				}
			}

			// se è vuoto, articolo non esisteva gia in ordine e quindi va inserito
			if (oldCarrello == null) {

				// controllo se ordinabile in base alla quantita
				if (articoloService.articleIsOrdinable(articolo.getId(), quantita) != null)
					// salvo nuova riga di carrello, deve aggiungerla nuova
					newCarrello = new Carrello(articolo, ordine, quantita);
				else {
					return ResponseEntity.badRequest().body("Error. Quantity in store insufficient.");
				}

				// salvo nuova riga di carrello
				boolean res = carrelloService.save(newCarrello);

				// se aggiornamento carrelo ok, aggiorno quantita in magazzino e salvo i costi
				if (res) {
					// ci pensa removeQuantita a prendersi articolo, aggiornarlo e salvarlo
					articoloService.removeQuantita(newCarrello);

					// aggiungo nuova riga al vecchio carrello che aveva gia tutti i valori
					carrello.add(newCarrello);
					res = ordine.updateCosti(carrello);
					res = ordineService.save(ordine);
				} else {
					return ResponseEntity.badRequest().body("Error. Update cart not save.");
				}

				// se ordine aggiornato correttamente esco se no annullo tutto
				if (res)
					return ResponseEntity.ok().body("Updated");
				else {
					carrelloService.deleteById(newCarrello.getId());
					articoloService.addQuantita(newCarrello);
					return ResponseEntity.badRequest().body("Error. Update order not save.");
				}
			} else {
				// salvo nuova riga carrello
				newCarrello = new Carrello();
				newCarrello.setId(oldCarrello.getId());
				newCarrello.setArticolo(oldCarrello.getArticolo());
				newCarrello.setOrdine(oldCarrello.getOrdine());
				newCarrello.setQuantita(quantita);

				// salvo vecchia quantita se no poi la perdo
				Integer vecchiaQuantita = oldCarrello.getQuantita();

				// aggiorno magazzino salvando articolo in automatico
				articoloService.addQuantita(oldCarrello);

				// controllo se ordinaile in base alla quantita
				if (articoloService.articleIsOrdinable(newCarrello.getArticolo().getId(), quantita) != null)
					articoloService.removeQuantita(newCarrello);
				else {
					oldCarrello.setQuantita(vecchiaQuantita);
					articoloService.removeQuantita(oldCarrello);
					return ResponseEntity.badRequest().body("Error. Quantity in store insufficient.");
				}

				// salvo nuova riga di carrello
				boolean res = carrelloService.save(newCarrello);

				// se aggiornato correttamente carrello, aggiorno costi
				if (res) {
					// cancello vecchia riga dal carrello che aveva tutti i vecchi valori
					carrello.remove(oldCarrello);
					// aggiungo nuova riga al carrello cosi da mantenere tutti i vecchi valori
					carrello.add(newCarrello);
					res = ordine.updateCosti(carrello);
					res = ordineService.save(ordine);
				} else {
					articoloService.addQuantita(newCarrello);
					oldCarrello.setQuantita(vecchiaQuantita);
					articoloService.removeQuantita(oldCarrello);
					return ResponseEntity.badRequest().body("Error. Update carrello not save.");
				}

				// se ordine aggiornato correttamente
				if (res)
					return ResponseEntity.ok().body("Updated");
				else {
					// TODO sistemare else che non vanno
					articoloService.addQuantita(newCarrello);
					oldCarrello.setQuantita(vecchiaQuantita);
					articoloService.removeQuantita(oldCarrello);
					res = carrelloService.save(oldCarrello);
					return ResponseEntity.badRequest().body("Error. Update order not save.");
				}
			}
		} else
			return ResponseEntity.badRequest().body("Error. Order, Article or quantity don't exist.");
	}

	@RequestMapping(value = "ordine-updatedelete", method = RequestMethod.POST)
	public ResponseEntity<?> ordineDeleteArticolo(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam(value = "idOrdine") Integer idOrdine,
			@RequestParam(value = "idArticolo") Integer idArticolo) {

		Optional<Ordine> optionalOrdine;
		Optional<Articolo> optionalArticolo;

		// controllo che id != null
		if (idOrdine != null && idArticolo != null) {
			optionalOrdine = ordineService.findById(idOrdine);
			optionalArticolo = articoloService.findById(idArticolo);
		} else
			return ResponseEntity.badRequest().body("Error. Incorrect data entered.");

		// controllo che ordine e articolo da cancellare esistano
		if (optionalOrdine.isPresent() && optionalArticolo.isPresent()) {

			// metto in ordine l'ordine e in articolo l'articolo
			Ordine ordine = optionalOrdine.get();
			Articolo articolo = optionalArticolo.get();

			// metto in carrello il carrello di quel ordine
			List<Carrello> carrello = carrelloService.findByIdOrdine(ordine.getId());

			// contiene riga da cancellare se presente
			Carrello oldCarrello = null;

			// cerco articolo nell'ordine
			for (Carrello c : carrello) {
				if (c.getArticolo() == articolo) {
					oldCarrello = c;
				}
			}

			// se NON è vuoto, l'articolo è presente, se non è l'unico articolo dell'ordine
			// allora entro
			if (oldCarrello != null && carrello.size() >= 2) {
				// sistemo magazzino
				articoloService.addQuantita(oldCarrello);

				// cancello vecchia riga dal carrello che aveva tutti i vecchi valori
				carrello.remove(oldCarrello);

				// ricalcolo costi
				boolean res = ordine.updateCosti(carrello);
				res = ordineService.save(ordine);

				// se costi aggiornati cancello riga da carrello
				if (res) {
					res = carrelloService.deleteById(oldCarrello.getId());
				} else {
					articoloService.removeQuantita(oldCarrello);
					return ResponseEntity.badRequest().body("Error. Order not save.");
				}

				// se riga da carrello cancellata successo se no risistemo magazzino e ordine
				if (res)
					return ResponseEntity.ok().body("Updated");
				else {
					articoloService.removeQuantita(oldCarrello);
					carrello.add(oldCarrello);
					res = ordine.updateCosti(carrello);
					res = ordineService.save(ordine);
					return ResponseEntity.badRequest().body("Error. Carrello not save.");
				}
			} else
				return ResponseEntity.badRequest().body("Error. Article not exist in Order or is the only.");
		} else
			return ResponseEntity.badRequest().body("Error. Order or Article don't exist.");
	}
}