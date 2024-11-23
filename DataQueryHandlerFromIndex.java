/**
 * A handler class for querying documents using an Index.
 * Provides functionality for handling mixed, AND, and OR queries on a dataset.
 */
public class DataQueryHandlerFromIndex {


    static Index INDEX;

    /**
     * Constructor to initialize the handler with the provided Index.
     */
    public DataQueryHandlerFromIndex(Index INDEX) {
        this.INDEX = INDEX;
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
     * retrieving matching document IDs for each term, and merging results using AND logic.
     */
    public static LinkedList<Integer> ANDQuery(String Query) {
        LinkedList<Integer> First = new LinkedList<>();
        LinkedList<Integer> Second = new LinkedList<>();

        String Terms[] = Query.split("AND");
        if (Terms.length == 0)
            return First;

        First = INDEX.SearchDocumentsByTerm(Terms[0].trim().toLowerCase());

        for (int i = 1; i < Terms.length; i++) {
            Second = INDEX.SearchDocumentsByTerm(Terms[i].trim().toLowerCase());
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
     * retrieving matching document IDs for each term, and merging results using OR logic.
     */
    public static LinkedList<Integer> ORQuery(String Query) {
        LinkedList<Integer> First = new LinkedList<>();
        LinkedList<Integer> Second = new LinkedList<>();

        String Terms[] = Query.split("OR");
        if (Terms.length == 0)
            return First;

        First = INDEX.SearchDocumentsByTerm((Terms[0].trim().toLowerCase()));

        for (int i = 1; i < Terms.length; i++) {
            Second = INDEX.SearchDocumentsByTerm(Terms[i].trim().toLowerCase());
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