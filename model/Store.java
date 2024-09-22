/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: Store
* Funcao...........: Armazena os valores globais necessarios
*************************************************************** */

package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Store {
  private static Store instance;
  private BooleanProperty running = new SimpleBooleanProperty(false);
  private IntegerProperty velocity;

  /*
   * ***************************************************************
   * Metodo: getInstance
   * Funcao: instancia a store
   * Retorno: instancia de store
   */
  public static Store getInstance() {
    if (instance == null) {
      instance = new Store();
    }
    return instance;
  }

  /*
   * ***************************************************************
   * Metodo: Store
   * Funcao: construtor de store
   */
  public Store() {
    this.velocity = new SimpleIntegerProperty(3);
  }

  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: inicia o algoritmo
   */
  public void start() {
    this.running.set(true);
  }

  /*
   * ***************************************************************
   * Metodo: stop
   * Funcao: finaliza o algoritmo
   */
  public void stop() {
    this.running.set(false);
  }

  /*
   * ***************************************************************
   * Metodo: getRunningProperty
   * Funcao: returna se algoritmo esta sendo executado
   */
  public BooleanProperty getRunningProperty() {
    return running;
  }

  /*
   * ***************************************************************
   * Metodo: setVelocity
   * Funcao: altera o valor de velocidade
   * Parametros: int vel = velocidade
   */
  public void setVelocity(int vel) {
    this.velocity.setValue(vel);
  }

  /*
   * ***************************************************************
   * Metodo: getVelocity
   * Funcao: retorna o valor de velocidade
   * Retorno: velocidade
   */
  public IntegerProperty getVelocity() {
    return this.velocity;
  }
}
