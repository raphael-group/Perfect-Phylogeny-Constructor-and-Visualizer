package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaxaNode {
  private List<Integer> genome;
  private List<Integer> binarygenome;
  private List<Edge> neighbors;
  private boolean isTaxa;
  
  public TaxaNode(List<Integer> l) {
    this.setGenome(l);
    this.setBinarygenome(new ArrayList<Integer>());
    this.setNeighbors(new ArrayList<Edge>());
  }
  
  public TaxaNode(List<Integer> l, boolean b) {
    this.setGenome(l);
    this.setBinarygenome(new ArrayList<Integer>());
    this.setNeighbors(new ArrayList<Edge>());
    this.setTaxa(b);
  }

  public List<Integer> getGenome() {
    return genome;
  }

  public void setGenome(List<Integer> genome) {
    this.genome = genome;
  }

  public List<Integer> getBinarygenome() {
    return binarygenome;
  }

  public void setBinarygenome(List<Integer> binarygenome) {
    this.binarygenome = binarygenome;
  }

  public List<Edge> getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(List<Edge> neighbors) {
    this.neighbors = neighbors;
  }

  public boolean hasNeighborWithMutation(int c) {
    Iterator<Edge> it = neighbors.iterator();
    while (it.hasNext()) {
      if (it.next().getLabel().equals(c)) return true;
    }
    return false;
  }

  public Edge getNeighborWithMutation(int c) {
    Iterator<Edge> it = neighbors.iterator();
    while (it.hasNext()) {
      Edge curr = it.next();
      if (curr.getLabel().equals(c)) return curr;
    }
    return null;
  }

  public void addNeighbor(Edge e) {
    this.neighbors.add(e);
    
  }

  public boolean isTaxa() {
    return isTaxa;
  }

  public void setTaxa(boolean isTaxa) {
    this.isTaxa = isTaxa;
  }
  
  @Override
  public String toString() {
    String toReturn = "(" + genome;
    for (int i=0; i<neighbors.size(); i++) {
      toReturn += neighbors.get(i).getOther(this);
    }
    toReturn += ")";
    return toReturn;
  }

  public List<Integer> getGenomeCopy() {
    ArrayList<Integer> l = new ArrayList<Integer>();
    l.addAll(genome);
    return l;
  }

  
}
