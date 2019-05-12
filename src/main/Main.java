package main;
import core.FileManager;
import core.Segmentifier;
import core.validators.ErrorController;
import core.validators.SegmentsValidator;
import utils.Instruction.Instruction;
import utils.InstructionIdentifier;
import utils.InstructionManager;
import utils.errors.ErrorRecord;

import java.util.ArrayList;
import java.util.List;


public class Main /*extends Application*/ {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\033[0;32m";
//    @Override
//    public void start(Stage primaryStage) throws Exception
//    {
//        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        //launch(args);


        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        assert lines != null;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.trim().equals("")) {
                Instruction instruction = Segmentifier.segmentify(line.toUpperCase());
                instruction.lineNumber = i;
                if (instruction.segments != null && !instruction.isComment) {
                    Instruction validatedInstruction = SegmentsValidator.validate(instruction);

                    if (!ErrorController.getInstance().foundErrors(instruction.lineNumber)) {
                        instruction = validatedInstruction;
                        if (!instruction.isComment) instruction = InstructionIdentifier.identify(instruction);
                        instructions.add(instruction);
                    }

                    SegmentsValidator.checkEndStatement(i == lines.size()-1);
                    List<ErrorRecord> errors = ErrorController.getInstance().getErrorList(instruction.lineNumber);

                    System.out.println((errors.size() == 0 ? ANSI_GREEN : ANSI_RED) + instruction.line + ANSI_RESET);
                    errors.forEach(error -> {
                        System.out.println(error.getErrorMsg());
                    });
                }
            }
        }


        InstructionManager.generateListFile(instructions);
        InstructionManager.printSymbolTable();

    }
}
