package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StateTree {
  private HashMap<Integer, StateTreeNode> nodes;
  private int states;
  
  public StateTree(List<Tuple<Integer, Integer>> l, int states) {
    System.out.println(states);
    this.states = states;
    this.nodes = new HashMap<Integer, StateTreeNode>();
    for (int i=0; i<states; i++) {
      nodes.put(i, new StateTreeNode(i));
    }
    for (Tuple<Integer, Integer> t : l) {
      nodes.get(t.getA()).addNeighbor(nodes.get(t.getB()));
      nodes.get(t.getB()).addNeighbor(nodes.get(t.getA()));
    }
  }
  
  public List<Integer> partition(int i, int j) {
   HashMap<Integer, Integer> iDistance = getDistancesFrom(i);
   HashMap<Integer, Integer> jDistance = getDistancesFrom(j);
   ArrayList<Integer> toReturn = new ArrayList<Integer>();
   for (int k=0; k<states; k++) {
     if (iDistance.get(k) < jDistance.get(k)) toReturn.add(k);
   }
   return toReturn;
  }


  private HashMap<Integer, Integer> getDistancesFrom(int i) {
    HashMap<Integer, Integer> toReturn = new HashMap<Integer, Integer>();
    int count = 0;
    List<Tuple<Integer, StateTreeNode>> queue = new ArrayList<Tuple<Integer, StateTreeNode>>();
    queue.add(new Tuple<Integer, StateTreeNode>(0, nodes.get(i)));
    while(queue.size() > 0) {
      Tuple<Integer, StateTreeNode> temp = queue.get(0);
      int level = temp.getA();
      toReturn.put(temp.getB().getState(), level);
      queue.remove(0);
      for (StateTreeNode stn : temp.getB().getNeighbors()) {
        if (toReturn.get(stn.getState()) == null) queue.add(new Tuple<Integer, StateTreeNode>(level + 1, stn));
      }
      count++;
    }
    return toReturn;
  }


  public int getNumberOfStates() {
    return states;
  }
  
  

}
