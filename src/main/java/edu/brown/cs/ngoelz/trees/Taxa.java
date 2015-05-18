package edu.brown.cs.ngoelz.trees;

import java.util.List;

public class Taxa {
  public List<Integer> genome;
  public int n;
  
  public Taxa(List<Integer> l) {
    this.genome = l;
    this.n = l.size();
  }
}
