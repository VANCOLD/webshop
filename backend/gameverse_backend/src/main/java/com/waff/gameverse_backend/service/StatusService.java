package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.Status;
import com.waff.gameverse_backend.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) { this.statusRepository = statusRepository; }


    public List<Status> findAllStatus() {
        return this.statusRepository.findAll();
    }

    public Optional<Status> findStatusById(Long id) {
        return this.statusRepository.findById(id);
    }

    public Optional<Status> deleteStatus(Long id)
    {
        Optional<Status> returnStatus = this.findStatusById(id);

        if( returnStatus.isPresent() ) {
            this.statusRepository.deleteById(id);
        }

        return returnStatus;
    }

    public Status saveStatus(Status status)
    {
        Optional<Status> checkStatus = this.statusRepository.findById(status.getSid());

        if( checkStatus.isEmpty() ) {
            return this.statusRepository.save(status);
        } else {
            return null;
        }
    }
}
