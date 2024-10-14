package team7.inplace.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.filter.OncePerRequestFilter;
import team7.inplace.global.exception.InplaceException;

@Slf4j
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public ExceptionHandlingFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InplaceException inplaceException) {
            setErrorResponse(response, inplaceException);
        }
    }

    private void setErrorResponse(
            HttpServletResponse response,
            InplaceException inplaceException
    ) throws IOException {
        log.info("ExceptionHandlingFilter.setErrorResponse");
        response.setStatus(inplaceException.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                inplaceException.getHttpStatus(), inplaceException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }
}
