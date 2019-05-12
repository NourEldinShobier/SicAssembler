package core;

import java.util.Map;
import static java.util.Map.entry;

public class Registers
{
    private static Map<String, String> registers = Map.ofEntries(
            entry("A", "0000"),
            entry("X", "0001"),
            entry("L", "0010"),
            entry("PC", "1000"),
            entry("SW", "1001"),

            entry("B", "0011"),
            entry("S", "0100"),
            entry("T", "0101"),
            entry("F", "0110")
    );

    public static String get(String registerName){
        return registers.get(registerName);
    }
}
