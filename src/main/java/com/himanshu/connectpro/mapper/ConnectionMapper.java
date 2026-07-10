
package com.himanshu.connectpro.mapper;

import com.himanshu.connectpro.dto.ConnectionResponse;
import com.himanshu.connectpro.entity.Connection;

public class ConnectionMapper {

    private ConnectionMapper() {
    }

    public static ConnectionResponse toResponse(Connection connection) {
        return ConnectionResponse.builder()
                .id(connection.getId())
                .senderId(connection.getSender().getId())
                .senderName(connection.getSender().getFirstName() + " " + connection.getSender().getLastName())
                .receiverId(connection.getReceiver().getId())
                .receiverName(connection.getReceiver().getFirstName() + " " + connection.getReceiver().getLastName())
                .status(connection.getStatus())
                .build();
    }
}