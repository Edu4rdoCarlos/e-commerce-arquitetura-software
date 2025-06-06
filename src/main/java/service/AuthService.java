package service;

import java.sql.SQLException;

import domain.model.User;
import infra.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();

    public User authenticate(String email, String password) {
        try {
            return userRepository.findByEmailAndPassword(email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao autenticar usuário", e);

        }
    }
}
