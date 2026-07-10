
package com.himanshu.connectpro.controller;

import com.himanshu.connectpro.dto.*;
import com.himanshu.connectpro.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<ConnectionResponse>> sendRequest(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(connectionService.sendRequest(senderId, receiverId));
    }

    @PutMapping("/{connectionId}/accept")
    public ResponseEntity<ApiResponse<ConnectionResponse>> acceptRequest(@PathVariable Long connectionId) {
        return ResponseEntity.ok(connectionService.acceptRequest(connectionId));
    }

    @PutMapping("/{connectionId}/reject")
    public ResponseEntity<ApiResponse<ConnectionResponse>> rejectRequest(@PathVariable Long connectionId) {
        return ResponseEntity.ok(connectionService.rejectRequest(connectionId));
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<ApiResponse<List<ConnectionResponse>>> getPendingRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.getPendingRequests(userId));
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<ApiResponse<List<ConnectionResponse>>> getMyConnections(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.getMyConnections(userId));
    }
}