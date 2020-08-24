package main;
import com.opencsv.CSVWriter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.tartarus.snowball.ext.PorterStemmer;

public class Crawling {
    public int Count_AllToken=0;
    public int Count_TokenArticle=0;
    public static Map<Integer,Integer> CountToken=new HashMap<>();
    public static Map<Integer,Integer> CountWord=new HashMap<>();
    private final Set<URL> links;
    public final Set<Articles> article;
    private static String row="";
    private static ArrayList<String> wordsList = new ArrayList<String>();
    private static final String token_file="d:/Tokens1.txt";
    private static final String articles_file="d:/ArticlesURLS.csv";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10 12 6) AppleWebKit/537.36 " +"(KHTML,like Gecko) Chrome/61.0.3163.100 Safari/537.36";
        private static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
    private static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    static HashSet noDupSetToken = new HashSet();
    static HashSet noDupSetStem = new HashSet();
    public Articles articles;
    static  Hashtable<Integer, String> ht = new Hashtable<>();
    private ArrayList<String> vocabList = new ArrayList<String>();
    private ArrayList<String> stopWordList = new ArrayList<String>();
    private String lowerCase;
    private String stemWord;
    public  int GermanyCount=0;
    public  int TropicalCount=0;
    public  int FishCount=0;
    public  int MexicoCount=0;
    public  int RefugeesCount=0;
    public  int WallCount=0;
    int count=1;
    public Map<String, ArrayList<Location>> map = new HashMap<String, ArrayList<Location>>();



    // Constructor
    public Crawling(final URL homeUrl) {
        // initial values
        this.article = new HashSet<>();
        this.links = new HashSet<>();
        //crawl(HomePage(homeUrl));
        CrawlURLS(homeUrl.toString());//Crawl URLs in home page
    }
    public  Crawling()
    {   this.article = new HashSet<>();
        this.links = new HashSet<>();}

    //Crawl URLs in home page
    public void CrawlURLS(String url){
        long startTime = System.currentTimeMillis();
        links.removeAll(this.links);
        if (links.isEmpty()) {
            try {
                Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
                Document htmlDocument = connection.get();
                Elements ele= htmlDocument.select("div#searchResult");
                for (Element el:ele.select("div.searchResult a"))
                {
                    final URL discoveredURL = new URL("https://www.dw.com"+el.attr("href"));
                    links.add(discoveredURL);
                }
      /*          for (Element el:ele.select("div.left div.basicTeaser div.white a"))
                {
                    final URL discoveredURL = new URL("https://www.dw.com"+el.attr("href"));
                    links.add(discoveredURL);
                }
                for (Element el:ele.select("div.left div.basicTeaser div.group div.linkList a"))
                {
                    final URL discoveredURL = new URL("https://www.dw.com"+el.attr("href"));
                    links.add(discoveredURL);
                }*/
//                for (Element el:ele.select("div.col2 div.basicTeaser div.linkList a"))
//                {
//                    final URL discoveredURL = new URL("https://www.dw.com"+el.attr("href"));
//                    links.add(discoveredURL);
//                }
//                for (Element el:ele.select("div.col2 div.basicTeaser div.group div.news a"))
//                {
//                    final URL discoveredURL = new URL("https://www.dw.com"+el.attr("href"));
//                    links.add(discoveredURL);
//                }
//                for (Element el:ele.select("div.col2 div.basicTeaser div.white a"))
//                {
//                    final URL discoveredURL = new URL("https://www.dw.com"+el.attr("href"));
//                    links.add(discoveredURL);
//                }
                long stopTime = System.currentTimeMillis();
                System.out.println("Tne Number of URLs Home page :"+links.size());
                System.out.println("Execution time for crawling URLs in milliseconds:"+ (stopTime - startTime));
            }catch (final Exception | Error ignored) {}} }



    // save all URLS in csv file
    public void WritePageURLS(String path_URLs) {
        long startTime= System.currentTimeMillis();
        try {
            FileWriter csvWriter = new FileWriter(path_URLs,true);
            for ( URL url : this.links) {
                if(!(SearchEngine.Is_Exit_Data(url.toString(),articles_file,0))) {
                    csvWriter.append(String.join(",", url.toString()));
                    csvWriter.append("\n"); }}
            csvWriter.flush();
            csvWriter.close();
            long stopTime= System.currentTimeMillis();
            System.out.println("Execution time for save URLs in CSV file in milliseconds:"+ (stopTime - startTime));
        }catch (Exception io){System.out.println(io.getMessage());}}


    // read urls from csv file and  crawl them
    public  void ReadURLs(String path_URLs) throws Exception {
        long startTime= System.currentTimeMillis();
        BufferedReader csvReader = new BufferedReader(new FileReader(path_URLs));
        while ((row = csvReader.readLine()) != null) {
            // crawl data from urls
            crawlURL(row); }
        csvReader.close();
        long stopTime= System.currentTimeMillis();
        System.out.println("Execution time for read urls from csv and Crawl them in milliseconds:"+ (stopTime - startTime));
    }



    // crawl  urls
    public  void crawlURL(String url){
        try {
            articles =new Articles();
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            Elements ele= htmlDocument.select("div#bodyContent");
            articles.setAritcle_Id((count++));
            // System.out.println("URL :"+url);
            articles.setAritcle_URL(url);
            // System.out.println( "The Title : " +ele.select("div.col3 h1").text());
            articles.setAritcle_Title(htmlDocument.title());
            //System.out.println("This Text : " +ele.select("div.longText p").text().replaceAll("",""));
            articles.setAritcle_Text(ele.select("div.longText p").text());
            // System.out.println("Category: "+ele.select("div.col3 h4.artikel").text());
            articles.setAritcle_Category(ele.select("div.col3 h4.artikel").text());
            // System.out.println( "SubTitle/Into : "+htmlDocument.select("meta[name=description]").first().attr("content"));
            articles.setAritcle_SubTilte(htmlDocument.select("meta[name=description]").first().attr("content"));
            // System.out.println( "Date : "+htmlDocument.select("meta[property=og:title]").first().attr("content"));
            articles.setAritcle_Date(htmlDocument.select("meta[property=og:title]").first().attr("content").substring(htmlDocument.select("meta[property=og:title]").first().attr("content").lastIndexOf("|") + 1).trim());
            for (Element elem:ele.select("div.col1 div.group ul.smallList  li") ){
                if(elem.text().indexOf("Author")!=-1)
                {
                    articles.setAritcle_Author(elem.text().substring(elem.text().lastIndexOf("Author") + 6).trim());
                }
            }
            article.add(articles);
        }catch (Exception e){
            e.getMessage();
        }
    }




    // stemming
    public  static String Stemmer(String TokString){
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(TokString); //set string you need to stem
        stemmer.stem();  //stem the word
        String stm=stemmer.getCurrent();
        return  stm;}

    public static String CleanStr(String str) {
        String text1 = str.replaceAll("[!”#$%&’()*+,-/:;<=>?@[\\]^_`{|}~]] ", " "). replaceAll("\'", "")
                .replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/","").replaceAll("[\\[\\](){}]","");
        String txt = text1.trim().replaceAll("\\s+", " ");
        return  txt;
    }

