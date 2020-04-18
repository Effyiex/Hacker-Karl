package cf.effectcode.game.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cf.effectcode.game.render.Sprite;

public class FileHandler {

	public static String[] readTextFile(String name) {
		ArrayList<String> lines = new ArrayList<String>();
        String fileName = Sprite.getCWD() + "\\assets\\" + name + ".txt";
        String line = null;
        try {
            FileReader fileReader =  new FileReader(fileName);
            BufferedReader bufferedReader =   new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();         
        }catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" +  fileName + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        String[] output = new String[lines.size()];
        for(int i = 0; i < lines.size(); i++) output[i] = lines.get(i);
        return output;
	}
	
	public static void writeIntoTextFile(String name, String[] input) {
        String fileName = Sprite.getCWD() + "\\assets\\" + name + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(String s : input) {
            	bufferedWriter.write(s);
            }

            bufferedWriter.close();
        }catch(IOException ex) {
            System.out.println("Error writing to file '"+ fileName + "'");
        }
	}
	
}
