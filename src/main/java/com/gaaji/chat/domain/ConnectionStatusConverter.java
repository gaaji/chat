package com.gaaji.chat.domain;

import javax.persistence.AttributeConverter;

public class ConnectionStatusConverter implements AttributeConverter<ConnectionStatus, String> {
    @Override
    public String convertToDatabaseColumn(ConnectionStatus attribute) {
        return attribute.toString();
    }

    @Override
    public ConnectionStatus convertToEntityAttribute(String dbData) {
        return ConnectionStatus.valueOf(dbData);
    }
}
