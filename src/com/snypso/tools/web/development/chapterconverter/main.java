package com.snypso.tools.web.development.chapterconverter;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class main extends Application {
    public static void main(String[] args){

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open txt File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));

        File readFile = fileChooser.showOpenDialog(window);


        TextInputDialog dialog = new TextInputDialog("Chapter Number");
        dialog.setTitle("Input the numeric value of the chapter");
        dialog.setHeaderText("Very much great");
        dialog.setContentText("Please enter numeric value");
        String number = "0";

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            number = result.get();
        }


        ChapterReader chapterReader = new ChapterReader();
        Chapter chapter = chapterReader.read(readFile, Integer.parseInt(number));
        HTMLWriter htmlWriter = new HTMLWriter();

        fileChooser.setTitle("Save html file");
        fileChooser.getExtensionFilters().removeAll();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("HTML Files", "*.html"));
        fileChooser.setInitialDirectory(new File(readFile.getParent()));
        File saveFile = fileChooser.showSaveDialog(window);

        boolean done = htmlWriter.write(saveFile, chapter);


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
