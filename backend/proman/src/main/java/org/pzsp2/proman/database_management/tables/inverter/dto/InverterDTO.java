package org.pzsp2.proman.database_management.tables.inverter.dto;

import org.pzsp2.proman.database_management.tables.inverter.model.Inverter;
import org.pzsp2.proman.database_management.tables.inverter_model.model.InverterModel;

public record InverterDTO(long inverterId, String code, String login, long userId,
                          String ipAddress, InverterModel inverterModel) {
    public static InverterDTO of(final Inverter inverter) {
        return new InverterDTO(
            inverter.getInverterId(),
            inverter.getCode(),
            inverter.getLogin(),
            inverter.getUserId(),
            inverter.getIpAddress(),
            inverter.getModel()
        );
    }
}
