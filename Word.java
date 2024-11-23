public class Word {
    String content;
    LinkedList<Integer>docIds;

    public Word(String content) {
        this.content = content;
        docIds = new LinkedList<Integer>();
    }

    public void add_ID(int id){

        docIds.insert(id);
    }


    public void DisplayWordInfo(){

        System.out.println("Word: "+content);
        if (!docIds.empty()){

            docIds.findFirst();

            System.out.print("[");
            while (!docIds.last())
            {
                System.out.print(docIds.retrieve() +",");
                docIds.findNext();
            }



            System.out.print(docIds.retrieve());
            System.out.println("]");


        }
        else
            System.out.println("The Document Id List Is Empty!!");


    }



}