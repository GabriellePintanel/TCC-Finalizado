package local.grabrielle.invernada.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import local.grabrielle.invernada.api.event.model.NewEventDetail;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "YourUsername", roles = "USER")
class EventApiTest {
    private static final String USER_ID = "user1";
    private static final String INVERNADA_ID = "1";
    private static final String BASE_URL = "/api/events";
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    void getAllWithValues() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(BASE_URL)
                                .param("start-date", "2024-12-01")
                                .param("end-date", "2024-12-31")
                                .param("invernada", INVERNADA_ID)
                                .param("user", USER_ID)
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("evento 1"));
    }

    @Test
    void getById() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(BASE_URL.concat("/{id}"), 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("evento 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.local").value("rancho da saudade"));
    }

    @Test
    void getByIdNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(BASE_URL.concat("/{id}"), 2))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void create() throws Exception {
        final NewEventDetail newEventDetail = new NewEventDetail("Outro evento", LocalDateTime.of(2025, 1, 10, 14, 0, 0), "CTG Tiaraju", "", 1, Set.of());
        final var request = MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(newEventDetail));
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("descricao").value("Outro evento"));
    }
}