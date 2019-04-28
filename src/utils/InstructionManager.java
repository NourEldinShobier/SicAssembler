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

            fileWriter.write("----------------------------------------------------------\n");
            fileWriter.write("Address          Label          Mnemonic          Operands\n");
            fileWriter.write("----------------------------------------------------------\n");

            instructions.forEach((instruction) -> {
                try {
                    if (!instruction.isComment){
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.setLength(100);

                        stringBuilder.insert(0, instruction.standardMemoryLocationFormat().toUpperCase());
                        stringBuilder.insert(17, instruction.segments[0].toUpperCase());
                        stringBuilder.insert(32, instruction.segments[1].toUpperCase());
                        stringBuilder.insert(50, instruction.segments[2].toUpperCase());

                        fileWriter.write(stringBuilder.toString().trim() + "\n");
                    }
                    else {
                        fileWriter.write(instruction.line.trim() + "\n");
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
