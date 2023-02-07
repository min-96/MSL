package Maswillaeng.MSLback.utils.auth;

import Maswillaeng.MSLback.domain.entity.User;

public class UserContext {
    public static ThreadLocal<Long> userId = new ThreadLocal<>();

    public static void remove() {
        if (userId != null)
            userId.remove();
    }
}
