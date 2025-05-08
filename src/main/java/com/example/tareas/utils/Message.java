package com.example.tareas.utils;

import lombok.Data;

@Data
public class Message {
    private String text;
    private TypeResponse type;
    private Object data;

    public Message(String text, TypeResponse type) {
        this.text = text;
        this.type = type;
    }

    public Message(Object data,String text, TypeResponse type) {
        this.text = text;
        this.type = type;
        this.data = data;
    }
}
