/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: Router
* Funcao...........: Modelo de Roteador, suas estilizacoes e metodos
*************************************************************** */

package model;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import service.shortest.Shortest;

public class Router {
  private String ip;
  private ArrayList<Connection> connections;
  private BooleanProperty permanent;
  private ObjectProperty<Router> from;
  private SimpleFloatProperty cost;
  private Text textFrom;
  private Text textCost;

  private StackPane stack;
  private Pane network;
  private Store store;
  private Shortest shortest;

  public Router(String ip, Pane network) {
    this.ip = ip;
    this.connections = new ArrayList<Connection>();
    this.network = network;
    this.init();
  }

  /*
   * ***************************************************************
   * Metodo: init
   * Funcao: inicializa os valores ao instanciar Router
   * Parametros: void
   * Retorno: void;
   */
  public void init() {
    this.permanent = new SimpleBooleanProperty(false);
    this.cost = new SimpleFloatProperty(0f);
    this.from = new SimpleObjectProperty<Router>(null);

    this.store = Store.getInstance();

    this.cost.addListener((observable, oldValue, newValue) -> {
      if (newValue.floatValue() == Float.MAX_VALUE) {
        textCost.setText("∞");
      } else {
        textCost.setText(newValue.toString());
      }
    });

    this.from.addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        textFrom.setText("-");
      } else {
        textFrom.setText(newValue.getIp());
      }
    });

    this.permanent.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        stack.getStyleClass().add("permanent");
      } else {
        stack.getStyleClass().remove("permanent");
      }
    });
  }

  /*
   * ***************************************************************
   * Metodo: shortestPath
   * Funcao: executa o shortest path para o algoritmo selecionado
   * Parametros: router = roteador de origem. toIp = roteador destino
   * Retorno: void
   */
  public void shortestPath(Router router, String toIp) {
    this.store.start();

    Thread thread = new Thread(() -> {
      if (router == null) {
        this.shortest.shortest(this.getIp(), toIp);
      } else {
        this.shortest.shortest(router.getIp(), toIp);
      }
    });

    thread.start();
    this.store.getRunningProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        thread.interrupt();
      }
    });
  }

  /*
   * ***************************************************************
   * Metodo: stopShortest
   * Funcao: para algoritmo
   * Parametros: void
   * Retorno: void
   */
  public void stopShortest() {
    this.store.stop();
  }

  /*
   * ***************************************************************
   * Metodo: getIp
   * Funcao: retorna o ip do roteador atual
   * Parametros: void
   * Retorno: string do ip
   */
  public String getIp() {
    return ip;
  }

  /*
   * ***************************************************************
   * Metodo: setIp
   * Funcao: atualiza o ip do roteador atual
   * Parametros: ip = string com o ip
   * Retorno: void
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /*
   * ***************************************************************
   * Metodo: getConnections
   * Funcao: retorna o as conexoes do roteador atual
   * Parametros: void
   * Retorno: conexoes
   */
  public ArrayList<Connection> getConnections() {
    return connections;
  }

  /*
   * ***************************************************************
   * Metodo: setConnections
   * Funcao: atualiza o as conexoes do roteador atual
   * Parametros: connections= conexoes deste roteador
   * Retorno: void
   */
  public void setConnections(ArrayList<Connection> connections) {
    this.connections = connections;
  }

  /*
   * ***************************************************************
   * Metodo: addConnection
   * Funcao: adiciona uma nova conexao ao roteador atual
   * Parametros: connection=nova conexao deste roteador
   * Retorno: void
   */
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

  /*
   * ***************************************************************
   * Metodo: getPermanent
   * Funcao: retorna booleano se o roteador atual eh permanente
   * Parametros: void
   * Retorno: boolean se eh permanente ou nao
   */
  public Boolean getPermanent() {
    return permanent.get();
  }

  /*
   * ***************************************************************
   * Metodo: setPermanent
   * Funcao: atualiza o valor booleano se o roteador atual eh permanente
   * Parametro: permanent= Boolean se eh o permanente
   * Retorno: void
   */
  public void setPermanent(Boolean permanent) {
    this.permanent.set(permanent);
  }

  /*
   * ***************************************************************
   * Metodo: getFrom
   * Funcao: retorna o roteador no qual sucedeu o atual
   * Parametros: void
   * Retorno: Router=roteador de origem
   */
  public Router getFrom() {
    return from.get();
  }

  /*
   * ***************************************************************
   * Metodo: setFrom
   * Funcao: atualiza o roteador no qual sucedeu o atual
   * Parametro: from=roteador de origem
   * Retorno: void
   */
  public void setFrom(Router from) {
    this.from.set(from);
  }

  /*
   * ***************************************************************
   * Metodo: getCost
   * Funcao: retorna o custo ate o roteador atual
   * Parametros: void
   * Retorno: custo ate o roteador atual
   */
  public Float getCost() {
    return cost.get();
  }

  /*
   * ***************************************************************
   * Metodo: setCost
   * Funcao: atualiza o custo ate o roteador atual
   * Parametro: cost=custo ate o roteador atual
   * Retorno: void
   */
  public void setCost(Float cost) {
    this.cost.set(cost);
  }

  /*
   * ***************************************************************
   * Metodo: getStack
   * Funcao: retorna a stack do roteador atual
   * Parametros: void
   * Retorno: StackPane do roteador atual
   */
  public StackPane getStack() {
    return stack;
  }

  /*
   * ***************************************************************
   * Metodo: setStack
   * Funcao: atualiza a stack do roteador atual
   * Parametros: stack=StackPane do roteador atual
   * Retorno: void
   */
  public void setStack(StackPane stack) {
    this.stack = stack;
  }

  /*
   * ***************************************************************
   * Metodo: getNetwork
   * Funcao: retorna a topologia do roteador atual
   * Parametros: void
   * Retorno: Pane da topologia do roteador atual
   */
  public Pane getNetwork() {
    return this.network;
  }

  /*
   * ***************************************************************
   * Metodo: getShortest
   * Funcao: retorna o algoritmo de caminho mais curto do roteador atual
   * Parametros: void
   * Retorno: Shortest algoritmo de caminho mais curto
   */
  public Shortest getShortest() {
    return shortest;
  }

  /*
   * ***************************************************************
   * Metodo: setShortest
   * Funcao: atualiza o algoritmo de caminho mais curto do roteador atual
   * Parametro: shortest=algoritmo de caminho mais curto
   * Retorno: void
   */
  public void setShortest(Shortest shortest) {
    this.shortest = shortest;
  }

  /*
   * ***************************************************************
   * Metodo: getTextFrom
   * Funcao: retorna o IP do roteador que sucedeu a este
   * Parametros: void
   * Retorno: Text do IP do roteador
   */
  public Text getTextFrom() {
    return textFrom;
  }

  /*
   * ***************************************************************
   * Metodo: setTextFrom
   * Funcao: atualiza o Text do IP do roteador que sucedeu a este
   * Parametro: textFrom= Text do IP do roteador
   * Retorno: void
   */
  public void setTextFrom(Text textFrom) {
    this.textFrom = textFrom;
  }

  /*
   * ***************************************************************
   * Metodo: getTextCost
   * Funcao: retorna o custo ate este roteador
   * Parametros: void
   * Retorno: Text do custo ate este roteador
   */
  public Text getTextCost() {
    return textCost;
  }

  /*
   * ***************************************************************
   * Metodo: setTextCost
   * Funcao: atualiza o custo ate este roteador
   * Parametros: textCost=Text do custo ate este roteador
   * Retorno: void
   */
  public void setTextCost(Text textCost) {
    this.textCost = textCost;
  }

  /*
   * ***************************************************************
   * Metodo: showSelection
   * Funcao: adiciona classe que exibe o roteador atual com uma borda
   * Parametros: void
   * Retorno: void
   */
  public void showSelection() {
    this.stack.getStyleClass().add("selected");
  }

  /*
   * ***************************************************************
   * Metodo: hideSelection
   * Funcao: remomve classe que exibe o roteador atual com uma borda
   * Parametros: void
   * Retorno: void
   */
  public void hideSelection() {
    this.stack.getStyleClass().remove("selected");
  }

  @Override
  public String toString() {
    return "Router{ip='" + this.ip + "'" + " connections=" + this.connections.toString() + "}";
  }

  public String toStringDijkstra() {
    return "Router{ip=" + this.ip + " cost=" + this.getCost() + " from=" + this.getFrom() + "}";
  }
}
