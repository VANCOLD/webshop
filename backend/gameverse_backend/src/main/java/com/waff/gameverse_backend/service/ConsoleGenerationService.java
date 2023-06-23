package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.ConsoleGeneration;
import com.waff.gameverse_backend.repository.ConsoleGenerationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConsoleGenerationService {

    private final ConsoleGenerationRepository congenRepo;

    public ConsoleGenerationService(ConsoleGenerationRepository congenRepo) { this.congenRepo = congenRepo; }


    public Optional<ConsoleGeneration> findConsoleGenerationById(Long cgid) { return this.congenRepo.findById(cgid); }

    public List<ConsoleGeneration> findAllConsoleGeneration() { return this.congenRepo.findAll(); }

    public Optional<ConsoleGeneration> deleteConsoleGeneration(Long cgid)
    {
        Optional<ConsoleGeneration> returnConsoleGeneration = this.findConsoleGenerationById(cgid);

        if( returnConsoleGeneration.isPresent() ) {
            this.congenRepo.deleteById(cgid);
        }

        return returnConsoleGeneration;
    }

    public ConsoleGeneration saveConsoleGeneration(ConsoleGeneration consoleGeneration)
    {
        Optional<ConsoleGeneration> checkConsoleGeneration = this.congenRepo.findById(consoleGeneration.getCgid());

        if( checkConsoleGeneration.isEmpty() ) {
            return this.congenRepo.save(consoleGeneration);
        } else {
            return null;
        }
    }
}
