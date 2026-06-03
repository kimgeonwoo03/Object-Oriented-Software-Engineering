package repository;

import entity.User;
import util.JsonFileUtil;

import java.util.List;

public class AuthRepository {

    private static final String USER_FILE = "user_info.json";

    public User findUserById(String userId) {
        List<User> userList = JsonFileUtil.readList(USER_FILE, User.class);

        for (User user : userList) {
            if (user.getUserId() != null && user.getUserId().equals(userId)) {
                return user;
            }
        }

        return null;
    }
}