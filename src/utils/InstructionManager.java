package utils;

import utils.Instruction.Instruction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class InstructionManager
{
    public static void generateListFile(List<Instruction> instructions)
    {
        try {
            FileWriter fileWriter = new FileWriter(new File("C:\\SICAssembler\\listFile.txt"));

            fileWriter.write("----------------------------------------------------------" + System.lineSeparator());
            fileWriter.write("Address          Label          Mnemonic          Operands" + System.lineSeparator());
            fileWriter.write("----------------------------------------------------------" + System.lineSeparator());

            instructions.forEach((instruction) -> {
                try {
                    if (!instruction.isComment){
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.setLength(100);

                        stringBuilder.insert(0, instruction.standardMemoryLocationFormat().toUpperCase());
                        stringBuilder.insert(17, instruction.segments[0].toUpperCase());
                        stringBuilder.insert(32, instruction.segments[1].toUpperCase());
                        stringBuilder.insert(50, instruction.segments[2].toUpperCase());

                        fileWriter.write(stringBuilder.toString().trim() + System.lineSeparator());
                    }
                    else {
                        fileWriter.write(instruction.line.trim() + System.lineSeparator());
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void generateOBJFile(List<Instruction> instructions)
    {

    }
}
