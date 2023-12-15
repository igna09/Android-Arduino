package com.example.carduino.arduinolistener;

import androidx.core.content.res.TypedArrayUtils;

public class StringBuffer {
    private String buffer;

    public void addData(String data) {
        buffer += data;
    }

    public String readUntil(char c) {
        if(buffer.indexOf(c) > -1) {
            String r = buffer.substring(0, buffer.indexOf(c));
            buffer = buffer.replace(r, "");
            buffer = buffer.replace("\n", "");
            return r;
        } else {
            return null;
        }
    }

    public String readNewLine() {
        return readUntil('\n');
    }
}
