package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.ConsoleGeneration;
import com.waff.gameverse_backend.service.ConsoleGenerationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/console_generation")
public class ConsoleGenerationController {

    private final ConsoleGenerationService consoleGenerationService;

    public ConsoleGenerationController(ConsoleGenerationService consoleGenerationService) {
        this.consoleGenerationService = consoleGenerationService;
    }

    @GetMapping
    public ResponseEntity<?> findAllConsoleGeneration()
    {
        List<ConsoleGeneration> response = this.consoleGenerationService.findAllConsoleGeneration();

        if (response.isEmpty() )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The console generation-list is empty!");
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{cgid}")
    public ResponseEntity<?> findConsoleGenerationById(@PathVariable Long cgid)
    {
        Optional<ConsoleGeneration> returnValue = consoleGenerationService.findConsoleGenerationById(cgid);

        if( returnValue.isPresent()  )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No console generation found with the id " + cgid);
    }

    @DeleteMapping("/{cgid}")
    public ResponseEntity<?> deleteConsoleGeneration(@PathVariable Long cgid)
    {

        if(cgid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A console generation with an id smaller than 0 can not exist!");

        Optional<ConsoleGeneration> returnValue = this.consoleGenerationService.deleteConsoleGeneration(cgid);

        if( returnValue.isPresent() )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The console generation with the id " + cgid + " doesn't exist!");
    }


    @PostMapping
    public ResponseEntity<?> saveConsoleGeneration(@RequestBody ConsoleGeneration consoleGeneration)
    {
        if(consoleGeneration.getCgid() < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A console generation with an id smaller than 0 can not exist!");

        ConsoleGeneration returnValue = this.consoleGenerationService.saveConsoleGeneration(consoleGeneration);

        if( returnValue != null )
            return ResponseEntity.ok(returnValue);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A console generation with the id " + consoleGeneration.getCgid() + " already exists!");
    }


    @PutMapping
    public ResponseEntity<?> updateConsoleGeneration(@RequestBody ConsoleGeneration consoleGeneration)
    {
        Long cgid = consoleGeneration.getCgid();
        if(cgid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A console generation with an id smaller than 0 can not exist!");


        Optional<ConsoleGeneration> findValue = this.consoleGenerationService.findConsoleGenerationById(cgid);
        ConsoleGeneration returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The console generation with the id " + cgid + " you are trying to update doesn't exist!");

        returnValue.setCgid(cgid);
        returnValue.setName(consoleGeneration.getName());
        returnValue.setIconPath(consoleGeneration.getIconPath());

        return ResponseEntity.ok( this.consoleGenerationService.saveConsoleGeneration(returnValue) );
    }
}