package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository<T extends User> extends JpaRepository<T, String> {
    Optional<T> findByCpf(String cpf);
}
