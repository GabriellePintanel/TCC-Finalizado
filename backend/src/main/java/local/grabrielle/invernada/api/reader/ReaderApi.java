package local.grabrielle.invernada.api.reader;

import local.grabrielle.invernada.api.reader.model.ReaderInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/reader")
@CrossOrigin
public interface ReaderApi {
    @GetMapping("{id}")
    ResponseEntity<ReaderInfo> getById(@PathVariable("id") String id);
}
