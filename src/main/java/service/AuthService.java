package service;

import entity.User;
import exception.EmptyFieldException;
import exception.NotFoundException;
import repository.AuthRepository;
import util.ValidationUtil;

public class AuthService {

    private AuthRepository authRepository = new AuthRepository();

    public User login(String userId, String password) {
        ValidationUtil.require(userId, "사용자 ID");
        ValidationUtil.require(password, "비밀번호");

        User user = authRepository.findUserById(userId);

        if (user == null) {
            throw new NotFoundException("존재하지 않는 사용자입니다.");
        }

        if (!user.getPassword().equals(password)) {
            throw new EmptyFieldException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}