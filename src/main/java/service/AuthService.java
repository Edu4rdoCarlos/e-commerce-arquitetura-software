package service;

import domain.model.User;
import repository.UserRepository;

import java.sql.SQLException;

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
