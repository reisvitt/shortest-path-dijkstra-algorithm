/* ***************************************************************
* Autor............: Vitor Reis
* Matricula........: 201710793
* Inicio...........: 10/09/2024
* Ultima alteracao.: 22/09/2024
* Nome.............: ConfigReader
* Funcao...........: Leitor das configuracoes
*************************************************************** */

package service.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigReader {
  private String pathname;

  public ConfigReader(String pathname) {
    this.pathname = pathname;
  }

  /*
   * ***************************************************************
   * Metodo: read
   * Funcao: busca o arquivo de configuracao
   * Parametros: sem parametros
   * Retorno: lista de strings
   */
  public ArrayList<String> read() {
    ArrayList<String> array = new ArrayList<String>();

    try {
      File backbone = new File(this.pathname);
      Scanner reader = new Scanner(backbone);
      while (reader.hasNextLine()) {
        String data = reader.nextLine();
        array.add(data);
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return array;
  }
}
