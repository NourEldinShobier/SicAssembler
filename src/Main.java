import core.FileManager;
import core.Segmentifier;
import core.Settings;
import core.validators.ErrorController;
import core.validators.SegmentsValidator;
import utils.ExpressionEvaluator;
import utils.Instruction.Instruction;
import utils.InstructionIdentifier;
import utils.InstructionManager;
import utils.InstructionsEncoders.FormatFOUREncoder;
import utils.errors.ErrorRecord;
import utils.errors.ErrorType;

import java.util.ArrayList;
import java.util.List;


public class Main /*extends Application*/ {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\033[0;32m";


    public static void main(String[] args) {

        //launch(args);

        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();

        InstructionIdentifier.PASS = 1;

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

                    SegmentsValidator.checkEndStatement(i == lines.size() - 1);
                    List<ErrorRecord> errors = ErrorController.getInstance().getErrorList(instruction.lineNumber);

                    System.out.println((errors.size() == 0 ? ANSI_GREEN : ANSI_RED) + instruction.line + ANSI_RESET);
                    errors.forEach(error -> {
                        System.out.println(error.getErrorMsg());
                    });
                }
            }
        }

        InstructionIdentifier.PASS = 2;

        for (int i = 0; i < instructions.size(); i++) {
            Instruction temp = instructions.get(i);
            temp = InstructionIdentifier.identify(temp);
            instructions.set(i, temp);
        }

        InstructionManager.generateListFile(instructions);
        InstructionManager.generateOBJFile(instructions);
        InstructionManager.printSymbolTable();


        /*ExpressionEvaluator e = new ExpressionEvaluator();
        System.out.println(e.evaluate("GAMMA+BETA - 10 * 9", 0));*/


       /* Instruction instruction = new Instruction();
        instruction.segments = new String[3];
        instruction.segments[0] = "";
        instruction.segments[1] = "+ADD";
        instruction.segments[2] = "#1000";
        instruction = FormatFOUREncoder.encode(instruction);
        System.out.println(instruction.opCode);*/

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