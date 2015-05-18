package edu.brown.cs.ngoelz.trees;

public class Edge {
  private Integer label;
  private TaxaNode a;
  private TaxaNode b;
  
  public Edge(TaxaNode a, TaxaNode b, Integer label) {
    this.setA(a);
    this.setB(b);
    this.setLabel(label);
  }

  public Integer getLabel() {
    return label;
  }

  public void setLabel(Integer label) {
    this.label = label;
  }

  public TaxaNode getB() {
    return b;
  }

  public void setB(TaxaNode b) {
    this.b = b;
  }

  public TaxaNode getA() {
    return a;
  }

  public void setA(TaxaNode a) {
    this.a = a;
  }
  
  public TaxaNode getOther(TaxaNode t) {
    if (t == a) return b;
    else if (t.equals(b)) return this.a;
    else if (a.equals(b)) return a;
    else return null;
  }
  
  @Override
  public String toString() {
    return a.getGenome() + "--(" + label + ")--" +  b.getGenome();
  }
}
