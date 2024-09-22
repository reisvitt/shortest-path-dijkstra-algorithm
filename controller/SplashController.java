/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: SplashController
* Funcao...........: Controler da SplashScreen
*************************************************************** */

package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SplashController implements Initializable {
  @FXML
  private StackPane main;

  /*
   * ***************************************************************
   * Metodo: goToMainScreen
   * Funcao: navega ate a tela principal
   */
  public void goToMainScreen() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ShortestScreen.fxml"));
      Parent newScreen = fxmlLoader.load();
      Stage stage = (Stage) main.getScene().getWindow();
      stage.setTitle("Algoritmo do Caminho mais curto");

      double screenWidth = Screen.getPrimary().getBounds().getWidth();
      double screenHeight = Screen.getPrimary().getBounds().getHeight();

      double windowWidth = screenWidth * 1;
      double windowHeight = screenHeight * 1;
      stage.setScene(new Scene(newScreen, windowWidth, windowHeight));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(() -> {
      Platform.runLater(() -> goToMainScreen());
    }, 1, TimeUnit.SECONDS);
    scheduler.shutdown();
  }
}
