package case_study.common;

import java.util.regex.Pattern;

public class RegexPatterns {

    // Username regex
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,15}$";

    // Password regex
    public static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}";

    // Email regex
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Name regex
    public static final String NAME_REGEX = "^[a-zA-Z\\s]{2,50}$";

    // Phone Number regex
    public static final String PHONE_NUMBER_REGEX = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";

    public static boolean validateUsername(String username) {
        return Pattern.matches(RegexPatterns.USERNAME_REGEX, username);
    }

    public static boolean validatePassword(String password) {
        return Pattern.matches(RegexPatterns.PASSWORD_REGEX, password);
    }

    public static boolean validateEmail(String email) {
        return Pattern.matches(RegexPatterns.EMAIL_REGEX, email);
    }

    public static boolean validateName(String name) {
        return Pattern.matches(RegexPatterns.NAME_REGEX, name);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return Pattern.matches(RegexPatterns.PHONE_NUMBER_REGEX, phoneNumber);
    }

}
