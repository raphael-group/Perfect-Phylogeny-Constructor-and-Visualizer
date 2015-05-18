package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.List;

public class StateTreeNode {
  private int state;
  private List<StateTreeNode> neighbors;
  
  public StateTreeNode(int n) {
    this.state = n;
    this.neighbors = new ArrayList<StateTreeNode>();
  }

  public Integer getState() {
    return state;
  }

  public List<StateTreeNode> getNeighbors() {
    return neighbors;
  }

  public void addNeighbor(StateTreeNode s) {
    neighbors.add(s);
  }
  
  @Override
  public String toString() {
    String str = "[" +  state + "->";
    for (StateTreeNode s : neighbors) str += s.getState() + ",";
    str += "]";
    return str;
  }
  
}
