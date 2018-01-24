import java.lang.IllegalArgumentException;

public class Percolation
{
  private int _gridSize;
  private int _topIndex;
  private int _bottomIndex;
  private boolean _grid[][];
  private QuickUnionFind _quf;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n_) throws IllegalArgumentException
  {
    if (n_ <= 0)
    {
      throw new IllegalArgumentException();
    }
    _gridSize = n_;
    _grid = new boolean[n_][n_];
    _quf = new QuickUnionFind(n_ * n_ + 2);
    _topIndex = n_*n_;
    _bottomIndex = n_*n_ + 1;

    for (int index=0; index < _gridSize; ++index)
    {
      _quf.union(_topIndex, index);
      _quf.union(_bottomIndex, (_gridSize * _gridSize - 1 - index));
    }
  }

  private void validate(int row_, int col_) throws IllegalArgumentException
  {
    if ((row_ < 1 || row_ > _gridSize) || (col_ < 1 || col_ > _gridSize))
    {
      throw new IllegalArgumentException("Invalid row/column: " + row_ + "/" + col_);
    }
  }

  private int getArrayIndex(int row_, int col_)
  {
    return _gridSize * row_ + col_;
  }

  // open site (row, col) if it is not open already
  public void open(int row_, int col_) throws IllegalArgumentException
  {
    validate(row_, col_);
    int rowIndex = row_ - 1;
    int colIndex = col_ - 1;
    _grid[rowIndex][colIndex] = true;

    // left neighbour
    if ((colIndex > 0) && (_grid[rowIndex][colIndex-1]))
    {
      _quf.union(getArrayIndex(rowIndex, colIndex), getArrayIndex(rowIndex, colIndex-1));
    }

    // right neighbour
    if ((colIndex < (_gridSize-1)) && (_grid[rowIndex][colIndex+1]))
    {
      _quf.union(getArrayIndex(rowIndex, colIndex), getArrayIndex(rowIndex, colIndex+1));
    }

    // top neighbour
    if ((rowIndex > 0) && (_grid[rowIndex-1][colIndex]))
    {
      _quf.union(getArrayIndex(rowIndex, colIndex), getArrayIndex(rowIndex-1, colIndex));
    }

    // bottom neighbour
    if ((rowIndex < (_gridSize-1)) && (_grid[rowIndex+1][colIndex]))
    {
      _quf.union(getArrayIndex(rowIndex, colIndex), getArrayIndex(rowIndex+1, colIndex));
    }
  }

  // is site (row, col) open?
  public boolean isOpen(int row_, int col_) throws IllegalArgumentException
  {
    validate(row_, col_);
    return _grid[row_ - 1][col_ - 1];
  }

  // is site (row, col) full?
  public boolean isFull(int row_, int col_)
  {
    return !isOpen(row_, col_);
  }

  // number of open sites
  public int numberOfOpenSites()
  {
    int counter = 0;
    for (int row = 0; row < _gridSize; ++row)
    {
      for (int col=0; col < _gridSize; ++col)
      {
        if (_grid[row][col])
        {
          counter +=1;
        }
      }
    }
    return counter;
  }

  // does the system percolate?
  public boolean percolates()
  {
    return _quf.connected(_topIndex, _bottomIndex);
  }

  private class QuickUnionFind
  {
    private int[] id;

    QuickUnionFind(int n_)
    {
      id = new int[n_];
      for (int index=0; index < n_; ++index)
      {
        id[index] = index;
      }
    }

    private int root(int i)
    {
      while (i != id[i])
      {
        i = id[i];
      }

      return i;
    }

    boolean connected(int index1_, int index2_)
    {
      return root(index1_) == root(index2_);
    }

    void union(int index1_, int index2_)
    {
      int rootOfIndex1 = root(index1_);
      int rootOfIndex2 = root(index2_);

      id[rootOfIndex1] = rootOfIndex2;
    }
  }
}