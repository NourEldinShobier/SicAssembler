package main;

import core.FileManager;
import core.Segmentifier;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Instruction.Instruction;
import utils.InstructionIdentifier;
import utils.InstructionManager;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        //launch(args);

        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;

        lines.forEach((line)->{
            if (!line.trim().equals("")){
                Instruction instruction = Segmentifier.segmentify(line.trim().toUpperCase());
                if (!instruction.isComment) instruction = InstructionIdentifier.identify(instruction);
                instructions.add(instruction);
            }
        });

        InstructionManager.generateListFile(instructions);
    }
}
