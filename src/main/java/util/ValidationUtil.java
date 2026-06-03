package util;

import exception.EmptyFieldException;

public class ValidationUtil {

    public static void require(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new EmptyFieldException(fieldName + " 값은 필수입니다.");
        }
    }

    public static void requireNumberGreaterThan(int value, int min, String fieldName) {
        if (value < min) {
            throw new EmptyFieldException(fieldName + " 값은 " + min + " 이상이어야 합니다.");
        }
    }

    public static void requireDoubleGreaterThan(double value, double min, String fieldName) {
        if (value < min) {
            throw new EmptyFieldException(fieldName + " 값은 " + min + " 이상이어야 합니다.");
        }
    }
}