package TestFiles;

import com.controller.TransfersController;
import com.model.requestStructure;
import com.model.replyStructure;
import com.service.LeftoverHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.Calculator;

import java.io.File;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TransfersController.class)
public class TransfersControllerTest {

    private MockMvc mockMvc;

    @Mock
    private Calculator calculator;
    @Mock
    private LeftoverHolder leftoverHolder;

    @InjectMocks
    private TransfersController transfersController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.leftoverHolder = new LeftoverHolder();
        this.transfersController = new TransfersController(calculator, leftoverHolder);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(transfersController).build();
    }

    private void generalTestMethod(String testType, ResultMatcher expectedStatus) throws Exception {
        requestStructure request = objectMapper.readValue(new File("src/test/java/Jsons/test_"+testType+"_input.json"), requestStructure.class);

        replyStructure expectedResponse = objectMapper.readValue(new File("src/test/java/Jsons/test_"+testType+"_output.json"), replyStructure.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/transfers/findOptimal")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus)
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void testValidRequest1() throws Exception {
        generalTestMethod("1", status().isOk());
    }

    @Test
    public void testValidRequest2() throws Exception {
        generalTestMethod("2", status().isOk());
    }

    @Test
    public void testEmptyTransfersList() throws Exception {
        generalTestMethod("empty", status().isOk());
    }

    @Test
    public void testIllogicalMaxWeight() throws Exception {
        generalTestMethod("illogical", status().isOk());
    }

    @Test
    public void testEmptyRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/transfers/findOptimal")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidJsonFormat() throws Exception {
        String invalidJson = "{ \"cost\": 100, \"weight\": 50 ";
        
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/transfers/findOptimal")
                        .content(invalidJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transfers/invalidEndpoint"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testIncorrectContentType() throws Exception {
        String validJson = "{\"cost\": 100, \"weight\": 50}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/transfers/findOptimal")
                        .content(validJson)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void testHighVolumeRequests() throws Exception {
        for (int i = 0; i < 100; i++) {
            generalTestMethod("1", status().isOk());
        }
    }

    @Test
    public void testCalculatorServiceFailure() throws Exception {
        String invalidRequest = "{\"cost\": 100, \"weight\": 50}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/transfers/findOptimal")
                        .content(invalidRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
