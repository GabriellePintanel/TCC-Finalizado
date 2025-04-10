package local.grabrielle.invernada.api.invernada;

import local.grabrielle.invernada.api.invernada.model.Invernada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/invernadas")
@CrossOrigin
public interface InvernadaApi {
    @GetMapping
    ResponseEntity<List<Invernada>> getAll();

}
