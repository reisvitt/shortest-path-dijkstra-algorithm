package model;

import java.sql.Connection;
import java.util.ArrayList;

public class Router {
   private String ip;
   private ArrayList<Connection> connections;

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

  @Override
  public String toString() {
    return "Router{ip='" + this.ip + "'" + " connections=" + this.connections.toString() + "}";
  }
}
