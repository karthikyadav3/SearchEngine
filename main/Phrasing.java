package main;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.*;
import java.util.*;

public class Phrasing {
    public static Map<String, ArrayList<Location>> map = new HashMap<String, ArrayList<Location>>();
    private static ArrayList<String> vocabList = new ArrayList<String>();
    private static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
    private static String lowerCase;
    private static String stemWord;
    static String Query1="make";
    static String Query2="America";
    static String Query3="great";
    static String Query4="again?";
    public static String CleanStr(String str) {
        String text1 = str.replaceAll("[!”#$%&’()*+,-/:;<=>?@[\\]^_`{|}~]] ", " "). replaceAll("\'", "")
                .replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/","").replaceAll("[\\[\\](){}]","");
        String txt = text1.trim().replaceAll("\\s+", " ");
        return  txt;
    }
    public static void Preprocessing() {
        try {
            Scanner scanner = new Scanner(new File("C:/Users/chirag/Documents/Ushma/webProjectNew/articles/ArticlesData.csv"));
            // Scanner scanner = new Scanner(sentence);
            //Set the delimiter used in file
            String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";
            PorterStemmer stemmer = new PorterStemmer();

            String tokens_data = "";
            int pos = 0;
            int artNo = 0;
            while (scanner.hasNextLine()) {
                artNo++;
                String line = scanner.nextLine();
                String line_Replace = CleanStr(line);
                // System.out.println(line_Replace);
                String[] tokens = line_Replace.split(useDelimiter);
                tokens_data += line;


                for (String token : tokens) {

                    pos++;


                    lowerCase = token.toLowerCase();
                    lowerCase = lowerCase.replaceAll("\n", "");

                    if (Arrays.asList(stopwords).contains(lowerCase)) {
                        //System.out.println("It is a StopWord");
                    } else {
                        stemmer.setCurrent(lowerCase);
                        stemmer.stem();
                        stemWord = stemmer.getCurrent();

                        if (stemWord == "" || stemWord.contains("https")) {


                        } else {

                            vocabList.add(stemWord);
                            Location loc = new Location(0, artNo, pos);
                            if (map.containsKey(stemWord)) {
                                ArrayList<Location> loc_arr = map.get(stemWord);
                                loc_arr.add(loc);
                                map.replace(stemWord, loc_arr);
                            } else {
                                ArrayList<Location> loc_arr = new ArrayList<>();
                                loc_arr.add(loc);
                                map.put(stemWord, loc_arr);
                            }
                        }
                    }
                }
            }
            scanner.close();
//        BufferedWriter writer = new BufferedWriter(new FileWriter("d:/All_Data.txt"));// save string all data of articles in txt file for next step Tokens
//        writer.write(tokens_data);
//        writer.close();
            String [] g=tokens_data.split("");
            System.out.println("Number of all tokens in Articles: " + g.length);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static  void indexing() throws Exception {
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(Query1.toLowerCase());
        Query1=stemmer.getCurrent();
        PorterStemmer stemmer1 = new PorterStemmer();
        stemmer1.setCurrent(Query2.toLowerCase());
        Query2=stemmer1.getCurrent();
        PorterStemmer stemmer2 = new PorterStemmer();
        stemmer2.setCurrent(Query3.toLowerCase());
        Query3=stemmer2.getCurrent();
        PorterStemmer stemmer4 = new PorterStemmer();
        stemmer4.setCurrent(Query4.toLowerCase());
        Query4=stemmer4.getCurrent();

        ArrayList<Location> loc_arr = new ArrayList<>();
        ArrayList<Location> loc_arr1 = new ArrayList<>();
        ArrayList<Location> loc_arr2 = new ArrayList<>();
        ArrayList<Location> loc_arr3 = new ArrayList<>();
        //stemmerOne.stem();
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
//        PrintStream fileOut = new PrintStream("./Assignment7.txt");
//        System.setOut(fileOut);
        System.out.println("make America great again");
        System.out.println("Term , ( ArticleNo | Position .....)");

        for(Map.Entry<String, ArrayList<Location>> entry :map.entrySet()) {

            //   System.out.print(count + "- " + entry.getKey() + " :");
            if (!(entry.getKey().equals("")||entry.getKey().equals(" ")))

                if ((entry.getKey().equals(Query1))){
                String value;
                value =  entry.getKey() + ",";
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Location location = entry.getValue().get(i);
                    loc_arr.add(location);
                    //   System.out.print(location.fileno + "|" + location.artNo + "|" + location.pos + ",");
                 value = value.concat( location.artNo + "|" + location.pos + ";");
                    // to seeach the word in Article and save it with ArticleNo

                }
//                writeDataLineByLine("D:/index.txt", value);
                    System.out.println(value);
            }}
        for(Map.Entry<String, ArrayList<Location>> entry :map.entrySet()) {

            //   System.out.print(count + "- " + entry.getKey() + " :");
            if (!(entry.getKey().equals("")||entry.getKey().equals(" ")))
         if(entry.getKey().equals(Query2)){
                String value;
                value =  entry.getKey() + ",";
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Location location1 = entry.getValue().get(i);
                    loc_arr1.add(location1);
                    //   System.out.print(location.fileno + "|" + location.artNo + "|" + location.pos + ",");
                     value = value.concat( location1.artNo + "|" + location1.pos + ";");
                    // to seeach the word in Article and save it with ArticleNo

                }
                    System.out.println(value);}

        }
        for(Map.Entry<String, ArrayList<Location>> entry :map.entrySet()) {

            //   System.out.print(count + "- " + entry.getKey() + " :");
            if (!(entry.getKey().equals("")||entry.getKey().equals(" ")))

                if ((entry.getKey().equals(Query3))){
                    String value;
                    value =  entry.getKey() + ",";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        Location location2 = entry.getValue().get(i);
                        loc_arr2.add(location2);
                        //   System.out.print(location.fileno + "|" + location.artNo + "|" + location.pos + ",");
                        value = value.concat( location2.artNo + "|" + location2.pos + ";");
                        // to seeach the word in Article and save it with ArticleNo

                    }
//                writeDataLineByLine("D:/index.txt", value);
                    System.out.println(value);
                }}
        for(Map.Entry<String, ArrayList<Location>> entry :map.entrySet()) {

            //   System.out.print(count + "- " + entry.getKey() + " :");
            if (!(entry.getKey().equals("")||entry.getKey().equals(" ")))

                if ((entry.getKey().equals("again"))){
                    String value;
                    value =  entry.getKey() + ",";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        Location location3 = entry.getValue().get(i);
                        loc_arr3.add(location3);
                        //   System.out.print(location.fileno + "|" + location.artNo + "|" + location.pos + ",");
                        value = value.concat( location3.artNo + "|" + location3.pos + ";");
                        // to seeach the word in Article and save it with ArticleNo

                    }
//                writeDataLineByLine("D:/index.txt", value);
                    System.out.println(value);
                }}

        File urlfile = new File("C:/Users/chirag/Documents/Ushma/webProjectNew/articles/ArticlesURLS.csv");
        ArrayList<Integer> list=new ArrayList();
        for (Location loc:loc_arr1
        ) {
            for (Location loc1:loc_arr
            ) {
                if(((loc.artNo)==(loc1.artNo))&&((loc.pos-1)==(loc1.pos)))
                { list.add(loc.artNo);
              //  System.out.println(loc.artNo);
                    //System.out.println(/*entry.getKey()+ ","+ + entry.getValue() +"," + */FileUtils.readLines(urlfile).get(loc.artNo));
               }
            }

        }

   /*     ArrayList<Integer>l=new ArrayList<>();
//        l.add(164);
//        l.add(312);
        l.add(664);
        l.add(826);
        l.add(853);
        l.add(939);
        l.add(1483);*/
        WriteCSVForTokens(list);
