package local.grabrielle.invernada.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import local.grabrielle.invernada.api.pilcha.model.NewPilchaDetail;
import local.grabrielle.invernada.api.pilcha.model.PilchaEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "YourUsername", roles = "USER")
class PilchaApiTest {
    private static final String BASE_URL = "/api/pilchas";
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);

    private AutoCloseable closeable;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAll() throws Exception {
        var request = MockMvcRequestBuilders.get(BASE_URL);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("bombacha larga"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].owner").value("user2"));
    }

    @Test
    void getById() throws Exception {
        var request = MockMvcRequestBuilders.get(BASE_URL.concat("/{id}"), 1);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("bombacha larga"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pilchaType").value(PilchaEnum.BOMBACHA.ordinal()));
    }

    @Test
    void getByIdNotFound() throws Exception {
        var request = MockMvcRequestBuilders.get(BASE_URL.concat("/{id}"), 3);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void create() throws Exception {
        final NewPilchaDetail newPilchaDetail = new NewPilchaDetail(PilchaEnum.BOTA, "bota gauderia", "tag01", "user1", "Uma bela bota preta");
        final var request = MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(newPilchaDetail));
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("bota gauderia"));
    }
}