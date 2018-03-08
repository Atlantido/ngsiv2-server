package eu.hopu.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.hopu.exception.Ngsi2Exception;
import eu.hopu.model.Error;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Error responses should contain an Error json structure.
 * Handle all errors by throwing an Ngsi2Exception containing the error.
 */
class Ngsi2ResponseErrorHandler implements ResponseErrorHandler {

    private ObjectMapper objectMapper;

    Ngsi2ResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Ngsi2Exception ex;
        try {
            ex = Ngsi2Exception.fromError(response.getStatusCode().value(), objectMapper.readValue(response.getBody(), Error.class));
        } catch (Exception e) {
            ex = new Ngsi2Exception(response.getStatusCode().toString(), response.getStatusText(), null);
        }
        throw ex;
    }
}
