package com.baking.bakingapp.util;

import java.io.InputStream;
import java.util.Scanner;

public class FileUtils {
    public static String readJSONFromRawFolder(String rawFilePath) {
        InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(rawFilePath);
        Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}