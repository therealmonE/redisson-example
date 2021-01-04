package io.github.therealmone.redisson.example.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.therealmone.redisson.example.dto.UpdateDocumentRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
public class DocumentControllerAction {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void updateDocument(UUID documentId,
                               UpdateDocumentRequest updateDocumentRequest,
                               HttpStatus expectedStatus) {

        mockMvc.perform(put("/api/document/{documentId}", documentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(updateDocumentRequest)))
                .andDo(print())
                .andExpect(status().is(expectedStatus.value()));
    }

}
