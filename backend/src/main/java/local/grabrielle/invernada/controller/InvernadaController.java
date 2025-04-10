package local.grabrielle.invernada.controller;

import local.grabrielle.invernada.api.invernada.InvernadaApi;
import local.grabrielle.invernada.api.invernada.model.Invernada;
import local.grabrielle.invernada.service.InvernadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvernadaController implements InvernadaApi {
    private final InvernadaService invernadaService;

    public InvernadaController(InvernadaService invernadaService) {
        this.invernadaService = invernadaService;
    }

    @Override
    public ResponseEntity<List<Invernada>> getAll() {
        return ResponseEntity.ofNullable(invernadaService.getAll());
    }
}
