/**
 * A handler class for querying an inverted index implemented using a Binary Search Tree (BST).
 * Provides functionality for handling mixed, AND, and OR queries on a dataset.
 */
public class DataQueryHandlerBST {


    static InvertedIndex_BST inverted;

    /**
     * Constructor to initialize the handler with the provided inverted index (BST-based).
     */
    public DataQueryHandlerBST(InvertedIndex_BST inverted) {
        this.inverted = inverted;
    }

    /**
     * Processes a mixed query containing both AND and OR operators.
     * Splits the query by "OR" and processes each part as an AND query,
     * then combines results using OR logic.
     */
    public static LinkedList<Integer> MIXEDQuery(String Query) {
        LinkedList<Integer> First = new LinkedList<>();
        LinkedList<Integer> Second = new LinkedList<>();

        if (Query.length() == 0)
            return First;

        String OR[] = Query.split("OR");
        First = ANDQuery(OR[0]);

        for (int i = 1; i < OR.length; i++) {
            Second = ANDQuery(OR[i]);
            First = ORQuery(First, Second);
        }

        return First;
    }

    /**
     * Processes an AND query string by splitting it into terms,
     * checking their existence in the BST, and merging results for each term using AND logic.
     */
    public static LinkedList<Integer> ANDQuery(String Query) {
        LinkedList<Integer> First = new LinkedList<>();
        LinkedList<Integer> Second = new LinkedList<>();

        String Terms[] = Query.split("AND");
        if (Terms.length == 0)
            return First;

        boolean Found = inverted.SearchWordBST(Terms[0].trim().toLowerCase());
        if (Found)
            First = inverted.invertedindexBST.retrieve().docIds;
        else
            return First;

        for (int i = 1; i < Terms.length; i++) {
            Found = inverted.SearchWordBST(Terms[i].trim().toLowerCase());
            if (Found)
                Second = inverted.invertedindexBST.retrieve().docIds;
            else
                return First;

            First = ANDQuery(First, Second);
        }

        return First;
    }

    /**
     * Combines two lists of document IDs using AND logic.
     * Returns a list of document IDs present in both input lists.
     */
    public static LinkedList<Integer> ANDQuery(LinkedList<Integer> First, LinkedList<Integer> Second) {
        LinkedList<Integer> Output = new LinkedList<>();

        if (First.empty() || Second.empty())
            return Output;

        First.findFirst();
        while (true) {
            boolean Found = existsIn_Output(Output, First.retrieve());
            if (!Found) {
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
     * checking their existence in the BST, and merging results for each term using OR logic.
     */
    public static LinkedList<Integer> ORQuery(String Query) {
        LinkedList<Integer> First = new LinkedList<>();
        LinkedList<Integer> Second = new LinkedList<>();

        String Terms[] = Query.split("OR");
        if (Terms.length == 0)
            return First;

        boolean Found = inverted.SearchWordBST(Terms[0].trim().toLowerCase());
        if (Found)
            First = inverted.invertedindexBST.retrieve().docIds;
        else
            return First;

        for (int i = 1; i < Terms.length; i++) {
            Found = inverted.SearchWordBST(Terms[i].trim().toLowerCase());
            if (Found)
                Second = inverted.invertedindexBST.retrieve().docIds;
            else
                return First;

            First = ORQuery(First, Second);
        }

        return First;
    }

    /**
     * Combines two lists of document IDs using OR logic.
     * Returns a list of document IDs present in either of the input lists.
     */
    public static LinkedList<Integer> ORQuery(LinkedList<Integer> First, LinkedList<Integer> Second) {
        LinkedList<Integer> Output = new LinkedList<>();

        if (First.empty() && Second.empty())
            return Output;

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