
package com.himanshu.connectpro.service;

import com.himanshu.connectpro.dto.*;

        import java.util.List;

public interface ConnectionService {

    ApiResponse<ConnectionResponse> sendRequest(Long senderId, Long receiverId);

    ApiResponse<ConnectionResponse> acceptRequest(Long connectionId);

    ApiResponse<ConnectionResponse> rejectRequest(Long connectionId);

    ApiResponse<List<ConnectionResponse>> getPendingRequests(Long userId);

    ApiResponse<List<ConnectionResponse>> getMyConnections(Long userId);
}