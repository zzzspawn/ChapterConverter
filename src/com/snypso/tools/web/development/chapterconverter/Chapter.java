package com.snypso.tools.web.development.chapterconverter;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private String title;
    private List<String> text;
    private int chapterNumber;
    private int index;

    public Chapter(String title, List<String> text, int chapterNumber){
        this.title = title;
        this.text = text;
        this.chapterNumber = chapterNumber;
    }
    public Chapter(String title, int chapterNumber){
        this.title = title;
        this.chapterNumber = chapterNumber;
    }
    public Chapter(int chapterNumber){
        this.chapterNumber = chapterNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public void addText(String line){
        if(text == null) {
            text = new ArrayList<>();
        }
        text.add(line);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getText() {
        return text;
    }
    public String getNextLine(){
        String line = null;
        if(index < text.size()) {
            line = text.get(index);
            index++;
        }
        return line;
    }
    public int getIndex(){
        return index;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }
}
