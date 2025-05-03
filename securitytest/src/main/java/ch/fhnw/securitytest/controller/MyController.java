package ch.fhnw.securitytest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal; // Import hinzufügen

@RestController
@RequestMapping("/securitytest")
public class MyController {

    @GetMapping("/view")
    public ResponseEntity<String> showContent() {
        return new ResponseEntity<>("Everyone can view this content.", HttpStatus.OK);
    }
    
    @GetMapping("/admin")
    public ResponseEntity<String> showAdminContent(Principal principal) {
        String message = "Welcome, " + principal.getName() + "! <BR> Only an admin can view this content.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> showUserContent(Principal principal) {
        String message = "Welcome, " + principal.getName() + "! <BR> Only a user can view this content.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}