package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.List;

public class PhylogenyMatrix {
  private int number_of_taxa;
  private int number_of_characters;
  private List<List<Integer>> m;
  
  public PhylogenyMatrix(List<List<Integer>> l, int n) {
     this.setNumber_of_taxa(l.size());
     this.setNumber_of_characters(n);
     this.setM(l);
  }

  public int getNumber_of_taxa() {
    return number_of_taxa;
  }

  public boolean checkForFourGametes() {
    boolean fourGametes = false;
    for (int i=0; i<number_of_characters-1; i++) {
      for (int j=i+1; j<number_of_characters; j++) {
        System.out.println(i + " " + j);

        boolean zero_zero = false;
        boolean zero_one = false;
        boolean one_zero = false;
        boolean one_one = false;
        for (int t = 0; t<number_of_taxa; t++) {
          if (m.get(t).get(i) == 1) {
            if (m.get(t).get(j) == 1) {
              System.out.println("1 1 " + t);
              one_one = true;
            } else {
              System.out.println("1 0 " + t);
              one_zero = true;
            }
          } else {
            if (m.get(t).get(j) == 1) {
              System.out.println("0 1 " + t);
              zero_one = true;
            } else {
              System.out.println("0 0 " + t);
              zero_zero = true;
            }
          }
          
        }
        if (zero_zero && zero_one && one_zero && one_one) {
          System.out.println("busted!");
          fourGametes = true;
        }
        zero_zero = false;
        zero_one = false;
        one_zero = false;
        one_one = false;
      }
    }
    return fourGametes;
  }
  
  public void setNumber_of_taxa(int number_of_taxa) {
    this.number_of_taxa = number_of_taxa;
  }

  public int getNumber_of_characters() {
    return number_of_characters;
  }

  public void setNumber_of_characters(int number_of_characters) {
    this.number_of_characters = number_of_characters;
  }

  public List<List<Integer>> getM() {
    return m;
  }

  public void setM(List<List<Integer>> m) {
    this.m = m;
  }
  
  public PhylogenyMatrix convert(List<StateTree> s) {
    ArrayList<List<Integer>> l = new ArrayList<List<Integer>>();
    for (int i=0; i<number_of_taxa; i++) {
      l.add(new ArrayList<Integer>());
    }
    for (int c=0; c<number_of_characters; c++) {
      StateTree stree = s.get(c);
      int states = stree.getNumberOfStates();
      for (int i=0; i<states-1; i++) {
        for (int j=i+1; j<states; j++) {
          List<Integer> zeroPartition = stree.partition(i, j);
          System.out.println(c + ":  " + i + ", " + j + zeroPartition);
          for (int k=0; k<number_of_taxa; k++) {
            if (zeroPartition.contains(m.get(k).get(c))) l.get(k).add(0);
            else l.get(k).add(1);
          }
        }
      }
    }
    
    return new PhylogenyMatrix(l, l.get(0).size());
  }
  

}
