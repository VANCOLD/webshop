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
            return new ResponseEntity<>("The producer-list is empty!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGereById(@PathVariable Long id)
    {
        Optional<Producer> returnValue = producerService.findProducerById(id);

        if( returnValue.isPresent()  )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("No producer found with the id " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducer(@PathVariable Long id)
    {

        if(id < 0)
            return new ResponseEntity<>("A producer with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Optional<Producer> returnValue = this.producerService.deleteProducer(id);

        if( returnValue.isPresent() )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("The producer with the id " + id + " doesn't exist!", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> saveProducer(@RequestBody Producer producer)
    {
        if(producer.getProid() < 0)
            return new ResponseEntity<>("A producer with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Producer returnValue = this.producerService.saveProducer(producer);

        if( returnValue != null )
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        else
            return new ResponseEntity<>("A producer with the id " + producer.getProid() + " already exists!", HttpStatus.CONFLICT);
    }


    @PutMapping
    public ResponseEntity<?> updateProducer(@RequestBody Producer producer)
    {
        Long pid = producer.getProid();
        if(pid < 0)
            return new ResponseEntity<>("A producer with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);


        Optional<Producer> findValue = this.producerService.findProducerById(pid);
        Producer returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return new ResponseEntity<>("The producer with the id " + pid + " you are trying to update doesn't exist!", HttpStatus.CONFLICT);

        returnValue.setProid(pid);
        returnValue.setName(producer.getName());

        return new ResponseEntity<>( this.producerService.saveProducer(returnValue), HttpStatus.OK );
    }
}