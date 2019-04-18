import core.FileManager;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.Instruction.Instruction;
import utils.Instruction.MnemonicFormat;
import utils.InstructionIdentifier;
import utils.InstructionManager;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {

    }


    public static void main(String[] args)
    {
        /*List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;

        lines.forEach((line)->{
            Instruction instruction = InstructionIdentifier.identify(line);
            instructions.add(instruction);
        });

        InstructionManager.generateListFile(instructions);

        // For testing
        instructions.forEach((instruction)->{
            if (instruction.mnemonic.format == MnemonicFormat.TWO) System.out.println("TWO");
            if (instruction.mnemonic.format == MnemonicFormat.THREE) System.out.println("THREE");
            if (instruction.mnemonic.format == MnemonicFormat.FOUR) System.out.println("FOUR");
        });*/


        String[] segments = "HELLO    BYTE    X'ABC'".split("\\s+");

        if (segments[2].startsWith("C")) {
            System.out.println("C");
            System.out.println(segments[2].substring(2, segments[2].length() - 1));
        }
        if (segments[2].startsWith("X")) {
            System.out.println("X");
            System.out.println(segments[2].substring(2, segments[2].length() - 1));
        }
    }
}
