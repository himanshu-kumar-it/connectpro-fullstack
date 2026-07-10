
package com.himanshu.connectpro.service.impl;

import com.himanshu.connectpro.dto.*;
        import com.himanshu.connectpro.entity.*;
        import com.himanshu.connectpro.exception.DuplicateResourceException;
import com.himanshu.connectpro.exception.ResourceNotFoundException;
import com.himanshu.connectpro.mapper.ConnectionMapper;
import com.himanshu.connectpro.repository.*;
        import com.himanshu.connectpro.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<ConnectionResponse> sendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new DuplicateResourceException("You cannot connect with yourself");
        }

        User sender = getUser(senderId);
        User receiver = getUser(receiverId);

        if (connectionRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new DuplicateResourceException("Connection request already exists");
        }

        Connection connection = Connection.builder()
                .sender(sender)
                .receiver(receiver)
                .status(ConnectionStatus.PENDING)
                .build();

        Connection saved = connectionRepository.save(connection);

        return ApiResponse.<ConnectionResponse>builder()
                .success(true)
                .message("Connection request sent successfully")
                .data(ConnectionMapper.toResponse(saved))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<ConnectionResponse> acceptRequest(Long connectionId) {
        Connection connection = getConnection(connectionId);
        connection.setStatus(ConnectionStatus.ACCEPTED);

        Connection saved = connectionRepository.save(connection);

        return ApiResponse.<ConnectionResponse>builder()
                .success(true)
                .message("Connection request accepted")
                .data(ConnectionMapper.toResponse(saved))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<ConnectionResponse> rejectRequest(Long connectionId) {
        Connection connection = getConnection(connectionId);
        connection.setStatus(ConnectionStatus.REJECTED);

        Connection saved = connectionRepository.save(connection);

        return ApiResponse.<ConnectionResponse>builder()
                .success(true)
                .message("Connection request rejected")
                .data(ConnectionMapper.toResponse(saved))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<List<ConnectionResponse>> getPendingRequests(Long userId) {
        User user = getUser(userId);

        List<ConnectionResponse> response = connectionRepository
                .findByReceiverAndStatus(user, ConnectionStatus.PENDING)
                .stream()
                .map(ConnectionMapper::toResponse)
                .toList();

        return ApiResponse.<List<ConnectionResponse>>builder()
                .success(true)
                .message("Pending requests fetched")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<List<ConnectionResponse>> getMyConnections(Long userId) {
        User user = getUser(userId);

        List<ConnectionResponse> response = connectionRepository
                .findBySenderAndStatus(user, ConnectionStatus.ACCEPTED)
                .stream()
                .map(ConnectionMapper::toResponse)
                .toList();

        return ApiResponse.<List<ConnectionResponse>>builder()
                .success(true)
                .message("Connections fetched")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Connection getConnection(Long id) {
        return connectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Connection not found"));
    }
}