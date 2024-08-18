package com.bloggerbackend.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {
    public static String mapToSlug(String input) {
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", " ")
                .trim()
                .replaceAll("\\s", "-");
    }
}
