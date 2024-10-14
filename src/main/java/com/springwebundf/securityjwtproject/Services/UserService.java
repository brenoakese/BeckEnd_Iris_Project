package com.springwebundf.securityjwtproject.Services;


import com.springwebundf.securityjwtproject.domain.user.User;
import com.springwebundf.securityjwtproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;


        @Override
        public List<User> findAll(){
                return List.of();
        }

        @Override
        public User findByCpf(String cpf) {
                String sql = "SELECT * FROM users WHERE cpf = ?";

                return jdbcTemplate.queryForObject(sql, new Object[]{cpf}, (rs, rowNum) ->
                        new User(
                                rs.getString("tipo"),
                                rs.getString("cpf")
                        ));
        }

        @Override
        public User save(User user) {
                return null;
        }

        @Override
        public void delete(User user) {

        }

        @Override
        public User update(User user) {
                return null;
        }

        @Override
        public User findById(Long id) {
                return null;
        }
}
