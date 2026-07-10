
package com.himanshu.connectpro.repository;

import com.himanshu.connectpro.entity.*;
        import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostOrderByCreatedAtDesc(Post post);

    long countByPost(Post post);
}