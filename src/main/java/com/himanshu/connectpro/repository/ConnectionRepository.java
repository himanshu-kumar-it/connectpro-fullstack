
package com.himanshu.connectpro.repository;

import com.himanshu.connectpro.entity.*;
        import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);

    List<Connection> findByReceiverAndStatus(User receiver, ConnectionStatus status);

    List<Connection> findBySenderAndStatus(User sender, ConnectionStatus status);
}