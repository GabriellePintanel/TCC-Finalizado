package local.grabrielle.invernada.api.profile;

import jakarta.servlet.http.HttpServletRequest;
import local.grabrielle.invernada.api.profile.model.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/profiles")
@CrossOrigin
public interface ProfileApi {
    @GetMapping
    ResponseEntity<Profile> getMyProfile(HttpServletRequest request);


}
