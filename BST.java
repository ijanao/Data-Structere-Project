public class BST <T> {
    private BSTNode <T> root ;
    private BSTNode <T> current ;
    
    public BST() {
        root =current= null ;

    }
    
    
    public boolean empty() {
        return root == null;
    }
    
    public boolean full() {
        return false;
    }
    
      public T retrieve() {
        return current.data;
    }
      
    public boolean findkey(String k) 
    { 
    BSTNode<T> p= root;
       while(p!= null) {
        current = p ;
  
        if(k.compareToIgnoreCase(p.key)==0){
            return true;
        }else if(k.compareToIgnoreCase(p.key)<0){
         p = p.left;
        }else{
         p = p.right;
        }
        }
        return false;
    }

    
    
    public boolean insert (String k, T val) 
    {
        if(root == null){
            current = root = new BSTNode<T>(k , val) ;
            return true ;
        }
        BSTNode<T> p= current;
        if(findkey(k)) {
            current = p; 
            return false; 
        }
        BSTNode<T> tmp = new BSTNode<T>(k , val) ;
        if(k.compareToIgnoreCase(current.key)<0){
            current.left = tmp ;
        }else {
            current.right = tmp ;
        }
        current = tmp ;
        return true ;
}
        
     public void inOrder()
    {
        if(root==null)
            System.out.println("Empty Tree");
        else
            inOrder(root);
    }
    
    private void inOrder(BSTNode p)
    {
        if(p==null)
        return;
        inOrder(p.left);
        System.out.println("key= " +p.key) ;
        ((Word)p.data).DisplayWordInfo();
        inOrder(p.right);
               
    }
    
    public void preOrder(){
    if (root==null)
        System.out.println("Empty Tree");
    else
       preOrder(root); 
    }
    
    
    private void preOrder(BSTNode p){
    if(p==null) return;
      System.out.println("key= "+p.key);
      System.out.println(p.data.toString());
      preOrder(p.left);
      preOrder(p.right);
    }

}