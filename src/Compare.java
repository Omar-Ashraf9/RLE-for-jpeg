import java.util.Comparator;

class Compare implements Comparator<Node> 
{ 
    public int compare(Node x, Node y) 
    { 
  
        return x.freq - y.freq; 
    } 
} 