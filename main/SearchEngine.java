package main;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
//import org.tartarus.snowball.ext.PorterStemmer;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import opennlp.tools.stemmer.PorterStemmer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchEngine {
        // create data members
        private static  FileWriter write_articels_CSV;
        private static  File file=null;
        private static  CSVWriter writer=null;
        private static  String data2Token=null;
        private static final String ariclesUELs="d:/ArticlesURLS.csv";
        private static final String articles_data="d:/ArticlesData.csv";
        private static final String tokens_data="d:/Data_To_Tokens.txt";
        private static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
        String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";





    // save all articles data in csv file with structure and string in txt for tokens
    public static void Write_Articles_In_CSV( Set<Articles> article)throws Exception {
        long startTime= System.currentTimeMillis();
        write_articels_CSV=new FileWriter(new File(articles_data),true);// save all articles data in csv file
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(tokens_data));// save string all data of articles in txt file for next step Tokens
        writer=new CSVWriter(write_articels_CSV);
  /*      if(!(new File(articles_data).exists()))//check if file already exit
        {   String[] header = { "article id", "article url","article headline","article intro","article text","publication timestamp","article authors","article categories" };
            for (Articles a:article) { data2Token+=a.getAritcle_Title()+" "+a.getAritcle_SubTilte()+" "+a.getAritcle_Text()+" "+a.getAritcle_Date()+" "+a.getAritcle_Author()+" "+a.getAritcle_Category();}
            writer.writeNext(header);}*/
        for (Articles a:article) {
//            if(!(Is_Exit_Data(a.getAritcle_URL(),articles_data,1)))
            {
                String [] data1={String.valueOf(a.getAritcle_Id()),a.getAritcle_URL(),a.getAritcle_Title(),(a.getAritcle_SubTilte())
                        ,(a.getAritcle_Text()),a.getAritcle_Date(),a.getAritcle_Author(),a.getAritcle_Category()};
                writer.writeNext(data1);
                // read string data for tokens ... not all data for tokens

                data2Token+=a.getAritcle_Title()+" "+a.getAritcle_SubTilte()+" "+a.getAritcle_Text()+" "+a.getAritcle_Date()+" "+a.getAritcle_Author()+" "+a.getAritcle_Category();
            }
            // read string data for tokens ... not all data for tokens
            data2Token+=a.getAritcle_Title()+" "+a.getAritcle_SubTilte()+" "+a.getAritcle_Text()+" "+a.getAritcle_Date()+" "+a.getAritcle_Author()+" "+a.getAritcle_Category();
        }

        writer1.write(data2Token);
        writer1.close();
        writer.flush();
        writer.close();
        long stopTime= System.currentTimeMillis();
        System.out.println("Execution time for save articles data  in CSV and string in txt in milliseconds:"+ (stopTime - startTime)); }



    // read txt for tokens
    public static void Read_Txt_To_Tokens()throws Exception {
        // to read all data from txt which i will tokens and saved in string
        long startTime= System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader(new File(tokens_data)));
        String st;

        while ((st = br.readLine()) != null)
            if(data2Token.isEmpty()){
                data2Token+=st; }

        long stopTime= System.currentTimeMillis();
        System.out.println("Execution time for read data from txt for tokens in milliseconds:"+ (stopTime - startTime));}


    // check if data exist in file to append
    public static boolean Is_Exit_Data(String str,String path,int id)throws Exception{
        String line="";
        boolean check=false;
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] cols = line.split(",");
            if(cols[id].contains(str)){check=true;}
        }return check;}


    public static  void BolRet(ArrayList<String> term) throws Exception { // term[0] = TRUMP, term[1] = AND, term[2] = PUTIN
        Map<String,ArrayList<Location>> map = new HashMap<String,ArrayList<Location>>();
        PorterStemmer stemmer = new PorterStemmer();

        String lowerCasezero = term.get(0).toLowerCase();
        String lowerCaseone = term.get(2).toLowerCase();


        // get all terms from all the URL's body
        Crawling crawler= new Crawling();
        crawler.Preprocessing();
        map.putAll(crawler.map);

        List listzero = new ArrayList();

        for(int i=0;i<map.get(new Crawling().Stemmer(lowerCasezero)).size();i++){

            Location location = map.get(new Crawling().Stemmer(lowerCasezero)).get(i);
            listzero.add(location.artNo);

        }

        List listone = new ArrayList();

        for(int i=0;i<map.get(new Crawling().Stemmer(lowerCaseone)).size();i++){

            Location location = map.get(new Crawling().Stemmer(lowerCaseone)).get(i);
            listone.add(location.artNo);

        }
        // store unique Values for ListZero and ListOne
        Set uniqueLsitZero = new HashSet<>(listzero);
        Set uniqueLsitOne = new HashSet<>(listone);
        System.out.println(uniqueLsitZero.size());
        System.out.println(uniqueLsitOne.size());
        List listRes = new ArrayList();
        Set listResAND = null;
        if(term.get(1) == "AND"){
            listone.retainAll(listzero);
            listResAND =new HashSet(listone);
        }
        else if(term.get(1) == "OR"){
            listone.addAll(listzero);
            listResAND =new HashSet(listone);
        }
        else if(term.get(1) == "NOT"){
            listzero.removeAll(listone);
            listResAND =new HashSet(listzero);
        }

//        for(int i=0;i<listResAND.size();i++){
//            System.out.println("size: "+listResAND.size());
//
//            System.out.println(listResAND);
//        }
        Set uniqueLsitZer = new HashSet<>(listzero);
        Set uniqueLsitOn = new HashSet<>(listone);
        System.out.println(uniqueLsitZer);
        System.out.println(uniqueLsitOn);
        System.out.println(listResAND);
        WriteCSVForTokens(listResAND);

        // from the article numbers in the list extract all the URLs from ArticlesData.csv file
        // first read ArticlesData.csv file in to an array
        // loop through the list
        // for every article number in the list get URL from the ArticlesData.csv
    }

    // main method
    public static void main(String[] args) {
        try {
//---------------------------------------Libraries require --------------------------------------------------------
//            1. Jsoup
//            2. lucence-snowball
//            3.opencsv
            /*if (args.length == 0) {
                System.out.println("Please enter the ArticleData.csv file path and Query");
            } else if (args.length == 1){
                System.out.println("Please enter the Query");
            }*/
            System.out.println("Query="+args[0]);
            String fileName = "../../../articles/NYTDataset-updated.csv";//args[0];
            ArrayList<String> Query_input = new ArrayList<>();
            ArrayList<String> Query_resolved = new ArrayList<>();
            for (int i=0; i<args.length;i++) {
                Query_input.add(args[i]);
            }
            int phrase = 0;
            int bol = 0;
            if(Query_input.get(0).startsWith("\""))
            {
                phrase = 1;

            }else if ((Query_input.get(1).equals("AND")) || (Query_input.get(1).equals("OR")) || ((Query_input.get(1).equals("NOT")))){
                bol = 1;
            }
            System.out.println("Query="+bol);
            String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";
            for (int i=0; i<Query_input.size();i++){
                String[] temp = Query_input.get(i).replaceAll("\"","").split(useDelimiter);
                for(int j=0; j<temp.length;j++){
                    Query_resolved.add(temp[j]);
                }
                System.out.println("Query="+Query_resolved.get(i));
            }
//--------------------------------//Assignment 1 ": Crawling"---------------------------------------------------------------

  /*      //    crawling articles from Deutsche Welle
            System.out.println("Assignment 1: Crawling");
            Crawling crawler= new Crawling(new URL("https://www.dw.com/search/?languageCode=en&contentType=ARTICLE&searchNavigationId=9097&from=02.05.2019&to=22.05.2019&sort=DATE&resultsCounter=1000"));
            // write all URLS in csv file "ArticlesURLS.csv"
              crawler.WritePageURLS(ariclesUELs);
          // crawl all data from csv file
           crawler.ReadURLs(ariclesUELs);
          //save all crawling data in csv file "ArticlesData.csv"
           Write_Articles_In_CSV(crawler.article);*/

//-----------------------------------------------------------------------------------------------


//-----------------------------------//Assignment 2 ": Tokens and Stemming"------------------------------------------------------------

       /*     System.out.println("Assignment 2: Tokens and Stemming");
            Token_Stem token_stem=new Token_Stem();
            token_stem.TokenAndStem();*/

//-----------------------------------------------------------------------------------------------

//------------------------------------ //Assignment 3 ": Indexing"-----------------------------------------------------------

         /*   System.out.println("Assignment 3: Indexing");
            Indexing indexing=new Indexing();
            indexing.To_Index();*/

//-----------------------------------------------------------------------------------------------

//------------------------------------ //Assignment 4 ": Compression"-----------------------------------------------------------

         /*   System.out.println("Assignment 4: Compression");
            // We do not Implement yet*/

//-----------------------------------------------------------------------------------------------

//---------------------------------------//Assignment 5 ": Boolean Retrieval"--------------------------------------------------------

       //     System.out.println("Assignment 5 : Boolean Retrieval");
            //String str[]={"TRUMP","AND","PUTIN"};
            //String str1[]={"Washington","OR","Berlin"};
            //String str2[]={"Germany","NOT","Merkel"};
            //System.out.println("Trump AND Putin");
            if(bol == 1) {
                System.out.println("call bol");
                BolRetnew(Query_resolved, fileName);
            }
            //System.out.println("Washington OR Berlin");
            //BolRet(str1);
            //System.out.println("Germany NOT Merkel");
            //BolRet(str2);*/

//-----------------------------------------------------------------------------------------------


//------------------------------------ //Assignment 6 ": Boolean Retrieval"-----------------------------------------------------------

/*            System.out.println("Assignment 6: Boolean Retrieval");
            ArrayList<String> QueryAssignment6 = new ArrayList<String>();
            // query 1
            QueryAssignment6.add("Germany");
            Query(QueryAssignment6);
            //query 2
            QueryAssignment6.add("Tropical"); // this can be even argument,
            QueryAssignment6.add("Fish");
            Query(QueryAssignment6);

            //query 3
            QueryAssignment6.add("Mexico");
            QueryAssignment6.add("Refugees");
            QueryAssignment6.add("Wall");
            Query(QueryAssignment6);*/

//-----------------------------------------------------------------------------------------------

//------------------------------------ //Assignment 7 ": Snippet Generation"-----------------------------------------------------------

            //System.out.println("Assignment 7: Snippet Generation");

            ArrayList<String> QueryAssignment7 = new ArrayList<String>();
            // query 1
            if((phrase == 0)&&(bol == 0)) {
                for (int i=0; i <Query_resolved.size();i++){
                    QueryAssignment7.add(Query_resolved.get(i));
                }
                //QueryAssignment7.add("G20");
                //QueryAssignment7.add("Xi");
                //QueryAssignment7.add("Trump");
                //QueryAssignment7.add("Meeting");
                Query(QueryAssignment7,fileName);
            }
//-----------------------------------------------------------------------------------------------

//------------------------------------ //Assignment 8 ": Phrase Queries"-----------------------------------------------------------

            //System.out.println("Assignment 8: Phrase Queries");
            ArrayList<String> QueryAssignment8 = new ArrayList<String>();
            // query 1
            //QueryAssignment8.add("Commission");
            //QueryAssignment8.add("President");
            //QueryAssignment7.add("three");
            if((phrase == 1)&&(bol == 0)) {
                for (int i = 0; i < Query_resolved.size(); i++) {
                    QueryAssignment8.add(Query_resolved.get(i));
                }
                Phrase(QueryAssignment8,fileName);
            }
          /*Phrasing phrasing =new Phrasing();
          phrasing.Preprocessing();
          phrasing.indexing();*/


//-----------------------------------------------------------------------------------------------

//------------------------------------ Last presentation: Evaluation (Graded) -----------------------------------------------------------

//Prof will ask us to enter some quries as inuput from (Consol or UI(simpe UI)):
//            And Grade Base on :
//            1.Speed for Query Processing
//            2. Look and Feel (implemented features)
//            3. Quality of Search Results
//                    >>Quries that should be used<<
//                    – NDCG for queries of this kind:
//            a) Merkel NOT Trump
//            b) “fridays for future”
//            c) San Francisco
//            d) “Commission President”
//            e) apple tree
//            f) G20 Xi Trump meeting
//            g) “to be or not to be”
//            h) Mexico USA wall border
//            i) US-China trade war
//            j) Japan whaling

//-----------------------------------------------------------------------------------------------




            } catch (Exception e){ System.out.println(e.getMessage());}
    }



    public static void BolRetnew(ArrayList<String> query, String fileName)
    {
        Scanner scanner;
        Map<Integer, String> map_data = new HashMap<>();
        Map<Integer, String> map_head = new HashMap<>();
        Map<Integer, String> map_url = new HashMap<>();

        {
            try {
                System.out.println("check file="+fileName);
                scanner = new Scanner(new File(fileName));
                System.out.println("file works");
                //System.out.println(scanner.hasNext());
                scanner.nextLine();
                int i = 0;
                while (scanner.hasNextLine()) {
                    String useDelimiter = "\\|";
                    String[] Col = scanner.nextLine().split(useDelimiter);
                    map_data.put(i, Col[4]+" "+Col[3]);
                    map_head.put(i, Col[4]);
                    //map_head.put(Integer.parseInt(Col[0].replaceAll("\"","")), Col[4]);
                    map_url.put(i, Col[1]);
                    i++;
                    //System.out.println(map_data.get(i));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        ArrayList vocab = new ArrayList();
        Map<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();
        Map<String, Integer> map_c = new HashMap<String, Integer>(); // total number of times a word appears in all the crowled dcuments
        Map<Integer, Integer> map_d = new HashMap<Integer,Integer>(); // stores |D| i.e total number of tokens in the each Document

        String lowerCase,lowerCaseOne,lowerCaseTwo;
        String stemWord;
        String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two"/*, "un"*/, "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon"/*, "us"*/, "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};


        int pos = 0;
        int artNo = 0;
        int docNo=0;
        PorterStemmer stemmer = new PorterStemmer();
        String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";



        for(int i =0; i<map_data.size();i++) {

            String[] tokens = map_data.get(i).split(useDelimiter);


            for (String token:tokens)
            {

                pos++;

                // map that stores article number and the total number of tokens in each article or token count.
                if (map_d.containsKey(artNo)) {
                    int art_word_count = map_d.get(artNo);
                    art_word_count++;
                    map_d.replace(artNo, art_word_count); // increment the token count for each article
                } else {
                    map_d.put(artNo, 1); // it will come to this loop only for the first time for each article
                }

                lowerCase = token.toLowerCase();
                //lowerCase = (Objects.equals(token, new String("US")))?token:token.toLowerCase();
                //if((Objects.equals(token, new String("US")))){
                //    System.out.println(lowerCase);
                //}
                lowerCaseOne=lowerCase.replaceAll("\n","");
                lowerCaseTwo = lowerCaseOne.replaceAll("[!”#$%&’()—*+,-/:;<=>?@[\\]^_`{|}~]]", "").replaceAll("\'", "").replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/", "").replaceAll("[\\[\\](){}]", "");


                if (Arrays.asList(stopwords).contains(lowerCaseTwo)) {
                    //System.out.println("It is a StopWord");
                } else {
                    //stemmer.setCurrent(lowerCaseTwo);
                    //stemmer.stem();
                    if(Objects.equals(lowerCaseTwo, new String("usa"))){
                        lowerCaseTwo = new String("us");
                    }
                    stemWord = stemmer.stem(lowerCaseTwo);
                    //stemmer.getCurrent();

                    if (stemWord == ""||stemWord.contains("https")) {

                    } else {

                        //vocabList.add(stemWord);
                        //Location loc = new Location(0,artNo, pos);

                        if (map.containsKey(stemWord)) {
                            ArrayList<Integer> loc_arr = map.get(stemWord);
                            if(!loc_arr.contains(artNo)){
                                loc_arr.add(artNo);
                            }
                            map.replace(stemWord, loc_arr);
                        } else {
                            ArrayList<Integer> loc_arr = new ArrayList<>();
                            loc_arr.add(artNo);
                            map.put(stemWord, loc_arr);
                        }

                        // map_c stores the total number of times each token/word has appeared in each document.
                        if (map_c.containsKey(stemWord)) {
                            Integer total_token_count = map_c.get(stemWord);
                            total_token_count++;
                            map_c.replace(stemWord, total_token_count); // increment the token count for each article
                        } else {
                            map_c.put(stemWord, 1); // it will come to this loop only for the first time for each article
                        }
                    }
                }
            }
            artNo++;
        }

        ArrayList<String> Query_stem = new ArrayList<String>();
        /*for(int i=0; i<query.size();i++) {
            String lowerCases = query.get(i).toLowerCase();
            //stemmerOne.setCurrent(lowerCases);
            //stemmerOne.stem();
            if(Objects.equals(lowerCases, new String("usa"))){
                lowerCases = new String("us");
            }

            Query_stem.add(new Crawling().Stemmer(lowerCases));
        }*/
        String lowerCasesOne = query.get(0).toLowerCase();
        Query_stem.add(new Crawling().Stemmer(lowerCasesOne));
        String lowerCasesTwo = query.get(1).toLowerCase();
        Query_stem.add(new Crawling().Stemmer(lowerCasesTwo));

        ArrayList<Integer> queryzero_present_art = map.get(Query_stem.get(0));
        ArrayList<Integer> queryone_present_art = map.get(Query_stem.get(1));
        String query_together = "";

        for(Integer q:queryzero_present_art){
            String data = map_data.get(q);
            if(query.get(1).equals("NOT")){
                if(!data.contains(Query_stem.get(1))){
                    System.out.println(map_url.get(q));
                }
            } else if(query.get(1).equals("AND")){
                if(data.contains(Query_stem.get(1))){
                    System.out.println(map_url.get(q));
                }
            } else if(query.get(1).equals("OR")){
                System.out.println(map_url.get(q));
            }


        }
        if(query.get(1).equals("OR")){
            for(Integer q:queryone_present_art){
                System.out.println(map_url.get(q));
            }
        }


    }



    public static void Phrase(ArrayList<String> query, String fileName)
    {
        Scanner scanner;
        Map<Integer, String> map_data = new HashMap<>();
        Map<Integer, String> map_head = new HashMap<>();
        Map<Integer, String> map_url = new HashMap<>();

        {
            try {
                scanner = new Scanner(new File(fileName));
                scanner.nextLine();
                int i = 0;
                while (scanner.hasNextLine()) {
                    String useDelimiter = "\\|";
                    String[] Col = scanner.nextLine().split(useDelimiter);
                    map_data.put(i, Col[4]+" "+Col[3]);
                    map_head.put(i, Col[4]);
                    //map_head.put(Integer.parseInt(Col[0].replaceAll("\"","")), Col[4]);
                    map_url.put(i, Col[1]);
                    i++;
                    //System.out.println(map_data.get(Integer.parseInt(Col[0].replaceAll("\"",""))));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        ArrayList vocab = new ArrayList();
        Map<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();
        Map<String, Integer> map_c = new HashMap<String, Integer>(); // total number of times a word appears in all the crowled dcuments
        Map<Integer, Integer> map_d = new HashMap<Integer,Integer>(); // stores |D| i.e total number of tokens in the each Document

        String lowerCase,lowerCaseOne,lowerCaseTwo;
        String stemWord;
        String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two"/*, "un"*/, "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon"/*, "us"*/, "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};


        int pos = 0;
        int artNo = 0;
        int docNo=0;
        PorterStemmer stemmer = new PorterStemmer();
        String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";



        for(int i =0; i<map_data.size();i++) {

            String[] tokens = map_data.get(i).split(useDelimiter);


            for (String token:tokens)
            {

                pos++;

                // map that stores article number and the total number of tokens in each article or token count.
                if (map_d.containsKey(artNo)) {
                    int art_word_count = map_d.get(artNo);
                    art_word_count++;
                    map_d.replace(artNo, art_word_count); // increment the token count for each article
                } else {
                    map_d.put(artNo, 1); // it will come to this loop only for the first time for each article
                }

                lowerCase = token.toLowerCase();
                //lowerCase = (Objects.equals(token, new String("US")))?token:token.toLowerCase();
                //if((Objects.equals(token, new String("US")))){
                //    System.out.println(lowerCase);
                //}
                lowerCaseOne=lowerCase.replaceAll("\n","");
                lowerCaseTwo = lowerCaseOne.replaceAll("[!”#$%&’()—*+,-/:;<=>?@[\\]^_`{|}~]]", "").replaceAll("\'", "").replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/", "").replaceAll("[\\[\\](){}]", "");


                if (Arrays.asList(stopwords).contains(lowerCaseTwo)) {
                    //System.out.println("It is a StopWord");
                } else {
                    //stemmer.setCurrent(lowerCaseTwo);
                    //stemmer.stem();
                    if(Objects.equals(lowerCaseTwo, new String("usa"))){
                        lowerCaseTwo = new String("us");
                    }
                    stemWord = stemmer.stem(lowerCaseTwo);
                    //stemmer.getCurrent();

                    if (stemWord == ""||stemWord.contains("https")) {

                    } else {

                        //vocabList.add(stemWord);
                        //Location loc = new Location(0,artNo, pos);

                        if (map.containsKey(stemWord)) {
                            ArrayList<Integer> loc_arr = map.get(stemWord);
                            if(!loc_arr.contains(artNo)){
                                loc_arr.add(artNo);
                            }
                            map.replace(stemWord, loc_arr);
                        } else {
                            ArrayList<Integer> loc_arr = new ArrayList<>();
                            loc_arr.add(artNo);
                            map.put(stemWord, loc_arr);
                        }

                        // map_c stores the total number of times each token/word has appeared in each document.
                        if (map_c.containsKey(stemWord)) {
                            Integer total_token_count = map_c.get(stemWord);
                            total_token_count++;
                            map_c.replace(stemWord, total_token_count); // increment the token count for each article
                        } else {
                            map_c.put(stemWord, 1); // it will come to this loop only for the first time for each article
                        }
                    }
                }
            }
            artNo++;
        }

        ArrayList<String> Query_stem = new ArrayList<String>();
        for(int i=0; i<query.size();i++) {
            String lowerCases = query.get(i).toLowerCase();
            //stemmerOne.setCurrent(lowerCases);
            //stemmerOne.stem();
            if(Objects.equals(lowerCases, new String("usa"))){
                lowerCases = new String("us");
            }

            Query_stem.add(new Crawling().Stemmer(lowerCases));
        }

        ArrayList<Integer> queryzero_present_art = map.get(Query_stem.get(0));
        String query_together = "";
        for(int i=0; i<query.size();i++) {
            if(i == query.size() -1){
                query_together = query_together + query.get(i).toLowerCase();
            } else {
                query_together = query_together + query.get(i).toLowerCase() + " ";
            }

        }
        for(Integer q:queryzero_present_art){
            String data = map_data.get(q);
            if(data.toLowerCase().contains(query_together)){
                System.out.println(map_url.get(q));
                //System.out.println(map_head.get(q));
                ArrayList<String> sentences = new ArrayList();
                Map<String,Double> sentences_sigfactor = new HashMap<>();
                String linez = map_data.get(q);
                BreakIterator bi = BreakIterator.getSentenceInstance();
                bi.setText(linez);
                int index = 0;
                int sentence_count=0;
                while (bi.next() != BreakIterator.DONE) {
                    String sentence = linez.substring(index, bi.current());
                    sentences.add(sentence);
                    //System.out.println("Sentence: " + sentence);
                    index = bi.current();
                    sentence_count++;
                }
                for(String sent:sentences) {
                    if(sent.toLowerCase().contains(query_together)){
                        System.out.println(sent);
                    }
                }
            }
        }


    }

    public static void Query(ArrayList<String> query, String fileName)
    {
        Scanner scanner;
        Map<Integer, String> map_data = new HashMap<>();
        Map<Integer, String> map_head = new HashMap<>();
        Map<Integer, String> map_url = new HashMap<>();

        {
            try {
                scanner = new Scanner(new File(fileName));
                scanner.nextLine();
                int i = 0;
                while (scanner.hasNextLine()) {
                    String useDelimiter = "\\|";
                    String[] Col = scanner.nextLine().split(useDelimiter);
                    map_data.put(i, Col[4]+" "+Col[3]);
                    map_head.put(i, Col[4]);
                    map_url.put(i, Col[1]);
                    i++;
                    //System.out.println(map_data.get(Integer.parseInt(Col[0].replaceAll("\"",""))));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        ArrayList vocab = new ArrayList();
        Map<String,ArrayList<Location>> map = new HashMap<String,ArrayList<Location>>();
        Map<String, Integer> map_c = new HashMap<String, Integer>(); // total number of times a word appears in all the crowled dcuments
        Map<Integer, Integer> map_d = new HashMap<Integer,Integer>(); // stores |D| i.e total number of tokens in the each Document

        String lowerCase,lowerCaseOne,lowerCaseTwo;
        String stemWord;
        String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two"/*, "un"*/, "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon"/*, "us"*/, "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};


        int pos = 0;
        int artNo = 0;
        int docNo=0;
        PorterStemmer stemmer = new PorterStemmer();
        String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";



        for(int i =0; i<map_data.size();i++) {

            String[] tokens = map_data.get(i).split(useDelimiter);


            for (String token:tokens)
            {

                pos++;

                // map that stores article number and the total number of tokens in each article or token count.
                if (map_d.containsKey(artNo)) {
                    int art_word_count = map_d.get(artNo);
                    art_word_count++;
                    map_d.replace(artNo, art_word_count); // increment the token count for each article
                } else {
                    map_d.put(artNo, 1); // it will come to this loop only for the first time for each article
                }

                lowerCase = token.toLowerCase();
                //lowerCase = (Objects.equals(token, new String("US")))?token:token.toLowerCase();
                //if((Objects.equals(token, new String("US")))){
                //    System.out.println(lowerCase);
                //}
                lowerCaseOne=lowerCase.replaceAll("\n","");
                lowerCaseTwo = lowerCaseOne.replaceAll("[!”#$%&’()—*+,-/:;<=>?@[\\]^_`{|}~]]", "").replaceAll("\'", "").replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/", "").replaceAll("[\\[\\](){}]", "");


                if (Arrays.asList(stopwords).contains(lowerCaseTwo)) {
                    //System.out.println("It is a StopWord");
                } else {
                    //stemmer.setCurrent(lowerCaseTwo);
                    //stemmer.stem();
                    if(Objects.equals(lowerCaseTwo, new String("usa"))){
                        lowerCaseTwo = new String("us");
                    }
                    stemWord = stemmer.stem(lowerCaseTwo);
                    //stemmer.getCurrent();

                    if (stemWord == ""||stemWord.contains("https")) {

                    } else {

                        //vocabList.add(stemWord);
                        Location loc = new Location(0,artNo, pos);

                        if (map.containsKey(stemWord)) {
                            ArrayList<Location> loc_arr = map.get(stemWord);
                            loc_arr.add(loc);
                            map.replace(stemWord, loc_arr);
                        } else {
                            ArrayList<Location> loc_arr = new ArrayList<>();
                            loc_arr.add(loc);
                            map.put(stemWord, loc_arr);
                        }

                        // map_c stores the total number of times each token/word has appeared in each document.
                        if (map_c.containsKey(stemWord)) {
                            Integer total_token_count = map_c.get(stemWord);
                            total_token_count++;
                            map_c.replace(stemWord, total_token_count); // increment the token count for each article
                        } else {
                            map_c.put(stemWord, 1); // it will come to this loop only for the first time for each article
                        }
                    }
                }
            }
            artNo++;
        }

        try {
        //Map<String,ArrayList<Location>> map = new HashMap<String,ArrayList<Location>>();
        //Map<String, Integer> map_c = new HashMap<String, Integer>(); // total number of times a word appears in all the crowled dcuments
        //Map<Integer, Integer> map_d = new HashMap<Integer,Integer>(); // stores |D| i.e total number of tokens in the each Document
        ArrayList<String> Query_stem = new ArrayList<String>();
        //String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";
        for(int i=0; i<query.size();i++) {
            String lowerCases = query.get(i).toLowerCase();
            //stemmerOne.setCurrent(lowerCases);
            //stemmerOne.stem();
            if(Objects.equals(lowerCases, new String("usa"))){
                lowerCases = new String("us");
            }

            Query_stem.add(new Crawling().Stemmer(lowerCases));
        }
        // get all terms from all the URL's body
            /*crawler.Preprocessing();
            map.putAll(crawler.map);
            map_d.putAll(crawler.map_d); // added map_d
            map_c.putAll(crawler.map_c); // added map_c*/


        Map<Integer,Map<Integer,Integer>> map_res = new HashMap<Integer, Map<Integer, Integer>>(); // map contains the query token number and a map of all document containing that word and number of times it occured
        ArrayList<Integer> artlist = new ArrayList<>(); // list of documents containing atleast one query word

        for(int i=0; i<Query_stem.size();i++) {
            for(int j=0;j<map.get(Query_stem.get(i)).size();j++){
                Location location = map.get(Query_stem.get(i)).get(j);
                if(!artlist.contains(location.getArtNo())){
                    artlist.add(location.getArtNo());
                }
                Map<Integer,Integer> map_nested = new HashMap<>();

                if (map_res.containsKey(i)) {
                    map_nested = map_res.get(i);
                    if (map_nested.containsKey(location.getArtNo())){
                        Integer cnt = map_res.get(i).get(location.getArtNo());
                        cnt++;
                        map_nested.replace(location.getArtNo(),cnt);
                    } else {
                        Integer cnt = 1;
                        map_nested.put(location.getArtNo(),cnt);
                    }
                    map_res.replace(i,map_nested);
                }
                else{
                    Integer cnt = 1;
                    map_nested.put(location.getArtNo(),cnt);
                    map_res.put(i,map_nested);
                }
            }
        }

        // at this point we have fqd value for the query word in all the documents containing it.

        // comptations of Cqi
        ArrayList<Integer> cq = new ArrayList<Integer>(); // possition 0 contains the word count of query word 0 in all documents
        for(int i=0; i<Query_stem.size();i++) {
            cq.add(map_c.get(Query_stem.get(i)));
        }

        // computation of |C| = add total number of words in all the document
        int C=0;
        for(int i=0; i<map_d.size();i++){
            C += map_d.get(i);
        }
        //iterate through each query word and  iterate through all documents in artlist in nested fashion -- to find fqd and |D|
        //you should compute score for all the documents present in artlist
        // once you compute score for all the documents in artlist sort them by score

        // below code shows how to extract fqd for query word 0 and atrlist[0]
        map_res.get(0).get(artlist.get(0)); // getting count for query word 0 and article number j of artlist[]

        // similarly find for all query word 1 , 2,...

        // |D| is in map_d if you want the total word count for article number j in artlist[]
        map_d.get(artlist.get(0));

        // now we have cq, |C|, |D| and fqd. Copute scores for each document in artlist and print them
        Map<Integer,Double> map_pqd = new HashMap<>();
        for(int i=0; i<artlist.size();i++){
            Double pqd_res=0.0;
            for(int j=0 ; j<Query_stem.size();j++){
                if(map_res.get(j).containsKey(artlist.get(i))){
                    pqd_res += PQD(map_res.get(j).get(artlist.get(i)),cq.get(j),map_d.get(artlist.get(i)),C,2000);
                }
                else{
                    pqd_res += PQD(0,cq.get(j),map_d.get(artlist.get(i)),C,2000);

                }
            }
            map_pqd.put(artlist.get(i),pqd_res);
        }

        Map<Integer,Double> sortedmap_pqd = map_pqd.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        //Stream<String> all_lines = Files.lines(Paths.get("d:/ArticlesURLS.csv"));
        //specific_line_15 = all_lines.skip(14).findFirst().get();
        //File urlfile = new File("d:/ArticlesURLS.csv");


        //PrintWriter out = new PrintWriter(new FileWriter("output_query2.txt"));
        //System.out.println("Article Number, Score, Article URL");

        sortedmap_pqd.entrySet().forEach(entry->{
            System.out.println(/*entry.getKey()+ ","+ + entry.getValue() +"," + */map_url.get(entry.getKey()));
            System.out.println(/*entry.getKey()+ ","+ + entry.getValue() +"," + */map_head.get(entry.getKey()));

            //File urlfilez = new File("d:/ArticlesData.csv");
            //String linez = FileUtils.readLines(urlfilez).get(entry.getKey()).toString();
            String linez = map_data.get(entry.getKey());
            String lowerCasez,lowerCaseOnez,lowerCaseTwoz;
            Map<String, Integer> map_freq = new HashMap<String, Integer>(); // word count for a perticular document
            //String broken = removeTillWord(linez,"DW");
            //String text = removeTillWordPlusOne(broken,",");
            //System.out.println(removeTillWordPlusOne(broken,","));
            BreakIterator bi = BreakIterator.getSentenceInstance();
            bi.setText(linez);
            int index = 0;
            int sentence_count=0;
            ArrayList<String> sentences = new ArrayList();
            Map<String,Double> sentences_sigfactor = new HashMap<>();
            while (bi.next() != BreakIterator.DONE) {
                String sentence = linez.substring(index, bi.current());
                sentences.add(sentence);
                //System.out.println("Sentence: " + sentence);
                index = bi.current();
                sentence_count++;
            }
            String[] tokensz = linez.split(useDelimiter);
            for (String token:tokensz)
            {
                lowerCasez = token.toLowerCase();
                lowerCaseOnez=lowerCasez.replaceAll("\n","");
                lowerCaseTwoz = lowerCaseOnez.replaceAll("[!”#$%&’()—*+,-/:;<=>?@[\\]^_`{|}~]]", "").replaceAll("\'", "").replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/", "").replaceAll("[\\[\\](){}]", "");


                if (Arrays.asList(stopwords).contains(lowerCaseTwoz)) {
                    //System.out.println("It is a StopWord");
                } else {
                    if (map_freq.containsKey(lowerCaseTwoz)) {
                        Integer count = map_freq.get(lowerCaseTwoz);
                        count += 1;
                        map_freq.replace(lowerCaseTwoz, (count));
                    } else {
                        map_freq.put(lowerCaseTwoz, 1);
                    }
                }
            }
                /*if (map_freq.containsKey("us")) {
                    System.out.println(map_freq.get("us"));
                }*/
            ArrayList<String> Significant_word = new ArrayList();
            for (Map.Entry<String, Integer> entryz : map_freq.entrySet()) {
                String key = entryz.getKey();
                if (key.length()>1) {
                    if (sentence_count < 25) {
                        if (entryz.getValue() > (7 - ((double) (25 - sentence_count) / 10))) {
                            Significant_word.add(entryz.getKey());
                        }
                    } else if ((sentence_count > 25) && (sentence_count < 40)) {
                        if (entryz.getValue() > (7)) {
                            Significant_word.add(entryz.getKey());
                        }
                    } else {
                        if (entryz.getValue() > (7 - ((double) (sentence_count-40) / 10))) {
                            Significant_word.add(entryz.getKey());
                        }
                    }
                }
                //System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            for(int i=0; i<query.size(); i++){
                if(!Significant_word.contains(query.get(i).toLowerCase())){
                    Significant_word.add(query.get(i).toLowerCase());
                }
            }

            for(String sent:sentences) {
                int count=0;
                double sig_factor=0.0;
                for (String sig : Significant_word) {
                    count += StringUtils.countMatches(sent.toLowerCase(), sig);
                    for(int i=0; i<query.size();i++){
                        if((sig.contains(query.get(i)))){
                            count += 1;
                        }
                    }
                    /*if((sig.contains(query.get(0)))||(sig.contains(query.get(1)))||(sig.contains(query.get(2)))) { //match the query size
                        count += 1;
                    }*/
                }
                StringTokenizer words = new StringTokenizer(sent.toLowerCase());
                sig_factor = (double)(count*count)/words.countTokens();
                sentences_sigfactor.put(sent,sig_factor);
            }
            Map<String,Double> sortedmap_sentences_sigfactor = sentences_sigfactor.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(3)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            sortedmap_sentences_sigfactor.entrySet().forEach(entryy->{
                String[] split_sent = entryy.getKey().replaceAll(",","").split(" ");
                String printone;
                if(split_sent.length > 25){
                    // find the first significant word
                    for(String sent:split_sent){
                        if(Arrays.stream(Significant_word.toArray(new String[Significant_word.size()])).parallel().anyMatch(sent::contains)){
                            printone = removeTillWord(entryy.getKey(),sent);
                            if(printone.length()>125) {
                                printone = printone.substring(0, 125);
                            }
                            else{
                                printone = printone.substring(0, printone.length()-1);
                            }
                            System.out.println("..."+printone+"...");
                            break;
                        }
                    }
                }
                else {
                    System.out.println(entryy.getKey());
                }
            });

            System.out.println();

        });


        System.out.println("Wait");
    }catch (Exception e){ System.out.println(e.getMessage());}

}
    public static String removeTillWord(String input, String word) {
        return input.substring(input.indexOf(word));
    }
    public static String removeTillWordPlusOne(String input, String word) {
        return input.substring(input.indexOf(word)+1);
    }
    public static double PQD (Integer Fqi, Integer Cqi, Integer Mod_D, Integer Mod_C, Integer u){
        // iterate or call this method n times, where in is the number of words in a query
        // once you iterate n times, add log_PQD_score value of all n iteration
        // For a give article D, in each call only Fqi value changes
        // For each document D this method has to be called n times
        // when the document D changes Mod_D value chages else only Fqi value changes at each iteration.
        double log_PQD_score = 0;
        log_PQD_score = Math.log((double)((Fqi+(u*(double)(Cqi/Mod_C)))/(Mod_D+u)));
        return log_PQD_score;
    }
public static void test() {
    ArrayList vocab = new ArrayList();
    Map<String, ArrayList<Location>> map = new HashMap<String, ArrayList<Location>>();
    Map<String, Integer> map_c = new HashMap<String, Integer>(); // total number of times a word appears in all the crowled dcuments
    Map<Integer, Integer> map_d = new HashMap<Integer, Integer>(); // stores |D| i.e total number of tokens in the each Document
    String lowerCase, lowerCaseOne, lowerCaseTwo;
    String stemWord = "";
    String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};

    try {
        int pos = 0;

        int artNo = 0;
        int docNo = 0;
        PorterStemmer stemmer = new PorterStemmer();
        //while (new File("C:/Users/chirag/Documents/Ushma/webProjectNew/articles/ArticlesData.csv").exists()){
        Scanner scanner = new Scanner(new File("D:/ArticlesData.csv"));
        // Scanner scanner = new Scanner(sentence);
        //Set the delimiter used in file
        String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] tokens = line.split(useDelimiter);

            for (String token : tokens) {
                pos++;
                // map that stores article number and the total number of tokens in each article or token count.
                if (map_d.containsKey(artNo)) {
                    int art_word_count = map_d.get(artNo);
                    art_word_count++;
                    map_d.replace(artNo, art_word_count); // increment the token count for each article
                } else {
                    map_d.put(artNo, 1); // it will come to this loop only for the first time for each article
                }


                lowerCase = token.toLowerCase();
                lowerCaseOne = lowerCase.replaceAll("\n", "");
                lowerCaseTwo = lowerCaseOne.replaceAll("[!”#$%&’()—*+,-/:;<=>?@[\\]^_`{|}~]]", "").replaceAll("\'", "").replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/", "").replaceAll("[\\[\\](){}]", "");


                if (Arrays.asList(stopwords).contains(lowerCaseTwo)) {
                    //System.out.println("It is a StopWord");
                } else {

                    stemWord=new Crawling().Stemmer(lowerCaseTwo);
                    if (stemWord == "" || stemWord.contains("https")) {

                    } else {

                        //vocabList.add(stemWord);
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

                        // map_c stores the total number of times each token/word has appeared in each document.
                        if (map_c.containsKey(stemWord)) {
                            Integer total_token_count = map_c.get(stemWord);
                            total_token_count++;
                            map_c.replace(stemWord, total_token_count); // increment the token count for each article
                        } else {
                            map_c.put(stemWord, 1); // it will come to this loop only for the first time for each article
                        }
                    }
                }
            }
            artNo++;
        }
        scanner.close();

        System.out.println(artNo);
        System.out.println(map_c);
        System.out.println(map_d);
    } catch (Exception io) {
        System.out.println(io.getMessage());
    }


}


public static void WriteCSVForTokens(Set l)throws Exception {
        long startTime= System.currentTimeMillis();


        BufferedReader br= new BufferedReader(new FileReader(articles_data));
        String line="";
        String [] col = new String[0];
        while ((line=br.readLine())!=null){
            col=line.split(",");
            if(l.contains(Integer.valueOf(col[0])))
              System.out.println(col[0] +" : "+col[1]);

            //System.out.println(col[0]);

        }

        long stopTime= System.currentTimeMillis();
        System.out.println("Execution time for write data for tokens in CSV in milliseconds:"+ (stopTime - startTime));

    }

// Can you check if this method only read from particular column from CSV file
    public static void ReadCSVToTokenize(){

        try {

            FileReader filereader = new FileReader(tokens_data);


            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();


          /*   //csv file containing data

             String strFile = "D://SS19//SOFTWARE ANALYSIS//Assignments//SearchEngineAssignments//ArticlesData.csv";

             CSVReader reader = new CSVReader(new FileReader(strFile));

             String[] nextLine;

             int lineNumber = 0;

             while ((nextLine = reader.readNext()) != null) {

                 lineNumber++;

                 System.out.println("Line # " + lineNumber);

                 System.out.println(nextLine[4] + "etc...");
*/
            }
        } catch (IOException io) {
            System.out.println(io.getCause());
        }
    }
}