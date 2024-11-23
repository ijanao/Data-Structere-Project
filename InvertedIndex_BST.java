/**
 * A class representing an Inverted Index implemented using a Binary Search Tree (BST).
 * Maps words to the list of document IDs where they appear, leveraging the BST for efficient search and traversal.
 */
public class InvertedIndex_BST {

    // A Binary Search Tree to store Word objects
    BST<Word> invertedindexBST;

    /**
     * Constructor to initialize the inverted index using a BST.
     */
    public InvertedIndex_BST() {
        invertedindexBST = new BST<>();
    }



    /**
     * Adds a word to the BST-based inverted index and associates it with a document ID.
     * If the word already exists, the document ID is appended to the existing word entry.
     * If the word does not exist, a new entry is created.
     */
    public void add(String text, int id) {
        if (!SearchWordBST(text)) {
            Word w = new Word(text);
            w.docIds.insert(id);
            invertedindexBST.insert(text, w);
        } else {
            Word existing_content = invertedindexBST.retrieve();
            existing_content.add_ID(id);
        }
    }

    /**
     * Searches for a word in the BST-based inverted index.
     */
    public boolean SearchWordBST(String w) {
        return invertedindexBST.findkey(w);
    }


}