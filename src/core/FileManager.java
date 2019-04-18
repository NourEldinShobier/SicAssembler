package core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class FileManager
{
    public static ArrayList<String> readFile()
    {
        Scanner scan;
        ArrayList<String> lines = new ArrayList<>();

        try {
            scan = new Scanner(new File("C:\\SICAssembler\\srcFile.txt"));
            while (scan.hasNextLine()) lines.add(scan.nextLine());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
