package local.grabrielle.invernada.controller;

import local.grabrielle.invernada.api.pilcha.PilchaApi;
import local.grabrielle.invernada.api.pilcha.model.NewPilchaDetail;
import local.grabrielle.invernada.api.pilcha.model.Pilcha;
import local.grabrielle.invernada.api.pilcha.model.PilchaDetail;
import local.grabrielle.invernada.service.PilchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PilchaController implements PilchaApi {
    private final PilchaService pilchaService;

    public PilchaController(PilchaService pilchaService) {
        this.pilchaService = pilchaService;
    }

    @Override
    public ResponseEntity<List<Pilcha>> getAll() {
        return ResponseEntity.ok(pilchaService.getAll());
    }

    @Override
    public ResponseEntity<PilchaDetail> getById(int id) {
        return ResponseEntity.ofNullable(pilchaService.getById(id));
    }

    @Override
    public ResponseEntity<PilchaDetail> create(NewPilchaDetail newPilcha) {
        return Optional
                .of(pilchaService.create(newPilcha))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<PilchaDetail> update(int id, NewPilchaDetail newPilcha) {
        return ResponseEntity.ofNullable(pilchaService.updateById(id, newPilcha));
    }

    @Override
    public ResponseEntity<PilchaDetail> delete(int id) {
        return ResponseEntity.ofNullable(pilchaService.deleteById(id));
    }
}
