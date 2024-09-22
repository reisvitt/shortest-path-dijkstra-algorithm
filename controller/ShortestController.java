/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: ShortestController
* Funcao...........: Controler da tela do algoritmo
*************************************************************** */

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Connection;
import model.Router;
import model.Store;
import service.config.ConfigReader;
import service.shortest.Dijkstra;
import service.shortest.Shortest;

public class ShortestController implements Initializable {
  @FXML
  private Pane network;

  @FXML
  private Spinner<Integer> ttlInput;

  @FXML
  private ComboBox<String> listSenders, listReceivers;

  @FXML
  private Slider velocity;

  @FXML
  private Button startButton, stopButton;

  private ArrayList<Router> routers;
  private Store store;
  private Shortest shortest;

  /*
   * ***************************************************************
   * Metodo: routerPositions
   * Funcao: calcula a posicao de cada roteador
   * Parametros: void
   * Retorno: void
   */
  public void routerPositions() {
    // area of pane

    double x = this.network.getLayoutX();
    double y = this.network.getLayoutY();
    double width = this.network.getWidth();
    double height = this.network.getHeight();
    double centerX = ((x + width) / 2);
    double centerY = ((y + height) / 2) - 100;

    double radius = centerY / 1.3;
    double radiusLegend = centerY / 1.14;

    double angleStep = 360.0 / this.routers.size();

    for (int i = 0; i < this.routers.size(); i++) {
      double angle = Math.toRadians(i * angleStep);
      double routerX = centerX + radius * Math.cos(angle);
      double routerY = centerY + radius * Math.sin(angle);

      double legendX = centerX + radiusLegend * Math.cos(angle);
      double legendY = centerY + 25 + radiusLegend * Math.sin(angle);

      this.addRouter(this.routers.get(i), routerX, routerY, legendX, legendY);
    }

    for (int i = 0; i < this.routers.size(); i++) {
      this.routers.get(i).getConnections().forEach(conn -> {
        if (!conn.isRendered()) {
          this.addConnection(conn);
        }
      });
    }
  }

  /*
   * ***************************************************************
   * Metodo: addRouter
   * Funcao: insere o roteador e suas informacoes na topologia
   * Parametros:
   * router=Roteador,
   * x=posicao x,
   * y=posicao y,
   * legendX=posicao da legend x,
   * legendY=posicao da legenda Y
   * Retorno:void
   */
  public void addRouter(Router router, double x, double y, double legendX, double legendY) {
    Text text = new Text(router.getIp());
    text.getStyleClass().add("router-text");

    Text textCost = new Text("∞");
    textCost.getStyleClass().add("router-cost");
    textCost.setLayoutX(legendX);
    textCost.setLayoutY(legendY);

    Text textFrom = new Text("-");
    textFrom.getStyleClass().add("router-from");
    textFrom.setLayoutX(legendX + 30);
    textFrom.setLayoutY(legendY);

    StackPane stack = new StackPane();
    // Renderizar aqui se necessário
    stack.setLayoutX(x);
    stack.setLayoutY(y);
    stack.getStyleClass().add("router");
    stack.getChildren().addAll(text);

    network.getChildren().addAll(stack, textCost, textFrom);

    router.setStack(stack);
    router.setTextCost(textCost);
    router.setTextFrom(textFrom);
  }

