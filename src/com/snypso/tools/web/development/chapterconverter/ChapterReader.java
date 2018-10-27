package com.snypso.tools.web.development.chapterconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ChapterReader {

    public Chapter read(File file, int chapterNumber){
        Chapter chapter = null;
        try {
            chapter = new Chapter(chapterNumber);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);



        boolean titleFound = false;
        boolean textStart = false;
        int lineCount = 0;
        int centerFound = 0;
        String line = bufferedReader.readLine();
        while (line != null){
            if(!titleFound && line.contains("<center>") && centerFound > 0){
                chapter.setTitle(extractTitle(line));
                titleFound = true;
            }else if(centerFound < 1 && line.contains("<center>")){
                centerFound++;
            }

            if(titleFound && lineCount != 5){
                lineCount++;
            }

            if(lineCount == 5){
                textStart = true;
            }

            if(textStart){
                chapter.addText(line);
            }

            line = bufferedReader.readLine();
        }

        }catch (Exception e){
            e.printStackTrace();
        }

        return chapter;
    }

    private String extractTitle(String line) {
        int i = 0;
        StringBuilder title = new StringBuilder();
        boolean firstAsterixFound = false;
        while (i < line.length()){
            if(firstAsterixFound){
                title.append(line.charAt(i));
            }

            if(!firstAsterixFound && line.charAt(i) == '*'){
                firstAsterixFound = true;
            }

            i++;
        }
        title.deleteCharAt(title.length()-1);
        return title.toString();
    }


}
