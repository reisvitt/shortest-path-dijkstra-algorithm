package model;

import java.util.ArrayList;

public class Router {
   private String ip;
   private ArrayList<Connection> connections;
   private Boolean permanent = false;
   private Router from;
   private Integer cost;

  public Router(String ip) {
     this.ip = ip;
     this.connections = new ArrayList<Connection>();
   }

  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }

  public ArrayList<Connection> getConnections() {
    return connections;
  }
  public void setConnections(ArrayList<Connection> connections) {
    this.connections = connections;
  }

  public void addConnection(Connection connection) {
    this.connections.add(connection);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Router router = (Router) obj;

    return this.ip.equals(router.getIp());
  }

  public Boolean getPermanent() {
    return permanent;
  }

  public void setPermanent(Boolean permanent) {
    this.permanent = permanent;
  }

  public Router getFrom() {
    return from;
  }

  public void setFrom(Router from) {
    this.from = from;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "Router{ip='" + this.ip + "'" + " connections=" + this.connections.toString() + "}";
  }

  public String toStringDijkstra() {
    return "Router{ip=" + this.ip + " cost=" + this.getCost() + " from=" + this.getFrom() + "}";
  }
}
