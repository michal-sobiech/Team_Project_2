package org.pzsp2.proman.data_analysis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.lang.Long;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.InverterADataDTO;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.FrontendInverterADataDTO;
import org.pzsp2.proman.database_management.tables.inverter_a_data.service.InverterADataService;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class InverterDataAnalysisModule {

    public enum TimeRange {
        // User analysis
        FIFTEEN_MINUTES,
        ONE_HOUR,
        TWELVE_HOURS,
        ONE_DAY,
        ONE_WEEK,
        ONE_MONTH,
        ONE_YEAR,
        TEN_YEARS,
        // User tips
        WEEK_IN_MINUTES
    }

    private final InverterADataService inverterADataService;

    public InverterDataAnalysisModule(InverterADataService inverterADataService) {
        this.inverterADataService = inverterADataService;
    }

    // Including the beginning, not including the end. Mathematically: <start, end)
    record TimeFrame(ZonedDateTime start, ZonedDateTime end) {}

    public record GroupedRecordsData(ArrayList<String> timeGroupsOrdered,
            HashMap<String, ArrayList<InverterADataDTO>> groupedRecords) {}
    
    private GroupedRecordsData getDataGroupedByTime(
            TimeRange timeRange, ArrayList<Long> inverterIds) {
        /*
            This function puts records from chosen inverters into
            chosen time groups
        */
  
        // All data from chosen inverters
        ArrayList<InverterADataDTO> allInvertersDataList = new ArrayList<>();
        for (Long id : inverterIds) {
            allInvertersDataList.addAll(inverterADataService.getDataByInverterId(id));
        }

        // Time ranges
        ZonedDateTime now = ZonedDateTime.now();
        // Time groups in the chronological order
        ArrayList<String> timeGroupsOrdered = new ArrayList<>();
        // Time groups mapped to their beginning and end times
        HashMap<String, TimeFrame> timeGroupsBorders = new HashMap<>();
        // Time groups mapped to their records
        HashMap<String, ArrayList<InverterADataDTO>> groupedRecords = new HashMap<>();

        int groupCount;
        DateTimeFormatter formatter;
        switch (timeRange) {
            case FIFTEEN_MINUTES -> {
                // Each group is a minute-long, 15 groups
                groupCount = 15;
                formatter = DateTimeFormatter.ofPattern("HH:mm");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusMinutes(groupCount - i);
                    ZonedDateTime newestDateInGroup = now.minusMinutes(groupCount - i - 1);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case ONE_HOUR -> {
                // Each group is 6 minutes long, 10 groups
                groupCount = 10;
                formatter = DateTimeFormatter.ofPattern("HH:mm");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusMinutes((groupCount - i) * 6);
                    ZonedDateTime newestDateInGroup = now.minusMinutes((groupCount - i - 1) * 6);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case TWELVE_HOURS -> {
                // Each group is 1 hour long, 12 groups
                groupCount = 12;
                formatter = DateTimeFormatter.ofPattern("HH:mm");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusHours(groupCount - i);
                    ZonedDateTime newestDateInGroup = now.minusHours(groupCount - i - 1);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case ONE_DAY -> {
                // Each group is 2 hour long, 12 groups
                groupCount = 12;
                formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusHours((groupCount - i) * 2);
                    ZonedDateTime newestDateInGroup = now.minusHours((groupCount - i - 1) * 2);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case ONE_WEEK -> {
                // Each group is 1 day long, 7 groups
                groupCount = 7;
                formatter = DateTimeFormatter.ofPattern("dd/MM");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusDays(groupCount - i - 1)
                      .with(LocalTime.MIN);
                    ZonedDateTime newestDateInGroup = now.minusDays(groupCount - i - 2)
                      .with(LocalTime.MIN);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case ONE_MONTH -> {
                // Each group is 1 day long, 30 groups
                groupCount = 30;
                formatter = DateTimeFormatter.ofPattern("dd/MM");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusDays(groupCount - i - 1)
                      .with(LocalTime.MIN);
                    ZonedDateTime newestDateInGroup = now.minusDays(groupCount - i - 2)
                      .with(LocalTime.MIN);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case ONE_YEAR -> {
                // Each group is 1 month long, 12 groups
                groupCount = 12;
                formatter = DateTimeFormatter.ofPattern("MM/yy");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusMonths(groupCount - i - 1)
                      .withDayOfMonth(1)
                      .with(LocalTime.MIN);
                    ZonedDateTime newestDateInGroup = now.minusMonths(groupCount - i - 2)
                      .withDayOfMonth(1)
                      .with(LocalTime.MIN);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case TEN_YEARS -> {
                // Each group is 1 year long, 10 groups
                groupCount = 10;
                formatter = DateTimeFormatter.ofPattern("yyyy");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusYears(groupCount - i - 1)
                      .withMonth(1)
                      .withDayOfMonth(1)
                      .with(LocalTime.MIN);
                    ZonedDateTime newestDateInGroup = now.minusYears(groupCount - i - 2)
                      .withMonth(1)
                      .withDayOfMonth(1)
                      .with(LocalTime.MIN);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
            case WEEK_IN_MINUTES -> {
                // Each group is 1 minute long, 60 * 24 * 7 groups
                groupCount = 60 * 24 * 7;
                formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
                for (int i = 0; i < groupCount; i++) {
                    ZonedDateTime oldestDateInGroup = now.minusMinutes(groupCount - i);
                    ZonedDateTime newestDateInGroup = now.minusMinutes(groupCount - i - 1);
                    String timeGroupName = formatter.format(oldestDateInGroup);
                    timeGroupsOrdered.add(timeGroupName);
                    timeGroupsBorders.put(
                      timeGroupName,
                      new TimeFrame(oldestDateInGroup, newestDateInGroup)
                    );
                }
            }
        }

        // Group the records
        for (InverterADataDTO record : allInvertersDataList) {
            for (String group : timeGroupsOrdered) {
                if ((!record.measureDate().isBefore(timeGroupsBorders.get(group).start))
                        && record.measureDate().isBefore(timeGroupsBorders.get(group).end)) {
                    // Record date is in group's time frame
                    ArrayList<InverterADataDTO> temp;
                    if (groupedRecords.get(group) != null) {
                        temp = groupedRecords.get(group);
                    } else {
                        temp = new ArrayList<>();
                    }
                    temp.add(record);
                    groupedRecords.put(group, temp);
                }
            }
        }
    
        return new GroupedRecordsData(timeGroupsOrdered, groupedRecords);
    }

    public ArrayList<FrontendInverterADataDTO> getFrontendInverterData(TimeRange timeRange,
            ArrayList<Long> inverterIds) {

        GroupedRecordsData groupedRecordsData = getDataGroupedByTime(timeRange, inverterIds);

        ArrayList<String> timeGroupsOrdered = groupedRecordsData.timeGroupsOrdered();
        HashMap<String, ArrayList<InverterADataDTO>> groupedRecords = groupedRecordsData.groupedRecords();

        // Convert to the frontend's DTO
        ArrayList<FrontendInverterADataDTO> finalData = new ArrayList<>();
        for (int i = 0; i < timeGroupsOrdered.size(); i++) {
            String groupName = timeGroupsOrdered.get(i);
            ArrayList<InverterADataDTO> recordsInGroup = groupedRecords.get(groupName);
            if ((recordsInGroup == null) || (recordsInGroup.size() == 0)) {
                finalData.add(
                    new FrontendInverterADataDTO(
                        i,
                        groupName,
                        0,
                        0,
                        0
                    )
                );
                continue;
            }
            float udc1Sum = 0;
            float idcSum = 0;
            float rotationsSum = 0;
            for (InverterADataDTO record : recordsInGroup) {
                udc1Sum += record.Udc1();
                idcSum += record.Idc();
                rotationsSum += record.generatorRotation();
            }
            finalData.add(
                new FrontendInverterADataDTO(
                    i,
                    groupName,
                    udc1Sum / recordsInGroup.size(),
                    idcSum / recordsInGroup.size(),
                    rotationsSum / recordsInGroup.size()
                )
            );
        }

        return finalData;
    }

    public GroupedRecordsData getPreciseDataFromLastWeek(long inverterId) {
        return getDataGroupedByTime(
            TimeRange.WEEK_IN_MINUTES,
          new ArrayList<>(List.of(inverterId))
        );
    }

    public int getInverterStatus(long inverterId) {
        List<InverterADataDTO> inverterDataList = inverterADataService.getDataByInverterId(inverterId);
        InverterADataDTO latestRecord = inverterDataList.stream()
            .max(Comparator.comparing(InverterADataDTO::measureDate))
            .orElse(null);
        if (latestRecord == null) {
            return 0;
        }
        return latestRecord.workingStatus();
    }
}