/*        for (Location loc:loc_arr1
             ) {
            for (Location loc1:loc_arr
                 ) {
                for (Location loc2:loc_arr2
                ) {
                    for (Location loc3:loc_arr3
                    ) {
                if(((loc.artNo)==(loc1.artNo))&&((loc2.artNo)==(loc3.artNo))&&((loc.pos-1)==(loc1.pos))&&((loc2.pos-1)==(loc3.pos)))
                   list.add(loc.artNo);
            }

        }
       WriteCSVForTokens(list);
    }}*/}
    public static void WriteCSVForTokens(ArrayList<Integer> l){
        long startTime= System.currentTimeMillis();
       try {

           PrintWriter out = new PrintWriter(new FileWriter("output_Assignment8.txt"));

           BufferedReader br= new BufferedReader(new FileReader("C:/Users/chirag/Documents/Ushma/webProjectNew/articles/ArticlesData.csv"));
           String line="";
           String [] col = new String[0];
           while ((line=br.readLine())!=null){
               col=line.split(",");
               if(l.contains(Integer.valueOf(col[0])))
               {System.out.println(col[0] +" : "+col[1]);
                  }


               //System.out.println(col[0]);

           }


       }catch (Exception i){
           System.out.println(i.getMessage());
       }
                long stopTime= System.currentTimeMillis();
       // System.out.println("Execution time for write data for tokens in CSV in milliseconds:"+ (stopTime - startTime));

    }
    public static void main(String[] args) {

        try {
            Preprocessing();
            indexing();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
