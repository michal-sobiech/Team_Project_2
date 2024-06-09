package org.pzsp2.proman.data_analysis;

import org.pzsp2.proman.data_analysis.InverterDataAnalysisModule.GroupedRecordsData;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.pzsp2.proman.database_management.tables.inverter.service.InverterService;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.InverterADataDTO;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.TipAnalysisInverterADataDTO;
import org.pzsp2.proman.tips.Tip;
import lombok.Getter;
import lombok.AllArgsConstructor;
import org.pzsp2.protocoltranslation.inverterdata.WorkStatus;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TipsAnalysisModule {

    @Value("${spike_treshold}")
    private float spikeTreshold;

    @Getter
    @AllArgsConstructor
    private enum TipType {
        POINT_DROP("Point drop and rise"),
        POINT_RISE("Point rise and drop"),
        INVERTER_OFF("Device is turned off");

        private final String tipTitle;
    }

    private final InverterDataAnalysisModule inverterDataAnalysisModule;
    private final InverterService inverterService;

    public TipsAnalysisModule(InverterDataAnalysisModule inverterDataAnalysisModule,
            InverterService inverterService) {
        this.inverterDataAnalysisModule = inverterDataAnalysisModule;
        this.inverterService = inverterService;
    }

    private Optional<TipType> checkForSpikes(float previousVal,
            float currentVal, float nextVal) {
        if ((previousVal * (1 - spikeTreshold) > currentVal)
                && (nextVal * (1 - spikeTreshold) > currentVal)) {
            // Rapid drop and rise
            return Optional.of(TipType.POINT_DROP);
        } else if ((previousVal * (1 + spikeTreshold) < currentVal)
              && (nextVal * (1 + spikeTreshold) < currentVal)) {
            // Or a rapid rise and drop
            return Optional.of(TipType.POINT_RISE);
        }
        return Optional.empty();
    }

    private record SpikeInfo(String param, TipType type) {}

    private TipAnalysisInverterADataDTO getTimeGroupRecord(
            Map<String, ArrayList<InverterADataDTO>> groupedRecords,
            String timeGroup) {
        if (groupedRecords.containsKey(timeGroup)) {
            return TipAnalysisInverterADataDTO.of(groupedRecords.get(timeGroup).get(0));
        }
        return new TipAnalysisInverterADataDTO();
    }

    private void addSpikeToHashMap(HashMap<SpikeInfo, Integer> spikes,
            SpikeInfo spikeInfo) {
        if (spikes.containsKey(spikeInfo) == false) {
            spikes.put(spikeInfo, 1);
            return;
        }
        spikes.put(spikeInfo, spikes.get(spikeInfo) + 1);
    }

    private HashMap<SpikeInfo, Integer> getLastWeekParamSpikes(List<Long> inverterIds) {
        HashMap<SpikeInfo, Integer> spikes = new HashMap<>();
        for (long id : inverterIds) {
            GroupedRecordsData groupedRecordsData = inverterDataAnalysisModule.getPreciseDataFromLastWeek(id);
            List<String> timeGroupsOrdered = groupedRecordsData.timeGroupsOrdered();
            Map<String, ArrayList<InverterADataDTO>> groupedRecords = groupedRecordsData.groupedRecords();
            for (int i = 1; i < timeGroupsOrdered.size() - 1; i++) {
                String previousTimeGroup = timeGroupsOrdered.get(i - 1);
                String currentTimeGroup = timeGroupsOrdered.get(i);
                String nextTimeGroup = timeGroupsOrdered.get(i + 1);
                TipAnalysisInverterADataDTO previousRecord = getTimeGroupRecord(
                    groupedRecords, previousTimeGroup);
                TipAnalysisInverterADataDTO currentRecord = getTimeGroupRecord(
                    groupedRecords, currentTimeGroup);
                TipAnalysisInverterADataDTO nextRecord = getTimeGroupRecord(
                    groupedRecords, nextTimeGroup);
                
                // Check for generator rotations
                Optional<TipType> spike = checkForSpikes(
                    previousRecord.generatorRotation(),
                    currentRecord.generatorRotation(),
                    nextRecord.generatorRotation());
                if (spike.isEmpty()) {
                    continue;
                }
                SpikeInfo spikeInfo = new SpikeInfo("Generator rotations/s instability", spike.get());
                addSpikeToHashMap(spikes, spikeInfo);
            }
        }
        return spikes;
    }

    private List<Tip> getSpikeTips(List<Long> inverterIds) {
        List<Tip> tips = new ArrayList<>();
        HashMap<SpikeInfo, Integer> spikes = getLastWeekParamSpikes(inverterIds);
        for (SpikeInfo info : spikes.keySet()) {
            String param = info.param();
            TipType type = info.type();
            int occurences = spikes.get(info);

            String title =  String.format("%s instability", param);
            String desc = String.format(
                "%s, occurences during the last week: %d",
                type.getTipTitle(), occurences
            );
            tips.add(new Tip(title, desc));
        }
        return tips;
    }

    private List<Tip> getInvertersStatusTips(List<Long> inverterIds) {
        List<Tip> tips = new ArrayList<>();
        for (Long id : inverterIds) {
            InverterDTO inverterDTO = inverterService.getInverterById(id);
            int statusNumber = inverterDataAnalysisModule.getInverterStatus(id);
            WorkStatus status = WorkStatus.fromIdentifier(statusNumber);
            String title;
            switch(status) {
                case UNDEFINED:
                    title = "Inverter status undefined";
                    break;
                case ERROR:
                    title = "Inverter not working";
                    break;
                case SERVICE:
                    title = "Inverter is being repaired";
                    break;
                case STARTING:
                case WORKING:
                default:
                    continue;
            }
            String desc = String.format("Device code: %s", inverterDTO.code());
            tips.add(new Tip(title, desc));
        }
        return tips;
    }

    public List<Tip> getAllTips(List<Long> inverterIds) {
        ArrayList<Tip> tips = new ArrayList<>();
        tips.addAll(getSpikeTips(inverterIds));
        tips.addAll(getInvertersStatusTips(inverterIds));
        return tips;
    }

}
