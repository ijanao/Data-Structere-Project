

public class Document {
    LinkedList<String>words=new LinkedList<String>();
    int id;

    String content;

    Document(int id,LinkedList<String> words,String content ){
        this.id=id;
        this.words=words;
        this.content=content;
    }




}