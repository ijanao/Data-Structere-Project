/**
 * A class representing an Inverted Index.
 * Maps words to the list of document IDs where they appear.
 */
public class InvertedIndex {

    // A linked list to store Word objects
    LinkedList<Word> invertedindex;

    /**
     * Constructor to initialize the inverted index.
     */
    public InvertedIndex() {
        invertedindex = new LinkedList<>();
    }

    /**
     * Adds a word to the inverted index and associates it with a document ID.
     * If the word already exists, the document ID is appended to the existing word entry.
     * If the word does not exist, a new word entry is created.
     */
    public void addWord(int docID, String word) {
        // Check if the word already exists
        if (wordExist(word)) {
            // Add the document ID to the existing word
            invertedindex.retrieve().add_ID(docID);
        } else {
            // Create a new word entry and add the document ID
            Word newWord = new Word(word);
            newWord.add_ID(docID);
            invertedindex.insert(newWord);
        }
    }

    /**
     * Checks if a word exists in the inverted index.
     * Updates the current position to the word's position if it exists.
     */
    public boolean wordExist(String word) {
        if (!invertedindex.empty()) {
            invertedindex.findFirst();
            while (!invertedindex.last()) {
                if (invertedindex.retrieve().content.equalsIgnoreCase(word))
                    return true;
                invertedindex.findNext();
            }
            // Check the last node
            if (invertedindex.retrieve().content.equalsIgnoreCase(word))
                return true;
        }
        return false;
    }

    /**
     * Displays the contents of the inverted index.
     * For each word, prints the word itself and the document IDs where it appears.
     */
    public void displayInvertedIndex() {
        if (invertedindex.empty()) {
            System.out.println("empty Documents");
            return;
        }

        invertedindex.findFirst();
        while (!invertedindex.last()) {
            System.out.println("-----------------------------");
            System.out.print("Word: " + invertedindex.retrieve().content);
            System.out.println(" Appeared "+ invertedindex.retrieve().docIds.size()+" Documents " );
            invertedindex.findNext();
        }

        // Print the last node
        System.out.println("\n-----------------------------");
        System.out.print("Word: " + invertedindex.retrieve().content);
        System.out.println(" Appeared "+ invertedindex.retrieve().docIds.size()+" Documents " );

    }
}