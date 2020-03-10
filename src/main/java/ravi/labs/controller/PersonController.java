package ravi.labs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ravi.labs.exception.PersonConstraintVoilationException;
import ravi.labs.exception.PersonNotFoundException;
import ravi.labs.model.Person;
import ravi.labs.service.PersonDataService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class PersonController {
    Logger log = LoggerFactory.getLogger(PersonController.class);
    // TODO : Log end points along with input parameters

    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public Person person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) throws PersonNotFoundException {
        return personDataService.findPerson(lastName, firstName);
    }

    @GetMapping("/persons")
    public List<Person> persons(@PathParam(value="lastName") String lastName) {
        return personDataService.findAllPersonBySurname(lastName);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) throws PersonConstraintVoilationException {
        return new ResponseEntity<Person>(personDataService.addPerson(person), HttpStatus.CREATED);
    }
}