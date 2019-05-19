import core.FileManager;
import core.Segmentifier;
import core.Settings;
import core.validators.ErrorController;
import core.validators.SegmentsValidator;
import utils.ExpressionEvaluator;
import utils.Instruction.Instruction;
import utils.InstructionIdentifier;
import utils.InstructionsEncoders.FormatFOUREncoder;
import utils.ListFileManager;
import utils.OBJFileManager;
import utils.errors.ErrorRecord;
import utils.errors.ErrorType;

import java.util.ArrayList;
import java.util.List;


public class Main /*extends Application*/ {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\033[0;32m";


    public static void main(String[] args) {

        List<String> lines = FileManager.readFile();
        List<Instruction> instructions = new ArrayList<>();
        List<ErrorRecord> errors = null;

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
                    errors = ErrorController.getInstance().getErrorList(instruction.lineNumber);

                    System.out.println((errors.size() == 0 ? ANSI_GREEN : ANSI_RED) + instruction.line + ANSI_RESET);
                    errors.forEach(error -> System.out.println(error.getErrorMsg()));
                }
            }
        }

        InstructionIdentifier.PASS = 2;

        for (int i = 0; i < instructions.size(); i++) {
            Instruction temp = instructions.get(i);
            temp = InstructionIdentifier.identify(temp);
            instructions.set(i, temp);
        }

        ListFileManager.printSymbolTable();
        OBJFileManager.generateOBJFile(instructions);
    }
}