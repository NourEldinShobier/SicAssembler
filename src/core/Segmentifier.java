package core;

import utils.Instruction.Instruction;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Segmentifier
{
    public static boolean fixedFormat = false;

    public static Instruction segmentify(String line)
    {
        Instruction instruction = new Instruction();
        instruction.line = line;

        instruction.isComment = checkIfComment(line);

        if (!instruction.isComment)
            instruction.segments = fixedFormat ?
                    fixedFormat(line) :
                    freeFormat(line);


        return instruction;
    }

    private static boolean checkIfComment(String line)
    {
        return line.startsWith(".");
    }

    private static String[] freeFormat(String line)
    {

        ArrayList<String> stage1 = new ArrayList<String>();
        ArrayList<String> stage2 = new ArrayList<String>();
        ArrayList<String> stage3 = new ArrayList<String>();


        String regex_COMMENT = "(?=(\\.)(.*))";
        String regex_Literals = "(?=(C'(.*)')|(X'(.*)')|(=C'(.*)')|(=X'(.*)'))";
        String regex_Spaces = "\\s+";

        /*
         *  STEPS:
         *  1) Split by (.)
         *  2) Split by (c'..' | x'..' | =c'....' | =x'....') if doesnt start with (.)
         *  3) Split by (spaces) if doesnt start with previous cases
         *  4) Exports all segments
         */


        stage1.addAll(Arrays.asList(line.split(regex_COMMENT)));

        for (String slice : stage1) {

            if (!slice.startsWith(".")) {
                stage2.addAll(Arrays.asList(slice.split(regex_Literals)));
                continue;
            }

            stage2.add(slice);
        }

        for (String slice : stage2) {

            if (!slice.startsWith(".") &&
                    !slice.startsWith("C'") &&
                    !slice.startsWith("X'") &&
                    !slice.startsWith("=X'") &&
                    !slice.startsWith("=C'")) {

                stage3.addAll(Arrays.asList(slice.split(regex_Spaces)));
                continue;
            }

            stage3.add(slice);
        }

        for (int counter = 0; counter < stage3.size(); counter++)
            stage3.set(counter, stage3.get(counter).trim());


        return stage3.toArray(new String[0]);
    }

    private static String[] fixedFormat(String line)
    {
        String[] segments = new String[4];

        segments[0] = line.substring(0, 8).trim();
        segments[1] = line.substring(9, 16).trim();
        segments[2] = line.substring(17, 34).trim();
        segments[3] = line.substring(35, line.length() - 1).trim(); // comment

        return segments;
    }
}
