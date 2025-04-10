package local.grabrielle.invernada.service;

import local.grabrielle.invernada.api.invernada.model.Invernada;
import local.grabrielle.invernada.data.repository.InvernadaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvernadaService {
    private final InvernadaRepository invernadaRepository;

    public InvernadaService(InvernadaRepository invernadaRepository) {
        this.invernadaRepository = invernadaRepository;
    }

    public List<Invernada> getAll() {
        return invernadaRepository.findAll(Sort.by("name").ascending())
                .stream()
                .map(Invernada::convert)
                .collect(Collectors.toList());
    }
}
