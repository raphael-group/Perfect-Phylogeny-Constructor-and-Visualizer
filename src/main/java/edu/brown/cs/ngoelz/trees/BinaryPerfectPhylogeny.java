package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryPerfectPhylogeny {
  private PhylogenyMatrix m;
  public TaxaNode root;
  public boolean fourGametes;
  
  public BinaryPerfectPhylogeny(List<List<Integer>> l) {
    this.m = new PhylogenyMatrix(l, l.get(0).size());
    this.fourGametes = m.checkForFourGametes();
    if (!fourGametes) {
      this.root = new TaxaNode(m.getM().get(0), true);
      System.out.println(m.getM());
      createTree(this.root, m);
    }
  }
  
  public BinaryPerfectPhylogeny(PhylogenyMatrix m) {
    this.m = m;
    this.root = new TaxaNode(m.getM().get(0), true);
    createTree(this.root, m);
  }
  
  public void createTree(TaxaNode r, PhylogenyMatrix mat) {
    //order characters
    List<List<Integer>> bins = new ArrayList<List<Integer>>();
    for (int i = 0; i <= mat.getNumber_of_taxa(); i++) {
      bins.add(new ArrayList<Integer>());
    }
    for (int i = 0; i < mat.getNumber_of_characters(); i++) {
      int count = 0;
      for (int j = 0; j < mat.getNumber_of_taxa(); j++) {
        if (mat.getM().get(j).get(i) != r.getGenome().get(i)) count++;
      }
      bins.get(count).add(i);
    }
    System.out.println(bins);
    List<Integer> order = new ArrayList<Integer>();
    for (int i=bins.size() - 1; i>=0; i--) {
      for (int j=0; j<bins.get(i).size(); j++) {
        order.add(bins.get(i).get(j));
      }
    }
    System.out.println(order);
    //now with the order we can thread each taxa through the root
    for (int i=1; i<mat.getNumber_of_taxa(); i++) {
      System.out.println(root);
      TaxaNode treeNode = r;
      List<Integer> threadedGenome = mat.getM().get(i);
      for (int z=0; z<order.size(); z++) {
        int c = order.get(z);
        if (treeNode.getGenome().get(c) != threadedGenome.get(c)) {
          if (treeNode.hasNeighborWithMutation(c)) {
            Edge e = treeNode.getNeighborWithMutation(c);
            treeNode = e.getOther(treeNode);
          } else {
            if (treeNode.isTaxa()) {
              treeNode.setTaxa(false);
              TaxaNode t = new TaxaNode(treeNode.getGenome(), true);
              Edge e = new Edge(treeNode, t, -1);
              treeNode.addNeighbor(e);
             // t.addNeighbor(e);
            }
            List<Integer> newGenome = treeNode.getGenomeCopy();
            if (newGenome.get(c) == 0) newGenome.set(c, 1);
            else newGenome.set(c, 0);
            TaxaNode next = new TaxaNode(newGenome, false);
            Edge e = new Edge(treeNode, next, c);
            treeNode.addNeighbor(e);
            //next.addNeighbor(e);
            treeNode = next;
          }
        }
      }
      TaxaNode leaf = new TaxaNode(mat.getM().get(i), true);
      Edge e = new Edge(treeNode, leaf, -1);
      treeNode.addNeighbor(e);
      //leaf.addNeighbor(e);
      
    }
  }
  
  public Map<String, List<String>> forGraph() {
    HashMap<TaxaNode, List<Integer>> numbers = new HashMap<TaxaNode, List<Integer>>();
    List<Tuple<TaxaNode, Integer>> queue = new ArrayList<Tuple<TaxaNode, Integer>>();
    queue.add(new Tuple<TaxaNode, Integer>(root, 0));
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
    System.out.println(toReturn);
    return toReturn;
    
  }
  
  @Override
  public String toString() {
    return root.toString();
  }
  
  public static void main(String[] args) {
    //000
    List<Integer> a = new ArrayList<Integer>();
    a.add(0);
    a.add(0);
    a.add(0);
    a.add(1);
    a.add(1);
    a.add(0);
    a.add(0);
    a.add(0);
    a.add(1);
    //001
    List<Integer> b = new ArrayList<Integer>();
    b.add(0);
    b.add(0);
    b.add(0);
    b.add(1);
    b.add(1);
    b.add(1);
    b.add(0);
    b.add(1);
    b.add(1);
    //100
    List<Integer> c = new ArrayList<Integer>();
    c.add(1);
    c.add(1);
    c.add(0);
    c.add(0);
    c.add(0);
    c.add(1);
    c.add(0);
    c.add(0);
    c.add(1);
    List<Integer> d = new ArrayList<Integer>();
    d.add(1);
    d.add(1);
    d.add(0);
    d.add(1);
    d.add(1);
    d.add(1);
    d.add(1);
    d.add(0);
    d.add(0);
    List<Integer> e = new ArrayList<Integer>();
    e.add(1);
    e.add(1);
    e.add(1);
    e.add(1);
    e.add(1);
    e.add(1);
    e.add(0);
    e.add(0);
    e.add(1);
    
    List<List<Integer>> l = new ArrayList<List<Integer>>();
    l.add(a);
    l.add(b);
    l.add(c);
    l.add(d);
    l.add(e);
    
    BinaryPerfectPhylogeny p = new BinaryPerfectPhylogeny(l);
    System.out.println(p);
  }
  
}
