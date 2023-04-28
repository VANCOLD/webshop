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
            return new ResponseEntity<>("The status-list is empty!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{sid}")
    public ResponseEntity<?> findStatusById(@PathVariable Long sid)
    {
        Optional<Status> returnValue = statusService.findStatusById(sid);

        if( returnValue.isPresent()  )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
         else
            return new ResponseEntity<>("No status found with the id " + sid, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{sid}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long sid)
    {

        if(sid < 0)
            return new ResponseEntity<>("A status with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Optional<Status> returnValue = this.statusService.deleteStatus(sid);

        if( returnValue.isPresent() )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
         else
            return new ResponseEntity<>("The status with the id " + sid + " doesn't exist!", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> saveStatus(@RequestBody Status status)
    {
        if(status.getSid() < 0)
            return new ResponseEntity<>("A status with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Status returnValue = this.statusService.saveStatus(status);

        if( returnValue != null )
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        else
            return new ResponseEntity<>("A status with the id " + status.getSid() + " already exists!", HttpStatus.CONFLICT);
    }


    @PutMapping
    public ResponseEntity<?> updateStatus(@RequestBody Status status)
    {
        Long sid = status.getSid();
        if(sid < 0)
            return new ResponseEntity<>("A status with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);


        Optional<Status> findValue = this.statusService.findStatusById(sid);
        Status returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return new ResponseEntity<>("The status with the id " + sid + " you are trying to update doesn't exist!", HttpStatus.CONFLICT);

        returnValue.setSid(sid);
        returnValue.setStatus(status.getStatus());

        return new ResponseEntity<>( this.statusService.saveStatus(returnValue), HttpStatus.OK );
    }
}