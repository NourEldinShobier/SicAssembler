package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        launch(args);

        /*List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;

        lines.forEach((line)->{
            Instruction instruction = InstructionIdentifier.identify(line);
            instructions.add(instruction);
        });

        InstructionManager.generateListFile(instructions);*/

        // For testing
        /*instructions.forEach((instruction)->{
            if (instruction.mnemonic.format == MnemonicFormat.TWO) System.out.println("TWO");
            if (instruction.mnemonic.format == MnemonicFormat.THREE) System.out.println("THREE");
            if (instruction.mnemonic.format == MnemonicFormat.FOUR) System.out.println("FOUR");
        });*/
    }
}
