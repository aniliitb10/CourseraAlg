import edu.princeton.cs.algs4.StdIn;

public class Test
{
  // test client (optional)
  public static void main(String[] args_)
  {
    int gridSize = StdIn.readInt();
    Percolation percolation = new Percolation(gridSize);
    while(!StdIn.isEmpty())
    {
      percolation.open(StdIn.readInt(), StdIn.readInt());
    }

    System.out.println("The number of open sites: " + percolation.numberOfOpenSites());

    System.out.println(percolation.isOpen(2 ,2));

    System.out.println(percolation.percolates());
  }
}
