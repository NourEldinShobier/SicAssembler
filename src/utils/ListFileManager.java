package utils;

import core.LookupTables;
import core.Settings;
import utils.Instruction.Instruction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class ListFileManager {

    public static void printListFileHeader() {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\listFile.txt"));
            fileWriter.write("------------------------------------ OUTPUT -----------------------------------" + System.lineSeparator());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("      ADDRESS   LABEL    OPCODE  OPERAND   COMMENTS" + System.lineSeparator());
            fileWriter.write("      -------   -----    ------  -------   --------" + System.lineSeparator());
            fileWriter.write(System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printComment(String line) {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\listFile.txt"), true);
            fileWriter.write("      " + line + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printLine(Instruction instruction) {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\listFile.txt"), true);
            fileWriter.write((instruction.lineNumber + 1) +
                    ")    " +
                    instruction.standardMemoryLocationFormat().toUpperCase() +
                    "    " +
                    instruction.line +
                    System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printError(String error) {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\listFile.txt"), true);
            fileWriter.write("  ••••••• " + error + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printSymbolTable() {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("C:\\SICAssembler\\listFile.txt"), true);
            fileWriter.write(System.lineSeparator());
            fileWriter.write(System.lineSeparator());
            fileWriter.write("--------------------------------- SYMBOL TABLE --------------------------------" + System.lineSeparator());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("                             LABEL         ADDRESS " + System.lineSeparator());
            fileWriter.write("                             -----         -------" + System.lineSeparator());
            fileWriter.write(System.lineSeparator());

        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter finalFileWriter = fileWriter;
        LookupTables.symbolTable.forEach((key, value) -> {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.setLength(100);

                stringBuilder.insert(29, key.toUpperCase());
                stringBuilder.insert(43, Settings.standardMemoryLocation(value));

                finalFileWriter.write(stringBuilder.toString() + System.lineSeparator());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            fileWriter.write(System.lineSeparator());
            fileWriter.write("-------------------------------------------------------------------------------" + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
