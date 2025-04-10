package local.grabrielle.invernada.service;

import local.grabrielle.invernada.api.pilcha.model.NewPilchaDetail;
import local.grabrielle.invernada.api.pilcha.model.Pilcha;
import local.grabrielle.invernada.api.pilcha.model.PilchaDetail;
import local.grabrielle.invernada.api.reader.model.ReaderInfo;
import local.grabrielle.invernada.data.entity.PilchaEntity;
import local.grabrielle.invernada.data.repository.PersonRepository;
import local.grabrielle.invernada.data.repository.PilchaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PilchaService {
    private final PilchaRepository pilchaRepository;
    private final PersonRepository personRepository;

    public PilchaService(PilchaRepository pilchaRepository, PersonRepository personRepository) {
        this.pilchaRepository = pilchaRepository;
        this.personRepository = personRepository;
    }

    public List<Pilcha> getAll() {
        return pilchaRepository.findAll()
                .stream()
                .map(Pilcha::convert)
                .collect(Collectors.toList());
    }

    public PilchaDetail getById(int id) {
        return pilchaRepository.findById(id)
                .map(PilchaDetail::convert)
                .orElse(null);
    }

    public PilchaDetail create(NewPilchaDetail newPilcha) {
        return Optional.of(newPilcha)
                .map(this::convert)
                .map(pilchaRepository::save)
                .map(PilchaDetail::convert)
                .orElse(null);
    }

    public PilchaDetail updateById(int id, NewPilchaDetail pilcha) {
        return pilchaRepository.findById(id)
                .map(value -> {
                    value.setDescription(pilcha.description());
                    value.setNotes(pilcha.notes());
                    if (pilcha.user() == null) {
                        value.setOwner(null);
                    } else if (!pilcha.user().equals(value.getOwner().getId())) {
                        value.setOwner(
                                personRepository.findById(pilcha.user())
                                .orElse(null)
                        );
                    }
                    return pilchaRepository.save(value);
                })
                .map(PilchaDetail::convert)
                .orElse(null);
    }

    public PilchaDetail deleteById(int id) {
        return pilchaRepository.findById(id)
                .map(PilchaDetail::convert)
                .orElse(null);
    }

    public ReaderInfo getInfoByTag(String id) {
        return Optional.of(pilchaRepository.findByTag(id))
                .map(ReaderInfo::convert)
                .orElse(null);
    }

    private PilchaEntity convert(NewPilchaDetail newPilchaDetail) {
        var person = personRepository.findById(newPilchaDetail.user()).orElse(null);
        return new PilchaEntity(null, newPilchaDetail.description(), newPilchaDetail.pilchaType(), newPilchaDetail.notes(), person);
    }
}
