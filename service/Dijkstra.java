package service;

import java.util.ArrayList;

import model.Connection;
import model.Router;

public class Dijkstra {
  public void shortest(ArrayList<Router> routers, String fromIp, String toIp){
    Router selected = null;

    // initialize with max cost
    for(Router router : routers){
      router.setCost(Integer.MAX_VALUE);

      // if the current ip is equal to fromIp, set selected
      if(router.getIp().equals(fromIp)){
        selected = router;
        selected.setCost(0);
      }
    }

    if(selected == null){
      return;
    }
    

    do {
      selected.setPermanent(true);
      
      for(Connection connection : selected.getConnections()){
        Router neighbor = connection.getConnection1().equals(selected) ? connection.getConnection2() : connection.getConnection1();
        
        if(!neighbor.getPermanent() && neighbor.getCost() > selected.getCost() + connection.getCost()){
          neighbor.setCost(selected.getCost() + connection.getCost());
          neighbor.setFrom(selected);
        }
      }

      selected = null;

      for (Router router : routers){
        if (!router.getPermanent() && selected == null){
          selected = router;
        }else if(!router.getPermanent() && router.getCost() < selected.getCost()){
          selected = router;
        }
      }

    } while(selected != null);


    // find the path
    Router toRouter = routers.stream().filter(router -> router.getIp().equals(toIp)).findFirst().orElse(null);

    if(toRouter == null){
      System.out.println("No path found");
      return;
    }

    ArrayList<Router> path = new ArrayList<>();
    
    path.add(toRouter);

    Router current = toRouter;

    while(current.getFrom() != null){
      path.add(0, current.getFrom());
      current = current.getFrom();
    }

    System.out.println("Shortest path from " + fromIp + " to " + toIp + ":");
    path.forEach(router -> System.out.print(router.getIp() + " > "));
    System.out.println("Total cost: " + toRouter.getCost());
  }
}
