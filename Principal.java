import java.util.ArrayList;

import service.config.ConfigReader;

public class Principal{
  public static void main(String[] args) {

    // load configs
    ConfigReader reader = new ConfigReader("backbone.txt");
    ArrayList<String> configs = reader.read();

    String firstLine = configs.get(0).replace(";", "");
    Integer size = Integer.parseInt(firstLine);

    System.out.println("The size of the array is: " + size);
    System.out.println("Configs: "+ configs.subList(1, configs.size()).toString());
  }
}