public ArrayList Preprocessing() {
    try {
        Scanner scanner = new Scanner(new File("d://ArticlesData.csv"));
        // Scanner scanner = new Scanner(sentence);
        //Set the delimiter used in file
        String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";
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

            CountToken.put(artNo, tokens.length);

            for (String token : tokens) {

                pos++;


                lowerCase = token.toLowerCase();
                lowerCase = lowerCase.replaceAll("\n", "");

                if (Arrays.asList(stopwords).contains(lowerCase)) {
                    //System.out.println("It is a StopWord");
                } else {

                    stemWord = Stemmer(lowerCase);

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
        System.out.println("Tokens in each Article: " + CountToken);
        return vocabList;
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    return vocabList;
}

    private Set<URL> HomePage(final URL homeUrl) {

        final Set<URL> homeURLs = new HashSet<>();
        homeURLs.add(homeUrl);
        return homeURLs;
    }
    private void crawl(final Set<URL> URLS) {

        URLS.removeAll(this.links);
        if (!URLS.isEmpty()) {
            final Set<URL> newURLS = new HashSet<>();
            try {
                this.links.addAll(URLS);
                 for (final URL url : URLS) {
                  /*  System.out.println("time = " + (System.currentTimeMillis() - this.startTime) +
                            " connect to : " + url);*/
                    final Document document = Jsoup.connect(url.toString()).userAgent(USER_AGENT).get();
                    final Elements linksOnPage = document.select("a[href]");
                    for (final Element page : linksOnPage) {
                        final String urlText = page.attr("abs:href").trim();
                        //crawlURL(urlText);
                        final URL discoveredURL = new URL(urlText);
                        newURLS.add(discoveredURL);

                    }
                }
            } catch (final Exception | Error ignored) {
            }
            //crawl(newURLS);
        }
    }
    public  static  String Tokenize(String TokString) throws IOException {
        String text1 = TokString.replaceAll("[!”#$\"%&’()*+,-./:;<=>?@[\\]^_`{|}~]]", " ");
        String text []=text1.split(" ");
        File newTextFile = new File(token_file);
        FileWriter fw1 = new FileWriter(newTextFile,true);
       // CSVWriter fw=new CSVWriter(fw1);
        fw1.write(text1);
        fw1.close();
        return text1;
         }



}

