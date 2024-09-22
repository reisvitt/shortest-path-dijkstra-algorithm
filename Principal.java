/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: Principal
* Funcao...........: Inicia a apliacao
*************************************************************** */

import controller.ShortestController;
import controller.SplashController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {
  SplashController splashController = new SplashController();
  ShortestController shortestController = new ShortestController();

  @Override
  public void start(Stage stage) throws Exception {
    Image icon = new Image("/view/images/icon.png");

    Parent root = FXMLLoader.load(getClass().getResource("/view/SplashScreen.fxml"));

    stage.getIcons().add(icon);

    double screenWidth = Screen.getPrimary().getBounds().getWidth();
    double screenHeight = Screen.getPrimary().getBounds().getHeight();

    double windowWidth = screenWidth * 1;
    double windowHeight = screenHeight * 1;

    Scene scene = new Scene(root, windowWidth, windowHeight);

    stage.setScene(scene);
    stage.setTitle("Algoritmo do Caminho mais curto");
    stage.setResizable(true);
    stage.setFullScreen(true);
    stage.initStyle(StageStyle.UNIFIED);
    stage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}