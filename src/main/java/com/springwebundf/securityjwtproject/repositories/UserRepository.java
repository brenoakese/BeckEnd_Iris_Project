package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    List<User> findAll();

    User findByCpf(String cpf);

    User save(User user);

    void delete(User user);

    User update(User user);

    User findById(Long id);


}

