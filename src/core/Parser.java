package core;

import utils.Instruction.Instruction;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser
{
    public static boolean fixedFormat = false;

    public static Instruction parse(String line)
    {
        Instruction instruction = new Instruction();
        instruction.line = line;

        instruction.isComment = checkIfComment(line);

        if (!instruction.isComment) {
            instruction.segments = fixedFormat ?
                    diagnoseSegments_fixedFormat(line) :
                    diagnoseSegments_freeFormat(line);
        }

        return instruction;
    }

    private static boolean checkIfComment(String line)
    {
        return line.startsWith(".");
    }


    private static String[] diagnoseSegments_freeFormat(String line)
    {
        String[] segments = new String[3];

        String regex_BYTE$BLA = "((BYTE\\b)|(BLA\\b))(\\s+)((C'(.*)')|(X'(.*)')|(=C'(.*)'))";

        Pattern pattern = Pattern.compile(regex_BYTE$BLA);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {

            line = line.replaceAll(regex_BYTE$BLA, "");
            line = line.trim();

            segments[0] = line; // Label
            segments[1] = matcher.group(1); // Instruction
            segments[2] = matcher.group(5); // Operand

            return segments;
        }
        else {
            String[] slices = line.split("\\s+");

            if (slices.length == 2) {
                segments[0] = "";
                segments[1] = slices[0];
                segments[2] = slices[1];

                return segments;
            }

            return slices; // length = 3
        }
    }

    private static String[] diagnoseSegments_fixedFormat(String line)
    {
        String[] segments = new String[4];

        segments[0] = line.substring(0, 8).trim();
        segments[1] = line.substring(9, 16).trim();
        segments[2] = line.substring(17, 34).trim();
        segments[3] = line.substring(35, line.length() - 1).trim(); // comment

        return segments;
    }
}
