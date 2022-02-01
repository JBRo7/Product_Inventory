package Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *  Class AddProductController.java
 */

/**
 *
 * @author Justin Brown
 */
public class AddProductController implements Initializable {

    private Product tempProduct;

    @FXML
    private TextField productName;
    @FXML
    private TextField productInventory;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMax;
    @FXML
    private TextField productMin;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> ID;
    @FXML
    private TableColumn<Part, String> Name;
    @FXML
    private TableColumn<Part, Integer> InventoryLvl;
    @FXML
    private TableColumn<Part, Double> PartCost;
    @FXML
    private TableView<Part> partTable2;
    @FXML
    private TableColumn<Part, Integer> ID2;
    @FXML
    private TableColumn<Part, String> Name2;
    @FXML
    private TableColumn<Part, Integer> InventoryLvl2;
    @FXML
    private TableColumn<Part, Double> PartCost2;
    @FXML
    private TextField searchP;

    public static int productId = 204;
    /**
     @param resources The table's values
     */
    public void initialize(URL url, ResourceBundle resources) {
        partTable.setItems(Inventory.getAllParts());
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Part> parts = Inventory.getAllParts();
        partTable.setItems(parts);

        tempProduct = new Product(0, null, 0, 0, 0, 0);

        partTable2.setItems(tempProduct.getAllAssociatedParts());
        ID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name2.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryLvl2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartCost2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     @param actionEvent The cancel button returns to home page.
     */
    @FXML
    private void cancelHandler(ActionEvent actionEvent) throws IOException {
        //confirmation box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All Text values will be cleared, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     @param actionEvent The searchbar.

     RUNTIME ERROR The user enters a string that is invalid (no strings matching the user input) showing errors:
     NumberFormatException.java:65,
     Integer.java:652,
     Integer.java.770,
     AddProductController.java.117
     Added Try block and catch with error message, showing that this is an invalid string.
     The error message displays when an invalid sting is entered. The error box requires a button click to confirm the error,
     The program stops so error is not created.
     */
    @FXML
    private void searchHandler(ActionEvent actionEvent) {
        String q = searchP.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);

        try {
            if (parts.size() == 0) {

                int id = Integer.parseInt(q);
                Part tempPart = Inventory.getAllParts(id);
                if (tempPart != null)
                    parts.add(tempPart);
                else {
                    //This is the Error dialog box when no matches are found.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("There are no Part ID's that match this entry!");
                    alert.showAndWait();
                    return;
                }
            }
        } catch (NumberFormatException e) {
            //This is the Error dialog box when no matches are found.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no Part Names that match this entry!");
            alert.showAndWait();
            return;
        }
        partTable.setItems(parts);
    }
    /**
     @param actionEvent The add button adds to the part to the bottom table.
     */
    @FXML
    private void addHandler(ActionEvent actionEvent) throws IOException {

        tempProduct.addAssociatedPart(partTable.getSelectionModel().getSelectedItem());
    }
    /**
     @param actionEvent The save button adds a new Product.
     */
    @FXML
    public void saveHandler(ActionEvent actionEvent) throws IOException {
        try {
            int id = productId;
            String name = productName.getText();
            int stock = Integer.parseInt(productInventory.getText());
            double price = Double.parseDouble(productPrice.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());

            if (name.isEmpty()) {
                //confirmation box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Name should not be null.");
                alert.showAndWait();
                return;
            }
            if (stock < min) {
                //confirmation box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory should be greater than Min");
                alert.showAndWait();
                return;
            }
            if (stock > max) {
                //confirmation box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory should be less than Max");
                alert.showAndWait();
                return;
            }

            /* if (min > max) {
                //confirmation box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory should be greater than Min");
                alert.showAndWait();
                return;
            }
             */

            Product product = new Product(id, name, price, stock, min, max);
            product.getAllAssociatedParts().addAll(tempProduct.getAllAssociatedParts());
            Inventory.addProduct(product);
            productId++;

            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please Enter a Valid Value for Each Text Field!");
            alert.showAndWait();
        }
    }
    /**
     @param event The remove button removes a selected associated part.
     */
    @FXML
    private void removeHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            tempProduct.deleteAssociatedPart(partTable2.getSelectionModel().getSelectedItem());
            System.out.println("This part was removed from the product");
        }
    }
}


