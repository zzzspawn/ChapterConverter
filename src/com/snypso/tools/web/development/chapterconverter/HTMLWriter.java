package com.snypso.tools.web.development.chapterconverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class HTMLWriter {


    public boolean write(File file, Chapter chapter){

        boolean successful = false;
        try {

            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

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



            int i = 1;
            while (i < 26){
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

            List<String> text = chapter.getText();

            String startParagraph = "\t\t\t<p class='Para'>";
            String endParagraph = "</p>\n";

            int index = 0;
            while (index < text.size()){
                if(!text.get(index).equals("")){
                    template.append(startParagraph);
                    template.append(text.get(index));
                    template.append(endParagraph);
                }
                index++;
            }

            template.append(
                    "\t\t</div>\n" +
                            "\t</div>\n" +
                            "</body>\n" +
                            "</html>\n");


            bufferedWriter.write(template.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            successful = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return successful;

    }


}
