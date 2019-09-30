package eu.winwinit.bcc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PingController {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("PING", HttpStatus.OK);
    }
}
