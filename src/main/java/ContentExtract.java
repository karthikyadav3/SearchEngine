import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ContentExtract {

    public static void main(String[] args){
        Scanner scanner;
        Map<Integer, String> map_data = new HashMap<>();
        Map<Integer, String> map_url = new HashMap<>();

        {
            try {
                scanner = new Scanner(new File("C:/Users/chirag/Documents/Ushma/webProjectNew/articles/NYTDataset-updated.csv"));
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String useDelimiter = "\\|";
                    String[] Col = scanner.nextLine().split(useDelimiter);
                    map_data.put(Integer.parseInt(Col[0].replaceAll("\"","")), Col[4]+" "+Col[3]);
                    map_url.put(Integer.parseInt(Col[0].replaceAll("\"","")), Col[1]);
                    System.out.println(map_data.get(Integer.parseInt(Col[0].replaceAll("\"",""))));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
