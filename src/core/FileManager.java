package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileManager
{
    public static ArrayList<String> readFile()
    {

        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("\"C:\\Users\\latin\\Documents\\srcFile.txt\""));
            while (scanner.hasNextLine()) lines.add(scanner.nextLine());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
