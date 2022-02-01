package main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 JavaDoc is located in D:\Java\Javadoc
 */
/**
  Class Main.java
 */

/**
 @author Justin Brown
 */
public class Main extends Application {

    //Method documentation comments. Tools, Create JavaDoc.
    /**
     @param primaryStage The opening scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Main");
        primaryStage.setScene(new Scene(root, 1000, 400));
        primaryStage.show();
    }

    /**
     FUTURE ENHANCEMENT This information could be put into a database.
     The database could be updated as needed. This would also work with relating the associated part to the product.
     The information could be redily updated by the user, instead of implemented directly into the code.
     */

    /** This method holds the stock information for the tables.
     @param args The table information.
     RUNTIME ERROR Used new Part instead of new InHouse/OutSource. Part is abstract and is not able to be use here,
     To bypass the abstract Part "[]" was added after machineID and ")" before the ";".
     This assignment asks that the abstract not be bypassed.
     To fix this used the InHouse/OutSource classes.
     addAssociatedPart was added after Product to add associated part with its associated Product number.
     */
    public static void main(String[] args) {
        InHouse part1= new InHouse(1, "Brake Pads", 59.99, 25, 15, 40, 55);
        InHouse part2= new InHouse(2, "Calipers", 79.99, 15, 10, 30, 56);
        InHouse part3= new InHouse(3, "Spark Plugs", 9.99, 60, 20, 100, 57);
        OutSource part4= new OutSource(4, "Wires", 19.99, 25, 15, 60, "Alpha");
        OutSource part5= new OutSource(5, "Fuel Pump", 39.99, 20, 12, 35, "Beta");
        Product product1= new Product(201, "Brakes", 134.99, 20, 10, 40);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part5);
        Product product2= new Product(202, "Fuel Parts", 119.99, 20, 10, 40);
        product2.addAssociatedPart(part2);
        Product product3= new Product(203, "Engine parts", 59.95, 10, 8, 20);
        product3.addAssociatedPart(part3);
        product3.addAssociatedPart(part4);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch(args);
    }
}
