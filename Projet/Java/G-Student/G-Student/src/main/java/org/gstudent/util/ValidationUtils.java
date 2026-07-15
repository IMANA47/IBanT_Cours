package org.gstudent.util;

import java.util.regex.Pattern;

public class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[a-zA-ZÀ-ÿ\\s-]{2,50}$"
    );
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    public static boolean isValidCoefficient(double coeff) {
        return coeff >= 0 && coeff <= 20;
    }
    
    public static boolean isValidNote(double note) {
        return note >= 0 && note <= 20;
    }
    
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
