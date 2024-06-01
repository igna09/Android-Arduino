package com.example.carduino.arduinolistener;

import androidx.core.content.res.TypedArrayUtils;

public class StringBuffer {
    private String buffer;

    public StringBuffer() {
        this.buffer = "";
    }

    public void addData(String data) {
        buffer += data;
    }

    public String readUntil(char c) {
        if(buffer.indexOf(c) > -1) {
            int indexOfChar = buffer.indexOf(c);
            String r = buffer.substring(0, indexOfChar);
            buffer = buffer.substring(indexOfChar + 1);
            return r;
        } else {
            return null;
        }
    }

    public String readNewLine() {
        return readUntil('\n');
    }
}
