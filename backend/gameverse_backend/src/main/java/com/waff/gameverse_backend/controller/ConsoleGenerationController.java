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
            return new ResponseEntity<>("The console generation-list is empty!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{cgid}")
    public ResponseEntity<?> findConsoleGenerationById(@PathVariable Long cgid)
    {
        Optional<ConsoleGeneration> returnValue = consoleGenerationService.findConsoleGenerationById(cgid);

        if( returnValue.isPresent()  )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("No console generation found with the id " + cgid, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{cgid}")
    public ResponseEntity<?> deleteConsoleGeneration(@PathVariable Long cgid)
    {

        if(cgid < 0)
            return new ResponseEntity<>("A console generation with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Optional<ConsoleGeneration> returnValue = this.consoleGenerationService.deleteConsoleGeneration(cgid);

        if( returnValue.isPresent() )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("The console generation with the id " + cgid + " doesn't exist!", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> saveConsoleGeneration(@RequestBody ConsoleGeneration consoleGeneration)
    {
        if(consoleGeneration.getCgid() < 0)
            return new ResponseEntity<>("A console generation with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        ConsoleGeneration returnValue = this.consoleGenerationService.saveConsoleGeneration(consoleGeneration);

        if( returnValue != null )
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        else
            return new ResponseEntity<>("A console generation with the id " + consoleGeneration.getCgid() + " already exists!", HttpStatus.CONFLICT);
    }


    @PutMapping
    public ResponseEntity<?> updateConsoleGeneration(@RequestBody ConsoleGeneration consoleGeneration)
    {
        Long cgid = consoleGeneration.getCgid();
        if(cgid < 0)
            return new ResponseEntity<>("A console generation with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);


        Optional<ConsoleGeneration> findValue = this.consoleGenerationService.findConsoleGenerationById(cgid);
        ConsoleGeneration returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return new ResponseEntity<>("The console generation with the id " + cgid + " you are trying to update doesn't exist!", HttpStatus.CONFLICT);

        returnValue.setCgid(cgid);
        returnValue.setName(consoleGeneration.getName());
        returnValue.setIconPath(consoleGeneration.getIconPath());

        return new ResponseEntity<>( this.consoleGenerationService.saveConsoleGeneration(returnValue), HttpStatus.OK );
    }
}