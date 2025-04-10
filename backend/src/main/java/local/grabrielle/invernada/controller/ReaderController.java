package local.grabrielle.invernada.controller;

import local.grabrielle.invernada.api.reader.ReaderApi;
import local.grabrielle.invernada.api.reader.model.ReaderInfo;
import local.grabrielle.invernada.service.PilchaService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReaderController implements ReaderApi {
    private final PilchaService pilchaService;


    public ReaderController(PilchaService pilchaService) {
        this.pilchaService = pilchaService;
    }

    @Override
    public ResponseEntity<ReaderInfo> getById(String id) {
        var info = pilchaService.getInfoByTag(id);
        if (info == null) {
            return ResponseEntity.notFound().cacheControl(CacheControl.noCache()).build();
        }
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(info);
    }
}
