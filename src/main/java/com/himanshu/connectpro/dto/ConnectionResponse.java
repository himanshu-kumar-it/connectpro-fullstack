
package com.himanshu.connectpro.dto;

import com.himanshu.connectpro.entity.ConnectionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionResponse {

    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private ConnectionStatus status;
}