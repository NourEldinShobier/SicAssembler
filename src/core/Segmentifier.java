package core;

import utils.Instruction.Instruction;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Segmentifier
{
    public static Instruction segmentify(String line)
    {
        Instruction instruction = new Instruction();
        instruction.line = line;

        instruction.isComment = checkIfComment(line);

        if (!instruction.isComment)
            instruction.segments = Settings.isFixedFormat ?
                    fixedFormat(line) :
                    freeFormat(line);


        return instruction;
    }

    private static boolean checkIfComment(String line)
    {
        return line.trim().startsWith(".");
    }

    public static String[] freeFormat(String line)
    {
        line = line.trim();

        ArrayList<String> stage1 = new ArrayList<String>();
        ArrayList<String> stage2 = new ArrayList<String>();
        ArrayList<String> stage3 = new ArrayList<String>();
        ArrayList<String> stage4 = new ArrayList<String>();

        String regex_COMMENT = "(?=(\\.)(.*))";
        String regex_Literals = "(?=(=C'(.*)')|(=X'(.*)')|(=W'(.*)'))";
        String regex_CHAR$HEX = "(?=(C'(.*)')|(X'(.*)'))";
        String regex_Spaces = "\\s+";

        /*
         *  STEPS:
         *  1) Split by (.)
         *  2) Split by (=c'....' | =x'....') if doesnt with (.)
         *  3) Split by (c'..' | x'..' ) if doesnt start with previous cases
         *  4) Split by (spaces) if doesnt start with previous cases
         *
         *  5) Exports all segments
         */


        // 1) Split by (.)
        stage1.addAll(Arrays.asList(line.split(regex_COMMENT)));

        // 2) Split by (=c'....' | =x'....') if doesnt with (.)
        for (String slice : stage1) {

            if (!slice.startsWith(".")) {
                stage2.addAll(Arrays.asList(slice.split(regex_Literals)));
                continue;
            }

            stage2.add(slice);
        }

        // 3) Split by (c'..' | x'..' ) if doesnt start with previous cases
        for (String slice : stage2) {

            if (!slice.startsWith(".") && !slice.startsWith("=C'") && !slice.startsWith("=X'")) {
                stage3.addAll(Arrays.asList(slice.split(regex_CHAR$HEX)));
                continue;
            }

            stage3.add(slice);
        }

        // 4) Split by (spaces) if doesnt start with previous cases
        for (String slice : stage3) {

            if (!slice.startsWith(".") &&
                    !slice.startsWith("C'") &&
                    !slice.startsWith("X'") &&
                    !slice.startsWith("=X'") &&
                    !slice.startsWith("=C'")) {

                stage4.addAll(Arrays.asList(slice.split(regex_Spaces)));
                continue;
            }

            stage4.add(slice);
        }

        // Trim all segments
        for (int counter = 0; counter < stage4.size(); counter++)
            stage4.set(counter, stage4.get(counter).trim());


        return stage4.toArray(new String[0]);
    }

    private static String[] fixedFormat(String line)
    {
        StringBuilder stringBuilder = new StringBuilder(line);
        while (stringBuilder.length() < 66) stringBuilder.append(" ");
        line =  stringBuilder.toString();

        String[] segments = {"", "", "", ""};

        segments[0] = line.substring(0, 9);
        segments[1] = line.substring(9, 17);
        segments[2] = line.substring(17, 35);
        segments[3] = line.substring(35, line.length());

        return segments;
    }
}
