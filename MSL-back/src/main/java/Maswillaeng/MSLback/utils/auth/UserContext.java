package Maswillaeng.MSLback.utils.auth;

import Maswillaeng.MSLback.domain.entity.User;

public class UserContext {
    public static ThreadLocal<User> currentMember = new ThreadLocal<>();
}
