package local.grabrielle.invernada.api.pilcha;

import local.grabrielle.invernada.api.pilcha.model.NewPilchaDetail;
import local.grabrielle.invernada.api.pilcha.model.Pilcha;
import local.grabrielle.invernada.api.pilcha.model.PilchaDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/pilchas")
@CrossOrigin
public interface PilchaApi {
    @GetMapping
    ResponseEntity<List<Pilcha>> getAll();

    @GetMapping("{id}")
    ResponseEntity<PilchaDetail> getById(@PathVariable("id") int id);

    @PostMapping
    ResponseEntity<PilchaDetail> create(@RequestBody NewPilchaDetail newPilcha);

    @PutMapping("{id}")
    ResponseEntity<PilchaDetail> update(@PathVariable("id") int id, @RequestBody NewPilchaDetail newPilcha);

    @DeleteMapping("{id}")
    ResponseEntity<PilchaDetail> delete(@PathVariable("id") int id);
}
