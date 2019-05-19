package core;

public class Settings {
    public static boolean isFixedFormat = false;
    public static String filePath = "C:\\SICAssembler\\srcFile.txt";

    public static String startLabel = "";
    public static String startOperand = "";
    public static String ltorg = "";

    public static int programLength = 0;

    public static String standardMemoryLocation(String address) {
        StringBuilder stringBuilder = new StringBuilder(address);
        while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

        if (stringBuilder.toString().toUpperCase().startsWith("A") ||
                stringBuilder.toString().toUpperCase().startsWith("B") ||
                stringBuilder.toString().toUpperCase().startsWith("C") ||
                stringBuilder.toString().toUpperCase().startsWith("D") ||
                stringBuilder.toString().toUpperCase().startsWith("E") ||
                stringBuilder.toString().toUpperCase().startsWith("F")) {

            stringBuilder.insert(0, "0");
        }

        return stringBuilder.toString().toUpperCase();
    }

    public static String standardProgramLength() {
        String hexLength = Integer.toHexString(programLength - 1);

        StringBuilder stringBuilder = new StringBuilder(hexLength);
        while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }
}
