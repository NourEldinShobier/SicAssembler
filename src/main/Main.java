package main;

import core.FileManager;
import core.Segmentifier;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
import core.validators.ErrorController;
import core.validators.SegmentsValidator;
import utils.Instruction.Instruction;
import utils.InstructionIdentifier;
import utils.InstructionManager;
import utils.errors.ErrorRecord;

import java.util.ArrayList;
import java.util.List;


public class Main /*extends Application*/
{

//    @Override
//    public void start(Stage primaryStage) throws Exception
//    {
//        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }

    public static void main(String[] args)
    {
        //launch(args);

        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;


        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if (!line.trim().equals("")){
                Instruction instruction = Segmentifier.segmentify(line.toUpperCase());
                instruction.lineNumber = i;
                if(instruction.segments != null) {
                    Instruction validatedInstruction = SegmentsValidator.validate(instruction);
                    if (validatedInstruction != null){
                        instruction = validatedInstruction;
                        if (!instruction.isComment) instruction = InstructionIdentifier.identify(instruction);
                        instructions.add(instruction);
                    }else {
                        System.out.println(instruction.line);
                        List<ErrorRecord> errors = ErrorController.getInstance().getErrorLIst(instruction.lineNumber);
                        errors.forEach(error->{
                            System.out.println(error.getErrorMsg());
                        });
                    }
                }
            }
        }

        InstructionManager.generateListFile(instructions);

        /////////////
        // Testing //
        /////////////

        // Call to push an error
        /*ErrorController.getInstance().pushError("1000", ErrorType.IllegalRegisterAddress);
        ErrorController.getInstance().pushError("1003", ErrorType.CannotBeFormatFour);

        // Get a list of all errorRecords
        ErrorController.getInstance().getErrorsList().forEach(errorRecord -> System.out.println(errorRecord.getErrorMsg()));

        // Get the last errorMsg
        System.out.println(ErrorController.getInstance().getLastError().getErrorMsg());

        // Get the last errorAddress
        System.out.println(ErrorController.getInstance().getLastError().getAddress());*/

    }
}
