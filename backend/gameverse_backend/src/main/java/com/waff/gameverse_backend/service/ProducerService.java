package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.Producer;
import com.waff.gameverse_backend.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProducerService {

    private final ProducerRepository producerRepo;

    public ProducerService(ProducerRepository producerRepo) { this.producerRepo = producerRepo;}


    public Optional<Producer> findProducerById(Long cid) { return this.producerRepo.findById(cid); }
    public List<Producer> findAllProducers() { return this.producerRepo.findAll(); }
    public Producer saveProducer(Producer category) {

        Optional<Producer> checkStatus = this.producerRepo.findById(category.getProid());

        if( checkStatus.isEmpty() ) {
            return this.producerRepo.save(category);
        } else {
            return null;
        }
    }

    public Optional<Producer> deleteProducer(Long proid) {

        Optional<Producer> returnProducer = this.findProducerById(proid);

        if( returnProducer.isPresent() ) {
            this.producerRepo.deleteById(proid);
        }

        return returnProducer;
    }
}
