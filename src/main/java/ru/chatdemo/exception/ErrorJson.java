package ru.chatdemo.exception;

import lombok.Data;

import java.util.Arrays;

@Data
public class ErrorJson {
    private String url;
    private String type;
    private String[] details;

    @Override
    public String toString() {
        return "ErrorJson{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", details=" + Arrays.toString(details) +
                '}';
    }
}
