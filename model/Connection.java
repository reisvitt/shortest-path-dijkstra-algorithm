/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: Connection
* Funcao...........: Modelo de conexao entre reteadores e suas estilizacoes
*************************************************************** */

package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Connection {
  private Router connection1;
  private Router connection2;
  private Float cost;
  private boolean rendered = false;
  private Line line;

  public Connection(Router connection1, Router connection2, Float cost) {
    this.connection1 = connection1;
    this.connection2 = connection2;
    this.cost = cost;
  }

  /*
   * ***************************************************************
   * Metodo: getConnection1
   * Funcao: Retorna a connection 1 da conexao atual
   * Parametros: void
   * Retorno: Router da Connection1
   */
  public Router getConnection1() {
    return connection1;
  }

  /*
   * ***************************************************************
   * Metodo: setConnection1
   * Funcao: Atualiza a connection 1 da conexao atual
   * Parametros: connection1=Router da conexao 1
   * Retorno: void
   */
  public void setConnection1(Router connection1) {
    this.connection1 = connection1;
  }

  /*
   * ***************************************************************
   * Metodo: getConnection2
   * Funcao: Retorna a connection 2 da conexao atual
   * Parametros: void
   * Retorno: Router da Connection2
   */
  public Router getConnection2() {
    return connection2;
  }

  /*
   * ***************************************************************
   * Metodo: setConnection2
   * Funcao: Atualiza a connection 2 da conexao atual
   * Parametros: connection2=Router da conexao 2
   * Retorno: void
   */
  public void setConnection2(Router connection2) {
    this.connection2 = connection2;
  }

  /*
   * ***************************************************************
   * Metodo: getCost
   * Funcao: Retorna o custo desta conexao
   * Parametros: void
   * Retorno: Float do custo desta conexao
   */
  public Float getCost() {
    return cost;
  }

  /*
   * ***************************************************************
   * Metodo: setCost
   * Funcao: Atualiza o custo desta conexao
   * Parametro: cost= custo desta conexao
   * Retorno: void
   */
  public void setCost(Float cost) {
    this.cost = cost;
  }

  /*
   * ***************************************************************
   * Metodo: isRendered
   * Funcao: verifica se a conexao atual esta renderizada
   * Parametros: void
   * Retorna: boolean se a conexao atual esta renderizada
   */
  public boolean isRendered() {
    return rendered;
  }

  /*
   * ***************************************************************
   * Metodo: setRendered
   * Funcao: Atualiza se a conexao atual esta renderizada
   * Parametro: rendered=boolean se a conexao atual esta renderizada
   * Retorno: void
   */
  public void setRendered(boolean rendered) {
    this.rendered = rendered;
  }

  /*
   * ***************************************************************
   * Metodo: getLine
   * Funcao: Retorna a Line da conexao atual
   * Parametros: void
   * Retorno: Line da conexao atual
   */
  public Line getLine() {
    return line;
  }

  /*
   * ***************************************************************
   * Metodo: setLine
   * Funcao: Atualiza a Line da conexao atual
   * Parametro: line=Line da conexao atual
   * Retorno: void
   */
  public void setLine(Line line) {
    this.line = line;
  }

  /*
   * ***************************************************************
   * Metodo: showSelection
   * Funcao: altera a cor da borda da linha atual
   * Parametros: void
   * Retorno: void
   */
  public void showSelection() {
    this.line.setStroke(Color.web("#000"));
  }

  /*
   * ***************************************************************
   * Metodo: hideSelection
   * Funcao: retorna a cor da borda da linha atual para o padrao
   * Parametros: void
   * Retorno: void
   */
  public void hideSelection() {
    this.line.setStroke(Color.web("#8fd0d3"));
  }

  @Override
  public String toString() {
    return "Connection{con1=" + this.connection1.getIp() + " con2=" + this.connection2.getIp() + " cost=" + this.cost
        + "}";
  }
}
