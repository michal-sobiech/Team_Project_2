package org.pzsp2.proman.controllers;

import org.pzsp2.proman.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import org.pzsp2.proman.tips.Tip;
import org.pzsp2.proman.data_analysis.InverterDataAnalysisModule;
import org.pzsp2.proman.data_analysis.TipsAnalysisModule;
import org.pzsp2.proman.database_management.tables.admin.service.AdminServiceImpl;
import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.service.InverterServiceImpl;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.FrontendInverterADataDTO;
import org.pzsp2.proman.database_management.tables.inverter_a_data.service.InverterADataServiceImpl;
import org.pzsp2.proman.database_management.tables.user.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;


@RestController
@CrossOrigin
@Secured("USER")
public class UserPagesController {

    private final InverterServiceImpl inverterServiceImpl;
    private final UserServiceImpl userDetailsServiceImpl;
    private final InverterDataAnalysisModule inverterDataAnalysisModule;
    private final TipsAnalysisModule tipsAnalysisModule;

    public UserPagesController(TokenService tokenService, 
            AuthenticationManager authenticationManager,
            InverterServiceImpl inverterServiceImpl,
            InverterADataServiceImpl inverterADataServiceImpl,
            UserServiceImpl userDetailsServiceImpl,
            AdminServiceImpl adminServiceImpl,
            InverterDataAnalysisModule inverterDataAnalysisModule,
            TipsAnalysisModule tipsAnalysisModule) {
        this.inverterServiceImpl = inverterServiceImpl;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.inverterDataAnalysisModule = inverterDataAnalysisModule;
        this.tipsAnalysisModule = tipsAnalysisModule;
    }

    public record UserOverviewResponse(String usersName, List<FrontendInverterADataDTO> records,
        List<InverterDTO> devices, ArrayList<Tip> tips) {}

    public record UserAnalysisResponse(String usersName, List<InverterDTO> devices) {}


    @GetMapping("/user_home/overview")
    public ResponseEntity<UserOverviewResponse> userOverviewPage() {
        System.out.println("Tried to access the user home page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        long userId = userDetailsServiceImpl.getUserId(username);
        if (userId == -1) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String usersRealName = userDetailsServiceImpl.getUserById(userId).name();
        List<InverterDTO> inverters = inverterServiceImpl.getInvertersByUser(userId);
        ArrayList<Long> inverterIds = new ArrayList<>(
            inverters.stream().map(inv -> inv.inverterId()).toList()
        );

        UserOverviewResponse response = new UserOverviewResponse(
            usersRealName,
            inverterDataAnalysisModule.getFrontendInverterData(
                InverterDataAnalysisModule.TimeRange.ONE_DAY,
                inverterIds
            ),
            inverters,
            new ArrayList<Tip>()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user_home/analysis")
    public ResponseEntity<UserAnalysisResponse> userAnalysisPage(){
        System.out.println("Tried to access the user analysis page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        long userId = userDetailsServiceImpl.getUserId(username);
        String usersRealName = userDetailsServiceImpl.getUserById(userId).name();
        List<InverterDTO> inverters = inverterServiceImpl.getInvertersByUser(userId);
        UserAnalysisResponse response = new UserAnalysisResponse(
                usersRealName,
                inverters
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user_home/devices")
    public ResponseEntity<List<InverterDTO>> userDevicesPage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        long userId = userDetailsServiceImpl.getUserId(username);
        if (userId == -1) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
            inverterServiceImpl.getInvertersByUser(userId),
            HttpStatus.OK
        );
    }

    @GetMapping("/user_home/tips")
    public ResponseEntity<List<Tip>> userTipsPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("USERNAME: " + username);
        long userId = userDetailsServiceImpl.getUserId(username);
        if (userId == -1) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<InverterDTO> inverters = inverterServiceImpl.getInvertersByUser(userId);
        ArrayList<Long> inverterIds = new ArrayList<>(
            inverters.stream().map(inv -> inv.inverterId()).toList()
        );
        List<Tip> tips = tipsAnalysisModule.getAllTips(inverterIds);
        return new ResponseEntity<>(
            tips,
            HttpStatus.OK
        );
    }
}