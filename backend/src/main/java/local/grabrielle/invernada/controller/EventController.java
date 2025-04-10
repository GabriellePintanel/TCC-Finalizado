package local.grabrielle.invernada.controller;

import local.grabrielle.invernada.api.event.EventApi;
import local.grabrielle.invernada.api.event.model.Event;
import local.grabrielle.invernada.api.event.model.EventDetail;
import local.grabrielle.invernada.api.event.model.NewEventDetail;
import local.grabrielle.invernada.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController implements EventApi {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public ResponseEntity<List<Event>> getAll(LocalDate startDate, LocalDate endDate, Integer invernadaId, String userId) {
        return ResponseEntity.ofNullable(eventService.getAll(startDate, endDate, invernadaId, userId));
    }

    @Override
    public ResponseEntity<EventDetail> getById(long id) {
        return ResponseEntity.ofNullable(eventService.getById(id));
    }

    @Override
    public ResponseEntity<EventDetail> create(NewEventDetail event) {
        return Optional
                .of(eventService.create(event))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<EventDetail> update(long id, EventDetail eventDetail) {
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<EventDetail> remove(long id) {
        return ResponseEntity.notFound().build();
    }
}
