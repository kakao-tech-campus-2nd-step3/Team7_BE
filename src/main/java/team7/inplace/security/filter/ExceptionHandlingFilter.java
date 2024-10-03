package team7.inplace.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.filter.OncePerRequestFilter;
import team7.inplace.security.AuthorizationException;

@NoArgsConstructor
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthorizationException authorizationException) {
            setErrorResponse(response, authorizationException);
        }
    }

    private void setErrorResponse(HttpServletResponse response,
        AuthorizationException authorizationException)
        throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(authorizationException.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            authorizationException.getHttpStatus(), authorizationException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }
}
