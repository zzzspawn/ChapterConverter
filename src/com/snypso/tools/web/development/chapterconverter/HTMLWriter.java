package com.snypso.tools.web.development.chapterconverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;


/**
 * Writes an HTML file using a hardcoded template for how I structure my chapters
 */
public class HTMLWriter {

    /**
     *
     * @param file the file to be written
     * @param chapter the chapter instance to be used as the data.
     * @return a boolean value which indicates whether the writing process threw an exception
     */
    public boolean write(File file, Chapter chapter){

        boolean successful = false;
        try {

            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //I use a template I created in webstorm to fill in the data from the chapter txt
            StringBuilder template = new StringBuilder(
                    "<html>\n" +
                            "<head>\n" +
                            "\t<meta charset=\"UTF-8\">\n" +
                            "\t<title>"+chapter.getTitle()+"</title>\n" +
                            "\t<link rel=\"stylesheet\" href=\"../../../stylesheets/standardColors.css\">\n" +
                            "\t<link rel=\"stylesheet\" href=\"../../../stylesheets/chapterStyles.css\">\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "\t<div class=\"page-container\">\n" +
                            "\t\t<div class=\"chapter-menu\">\n" +
                            "\t\t\t<a class=\"top-element-nav-bar\"><button>Back to Writings</button></a>\n" +
                            "\t\t\t<ol class=\"vertical-ordered-list\">\n");


            //here I add the buttons on the side, depending on the chapter we are in, and such, standard max limit to 25 chapters, unless this chapter is bigger.
            int size = 25;
            if(chapter.getChapterNumber() > size){
                size = chapter.getChapterNumber();
            }
            int i = 1;
            while (i < size+1){
                if(i > chapter.getChapterNumber()){
                    String start = "\t\t\t\t<li><a class=\"not-published-yet\"><button title=\"Not published yet\">Chapter ";
                    String end = "</button></a></li>\n";
                    template.append(start);
                    template.append(Integer.toString(i));
                    template.append(end);
                }else if(i < chapter.getChapterNumber()){
                    String start = "\t\t\t\t<li><a><button title=\"Go to\">Chapter ";
                    String end = "</button></a></li>\n";
                    template.append(start);
                    template.append(Integer.toString(i));
                    template.append(end);
                }else if(i == chapter.getChapterNumber()){
                    String start = "\t\t\t\t<li id=\"selected\"><a><button title=\"Current chapter\">Chapter ";
                    String end = "</button></a></li>\n";
                    template.append(start);
                    template.append(Integer.toString(i));
                    template.append(end);
                }
                i++;
            }

            //appending the bottom of the list and div containers
            template.append(
                    "\t\t\t</ol>\n" +
                            "\t\t</div>\n" +
                            "\t\t<div class=\"container center-justified\">\n" +
                            "\t\t\t<p align='justify'><i>&nbsp;</i></p>\n" +
                            "\t\t\t<p align='justify'><i>&nbsp;</i></p>\n");


            //adding title
            String start = "\t\t\t<p class='chapter'><b>";
            String end = "</b></p>\n";
            template.append(start);
            template.append(chapter.getTitle());
            template.append(end);


            template.append("\t\t\t<p align='justify'><i>&nbsp;</i></p>\n");

            //here I get all the lines of text from the chapter
            List<String> text = chapter.getText();

            //the enclosing tags of the paragraphs of text in the chapter is created
            String startParagraph = "\t\t\t<p class='Para'>";
            String endParagraph = "</p>\n";

            //appending all the text and tags together
            int index = 0;
            while (index < text.size()){
                if(!text.get(index).equals("")){
                    template.append(startParagraph);
                    template.append(text.get(index));
                    template.append(endParagraph);
                }
                index++;
            }

            //appending the closing tags of the document
            template.append(
                    "\t\t</div>\n" +
                            "\t</div>\n" +
                            "</body>\n" +
                            "</html>\n");

            //writing flushing and closing using the BufferedWriter
            bufferedWriter.write(template.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            //marking the write as a success, since we haven't crashed
            successful = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        //returning the boolean value
        return successful;

    }


}
