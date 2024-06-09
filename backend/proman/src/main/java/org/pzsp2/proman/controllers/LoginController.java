package org.pzsp2.proman.controllers;

import org.pzsp2.proman.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.pzsp2.proman.data_analysis.InverterDataAnalysisModule;
import org.pzsp2.proman.data_analysis.TipsAnalysisModule;
import org.pzsp2.proman.database_management.tables.admin.service.AdminServiceImpl;
import org.pzsp2.proman.database_management.tables.inverter.service.InverterServiceImpl;
import org.pzsp2.proman.database_management.tables.inverter_a_data.service.InverterADataServiceImpl;
import org.pzsp2.proman.database_management.tables.user.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
public class LoginController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginController(TokenService tokenService, 
            AuthenticationManager authenticationManager,
            InverterServiceImpl inverterServiceImpl,
            InverterADataServiceImpl inverterADataServiceImpl,
            UserServiceImpl userDetailsServiceImpl,
            AdminServiceImpl adminServiceImpl,
            InverterDataAnalysisModule inverterDataAnalysisModule,
            TipsAnalysisModule tipsAnalysisModule) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public record LoginResponse(String token, String role) {}
    @PostMapping("/log_in")
    public ResponseEntity<LoginResponse> logAUserIn(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        try {
            Authentication authentication = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        username,
                        password
                    )
            );
            String token = tokenService.generateToken(authentication);
            System.out.println("Authenticated successfully");

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // Extract the role from authorities
            String role = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("");

            return new ResponseEntity<LoginResponse>(
                new LoginResponse(token, role),
                HttpStatus.OK
            );
        } catch (AuthenticationException ex) {
            System.out.println("Could not authenticate user");
            return new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);
        }
    }
}
