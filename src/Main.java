import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static String output = "";
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("src/input.html"));
        output = input;
        String key= "AT_THAT";

        int keyIndex=0;
        for (int i=0;i<input.length();i++){

            if (input.charAt(i) == key.charAt(keyIndex)){
                if (keyIndex == key.length()-1){
                    announceKey(i-keyIndex-1, key.length());
                    keyIndex=0;
                }else
                    keyIndex++;
            } else {
                keyIndex=0;
            }
        }


        File myObj = new File("src/output.html");
        myObj.createNewFile();
        FileWriter myWriter = new FileWriter("src/output.html");
        myWriter.write(output);
        myWriter.close();
    }

    static void announceKey(int i,int textLenght){
        output = output.substring(0,i+1) + "<mark>" + output.substring(i+1,i+textLenght+1) + "</mark>" + output.substring(i+textLenght+1);
        System.out.println(output);
    }
}