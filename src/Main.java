import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    static String output = "";
    static int bruteForceComp = 0;
    static int horspoolComp = 0;
    static int boyerComp = 0;

    static int param = 0;


    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        String input = Files.readString(Path.of("src/sample1.html"));
        output = input;
        String key= "This";

        //bruteForce(input, key);
        badSymbol(key,input);
        //boyerMoore(input,key);

        File myObj = new File("src/output.html");
        myObj.createNewFile();
        FileWriter myWriter = new FileWriter("src/output.html");
        myWriter.write(output);
        myWriter.close();
        long endTime   = System.nanoTime();

        long totalTime = TimeUnit.NANOSECONDS.toMillis(endTime-startTime);
        //System.out.println(bruteForceComp);
        System.out.println(horspoolComp);
        //System.out.println(boyerComp);
        System.out.println(totalTime);
    }

    static void bruteForce(String text, String pattern){
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                bruteForceComp++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }

            if (j == m) {
                // pattern found at index i
                announceKey(i-1+param, m);
                param+=13;

            }
        }

    }

    static void announceKey(int i,int textLenght){
        output = output.substring(0,i+1) + "<mark>" + output.substring(i+1,i+textLenght+1) + "</mark>" + output.substring(i+textLenght+1);
    }



    public static ArrayList<Object> goodSuffixTable(String st){
        int length = st.length();
        int count = 0;
        String[] end_arr = new String[length+1];
        String[] beg_arr = new String[length+1];
        ArrayList<Object> suftable = new ArrayList<>();

        for(int i=0; i<length; i++){
            end_arr[i] = st.substring(length-i-1, length);
            beg_arr[i] = st.substring(0, i+1);
            for(int j=i; j>=0; j--){
                if((end_arr[i].charAt(j)+"").equals(beg_arr[i].charAt(j)+"")){
                    count++;
                }
                else{
                    break;
                }
            }
            if(count == 1 || count == 0){
                count = length;
            }
            else if(count == length){
                count = 1;
            }
            suftable.add(st.charAt(i)+": "+ count);
            count = 0;
        }
        return suftable;
    }

    public static void badSymbol(String pattern, String text) {
        int index = pattern.length() - 1;
        while (text.length() >= pattern.length()) {
            index += compare(pattern, text, index);
            if (index >= text.length()) {
                break;
            }
        }

    }


    public static int compare(String pattern, String text, int index) {
        if (text.substring(index - pattern.length() + 1, index + 1).equals(pattern)) {
            horspoolComp += pattern.length();
            announceKey(index-pattern.length()+param, pattern.length());
            param+=13;
            return 1;
        }
        else{
            for(int i = 0; i < pattern.length(); i++){
                horspoolComp++;
                if(text.charAt(index-i) != pattern.charAt(pattern.length()-i-1)){
                    break;
                }
               
            }
        }
        char mismatchedChar = text.charAt(index);
        System.out.println(" mismatch -> "+mismatchedChar);
        int shiftVal = shiftHorspool(mismatchedChar, pattern);
        return Math.max(1, shiftVal);
    }


    public static int shiftHorspool(char letter, String pattern) {
        int shiftval = pattern.length();

        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (letter == pattern.charAt(i) && (i != pattern.length() -1)) {
                return shiftval - i - 1;
            }
        }
        return shiftval;
    }

    public static ArrayList<Object> badSymbolTable(String st){
        int length = st.length();
        int index = 0;
        int formula = length-index-1;
        String ch;
        int k = 0;
        ArrayList<Object> table = new ArrayList<>();
        for(int i=0; i<length; i++){
            ch = st.charAt(index)+"";
            table.add(ch+": "+Math.max(1, formula));
            for (int j=0; j<k; j++){
                if ((table.get(k).toString().charAt(0)+"").equals(table.get(j).toString().charAt(0)+"")){
                    table.remove(j);
                    k--;
                    break;
                }
                continue;
            }
            k++;
            index++;
            formula--;
        }
        table.add("*: " + (length-1));
        return table;
    }



}