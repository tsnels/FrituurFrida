package be.vdab.frituurfrida.services;


import be.vdab.frituurfrida.dto.TotaleVerkopen;
import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.repositories.SnackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class SnackService {

    private final SnackRepository snackRepository;

    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public List<Snack> findAll() {
        return snackRepository.findAll();
    }

    public List<Snack> findByEersteLetter(String eersteLetter) {
        return snackRepository.findByEersteLetter(eersteLetter);
    }

    public List<TotaleVerkopen> sells() {
        return snackRepository.sells();
    }

    public Optional<Snack> findById(long id) {
        return snackRepository.findById(id);
    }


}