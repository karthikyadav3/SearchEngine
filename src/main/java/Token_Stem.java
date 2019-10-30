import org.tartarus.snowball.ext.PorterStemmer;

import java.io.*;

import java.util.*;

public class Token_Stem {
    private static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
    public static Map<Integer,Integer> CountToken=new HashMap<>();
    private String lowerCase;
    private String stemWord;
    // stemming
    public  static String Stemmer(String TokString){
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(TokString); //set string you need to stem
        stemmer.stem();  //stem the word
        String stm=stemmer.getCurrent();
        return  stm;}


    public void TokenAndStem() {
        try {
            Scanner scanner = new Scanner(new File("d://ArticlesData.csv"));
            // save string all data of articles in txt file for next step Tokens
            BufferedWriter writer_in_txt_file = new BufferedWriter(new FileWriter("d:/All_Data.txt",true));
            FileWriter outputfile = new FileWriter(new File("d:/Tokens_Stems.txt"), true);
            BufferedWriter bw = new BufferedWriter(outputfile);
            PrintWriter pw = new PrintWriter(bw);
            // Scanner scanner = new Scanner(sentence);
            //Set the delimiter used in file
//            String useDelimiter = "[ ]|,|\"|\\||-|[']|[.]|[(]|[)]|[/]|[”]|[“]|[’]|[‘]|[ ]";
            String tokens_data = "";
            int artNo = 0;
            System.out.println("The file was read. Token and Stemming information, please wait...");

            while (scanner.hasNextLine()) {
                artNo++;
                String line = scanner.nextLine();
                String text1 = line.replaceAll("[!”#$%&’()*+,-/:;<=>?@[\\]^_`{|}~]] ", " "). replaceAll("\'", "").replaceAll("\"", "").replaceAll("http.*?\\s", " ").replaceAll("\\/","").replaceAll("[\\[\\](){}]","");
                String txt = text1.trim().replaceAll("\\s+", " ");
                String tokens []=txt.split(" ");
                // System.out.println(line_Replace);
                tokens_data += txt;
                CountToken.put(artNo, tokens.length);
                for (String token : tokens) {
                    lowerCase = token.toLowerCase();
                    lowerCase = lowerCase.replaceAll("\n", "");
                    if (Arrays.asList(stopwords).contains(lowerCase)) {
                        //System.out.println("It is a StopWord");
                    } else {
                        stemWord = Stemmer(lowerCase);
                        if (stemWord == "" || stemWord.contains("https")) {


                        } else {
                            pw.println(lowerCase+" | "+stemWord);
//                            writeDataLineByLine("d:/All_Data_Token_Stem.txt",stemWord);
                        }
                    }
                }
            }
            writer_in_txt_file.write(tokens_data);
            scanner.close();
            pw.flush();
            pw.close();
        writer_in_txt_file.flush();
        writer_in_txt_file.close();
            String [] g=tokens_data.split("");
            System.out.println("Number of all tokens in Articles: " + g.length);
            System.out.println("Tokens in each Article: " + CountToken);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
