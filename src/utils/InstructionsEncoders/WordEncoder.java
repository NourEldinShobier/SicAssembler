package utils.InstructionsEncoders;

import utils.Instruction.Instruction;

import java.util.Arrays;

public class WordEncoder {
    public static Instruction encode(Instruction instruction){
        String [] nums = instruction.segments[2].split(",");

        for (int i = 0; i < nums.length; i++) {
            int intNum = Integer.parseInt(nums[i]);
            String hexNum = Integer.toHexString(intNum);
            nums[i] = standardWord(hexNum);
        }

        String result = Arrays.toString(nums);

        result = result.replace(",","");
        result = result.replace(" ", "");
        result = result.replace("[","");
        result = result.replace("]","");

        instruction.opCode = result.toUpperCase();
        return instruction;
    }

    private static String standardWord(String hexNum){
        StringBuilder stringBuilder = new StringBuilder(hexNum);
        while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }
}
