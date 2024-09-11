import java.util.ArrayList;

import model.Connection;
import model.Router;
import service.Dijkstra;
import service.config.ConfigReader;

public class Principal{
  public static void main(String[] args) {

    // load configs
    ConfigReader reader = new ConfigReader("backbone.txt");
    ArrayList<String> configs = reader.read();

    String firstLine = configs.get(0).replace(";", "");
    Integer size = Integer.parseInt(firstLine);

    ArrayList<Router> routers = new ArrayList<Router>(size);

    configs.subList(1, configs.size()).forEach(config -> {
      String[] ipAndRouter = config.split(";");
      String ip = ipAndRouter[0];
      String connectionIp = ipAndRouter[1];
      Integer cost = Integer.parseInt(ipAndRouter[2]);

      Router router = new Router(ip);
      Router connectionRouter = new Router(connectionIp);

      if (routers.contains(router)) {
        Integer index = routers.indexOf(router);
        router = routers.get(index);
      } else {
        routers.add(router);
      }

      if (routers.contains(connectionRouter)) {
        Integer index = routers.indexOf(connectionRouter);
        connectionRouter = routers.get(index);
      } else {
        // add connection router to the routers
        routers.add(connectionRouter);
      }

      // add connection to current router
      Connection connection = new Connection(router, connectionRouter, cost);
      router.addConnection(connection);
      connectionRouter.addConnection(connection);
    });

    Dijkstra dijkstra = new Dijkstra();
    dijkstra.shortest(routers, "A", "D");
  }
}