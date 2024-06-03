package dev.jlcorradi.monolithseed.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jlcorradi.monolithseed.common.KeyValuePairType;
import dev.jlcorradi.monolithseed.common.dto.KeyValuePairDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KeyValuePairApiTest {

    static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateKeyValuePairSuccessfully() throws Exception {
        // GIVEN
        KeyValuePairDTO dummyCaregory =
                new KeyValuePairDTO(null, KeyValuePairType.CATEGORY, "Dummy Category", true);

        // THEN
        String result = mockMvc.perform(post(EndPointConstants.KEY_VALUE_PAIRS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(dummyCaregory)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // EXPECT
        KeyValuePairDTO keyValuePairDTO = OBJECT_MAPPER.readValue(result, KeyValuePairDTO.class);
        assertNotNull(keyValuePairDTO.id());
        assertEquals(dummyCaregory.active(), keyValuePairDTO.active());
        assertEquals(dummyCaregory.description(), keyValuePairDTO.description());
    }

}