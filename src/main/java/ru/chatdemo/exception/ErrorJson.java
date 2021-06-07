package ru.chatdemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@Data
@AllArgsConstructor
public class ErrorJson {
    private CharSequence url;
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
