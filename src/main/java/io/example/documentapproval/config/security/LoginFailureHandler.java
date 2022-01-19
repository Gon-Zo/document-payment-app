package io.example.documentapproval.config.security;

import io.example.documentapproval.enums.ExceptionType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("authenticationFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String message = getExceptionMessage(exception);

        response.reset();

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println(String.format("<script>alert('%s'); location.href='/login';</script>", message));

        out.flush();

    }

    private String getExceptionMessage(AuthenticationException exception) {
        ExceptionType authenticationTypes = ExceptionType.findOf(exception.getClass().getSimpleName());
        return authenticationTypes.getValue();
    }

}
