package edu.brown.cs.ngoelz.trees;

public class Tuple<S1, S2> {
  private S1 a;
  private S2 b;
  
  public Tuple(S1 a, S2 b) {
    this.setA(a);
    this.setB(b);
  }

  public S1 getA() {
    return a;
  }

  public void setA(S1 a) {
    this.a = a;
  }

  public S2 getB() {
    return b;
  }

  public void setB(S2 b) {
    this.b = b;
  }
  
  @Override
  public String toString() {
    return "(" + a + ", " + b + ")";
  }
}
