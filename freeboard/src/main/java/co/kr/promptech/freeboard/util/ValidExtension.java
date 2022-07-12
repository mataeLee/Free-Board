package co.kr.promptech.freeboard.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidExtension {
    private static final Set<String> extensionSet = new HashSet<>(Arrays.asList("jpg", "png", "gif", "jpeg"));

    public static boolean validation(String extension){
        return extensionSet.contains(extension);
    }
}