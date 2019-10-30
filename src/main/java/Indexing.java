import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Indexing {
    Map<String,ArrayList<Location>> map = new HashMap<String,ArrayList<Location>>();
    ArrayList vocab = new ArrayList();

    public Indexing() throws FileNotFoundException {
        Crawling crawling= new Crawling();
        vocab.addAll(crawling.Preprocessing());
        map.putAll(crawling.map);
    }
    public void To_Index(){
        System.out.println("The file was read. indexing information, please wait...");
        for(Map.Entry<String, ArrayList<Location>> entry :map.entrySet()) {

            //   System.out.print(count + "- " + entry.getKey() + " :");
            if (!(entry.getKey().equals("")||entry.getKey().equals(" ")))
            {
                String value;
                value =  entry.getKey() + ",";
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Location location = entry.getValue().get(i);
                    //   System.out.print(location.fileno + "|" + location.artNo + "|" + location.pos + ",");
                    value = value.concat(location.fileno + "|" + location.artNo + "|" + location.pos + ";");
                    // to seeach the word in Article and save it with ArticleNo

                }
                writeDataLineByLine("D:/index.txt", value);
    }
}
    }
    public void writeDataLineByLine(String filePath, String value) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            BufferedWriter bw = new BufferedWriter(outputfile);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(value);
            pw.flush();
            pw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
