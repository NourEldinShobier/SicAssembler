package utils;

import core.Settings;
import utils.Instruction.Instruction;
import utils.Instruction.MnemonicFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJFileManager {

    private static List<String> textRecordLines = new ArrayList<>();
    private static List<String> opCodes = new ArrayList<>();

    public static void generateOBJFile(List<Instruction> instructions) {
        printHeader();
        printText(instructions);
        printEnd();
    }

    private static void printHeader() {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\objFile.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.setLength(100);

            stringBuilder.insert(0, "H" + Settings.startLabel.toUpperCase());
            stringBuilder.insert(7, "^" + Settings.standardMemoryLocation(Settings.startOperand));
            stringBuilder.insert(14, "^" + Settings.standardProgramLength());
            fileWriter.write(stringBuilder.toString().trim().toUpperCase() + System.lineSeparator());

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printText(List<Instruction> instructions) {
        extractInstructionsOpCodes(instructions);
        concatAllOpCodes();

        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\objFile.txt"), true);
            fileWriter.write("T" + Settings.standardMemoryLocation(Settings.startOperand));

            for (int i = 0; i < textRecordLines.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.setLength(100);

                stringBuilder.insert(7, "^" + hexLength(textRecordLines.get(i)));
                stringBuilder.insert(10, "^" + textRecordLines.get(i).toUpperCase());
                fileWriter.write(stringBuilder.toString().trim() + System.lineSeparator());
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printEnd() {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\objFile.txt"), true);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.setLength(100);

            stringBuilder.insert(0, "E" + Settings.standardMemoryLocation(Settings.startOperand));
            fileWriter.write(stringBuilder.toString().trim() + System.lineSeparator());

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractInstructionsOpCodes(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            boolean expression = instruction.mnemonic.format == MnemonicFormat.TWO ||
                    instruction.mnemonic.format == MnemonicFormat.FOUR ||
                    (instruction.isDirective && instruction.segments[1].equals("WORD") ||
                    (instruction.isDirective && instruction.segments[1].equals("BYTE")));

            if (expression) opCodes.add(instruction.opCode);
        }
    }
    private static void concatAllOpCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(60);

        for (String opCode : opCodes) {
            String line = stringBuilder.toString().trim();

            if (line.length() < 60 && (line.length() + opCode.length()) < 60) {
                stringBuilder.append(opCode);
            } else {
                textRecordLines.add(stringBuilder.toString().trim());

                stringBuilder = new StringBuilder();
                stringBuilder.setLength(60);

                stringBuilder.append(opCode);
            }
        }

        textRecordLines.add(stringBuilder.toString().trim());
    }

    private static String hexLength(String line){
        String hexLength = Integer.toHexString(line.length() / 2);

        StringBuilder stringBuilder = new StringBuilder(hexLength);
        while (stringBuilder.length() < 2) stringBuilder.insert(0, "0");

        return stringBuilder.toString().toUpperCase();
    }
}
