import core.FileManager;
//import javafx.application.Application;
//import javafx.stage.Stage;
import utils.Instruction.Instruction;

import core.validators.ErrorController;
import utils.errors.ErrorType;

import java.util.ArrayList;
import java.util.List;

public class Main /*extends Application*/
{

//    @Override
//    public void start(Stage primaryStage) throws Exception
//    {
//
//    }


    public static void main(String[] args)
    {
        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;

//        lines.forEach((line)->{
//             // Ignore comments
//            if(!line.contains(".")){
//                Instruction instruction = InstructionIdentifier.identify(line);
//                instructions.add(instruction);
//            }
//        });
//
//        InstructionManager.generateListFile(instructions);

        /////////////
        // Testing //
        /////////////

        // Call to push an error
        ErrorController.getInstance().pushError("1000", ErrorType.IllegalRegisterAddress);
        ErrorController.getInstance().pushError("1003", ErrorType.CannotBeFormatFour);

        // Get a list of all errorRecords
        ErrorController.getInstance().getErrorsList().forEach(errorRecord -> System.out.println(errorRecord.getErrorMsg()));

        // Get the last errorMsg
        System.out.println(ErrorController.getInstance().getLastError().getErrorMsg());

        // Get the last errorAddress
        System.out.println(ErrorController.getInstance().getLastError().getAddress());

    }
}
