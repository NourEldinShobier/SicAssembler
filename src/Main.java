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
        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;

        lines.forEach((String line)->{
            Instruction instruction = InstructionIdentifier.identify(line);
            instructions.add(instruction);
        });

        InstructionManager.generateListFile(instructions);
    }
}
