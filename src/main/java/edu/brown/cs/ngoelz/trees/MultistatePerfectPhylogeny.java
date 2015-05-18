package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MultistatePerfectPhylogeny {
  PhylogenyMatrix multistateMatrix;
  PhylogenyMatrix binaryMatrix;
  BinaryPerfectPhylogeny binaryTree;
  List<StateTree> stateTrees;
  List<HashMap<String, Integer>> key;
  Integer states;
  TaxaNode multistateRoot;
  boolean fourGametes;
  
  public MultistatePerfectPhylogeny(List<List<Integer>> l, List<List<Tuple<Integer, Integer>>> sts, int states) {
    this.multistateMatrix = new PhylogenyMatrix(l, l.get(0).size());
    this.states = states;
    this.stateTrees = new ArrayList<StateTree>();
    for (int i = 0; i < sts.size(); i++) {
      StateTree temp = new StateTree(sts.get(i), states);
      stateTrees.add(temp);
    }
    this.binaryMatrix = multistateMatrix.convert(stateTrees);
    this.key = new ArrayList<HashMap<String, Integer>>();
    constructKey();
    this.binaryTree = new BinaryPerfectPhylogeny(binaryMatrix);
    this.fourGametes = binaryTree.fourGametes;
    if (!fourGametes) {
      this.multistateRoot = /*binaryTree.root;//*/convertTree(binaryTree);
      System.out.println("pre shrink\n" + multistateRoot);
      shrinkTree(multistateRoot);
    }
  }
  
  public void constructKey() {
    for (int i=0; i<multistateMatrix.getNumber_of_characters(); i++) {
      HashMap<String, Integer> m = new HashMap<String, Integer>();
      for (int j=0; j<multistateMatrix.getNumber_of_taxa(); j++) {
        int val = multistateMatrix.getM().get(j).get(i);
        StringBuilder toKey = new StringBuilder();
        int seg = binaryMatrix.getNumber_of_characters() / multistateMatrix.getNumber_of_characters();
        for (int n=0; n<seg; n++) { 
          toKey.append(binaryMatrix.getM().get(j).get(i * seg + n));
        }
        m.put(toKey.toString(), val);
      }
      key.add(m);
    }
    System.out.println(key);
  }
  
  public TaxaNode convertTree(BinaryPerfectPhylogeny b) {
    return convertNode(b.root);
  }
  
  public TaxaNode convertNode(TaxaNode t) {
    TaxaNode curr = new TaxaNode(convertGenome(t.getGenome()), t.isTaxa());
    
    for (int i=0; i<t.getNeighbors().size(); i++) {
      curr.addNeighbor(new Edge(curr, convertNode(t.getNeighbors().get(i).getOther(t)), -1));
    }
    
    return curr;
  }
  
  public void shrinkTree(TaxaNode curr) {
    Edge needsShrink = null;
    for (Edge e : curr.getNeighbors()) if (e.getOther(curr).getGenome().contains(-1)) needsShrink = e;
    while (needsShrink != null) {
      TaxaNode toRemove = needsShrink.getOther(curr);
      for (Edge e : toRemove.getNeighbors()) {
        if (e.getA() == toRemove) e.setA(curr);
        else if (e.getB() == toRemove) e.setB(curr);
        curr.addNeighbor(e);
      }
      curr.getNeighbors().remove(needsShrink);
      needsShrink = null;
      for (Edge e : curr.getNeighbors()) if (e.getOther(curr).getGenome().contains(-1)) needsShrink = e;
    }
    for (Edge e : curr.getNeighbors()) shrinkTree(e.getOther(curr));
 
  }
  
  public List<Integer> convertGenome(List<Integer> g) {
    int segment = binaryMatrix.getNumber_of_characters() / multistateMatrix.getNumber_of_characters();
    List<Integer> r = new ArrayList<Integer>();
    for (int i=0; i<multistateMatrix.getNumber_of_characters(); i++) {
      StringBuilder s = new StringBuilder();
      for (int n=0; n<segment; n++) {
        s.append(g.get(i * segment + n));
      }
      if (key.get(i).get(s.toString()) == null) {
        r.add(-1);
      }
      else r.add(key.get(i).get(s.toString()));
    }
    return r;
  }
  
  public Map<String, List<String>> forGraph() {
    System.out.println(multistateRoot);
    HashMap<TaxaNode, List<Integer>> numbers = new HashMap<TaxaNode, List<Integer>>();
    List<Tuple<TaxaNode, Integer>> queue = new ArrayList<Tuple<TaxaNode, Integer>>();
    queue.add(new Tuple<TaxaNode, Integer>(multistateRoot, 0));
    int currentLevel = 0;
    int levelCount = -1;
    while (!queue.isEmpty()) {
      Tuple<TaxaNode, Integer> temp = queue.get(0);
      queue.remove(0);
      if (temp.getB() != currentLevel) {
        currentLevel = temp.getB();
        levelCount = 0;
      } else {
        levelCount++;
      }
      if (numbers.get(temp) == null) {
        ArrayList<Integer> l = new ArrayList<Integer>();
        l.add(numbers.size());
        l.add(currentLevel);
        l.add(levelCount);
        numbers.put(temp.getA(), l);
      }
      for(Edge e : temp.getA().getNeighbors()) {
        queue.add(new Tuple<TaxaNode, Integer>(e.getOther(temp.getA()), temp.getB() + 1));
      }
    }
    HashMap<String, List<String>> toReturn = new HashMap<String, List<String>>();
    toReturn.put("nodes", new ArrayList<String>());
    toReturn.put("edges", new ArrayList<String>());
    
    for (TaxaNode t : numbers.keySet()) {
      StringBuilder str = new StringBuilder(numbers.get(t) + ":");
      for (int i : t.getGenome()) str.append(i);
      str.append(":" + t.getNeighbors().size());
      toReturn.get("nodes").add(str.toString());
      
      for (Edge e : t.getNeighbors()) {
        toReturn.get("edges").add(numbers.get(t).get(0) + "-" + numbers.get(e.getOther(t)).get(0));
      }
    }
    return toReturn;
    
  }
  
  public static void main(String[] args) {
    List<List<Tuple<Integer, Integer>>> sts = new ArrayList<List<Tuple<Integer, Integer>>>();
    sts.add(new ArrayList<Tuple<Integer, Integer>>());
    sts.get(0).add(new Tuple<Integer, Integer>(0,1));
    sts.get(0).add(new Tuple<Integer, Integer>(1,2));
    sts.add(new ArrayList<Tuple<Integer, Integer>>());
    sts.get(1).add(new Tuple<Integer, Integer>(1,2));
    sts.get(1).add(new Tuple<Integer, Integer>(2,0));
    sts.add(new ArrayList<Tuple<Integer, Integer>>());
    sts.get(2).add(new Tuple<Integer, Integer>(2,0));
    sts.get(2).add(new Tuple<Integer, Integer>(0,1));
    
    List<List<Integer>> l = new ArrayList<List<Integer>>();
    l.add(new ArrayList<Integer>());
    l.get(0).add(0);
    l.get(0).add(1);
    l.get(0).add(0);
    l.add(new ArrayList<Integer>());
    l.get(1).add(0);
    l.get(1).add(2);
    l.get(1).add(2);
    l.add(new ArrayList<Integer>());
    l.get(2).add(1);
    l.get(2).add(0);
    l.get(2).add(0);
    l.add(new ArrayList<Integer>());
    l.get(3).add(1);
    l.get(3).add(2);
    l.get(3).add(1);
    l.add(new ArrayList<Integer>());
    l.get(4).add(2);
    l.get(4).add(2);
    l.get(4).add(0);
    
    System.out.println(l + "\n" + sts);
    MultistatePerfectPhylogeny m = new MultistatePerfectPhylogeny(l, sts, 3);
    System.out.println(m.binaryMatrix.getM());
    System.out.println(m.key);
    System.out.println(m.binaryTree.root);
    System.out.println(m.multistateRoot);

  }
}
