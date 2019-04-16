import javafx.application.Application;
import javafx.stage.Stage;
import utils.Instruction.Instruction;
import utils.InstructionIdentifier;

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
        List<String> lines = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();

        lines.forEach((String line)->{
            Instruction instruction = InstructionIdentifier.identify(line);
            instructions.add(instruction);
        });
    }
}
