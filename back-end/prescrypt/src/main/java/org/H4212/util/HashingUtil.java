package org.H4212.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashingUtil {

    public static String hashString(String str)
    {
        String sha256hex = Hashing.sha256()
                .hashString(str, StandardCharsets.UTF_8)
                .toString();

        return sha256hex;
    }

}
