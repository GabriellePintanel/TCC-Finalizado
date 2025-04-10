package local.grabrielle.invernada.api.event;

import local.grabrielle.invernada.api.event.model.Event;
import local.grabrielle.invernada.api.event.model.EventDetail;
import local.grabrielle.invernada.api.event.model.NewEventDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/events")
@CrossOrigin
public interface EventApi {
    @GetMapping
    ResponseEntity<List<Event>> getAll(
            @RequestParam(name = "start-date", required = false) LocalDate startDate,
            @RequestParam(name = "end-date", required = false) LocalDate endDate,
            @RequestParam(name = "invernada", required = false) Integer invernadaId,
            @RequestParam(name = "user", required = false) String userId
    );

    @GetMapping("{id}")
    ResponseEntity<EventDetail> getById(@PathVariable("id") long id);

    @PostMapping
    ResponseEntity<EventDetail> create(@RequestBody NewEventDetail event);

    @PutMapping("id")
    ResponseEntity<EventDetail> update(@PathVariable("id") long id, EventDetail eventDetail);

    @DeleteMapping("id")
    ResponseEntity<EventDetail> remove(@PathVariable("id") long id);
}