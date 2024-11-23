import java.util.*;

public class Ranking {
    public String Query;
    public InvertedIndex_BST invertedBST;
    public Index index;
    public LinkedList<Integer> AllDocInQuery;
    public LinkedList<Rank> AllDocRanked;

    // Constructor to initialize the ranking system with the necessary indices and query.
    public Ranking(InvertedIndex_BST invertedBST, Index index, String Query) {
        this.invertedBST = invertedBST;
        this.index = index;
        this.Query = Query;
        AllDocInQuery = new LinkedList<>();
        AllDocRanked = new LinkedList<>();
    }

    // Displays all ranked documents in a table format (Document ID and Score).
    public void DisplayDocuments() {
        if (AllDocRanked.empty()) {
            System.out.println("empty");
            return;
        }
        System.out.printf("%-10s%-10s\n", "DocId", "Score");
        AllDocRanked.findFirst();
        while (!AllDocRanked.last()) {
            AllDocRanked.retrieve().displayRank();
            AllDocRanked.findNext();
        }
        AllDocRanked.retrieve().displayRank();
    }

    // Retrieves a document from the index using its ID.
    public Document getDocGivenId(int id) {
        return index.GetDocument(id);
    }

    // Calculates the frequency of a term in a specific document.
    public  int FrequencyOfTerm(Document d, String term) {
        int Frequency = 0;
        LinkedList<String> words = d.words;

        if (words.empty())
            return 0;

        words.findFirst();
        while (!words.last()) {
            if (words.retrieve().equalsIgnoreCase(term))
                Frequency++;
            words.findNext();
        }
        if (words.retrieve().equalsIgnoreCase(term))
            Frequency++;
        return Frequency;
    }

    // Calculates the rank score of a document based on the given query.
    public int GetDocRankScore(Document d, String Query) {
        if (Query.length() == 0)
            return 0;

        String[] WORDS = Query.split(" ");
        int SUM = 0;

        for (String word : WORDS) {
            SUM += FrequencyOfTerm(d, word.trim().toLowerCase());
        }
        return SUM;
    }

    // Processes the query and retrieves relevant documents, adding them to the sorted list.
    public void RankingQuery(String Query) {
        LinkedList<Integer> query = new LinkedList<>();
        if (Query.length() == 0)
            return;

        String[] WORDS = Query.split("\\s+");
        for (String word : WORDS) {
            boolean Located = invertedBST.SearchWordBST(word.trim().toLowerCase());
            if (Located)
                query = invertedBST.invertedindexBST.retrieve().docIds;
            addSorted(query);
        }
    }

    // Inserts a document ID into the list of document IDs in sorted order.
    public void InsertSortedID(int ID) {
        if (AllDocInQuery.empty()) {
            AllDocInQuery.insert(ID);
            return;
        }
        AllDocInQuery.findFirst();
        while (!AllDocInQuery.last()) {
            if (ID < AllDocInQuery.retrieve()) {
                Integer ID1 = AllDocInQuery.retrieve();
                AllDocInQuery.update(ID);
                AllDocInQuery.insert(ID1);
                return;
            } else
                AllDocInQuery.findNext();
        }
        if (ID < AllDocInQuery.retrieve()) {
            Integer ID2 = AllDocInQuery.retrieve();
            AllDocInQuery.update(ID);
            AllDocInQuery.insert(ID2);
        } else
            AllDocInQuery.insert(ID);
    }

    // Adds a list of document IDs to the main query result list in sorted order.
    public void addSorted(LinkedList<Integer> A) {
        if (A.empty())
            return;
        A.findFirst();
        while (!A.empty()) {
            boolean found = CheckResults(AllDocInQuery, A.retrieve());
            if (!found) {
                InsertSortedID(A.retrieve());
            }
            if (!A.last())
                A.findNext();
            else
                break;
        }
    }

    // Sorts and ranks the documents based on the query and their relevance scores.
    public void InsertSortedList() {
        RankingQuery(Query);
        if (AllDocInQuery.empty()) {
            System.out.println("empty Query");
            return;
        }

        AllDocInQuery.findFirst();
        while (!AllDocInQuery.last()) {
            Document d = getDocGivenId(AllDocInQuery.retrieve());
            int Rank = GetDocRankScore(d, Query);
            InsertSort(new Rank(AllDocInQuery.retrieve(), Rank));
            AllDocInQuery.findNext();
        }
        Document d = getDocGivenId(AllDocInQuery.retrieve());
        int Rank = GetDocRankScore(d, Query);
        InsertSort(new Rank(AllDocInQuery.retrieve(), Rank));
    }

    // Checks if a document ID already exists in the results list.
    public boolean CheckResults(LinkedList<Integer> result, int id) {
        if (result.empty())
            return false;
        result.findFirst();
        while (!result.last()) {
            if (result.retrieve().equals(id))
                return true;
            result.findNext();
        }
        return result.retrieve().equals(id);
    }

    // Inserts a ranked document into the ranked list, maintaining descending order of scores.
    public void InsertSort(Rank r) {
        if (AllDocRanked.empty()) {
            AllDocRanked.insert(r);
            return;
        }
        AllDocRanked.findFirst();
        while (!AllDocRanked.last()) {
            if (r.rank > AllDocRanked.retrieve().rank) {
                Rank ranked = AllDocRanked.retrieve();
                AllDocRanked.update(r);
                AllDocRanked.insert(ranked);
                return;
            } else
                AllDocRanked.findNext();
        }
        if (r.rank > AllDocRanked.retrieve().rank) {
            Rank ranked = AllDocRanked.retrieve();
            AllDocRanked.update(r);
            AllDocRanked.insert(ranked);
        } else
            AllDocRanked.insert(r);
    }
}