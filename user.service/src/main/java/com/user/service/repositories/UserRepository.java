package com.user.service.repositories;

import com.user.service.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Page<User> findByIsEnable(Pageable pageable, Boolean isEnable);

    Page<User> findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            Pageable pageable, String email, String fullName);

    Page<User> findByEmailContainingIgnoreCaseAndIsEnableOrFullNameContainingIgnoreCaseAndIsEnable(
            Pageable pageable, String email, Boolean isEnable, String fullName, Boolean isEnable2);

    List<User> findByFavoritesContaining(UUID shopId);

    Optional<User> findByEmail(String email);
}
