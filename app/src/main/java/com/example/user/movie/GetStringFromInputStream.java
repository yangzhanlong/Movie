package com.example.user.movie;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetStringFromInputStream {
    public static String getString(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }
}
