package cn.edu.usc.quzhijie.emrservice.user.util;

import org.springframework.stereotype.Component;

@Component
public class InfoUtils {
    // 脱敏
    public static String maskName(String name) {
        if (name == null || name.isEmpty()) return name;

        int length = name.length();
        if (length == 1) {
            return name;
        } else if (length == 2) {
            return name.charAt(0) + "*";
        } else {
            return name.charAt(0) + "*" + name.substring(length - 1);
        }
    }
    public static String maskIdCard(String id) {
        if (id == null || id.length() < 8) return id;
        return id.substring(0, 6) + "**********" + id.substring(id.length() - 2);
    }
}
