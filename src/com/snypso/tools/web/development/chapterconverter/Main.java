package com.snypso.tools.web.development.chapterconverter;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Application start class, throws dialogues to extract the information needed from the user.
 * at the end it displays an alert notifying the user whether it succeeded or not, which let's the user know the program has terminated(or will after the alert is closed).
 */
public class Main extends Application {

    /**
     *
     * @param args arguments passed along to the launch method of Application
     */
    public static void main(String[] args){

        launch(args);

    }

    /**
     *
     * @param window the stage, prefer to call it window(Thanks Bucky) as it is easier to remember what it does this way.
     * @throws Exception when something goes wrong, haven't used this for anything yet.
     */
    @Override
    public void start(Stage window) throws Exception {
        //creates a filechooser and asks user for what textfile to convert to html
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open txt File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));
        //actually show the filechooser dialogue
        File readFile = fileChooser.showOpenDialog(window);

        //asks for what chapter this is, is used for generating the chapter selection buttons on the html page
        //(ask explicitly in case you don't want to use numbers in your chapter names)
        //I don't actually check if the user enters a number, as I am the primary user, and you pretty much have to crash on purpose here, can add a check later if needed
        TextInputDialog dialog = new TextInputDialog("Chapter Number");
        dialog.setTitle("Input the numeric value of the chapter");
        dialog.setHeaderText("Very much great");
        dialog.setContentText("Please enter numeric value");
        String number = "0";

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            number = result.get();
        }

        //initialize and run the Chapter reader class, which is a simple class reading in the chapter title and paragraphs from the yWriter txt file
        ChapterReader chapterReader = new ChapterReader();
        Chapter chapter = chapterReader.read(readFile, Integer.parseInt(number));


        //Query the user for where he/she wan't to save the file
        fileChooser.setTitle("Save html file");
        //removing the file extensions, and adding HTML filetype 
        fileChooser.getExtensionFilters().removeAll();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("HTML Files", "*.html"));
        fileChooser.setInitialDirectory(new File(readFile.getParent()));
        File saveFile = fileChooser.showSaveDialog(window);

        //initializing the class writing the html file using the Chapter class
        HTMLWriter htmlWriter = new HTMLWriter();
        //actually writes the html file, if the write process crashes for some reason, then it'll store the boolean value of crash or not crash in @done
        boolean done = htmlWriter.write(saveFile, chapter);

        //displays an alert notifying you whether the application succeeded or didn't, depending on the @done variable
        if(done){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Program terminated");
            alert.setHeaderText(null);
            alert.setContentText("HTML Write Successful!");

            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Program terminated");
            alert.setHeaderText(null);
            alert.setContentText("HTML Write Failed!");
            alert.showAndWait();
        }
    }
}
