package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;

public class RandomTree {
  
  public static ArrayList<ArrayList<Integer>> generateMatrix(int split, int maxState) {
    ArrayList<ArrayList<Integer>> toReturn = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> root = new ArrayList<Integer>();
    ArrayList<Integer> spot = new ArrayList<Integer>();
    for (int i=0; i<10; i++) {
      root.add(0);
      spot.add(0);
    }
    toReturn.add(root);
    for (int i=0; i<split; i++) {
      ArrayList<Integer> genome = toReturn.get((int) Math.floor(Math.random() * toReturn.size()));
      int s = (int) Math.floor(Math.random() * 10);
      if (genome.get(s) == spot.get(s) && genome.get(s) < maxState) {
        ArrayList<Integer> newGenome = (ArrayList<Integer>) genome.clone();
        newGenome.set(s, genome.get(s) + 1);
        spot.set(s, spot.get(s) + 1);
        toReturn.add(newGenome);
      }
    }
    
    
    
    return toReturn;
  }
  
  public static String format(ArrayList<ArrayList<Integer>> a) {
    StringBuilder builder = new StringBuilder();
    for (int i=0; i<a.size(); i++) {
      for (int j=0; j<a.get(i).size(); j++) {
        builder.append(a.get(i).get(j));
        if (j != a.get(i).size() - 1) builder.append(" ");
      }
      if (i != a.size() - 1) builder.append("\n");
    }
    return builder.toString();
  }
  
  public static ArrayList<ArrayList<Integer>> filter(ArrayList<ArrayList<Integer>> l, int n) {
    ArrayList<ArrayList<Integer>> toReturn = (ArrayList<ArrayList<Integer>>) l.clone();
    
    while (toReturn.size() > n) {
      toReturn.remove((int) Math.floor(Math.random() * toReturn.size()));
    }
    return toReturn;
  }
  
  public static void main(String[] args) {
    System.out.print(format(filter(generateMatrix(10000, 5), 100)));
  }
}
