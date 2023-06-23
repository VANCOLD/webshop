package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Producer;
import com.waff.gameverse_backend.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public ResponseEntity<?> findAllProducers()
    {
        List<Producer> response = this.producerService.findAllProducers();

        if (response.isEmpty() )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The producer-list is empty!");
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGereById(@PathVariable Long id)
    {
        Optional<Producer> returnValue = producerService.findProducerById(id);

        if( returnValue.isPresent()  )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No producer found with the id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducer(@PathVariable Long id)
    {

        if(id < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A producer with an id smaller than 0 can not exist!");

        Optional<Producer> returnValue = this.producerService.deleteProducer(id);

        if( returnValue.isPresent() )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The producer with the id " + id + " doesn't exist!");
    }


    @PostMapping
    public ResponseEntity<?> saveProducer(@RequestBody Producer producer)
    {
        if(producer.getProid() < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A producer with an id smaller than 0 can not exist!");

        Producer returnValue = this.producerService.saveProducer(producer);

        if( returnValue != null )
            return ResponseEntity.ok(returnValue);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A producer with the id " + producer.getProid() + " already exists!");
    }


    @PutMapping
    public ResponseEntity<?> updateProducer(@RequestBody Producer producer)
    {
        Long pid = producer.getProid();
        if(pid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A producer with an id smaller than 0 can not exist!");


        Optional<Producer> findValue = this.producerService.findProducerById(pid);
        Producer returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The producer with the id " + pid + " you are trying to update doesn't exist!");

        returnValue.setProid(pid);
        returnValue.setName(producer.getName());

        return ResponseEntity.ok( this.producerService.saveProducer(returnValue));
    }
}