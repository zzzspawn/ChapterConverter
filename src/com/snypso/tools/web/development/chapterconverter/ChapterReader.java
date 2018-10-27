package com.snypso.tools.web.development.chapterconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * A class that reads in a txt file exported from yWriter, and returns a Chapter object
 */
class ChapterReader {

    /**
     * @param file the file to be read
     * @param chapterNumber the index number of the chapter, starts at 1
     * @return returns a Chapter object, null if the read process fails with any exception
     */
    Chapter read(File file, int chapterNumber){

        //Initializing the chapter object to null so I can return it if the read fails
        Chapter chapter = null;
        try {

            //creating a new chapter, and adding the chapter index
            chapter = new Chapter(chapterNumber);
            //creating the fileReader and the BufferedReader
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);


        //initializing the variables used in the reading process
        boolean titleFound = false; //check for whether the title of the text has been found
        boolean textStart = false; //check for when you reach the line where the actual text paragraphs start.
        int lineCount = 0; //just a counter for skipping the lines between the title and the text paragraphs
        int centerFound = 0; //tacked on another check, since the txt file had the same tag used to denote the chapter start in the top description
        String line = bufferedReader.readLine();
        while (line != null){
            //here I check for the <center> tag, as I know this means a chapter title is being displayed, will probably use this later to denote multiple chapters in one book
            if(!titleFound && line.contains("<center>") && centerFound > 0){
                chapter.setTitle(extractTitle(line)); //extract the title, which is encapsulated like this: <center>*Title*
                titleFound = true;
            }else if(centerFound < 1 && line.contains("<center>")){
                centerFound++;
            }
            //if I find the title, I can start counting up until I get past the spacing of 3 lines between the title and the text paragraphs
            if(titleFound && lineCount != 5){
                lineCount++;
            }
            //here I check whether I have found the text paragraphs, could probably be cleaned up to a simpler check, but it works for now
            if(lineCount == 5){
                textStart = true;
            }
            //here I then add the text paragraphs to the chapter object
            if(textStart){
                chapter.addText(line);
            }
            //reads the next line
            line = bufferedReader.readLine();
        }

        //could probably write a better check, or throw a better exception, but haven't got so far as of yet
        }catch (Exception e){
            e.printStackTrace();
        }

        return chapter;//returns the chapter object, null if the reader got an exception
    }

    /**
     *
     * @param line this is the line you wan't to check for
     * @return the chapter title without the fluff from the txt file
     */
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
