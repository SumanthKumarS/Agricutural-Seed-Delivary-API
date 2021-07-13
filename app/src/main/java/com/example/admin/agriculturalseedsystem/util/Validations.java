package com.example.admin.agriculturalseedsystem.util;

import java.util.regex.Pattern;

public class Validations {
    private static final Pattern yphone = Pattern.compile("(0/91)?[7-9][0-9]{9}");
    private static final Pattern yemail = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    private static final Pattern lowercase = Pattern.compile("^.*[a-z].*$");
    private static final Pattern uppercase = Pattern.compile("^.*[A-Z].*$");
    private static final Pattern number = Pattern.compile("^.*[0-9].*$");
    private static final Pattern specialCharacter = Pattern.compile("^.*[^a-zA-Z0-9].*$");

    public static boolean validateEmail(String txt_email) {
        return yemail.matcher((CharSequence) txt_email).matches();
    }

    public static boolean isValidPhone(String lpass) {
        return yphone.matcher(lpass).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return true;
        }
        if (lowercase.matcher(password).matches()) {
            return true;
        }
        if (uppercase.matcher(password).matches()) {
            return true;
        }
        if (number.matcher(password).matches()) {
            return true;
        }
        return !specialCharacter.matcher(password).matches();
    }

}
