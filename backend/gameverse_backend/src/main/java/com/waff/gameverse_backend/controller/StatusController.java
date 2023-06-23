package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Status;
import com.waff.gameverse_backend.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<?> findAllStatus()
    {
        List<Status> response = this.statusService.findAllStatus();

        if (response.isEmpty() )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The status-list is empty!");
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{sid}")
    public ResponseEntity<?> findStatusById(@PathVariable Long sid)
    {
        Optional<Status> returnValue = statusService.findStatusById(sid);

        if( returnValue.isPresent()  )
            return ResponseEntity.ok(returnValue.get());
         else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No status found with the id " + sid);
    }

    @DeleteMapping("/{sid}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long sid)
    {

        if(sid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A status with an id smaller than 0 can not exist!");

        Optional<Status> returnValue = this.statusService.deleteStatus(sid);

        if( returnValue.isPresent() )
            return ResponseEntity.ok(returnValue.get());
         else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The status with the id " + sid + " doesn't exist!");
    }


    @PostMapping
    public ResponseEntity<?> saveStatus(@RequestBody Status status)
    {
        if(status.getSid() < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A status with an id smaller than 0 can not exist!");

        Status returnValue = this.statusService.saveStatus(status);

        if( returnValue != null )
            return ResponseEntity.ok(returnValue);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A status with the id " + status.getSid() + " already exists!");
    }


    @PutMapping
    public ResponseEntity<?> updateStatus(@RequestBody Status status)
    {
        Long sid = status.getSid();
        if(sid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A status with an id smaller than 0 can not exist!");


        Optional<Status> findValue = this.statusService.findStatusById(sid);
        Status returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The status with the id " + sid + " you are trying to update doesn't exist!");

        returnValue.setSid(sid);
        returnValue.setStatus(status.getStatus());

        return ResponseEntity.ok(this.statusService.saveStatus(returnValue));
    }
}