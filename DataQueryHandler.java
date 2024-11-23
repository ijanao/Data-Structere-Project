/**
 * A handler class for querying an inverted index.
 * Provides functionality for handling mixed, AND, and OR queries on a dataset.
 */
public class DataQueryHandler {


    static InvertedIndex inverted;

    /**
     * Constructor to initialize the handler with the provided inverted index.
     */
    public DataQueryHandler(InvertedIndex inverted) {
        this.inverted = inverted;
    }

    /**
     * Processes a mixed query containing both AND and OR operators.
     * Splits the query by "OR" and processes each part as an AND query,
     * then combines results using OR logic.
     */
    public static LinkedList<Integer> MIXEDQuery(String Query) {
        LinkedList<Integer> FirstHalf = new LinkedList<>();
        LinkedList<Integer> SecondHalf = new LinkedList<>();

        // Return an empty list for an empty query
        if (Query.length() == 0)
            return FirstHalf;

        // Split the query by "OR" operators
        String OR[] = Query.split("OR");

        // Process the first part as an AND query
        FirstHalf = ANDQuery(OR[0]);

        // Process the remaining parts and combine them using OR
        for (int i = 1; i < OR.length; i++) {
            SecondHalf = ANDQuery(OR[i]);
            FirstHalf = ORQuery(FirstHalf, SecondHalf);
        }

        return FirstHalf;
    }

    /**
     * Processes an AND query string by splitting it into terms, checking their existence,
     * and merging results for each term using AND logic.
     */
    public static LinkedList<Integer> ANDQuery(String Q) {
        LinkedList<Integer> FirstHalf = new LinkedList<>();
        LinkedList<Integer> SecondHalf = new LinkedList<>();
        String terms[] = Q.split("AND");

        // Return an empty list for an empty query
        if (terms.length == 0)
            return FirstHalf;

        // Check the first term in the query
        boolean found = inverted.wordExist(terms[0].trim().toLowerCase());
        if (found) {
            FirstHalf = inverted.invertedindex.retrieve().docIds;
        }

        // Process the remaining terms and combine results using AND
        for (int k = 1; k < terms.length; k++) {
            found = inverted.wordExist(terms[k].trim().toLowerCase());
            if (found) {
                SecondHalf = inverted.invertedindex.retrieve().docIds;
            }
            FirstHalf = ANDQuery(FirstHalf, SecondHalf);
        }

        return FirstHalf;
    }

    /**
     * Combines two lists of document IDs using AND logic.
     * Returns a list of document IDs present in both input lists.
     */
    public static LinkedList<Integer> ANDQuery(LinkedList<Integer> First, LinkedList<Integer> Second) {
        LinkedList<Integer> Output = new LinkedList<>();

        if (First.empty() || Second.empty())
            return Output;

        // Iterate through the first list
        First.findFirst();
        while (true) {
            boolean Found = existsIn_Output(Output, First.retrieve());
            if (!Found) {
                // Check if the current item exists in the second list
                Second.findFirst();
                while (true) {
                    if (Second.retrieve().equals(First.retrieve())) {
                        Output.insert(First.retrieve());
                        break;
                    }
                    if (!Second.last())
                        Second.findNext();
                    else
                        break;
                }
            }

            if (!First.last())
                First.findNext();
            else
                break;
        }

        return Output;
    }

    /**
     * Processes an OR query string by splitting it into terms,
     * checking their existence, and merging results for each term using OR logic.
     */
    public static LinkedList<Integer> ORQuery(String Query) {
        LinkedList<Integer> FirstHalf = new LinkedList<>();
        LinkedList<Integer> SecondHalf = new LinkedList<>();
        String Terms[] = Query.split("OR");

        if (Terms.length == 0)
            return FirstHalf;

        // Check the first term in the query
        boolean Found = inverted.wordExist(Terms[0].trim().toLowerCase());
        if (Found) {
            FirstHalf = inverted.invertedindex.retrieve().docIds;
        } else {
            return FirstHalf;
        }

        // Process the remaining terms and combine results using OR
        for (int i = 1; i < Terms.length; i++) {
            Found = inverted.wordExist(Terms[i].trim().toLowerCase());
            if (Found) {
                SecondHalf = inverted.invertedindex.retrieve().docIds;
            } else {
                return FirstHalf;
            }
            FirstHalf = ORQuery(FirstHalf, SecondHalf);
        }

        return FirstHalf;
    }

    /**
     * Combines two lists of document IDs using OR logic.
     * Returns a list of document IDs present in either of the input lists.  */
    public static LinkedList<Integer> ORQuery(LinkedList<Integer> First, LinkedList<Integer> Second) {
        LinkedList<Integer> Output = new LinkedList<>();

        if (First.empty() && Second.empty())
            return Output;

        // Add elements from the first list
        First.findFirst();
        while (!First.empty()) {
            boolean Found = existsIn_Output(Output, First.retrieve());
            if (!Found)
                Output.insert(First.retrieve());
            if (!First.last())
                First.findNext();
            else
                break;
        }

        // Add elements from the second list
        Second.findFirst();
        while (!Second.empty()) {
            boolean Found = existsIn_Output(Output, Second.retrieve());
            if (!Found)
                Output.insert(Second.retrieve());
            if (!Second.last())
                Second.findNext();
            else
                break;
        }

        return Output;
    }

    /**
     * Checks if a given ID exists in the output list.
     */
    public static boolean existsIn_Output(LinkedList<Integer> Output, Integer ID) {
        if (Output.empty())
            return false;

        Output.findFirst();
        while (!Output.last()) {
            if (Output.retrieve().equals(ID)) {
                return true;
            }
            Output.findNext();
        }
        return Output.retrieve().equals(ID);
    }
}