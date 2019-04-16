package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileManager
{
    public static ArrayList<String> readFile(){
    BufferedReader input;
    try{
        input = new BufferedReader(new FileReader("\"C:\\Users\\latin\\Documents\\srcFile.txt\""));
        String line = input.readLine();
        ArrayList<String> lines = new ArrayList<>();
        while(line!=null){
            System.out.println(line);
            //read next line
            line = input.readLine();
            lines.add(line);
        }
        input.close();
        return lines;
    }catch(IOException e){
        e.printStackTrace();
        return null;
    }

    }
}
