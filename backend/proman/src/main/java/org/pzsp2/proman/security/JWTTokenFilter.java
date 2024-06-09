package org.pzsp2.proman.security;


import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.json.JSONObject;
// import org.pzsp2.proman.database_management.DatabaseManager;

import java.io.PrintWriter;


@Component
@Order(1)
public class JWTTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JWTTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Filtr JWT");

        // permitAll() does not work for this filter for some reason,
        // so a URL check is needed
        String URL = request.getRequestURL().toString();

        // Everybody can try to log in
        if (URL.contains("/log_in")) {
            doFilter(request, response, filterChain);
            return;
        }
        
        // Posting inverter data will not require a JWT token,
        // but a login and a password for the inverter
        if (URL.contains("/inverter_data/add")) {
            doFilter(request, response, filterChain);
            return;
        }

        // For protected routes
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                return;
            }
            String token = authHeader.substring(7);
            if (tokenService.isTokenValid(token)) {
                System.out.println("Token is valid");

                doFilter(request, response, filterChain);
            }
        } catch(JwtException e) {
            System.out.println("Token is invalid");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "failure");

            PrintWriter writer = response.getWriter();
            writer.write(jsonObject.toString());
            writer.close();
        }
    }
}
