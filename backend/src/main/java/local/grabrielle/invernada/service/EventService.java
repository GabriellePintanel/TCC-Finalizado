package local.grabrielle.invernada.service;

import local.grabrielle.invernada.api.event.model.Event;
import local.grabrielle.invernada.api.event.model.EventDetail;
import local.grabrielle.invernada.api.event.model.NewEventDetail;
import local.grabrielle.invernada.data.entity.EventEntity;
import local.grabrielle.invernada.data.entity.InvernadaEntity;
import local.grabrielle.invernada.data.entity.PersonEntity;
import local.grabrielle.invernada.data.repository.EventRepository;
import local.grabrielle.invernada.data.repository.InvernadaRepository;
import local.grabrielle.invernada.data.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final PersonRepository personRepository;
    private final InvernadaRepository invernadaRepository;

    public EventService(EventRepository eventRepository, PersonRepository personRepository, InvernadaRepository invernadaRepository) {
        this.eventRepository = eventRepository;
        this.personRepository = personRepository;
        this.invernadaRepository = invernadaRepository;
    }

    private static Event convert(EventEntity entity) {
        return new Event(entity.getId(), entity.getName(), entity.getDate(), entity.getPlace());
    }

    public List<Event> getAll(LocalDate startDate, LocalDate endDate, Integer invernadaId, String userId) {
        final LocalDateTime startDateTime = Optional.ofNullable(startDate)
                .map(LocalDate::atStartOfDay)
                .orElseGet(() -> LocalDate.now().atStartOfDay());
        final LocalDateTime endDateTime = Optional.ofNullable(endDate)
                .map(value -> value.plusDays(1).atStartOfDay())
                .orElseGet(() -> LocalDate.now().plusDays(8).atStartOfDay());
        final Optional<InvernadaEntity> optionalInvernada = Optional.ofNullable(invernadaId).flatMap(invernadaRepository::findById);
        final Optional<PersonEntity> optionalPerson = Optional.ofNullable(userId).flatMap(personRepository::findById);
        return eventRepository.findByDateBetweenOrderByDateAsc(startDateTime, endDateTime)
                .stream()
                .filter(event -> optionalInvernada.isEmpty() || event.getInvernada().equals(optionalInvernada.get()))
                .filter(event -> optionalPerson.isEmpty() || event.getParticipants().contains(optionalPerson.get()))
                .map(EventService::convert)
                .collect(Collectors.toList());
    }

    public EventDetail getById(long id) {
        return eventRepository.findById(id)
                .map(EventDetail::convert)
                .orElse(null);
    }

    public EventDetail create(NewEventDetail event) {
        return Optional.of(event)
                .map(this::convert)
                .map(eventRepository::save)
                .map(EventDetail::convert)
                .orElse(null);
    }

    private EventEntity convert(NewEventDetail event) {
        final Set<PersonEntity> participants = event.participants()
                .parallelStream()
                .flatMap(value -> personRepository.findById(value).stream())
                .collect(Collectors.toSet());
        final InvernadaEntity invernada = invernadaRepository.findById(event.invernada())
                .orElse(null);
        return new EventEntity(null, event.descricao(), event.data(), event.local(), event.observacao(), invernada, participants);
    }

}
