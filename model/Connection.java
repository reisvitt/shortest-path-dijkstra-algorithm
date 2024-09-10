package model;

public class Connection {
  private Router connection1;
  private Router connection2;
  private Integer cost;

  public Connection(Router connection1, Router connection2, Integer cost) {
    this.connection1 = connection1;
    this.connection2 = connection2;
    this.cost = cost;
  }

  public Router getConnection1() {
    return connection1;
  }

  public void setConnection1(Router connection1) {
    this.connection1 = connection1;
  }

  public Router getConnection2() {
    return connection2;
  }

  public void setConnection2(Router connection2) {
    this.connection2 = connection2;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "Connection{con1=" + this.connection1.getIp() + " con2=" + this.connection2.getIp() + " cost=" + this.cost + "}";
  }
}
