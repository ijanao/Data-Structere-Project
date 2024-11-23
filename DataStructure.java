import java.io.*;
import java.util.*;

public class DataStructure {


static  int NumberOfTokens=0;
static  LinkedList<String>stopWords= new LinkedList<String>();

    public static void main(String[] args) {

// -----------------------------------  Declare Needed Variables -----------------------------------
//---------------------------------------------------------------------------------------------------

         Index index= new Index();
         InvertedIndex invertedIndex= new InvertedIndex();
         InvertedIndex_BST invertedIndex_bst= new InvertedIndex_BST();
         LinkedList<String>words;
         LinkedList<String>uniqueWords = new LinkedList<String>();




// -----------------------------------  Read Stop words  ----------------------------------------------
//---------------------------------------------------------------------------------------------------

        try{
            File file = new File("data/stop.txt");
            Scanner scanner= new Scanner(file);
            String Line;

            while(scanner.hasNext()){
                Line =scanner.nextLine();
                stopWords.insert(Line);
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }

// -----------------------------------  Read Dataset  ----------------------------------------------
//---------------------------------------------------------------------------------------------------
        try{
            File file = new File("data/dataset.csv");
            Scanner scanner= new Scanner(file);
//            Read first line
            scanner.nextLine();
            String Line;
            while(scanner.hasNext()){
                Line =scanner.nextLine();

//             Get the id of the document and convert it to integer
                int id = Integer.parseInt(Line.substring(0, Line.indexOf(",")));
//             Get the sentence of the document
                String sentence =Line.substring(Line.indexOf(",")+1);
                words= new LinkedList<String>();
                NumberOfTokensAndUniqueWord(sentence , uniqueWords);
                processing(sentence ,id ,words,invertedIndex ,invertedIndex_bst);
                index.allDOCS.insert(new Document(id ,words,sentence));


            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }






        Scanner input = new Scanner(System.in);
        char choice;
        do {

            System.out.println("------------------------------- MENU -------------------------------");
            System.out.println("1-Retrieve a word (Index).");
            System.out.println("2-Retrieve a word (Inverted Index).");
            System.out.println("3-Retrieve a word (BST Inverted).");
            System.out.println("4-Boolean Retrieval.");
            System.out.println("5-Ranked Retrieval.");
            System.out.println("6-Display all documents with the number of word (Tokens).");
            System.out.println("7-Display all the words (Tokens) with the number of documents they appear in.");
            System.out.println("8-Display total number of tokens & unique words.");
            System.out.println("9- Exit");
            System.out.println("---------------------------------------------------------------------");
            choice=input.next().charAt(0);
            switch (choice)
            {
                case '1':
                    System.out.println("--------------------- Retrieve a word (Index) ---------------------");
                    System.out.println("-------------------------------------------------------------------\n");
                    System.out.println("Enter the word you want to retrieve it (Index)?");
                    String word =input.next().toLowerCase().trim();
                    LinkedList<Integer> list1 =index.SearchDocumentsByTerm(word);
                    System.out.println("Term Appeared In:");
                    System.out.println("----------------------------------");
                    list1.display();
                    System.out.println("----------------------------------");

                    break;

                case '2':
                    System.out.println("--------------------- Retrieve a word (Index) ---------------------");
                    System.out.println("-------------------------------------------------------------------\n");
                    System.out.println("Enter the term you want to retrieve it (Inverted Index)?");
                     word =input.next().toLowerCase().trim();
                     if (invertedIndex.wordExist(word))
                         invertedIndex.invertedindex.retrieve().DisplayWordInfo();
                    break;

                case '3':
                    System.out.println("--------------------- Retrieve a word (Inverted Index) ---------------------");
                    System.out.println("----------------------------------------------------------------------------\n");
                    System.out.println("Enter the term you want to retrieve it (BST Inverted)?");
                    word =input.next().toLowerCase().trim();
                    if (invertedIndex_bst.SearchWordBST(word))
                        invertedIndex_bst.invertedindexBST.retrieve().DisplayWordInfo();
                    break;

                case '4':
                    System.out.println("------------------------ Boolean Retrieval ------------------------");
                    System.out.println("-------------------------------------------------------------------\n");
                    input.nextLine();
                    System.out.println("Enter Your Query");
                    String query =input.nextLine().toLowerCase() .replaceAll(" and ", " AND ").replaceAll(" or ", " OR ");

                    System.out.println("What would you like to use with your query");
                    System.out.println("1- Index");
                    System.out.println("2- Inverted Index");
                    System.out.println("3- BST");
                    char ch =input.next().charAt(0);

                    if(ch=='1')
                    {
                        System.out.println("--------------- Index ---------------");
                        System.out.println("# Q: "+query);
                    DataQueryHandlerFromIndex Q1 = new DataQueryHandlerFromIndex(index);
                    LinkedList List1 =DataQueryHandlerFromIndex.MIXEDQuery(query);
                   System.out.println( DisplayDocGivenIDS(List1, index));
                    }
                    else if (ch=='2')
                    {
                        System.out.println("--------------- Inverted Index ---------------");
                        System.out.println("# Q: "+query);
                        DataQueryHandler Q2 = new DataQueryHandler(invertedIndex);
                        LinkedList List2 =DataQueryHandler.MIXEDQuery(query);
                        System.out.println(DisplayDocGivenIDS(List2, index));
                    }
                    else if (ch=='3')
                    {
                        System.out.println("-------------------- BST --------------------");
                        System.out.println("# Q: "+query);
                        DataQueryHandlerBST Q3 = new DataQueryHandlerBST(invertedIndex_bst);
                        LinkedList List3 =DataQueryHandlerBST.MIXEDQuery(query);
                        System.out.println(DisplayDocGivenIDS(List3, index));
                    }


                    break;

                case '5':
                    System.out.println("------------------------- Ranked Retrieval -------------------------");
                    System.out.println("--------------------------------------------------------------------\n");
                    input.nextLine();
                    System.out.println("Enter a query to rank the results:");
                    String secondQuery = input.nextLine();
                    secondQuery = secondQuery.toLowerCase();
                    Ranking R5 = new Ranking(invertedIndex_bst, index, secondQuery);
                    R5.InsertSortedList();
                    R5.DisplayDocuments();
                    break;

                case '6':
                    System.out.println("------- Display all documents with the number of word (Tokens) ------");
                    System.out.println("---------------------------------------------------------------------\n");
                    index.allDOCS.findFirst();
                    while (!index.allDOCS.last())
                    {
                        Document document =index.allDOCS.retrieve();
                        System.out.println("Doc ID: "+document.id +" Number of words: "+document.words.size());
                        index.allDOCS.findNext();
                    }
                    Document document =index.allDOCS.retrieve();
                    System.out.println("Doc ID: "+document.id +" Number of words: "+document.words.size());

                    break;

                case '7':
                    System.out.println("--- Display all the words (Tokens) with the number of documents they appear in ---");
                    System.out.println("----------------------------------------------------------------------------------\n");
                    invertedIndex.displayInvertedIndex();
                    break;


                case '8':
                    System.out.println("------------ Display total number of tokens & unique words ------------");
                    System.out.println("--------------------------------------------------------------------------------\n");
                    System.out.println("Token: "+NumberOfTokens +"\nUnique Words: "+uniqueWords.size());

                    break;

                case '9':
                    System.out.println("Exit Successfully!!");
                    break;
            }

        }while (choice!='9');


    }



    public static void processing(String sentence, int id, LinkedList<String> words, InvertedIndex invertedIndex, InvertedIndex_BST invertedIndex_bst)
    {

        // Handle hyphens in the content: Replace hyphens based on their position in the string
        while(sentence.contains("-")) {
            if(sentence.charAt(sentence.indexOf("-") - 2) == ' ') // Check if a space exists before the hyphen
                sentence = sentence.replaceFirst("-", ""); // Remove the hyphen if a space exists
            else
                sentence = sentence.replaceFirst("-", " "); // Replace the hyphen with a space if no space exists
        }

        // Convert the content to lowercase and remove non-alphanumeric characters
        sentence = sentence.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");

        // Split the content into an array of words (tokens) based on spaces
        String[] ArrayOfWords = sentence.split("\\s+");

        // Iterate over the array of words
        for(int i = 0; i < ArrayOfWords.length; i++) {
            if (!stopword_Exists(ArrayOfWords[i])) { // Check if the word is not a stopword
                words.insert(ArrayOfWords[i]); // Add the valid word to the list of words for the current document
                invertedIndex.addWord(id, ArrayOfWords[i]); // Add the word and document ID to the inverted index
                invertedIndex_bst.add(ArrayOfWords[i], id); // Add the word and document ID to the BST-based inverted index
            }
        }
    }

    // Method to process tokens and update the total token count and unique words list.
    public static void NumberOfTokensAndUniqueWord(String content , LinkedList<String> uniqueWords){

        content = content.toLowerCase().replaceAll("\'"," ");
        content = content.toLowerCase().replaceAll("-"," ");
        content = content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
        String[] tokens = content.split("\\s+");
        NumberOfTokens+=tokens.length;// Add the number of tokens in the document to the total count



        for(int i=0 ;i<tokens.length;i++){
            if(!uniqueWords.exist(tokens[i])){
                uniqueWords.insert(tokens[i]);
            }


        }
    }




    public static<T> boolean stopword_Exists(String word ){
        if(!stopWords.empty())
        {
            stopWords.findFirst();
            while(!stopWords.last())
            {
                if(stopWords.retrieve().equalsIgnoreCase(word))
                    return true;
                stopWords.findNext();
            }
            if(stopWords.retrieve().equalsIgnoreCase(word))
                return true;

            return false;
        }
        return false;
    }


    public static <T>String DisplayDocGivenIDS(LinkedList<Integer> IDs,Index index){

        if (IDs.empty()) {

            return "no documents exist";
        }

        IDs.findFirst() ;
        String result="Result: { ";
        while (!IDs.last()){
            Document d=index.GetDocument(IDs.retrieve()) ;

            if (d!=null)
                result+=d.id+" ," ;

            IDs.findNext();
        }

        Document d=index.GetDocument(IDs.retrieve()) ;

        if (d!=null)
            result+=d.id+" }\n" ;

        return result;
    }


}