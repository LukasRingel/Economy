package de.lukasringel.economy.http.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * This class provides some kind of security to our application.
 * Our clients have to provide an api-key via the 'X-API-KEY' header.
 * You also should only run this application behind a firewall!
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class EconomyHttpServerSecurity extends GenericFilterBean {

    private static final String API_KEY_NAME = "X-API-KEY";
    private static final String REMOTE_ADDRESS_NAME = "X-FORWARDED-FOR";

    private final Properties properties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpServletRequest)) return;

        // checking for the provided key
        if(!httpServletRequest.getHeader(API_KEY_NAME).equals(getApiKey())) {
            String remoteAddress = httpServletRequest.getHeader(REMOTE_ADDRESS_NAME);
            String requestURI = ((HttpServletRequest) request).getRequestURI();

            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please provide a valid X-API-KEY Header!");
            log.warn("Incoming request from address " + remoteAddress + " failed apikey challenge on route: " + requestURI);

            return;
        }

        // delegating the request to the chain
        chain.doFilter(request, response);

    }

    private String getApiKey() {
        return properties.getProperty("application.apikey");
    }

}
