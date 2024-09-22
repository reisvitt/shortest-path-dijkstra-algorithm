/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: Dijkstra
* Funcao...........: Implementa o algoritmo do caminho mais curto usando Dijkstra
*************************************************************** */

package service.shortest;

import java.util.ArrayList;
import model.Connection;
import model.Router;
import model.Store;

public class Dijkstra implements Shortest {
  ArrayList<Router> routers;
  Integer BASE = 3000;
  Store store;

  public Dijkstra(ArrayList<Router> routers) {
    this.routers = new ArrayList<Router>(routers);
  }

  /*
   * ***************************************************************
   * Metodo: shortest
   * Funcao: implementa o algoritmo do caminho mais curto do Dijkstra
   * Parametros: fromIp = IP de origem. toIp = IP de destino.
   * Retorno: void
   */
  @Override
  public void shortest(String fromIp, String toIp) {
    this.store = Store.getInstance();
    Router selected = null;

    // initialize with max cost
    for (Router router : this.routers) {
      router.setPermanent(false);
      router.setCost(Float.MAX_VALUE);
      router.setFrom(null);
      router.hideSelection();
      router.getConnections().stream().forEach(conn -> {
        conn.hideSelection();
      });

      // if the current ip is equal to fromIp, set selected
      if (router.getIp().equals(fromIp)) {
        selected = router;
      }
    }

    if (selected == null) {
      return;
    }

    try {
      Thread.sleep(this.getKValue());
      selected.setCost(0f);

      do {

        selected.setPermanent(true);

        Thread.sleep(this.getKValue());

        selected.showSelection();

        // loop for neighbor
        for (Connection connection : selected.getConnections()) {
          Router neighbor = connection.getConnection1().equals(selected) ? connection.getConnection2()
              : connection.getConnection1();

          if (!neighbor.getPermanent() && neighbor.getCost() > selected.getCost() + connection.getCost()) {
            connection.showSelection();

            neighbor.showSelection();

            Thread.sleep(this.getKValue());

            neighbor.setCost(selected.getCost() + connection.getCost());
            Thread.sleep(this.getKValue());

            neighbor.setFrom(selected);
            Thread.sleep(this.getKValue());

            neighbor.hideSelection();
            connection.hideSelection();
          }
        }
        selected.hideSelection();

        selected = null;

        // find router with lowerst cost
        for (Router router : this.routers) {
          if (!router.getPermanent() && selected == null) {
            selected = router;
          } else if (!router.getPermanent() && router.getCost() < selected.getCost()) {
            selected = router;
          }
        }

      } while (selected != null);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // finding the path
    Router toRouter = this.routers.stream().filter(router -> router.getIp().equals(toIp)).findFirst().orElse(null);

    if (toRouter == null) {
      System.out.println("No path found");
      return;
    }

    ArrayList<Router> path = new ArrayList<>();

    path.add(toRouter);

    Router current = toRouter;

    while (current.getFrom() != null) {
      path.add(0, current.getFrom());
      current = current.getFrom();
    }

    // render the path
    for (Router router : path) {
      Connection connection = router.getConnections().stream().filter(conn -> {
        if (router.getFrom() == null) {
          return false;
        }
        if (conn.getConnection1().getIp().equals(router.getFrom().getIp())) {
          return true;
        }
        if (conn.getConnection2().getIp().equals(router.getFrom().getIp())) {
          return true;
        }
        return false;
      }).findFirst().orElse(null);

      if (connection != null) {
        connection.showSelection();
      }
    }

    System.out.println("Shortest path from " + fromIp + " to " + toIp + ":");
    path.forEach(router -> System.out.print(router.getIp() + " > "));
    System.out.println("Total cost: " + toRouter.getCost());

    current.stopShortest();
  }

  /*
   * ***************************************************************
   * Metodo: getKValue
   * Funcao: calcula valores para o Thread sleep
   * Parametros: sem parametros
   * Retorno: Integer com o valor calculado
   */
  public Integer getKValue() {
    Integer velocity = store.getVelocity().get();

    if (velocity <= 1) {
      velocity = 1;
    }

    Integer k1 = BASE / velocity;

    if (k1 < 200) {
      k1 = 200;
    }

    return k1;
  }

}
