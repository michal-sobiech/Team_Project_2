package org.pzsp2.proman.database_management.tables.inverter_a_data.controller;

import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.InverterADataDTO;
import org.pzsp2.proman.database_management.tables.inverter_a_data.service.InverterADataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.pzsp2.proman.data_analysis.InverterDataAnalysisModule;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.FrontendInverterADataDTO;
import lombok.Getter;

import org.pzsp2.proman.database_management.tables.user.service.UserServiceImpl;

import java.util.List;
import java.util.ArrayList;

@RestController
// @RequestMapping("/inverter_data")
@CrossOrigin
public class InverterADataController {

    private final InverterADataService inverterADataService;
    private final InverterDataAnalysisModule inverterDataAnalysisModule;

    @Getter
    private enum ValidRangeOptions {
        FIFTEEN_MINUTES("15_minutes", InverterDataAnalysisModule.TimeRange.FIFTEEN_MINUTES),
        ONE_HOUR("1_hour", InverterDataAnalysisModule.TimeRange.ONE_HOUR),
        TWELVE_HOURS("12_hours", InverterDataAnalysisModule.TimeRange.TWELVE_HOURS),
        ONE_DAY("1_day", InverterDataAnalysisModule.TimeRange.ONE_DAY),
        ONE_WEEK("1_week", InverterDataAnalysisModule.TimeRange.ONE_WEEK),
        ONE_MONTH("1_month", InverterDataAnalysisModule.TimeRange.ONE_MONTH),
        ONE_YEAR("1_year", InverterDataAnalysisModule.TimeRange.ONE_YEAR),
        TEN_YEARS("10_years", InverterDataAnalysisModule.TimeRange.TEN_YEARS);

        private final String optionName;
        private final InverterDataAnalysisModule.TimeRange timeRangeOption;

        ValidRangeOptions(String optionName, InverterDataAnalysisModule.TimeRange timeRangeOption) {
            this.optionName = optionName;
            this.timeRangeOption = timeRangeOption;
        }

        public static ValidRangeOptions hasOption(String optionName) {
            for (ValidRangeOptions option : ValidRangeOptions.values()) {
                if (option.getOptionName().equals(optionName)) {
                    return option;
                }
            }
            return null;
        }
    }

    public InverterADataController(InverterADataService inverterADataService,
            InverterDataAnalysisModule inverterDataAnalysisModule,
            UserServiceImpl userServiceImpl) {
        this.inverterADataService = inverterADataService;
        this.inverterDataAnalysisModule = inverterDataAnalysisModule;
    }

    @GetMapping("/inverter_data")
    public ResponseEntity<List<FrontendInverterADataDTO>> getDataByInverterId(
            @RequestParam("inverterId") long inverterId, @RequestParam("range") String range) {

        System.out.println(inverterId + ", " + range);


        //Check if the "range" parameter is valid
        ValidRangeOptions option = ValidRangeOptions.hasOption(range);
        if (option == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        InverterDataAnalysisModule.TimeRange timeRange = option.getTimeRangeOption();

        ArrayList<FrontendInverterADataDTO> formattedData = inverterDataAnalysisModule.getFrontendInverterData(
            timeRange, new ArrayList<>(List.of(inverterId)));

        return new ResponseEntity<List<FrontendInverterADataDTO>>(formattedData, HttpStatus.OK);
    }

    @PostMapping("/inverter_data/add")
    public ResponseEntity<String> addInverterAData(@RequestBody InverterADataDTO inverterADataDTO) {
        var currentDateData = InverterADataDTO.ofCurrentDate(inverterADataDTO);
        inverterADataService.saveData(currentDateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }  
}
