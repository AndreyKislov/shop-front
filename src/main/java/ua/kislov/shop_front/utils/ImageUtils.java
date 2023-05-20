package ua.kislov.shop_front.utils;

import java.util.Base64;

public class ImageUtils {

    public static String convertToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
