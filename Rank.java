public class Rank {
   public int id ;
   public int rank;

   public Rank(int id, int rank) {
      this.id = id;
      this.rank = rank;
   }

   public void displayRank(){
      System.out.printf("%-10d%-10d\n",id ,rank);
   }





}
