/**
 * A class to manage and index documents.
 * Provides functionality to store, retrieve, and search documents based on terms.
 */
public class Index {

    // A linked list to store all documents
    LinkedList<Document> allDOCS;

    /**
     * Constructor to initialize the document index.
     */
    public Index() {
        allDOCS = new LinkedList<>();
    }



    /**
     * Retrieves a document by its ID.
     * Returns the document if found, otherwise returns null.
     */
    public Document GetDocument(int id) {
        if (!allDOCS.empty()) {
            allDOCS.findFirst();

            while (!allDOCS.last()) {
                if (allDOCS.retrieve().id == id)
                    return allDOCS.retrieve();
                allDOCS.findNext();
            }

            // Check the last document
            if (allDOCS.retrieve().id == id)
                return allDOCS.retrieve();
        } else {
            System.out.println("There Is No Documents");
        }
        return null;
    }

    /**
     * Searches for documents containing a specific term.
     * Returns a list of document IDs where the term exists.
     */
    public LinkedList<Integer> SearchDocumentsByTerm(String term) {
        LinkedList<Integer> result = new LinkedList<>();

        if (allDOCS.empty()) {
            System.out.println("There is no Documents");
            return result;
        }

        allDOCS.findFirst();
        while (!allDOCS.last()) {
            if (allDOCS.retrieve().words.exist(term.toLowerCase().trim())) {
                result.insert(allDOCS.retrieve().id);
            }
            allDOCS.findNext();
        }

        // Check the last document
        if (allDOCS.retrieve().words.exist(term.toLowerCase().trim())) {
            result.insert(allDOCS.retrieve().id);
        }

        return result;
    }
}