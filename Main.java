/*

Wing Har - CSC330 - Object Oriented Software Design
Lab 6: Encrypt and Decrypt GUI
May 2, 2021
NOTE: Used IntelliJ so required manual application of JavaFX library and dependencies!

 */

package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;

// Public class must be called Main if I don't want to separate it into a different class file.
public class Main extends Application {

    private Label outputFileLabel, encryptLabel, inputFileLabel, decryptLabel, messageLabel;
    private TextField outputFileField, inputMessageField, inputFileField, decryptText;
    private Button encryptButton, decryptButton;
    private VBox root;

    @Override

    // Setting the labels and the text fields.
    public void start(Stage primaryStage) throws Exception {

        // Labels
        decryptLabel = new Label("Decrypted Message: ");
        encryptLabel = new Label("Message to Encrypt: ");
        outputFileLabel = new Label("Output File: ");
        inputFileLabel = new Label("Input File: ");
        messageLabel = new Label("This message text will change based on what you have done!");
        // Text fields
        decryptText = new TextField();
        inputMessageField = new TextField();
        inputFileField = new TextField();
        outputFileField = new TextField();
        // Buttons
        decryptButton = new Button("Decrypt");
        encryptButton = new Button("Encrypt");

        //set handler
        encryptButton.setOnAction(new EncryptHandler());
        decryptButton.setOnAction(new DecryptHandler());

        /*

        Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/geometry/Pos.html

        */

        // Horizontal box for encryption set
        HBox box1 = new HBox(10);
        box1.setPadding(new Insets(10));
        // Pos.CENTER - Aligns everything in the center both horizontally and vertically.
        box1.setAlignment(Pos.CENTER);
        box1.getChildren().addAll(outputFileLabel, outputFileField, encryptLabel, inputMessageField, encryptButton);

        // Horizontal box for decryption set
        HBox box2 = new HBox(10);
        box2.setPadding(new Insets(10));
        // Pos.CENTER - Aligns everything in the center both horizontally and vertically.
        box2.setAlignment(Pos.CENTER);
        box2.getChildren().addAll(inputFileLabel, inputFileField, decryptLabel, decryptText, decryptButton);

        // Vertical box with positioning of items to the left
        root = new VBox(10);
        root.setPadding(new Insets(10));
        // Pos.CENTER - Aligns everything in the center vertically and on the left horizontally.
        root.setAlignment(Pos.CENTER_LEFT);

        // Add the children to the main scene
        root.getChildren().addAll(box1, box2, messageLabel);
        Scene scene = new Scene(root, 650, 150);
        primaryStage.setTitle("Encrypt and Decrypt a File!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Encryption
    private class EncryptHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String en = "";
            String s = inputMessageField.getText();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int shifted = (int) c + 10;
                en += (char) shifted;
            }
            // Export into a file
            try {
                String outputLoc = outputFileField.getText();
                DataOutputStream output = new DataOutputStream(new FileOutputStream(outputLoc));
                output.writeUTF(en);
                output.close();
                messageLabel.setText("Encrypted the message to " + outputLoc);
            } catch (Exception e) {
                messageLabel.setText("An error has occurred when saving to the file!");
            }
        }
    }

    // Decryption
    private class DecryptHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String de = "";
            String jumbled = "";
            try {
                DataInputStream input = new DataInputStream(new FileInputStream(inputFileField.getText()));
                jumbled = input.readUTF();
                input.close();
            }
            catch (Exception e) {
                messageLabel.setText("Something terrible has happened when reading file.");
            }
                for (int i = 0; i < jumbled.length(); i++) {

                    char c = jumbled.charAt(i);

                    int shifted = (int) c - 10;

                    de += (char) shifted;
                }
                decryptText.setText(de);
                messageLabel.setText("Successfully decrypted the message!");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
