/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices service = null;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoBlueprints() {
        try {
            Set<Blueprint> data = service.getAllBlueprints();
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}")
    public ResponseEntity<?> manejadorGetRecursoBlueprintsPorAutor(@PathVariable("author") String name) {
        try {
            Set<Blueprint> data = service.getBlueprintsByAuthor(name);
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error 404", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}/{bpname}")
    public ResponseEntity<?> manejadorGetRecursoBlueprintsAutorYObra(
            @PathVariable("author") String name,
            @PathVariable("bpname") String bpname) {
        try {
            Blueprint data = service.getBlueprint(name, bpname);
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("ERROR 404", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoBlueprint(@RequestBody Blueprint bp) {
        try {
            service.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("ERROR 403", HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(method = RequestMethod.PUT ,  path = "{author}/{bpname}")
    public ResponseEntity<?> manejadorPutRecursoBlueprint(
            @RequestBody Blueprint bp,
            @PathVariable("author") String author,
            @PathVariable("bpname") String bpName) {
        try {
            service.updateBlueprint(bp,author, bpName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("ERROR 403", HttpStatus.FORBIDDEN);
        }

    }
}
