package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSVFile {
    // read meta data from csv
    public void ReadDataFromCSV(String filepath){
        String line = "";
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            String[] StrTitle = lines.get(0).split(",");
            String[] StrData = lines.get(1).split(",");
            System.out.println("file has " + (lines.size() - 1) + " Articles");
            System.out.println("1st article Info : ");
            for (int j = 0; j <= lines.size(); j++) {

                System.out.println(StrTitle[j] + " : " + StrData[j]);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        System.out.println("File Complete");
    }
}