  /*
   * ***************************************************************
   * Metodo: addConnection
   * Funcao: insere o conecao e suas informacoes na topologia
   * Parametros: connection=Connection a ser inserida
   * Retorno: void
   */
  public void addConnection(Connection connection) {
    connection.setRendered(true);

    Router con1 = connection.getConnection1();
    Router con2 = connection.getConnection2();

    double move = 20;

    double x1 = con1.getStack().getLayoutX() + move;
    double y1 = con1.getStack().getLayoutY() + move;

    double x2 = con2.getStack().getLayoutX() + move;
    double y2 = con2.getStack().getLayoutY() + move;

    Line line = new Line(x1, y1, x2, y2);
    line.getStyleClass().add("router-connection");

    Text textCost = new Text(connection.getCost().toString());
    textCost.getStyleClass().add("connection-text");

    textCost.setX(((x1 + x2) / 2) - 5);
    textCost.setY(((y1 + y2) / 2) - 5);

    this.network.getChildren().addAll(line, textCost);
    line.toBack();
    connection.setLine(line);
  }

  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: inicializa o algoritmo
   * Parametros: void
   * Retorno: void
   */
  @FXML
  public void start() {
    this.store.start();

    String from = this.listSenders.getValue();
    String to = this.listReceivers.getValue();

    Router selectedRouter = null;
    for (Router router : this.routers) {
      // selecionar o roteador de origem
      if (router.getIp().equals(from)) {
        selectedRouter = router;
      }

      // add o algoritmo para cada roteador
      router.setShortest(this.shortest);
    }

    // nao inicializa algoritmo se nao houver selecao
    if (selectedRouter == null) {
      return;
    }

    selectedRouter.shortestPath(null, to);
  }

  /*
   * ***************************************************************
   * Metodo: stop
   * Funcao: para o algoritmo
   * Parametros: void
   * Retorno: void
   */
  @FXML
  public void stop() {
    this.store.getRunningProperty().set(false);
  }

  /*
   * ***************************************************************
   * Metodo: openAbout
   * Funcao: Abre modal de Sobre
   * Parametros: void
   * Retorno: void
   */
  @FXML
  public void openAbout() {
    try {
      Stage aboutStage = new Stage();
      aboutStage.initModality(Modality.APPLICATION_MODAL);
      aboutStage.setTitle("Sobre");

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AboutScreen.fxml"));
      Parent newScreen = fxmlLoader.load();

      double screenHeight = Screen.getPrimary().getBounds().getHeight();

      double windowHeight = screenHeight * 0.95;

      if (windowHeight > 650) {
        windowHeight = 650;
      }

      Scene aboutScene = new Scene(newScreen, 740, windowHeight);

      aboutStage.setScene(aboutScene);

      aboutStage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: Inicializa o controller e suas dependencias
   * Parametros: void
   * Retorno: void
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.store = Store.getInstance();

    // load configs
    ConfigReader reader = new ConfigReader("backbone.txt");
    ArrayList<String> configs = reader.read();

    String firstLine = configs.get(0).replace(";", "");
    Integer size = Integer.parseInt(firstLine);

    routers = new ArrayList<Router>(size);

    configs.subList(1, configs.size()).forEach(config -> {
      String[] ipAndRouter = config.split(";");
      String ip = ipAndRouter[0];
      String connectionIp = ipAndRouter[1];
      Float cost = Float.parseFloat(ipAndRouter[2]);

      Router router = new Router(ip, this.network);
      Router connectionRouter = new Router(connectionIp, this.network);

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
        // add conexao aos roteadores
        routers.add(connectionRouter);
      }

      // add conexao ao roteador atual
      Connection connection = new Connection(router, connectionRouter, cost);
      router.addConnection(connection);
      connectionRouter.addConnection(connection);
    });

    // inializa o algoritmo de Dijstra com a lista de roteadores
    this.shortest = new Dijkstra(this.routers);

    Platform.runLater(() -> {
      this.routerPositions();
    });

    this.routers.forEach(router -> {
      // load list of origins
      listSenders.getItems().add(router.getIp());

      // load list of destinations
      listReceivers.getItems().add(router.getIp());
    });

    // define o primeiro roteador como origem por padrao
    listSenders.setValue(this.routers.get(0).getIp());

    // define o ultimo roteador como destino por padrao
    listReceivers.setValue(this.routers.get(this.routers.size() - 1).getIp());

    // listener para vincular os botoes de Start e Stop ao store.runnig
    this.store.getRunningProperty().addListener((observable, oldValue, newValue) -> {
      this.stopButton.setDisable(!newValue);
      this.startButton.setDisable(newValue);

      if (newValue) {
        this.stopButton.setOpacity(1);
        this.startButton.setOpacity(0.45);
      } else {
        this.stopButton.setOpacity(0.45);
        this.startButton.setOpacity(1);
      }
    });

    // listener para velocity para atualiza a velocidade na store
    velocity.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        store.setVelocity(newValue.intValue());
      }
    });
  }
}
