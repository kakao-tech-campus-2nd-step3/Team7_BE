package team7.inplace.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;

@Slf4j
public class CustomFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public CustomFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.info("CustomFailureHandler.onAuthenticationFailure");
        setErrorResponse(response, InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EXPIRED));
    }

    private void setErrorResponse(HttpServletResponse response, InplaceException inplaceException)
            throws IOException {
        response.setStatus(inplaceException.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                inplaceException.getHttpStatus(), inplaceException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }
}
