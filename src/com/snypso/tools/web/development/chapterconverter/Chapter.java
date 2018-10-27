package com.snypso.tools.web.development.chapterconverter;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds the information of a chapter
 *  it holds a title, in a string,
 *  a list of strings for the text paragrahs in the chapter txt
 *  a number representing which chapter it is
 *  an index for iterating over the different paragraphs.
 *  goes to the start of the list if you iterate past the list size.
 */
class Chapter {
    private String title;
    private List<String> text;
    private int chapterNumber;
    private int index;

    Chapter(String title, List<String> text, int chapterNumber){
        this.title = title;
        this.text = text;
        this.chapterNumber = chapterNumber;
    }
    Chapter(String title, int chapterNumber){
        this.title = title;
        this.chapterNumber = chapterNumber;
    }
    Chapter(int chapterNumber){
        this.chapterNumber = chapterNumber;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setText(List<String> text) {
        this.text = text;
    }

    void addText(String line){
        if(text == null) {
            text = new ArrayList<>();
        }
        text.add(line);
    }

    String getTitle() {
        return title;
    }

    List<String> getText() {
        return text;
    }
    String getNextLine(){
        String line = null;
        if(index < text.size()) {
            line = text.get(index);
            index++;
        }
        return line;
    }
    int getIndex(){
        return index;
    }

    int getChapterNumber() {
        return chapterNumber;
    }
}
