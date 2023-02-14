package org.leon.todoapp.utils;

public class Operations {

    public static String trimBrackets(String message){
        return message.replaceAll("[\\[\\]]","");
    }
}
