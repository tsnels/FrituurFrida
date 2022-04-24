package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import be.vdab.frituurfrida.repositories.GastenboekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GastenBoekService {

    private final GastenboekRepository gastenboekRepository;

    public GastenBoekService(GastenboekRepository gastenboekRepository) {
        this.gastenboekRepository = gastenboekRepository;
    }

    @Transactional
    public long create(GastenboekEntry entry) {
        return gastenboekRepository.create(entry);
    }

    public List<GastenboekEntry> findAll(){
        return gastenboekRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void delete(Long[] ids) {
        gastenboekRepository.delete(ids);
    }


}