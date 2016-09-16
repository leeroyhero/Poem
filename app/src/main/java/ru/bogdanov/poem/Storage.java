package ru.bogdanov.poem;

import java.util.ArrayList;

/**
 * Created by Владимир on 15.09.2016.
 */
public class Storage {
    private static String POEM_TEXT="";
    private static ArrayList<String> poemList;

    public static String getPoemText() {
        return POEM_TEXT;
    }

    public static void setPoemText(String poemText) {
        POEM_TEXT = poemText;
    }

    public static ArrayList<String> getPoemList() {
        return poemList;
    }

    public static void setPoemList(ArrayList<String> poemList) {
        Storage.poemList = poemList;
    }
}
