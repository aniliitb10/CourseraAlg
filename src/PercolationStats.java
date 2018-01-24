import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats
{
  private int _trials;
  private double _trialStat[];

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n_, int trials_)
  {
    _trials = trials_;
    _trialStat = new double[_trials];

    for (int trialCount = 0; trialCount < _trials; ++trialCount)
    {
      int numOfTrials = 0;
      Percolation pc = new Percolation(n_);
      StdRandom.setSeed(java.lang.System.nanoTime());

      while(!pc.percolates())
      {
        int randomNum = StdRandom.uniform(1, n_ * n_);
        int row = (randomNum / n_) + 1;
        int col = randomNum % n_ + 1;

        if (!pc.isOpen(row, col))
        {
          pc.open(row, col);
          numOfTrials += 1;
        }
      }
      _trialStat[trialCount] = ((double) numOfTrials)/(n_*n_);
    }
  }

  // sample mean of percolation threshold
  public double mean()
  {
    return StdStats.mean(_trialStat);
  }

  // sample standard deviation of percolation threshold
  public double stddev()
  {
    return StdStats.stddev(_trialStat);
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo()
  {
    return mean() - (1.96 * stddev())/java.lang.Math.sqrt(_trials);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi()
  {
    return mean() + (1.96 * stddev())/java.lang.Math.sqrt(_trials);
  }

  // test client (described below)
  public static void main(String[] args)
  {
    while(!StdIn.isEmpty())
    {
      PercolationStats ps = new PercolationStats(StdIn.readInt(), StdIn.readInt());
      StdOut.println("mean\t\t\t\t\t= " + ps.mean());
      StdOut.println("stddev\t\t\t\t\t= " + ps.stddev());
      StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
  }
}
