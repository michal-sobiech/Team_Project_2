package org.pzsp2.proman;

import org.pzsp2.proman.database_management.tables.admin.service.AdminService;
import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.service.InverterService;
import org.pzsp2.proman.database_management.tables.inverter_a_data.service.InverterADataService;
import org.pzsp2.proman.database_management.tables.user.dto.UserDTO;
import org.pzsp2.proman.database_management.tables.user.service.UserService;
import org.pzsp2.proman.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.pzsp2.proman.data_analysis.InverterDataAnalysisModule;
import org.pzsp2.proman.database_management.tables.owner.service.OwnerService;
import org.pzsp2.proman.database_management.tables.owner.model.Owner;
import org.pzsp2.proman.database_management.tables.admin.model.Admin;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.FrontendInverterADataDTO;

@Service
public class TestUtils {

    @Value("${server.port}")
    private int serverPort;

    private final String backendURLNoPort = "http://localhost:";

    @Getter
    public enum MockUser {
        USER(1L, "USER", "123"),
        ADMIN(1L, "ADMIN", "123"),
        OWNER(1L, "OWNER", "123");

        private final Long id;
        private final String role;
        private final String unhashedPassword;

        MockUser(Long id, String role, String unhashedPassword) {
            this.id = id;
            this.role = role;
            this.unhashedPassword = unhashedPassword;
        }
    }

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userServiceImpl;
    private final InverterService inverterServiceImpl;
    private final AdminService adminService;
    private final OwnerService ownerService;
    private final InverterDataAnalysisModule inverterDataAnalysisModule;

    public TestUtils(TokenService tokenService,
            AuthenticationManager authenticationManager,
            UserService userServiceImpl,
            InverterService inverterServiceImpl,
            InverterADataService inverterADataServiceImpl,
            AdminService adminService,
            OwnerService ownerService,
            InverterDataAnalysisModule inverterDataAnalysisModule) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userServiceImpl = userServiceImpl;
        this.inverterServiceImpl = inverterServiceImpl;
        this.adminService = adminService;
        this.ownerService = ownerService;
        this.inverterDataAnalysisModule = inverterDataAnalysisModule;
    }

    public String getBackendURL() {
        return backendURLNoPort + Integer.toString(serverPort);
    }

    public record MockUserData(String username, String unhashedPassword, String name) {}

    public String getTestJWTToken(MockUser mockUser) {
        MockUserData mockUserData = getMockUserData(mockUser);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    mockUserData.username(),
                    mockUserData.unhashedPassword()
                )
            );
            return tokenService.generateToken(authentication);
        } catch(BadCredentialsException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public List<FrontendInverterADataDTO> getMockInverterDataUserOverview() {
        List<InverterDTO> inverterDTOs = inverterServiceImpl.getInvertersByUser(MockUser.USER.getId());
        ArrayList<Long> inverterIds = new ArrayList<>(
            inverterDTOs.stream().map(InverterDTO::inverterId).toList()
        );
        return inverterDataAnalysisModule.getFrontendInverterData(
            InverterDataAnalysisModule.TimeRange.ONE_DAY,
            inverterIds
        );
    }

    public List<InverterDTO> getMockInverterDTOs() {
        return inverterServiceImpl.getInvertersByUser(MockUser.USER.getId());
    }

    public HttpEntity<String> getRequestEntityWithMockJWT(MockUser mockUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getTestJWTToken(mockUser));
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(headers);
    }

    public MockUserData getMockUserData(MockUser mockUser) {
        Long mockUserId = mockUser.getId();
        String username;
        String unhashedPassword;
        String name;
        switch (mockUser) {
            case USER -> {
                UserDTO userDTO = userServiceImpl.getUserById(mockUserId);
                username = userDTO.username();
                unhashedPassword = mockUser.getUnhashedPassword();
                name = userDTO.name();
            }
            case ADMIN -> {
                Admin admin = adminService.getAdminById(mockUserId);
                username = admin.getLogin();
                unhashedPassword = mockUser.getUnhashedPassword();
                name = admin.getName();
            }
            case OWNER -> {
                Owner owner = ownerService.getOwnerById(mockUserId);
                username = owner.getLogin();
                unhashedPassword = mockUser.getUnhashedPassword();
                name = owner.getName();
            }
            default -> {
                username = "";
                unhashedPassword = "";
                name = "";
            }
        }
        return new MockUserData(
            username,
            unhashedPassword,
            name
        );
    }
}
