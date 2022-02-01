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
 *  Class ModifyProductController.java
 */

/**
 *
 * @author Justin Brown
 */
public class ModifyProductController implements Initializable {
    private Product sendProd;

    private Product tempProduct;

    @FXML
    private TextField productId;
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
    private TableColumn<Part, Double> Cost2;
    @FXML
    private TextField searchTxt;
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

        tempProduct = new Product(0, null, 0 , 0 , 0 , 0 );

        partTable2.setItems(tempProduct.getAllAssociatedParts());
        ID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name2.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryLvl2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Cost2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     @param actionEvent The cancel button returns to home page.
     */
    @FXML
    private void modprodCancelbtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All changes will not be saved, do you want to continue?");
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
     * This method will send product info.
     *
     * @param product The table information.
     */
    public void sendProducts(Product product) {
        sendProd = product;
        productId.setText(String.valueOf(sendProd.getId()));
        productName.setText(sendProd.getName());
        productInventory.setText((String.valueOf(sendProd.getStock())));
        productPrice.setText(String.valueOf(sendProd.getPrice()));
        productMax.setText(String.valueOf(sendProd.getMax()));
        productMin.setText(String.valueOf(sendProd.getMin()));

        partTable2.getItems().addAll(product.getAllAssociatedParts());
        ID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name2.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryLvl2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Cost2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     @param actionEvent The save button updates the Product.
     */
    @FXML
    private void modprodSavebtn(ActionEvent actionEvent) throws IOException {
        try {
            int id = sendProd.getId();
            String name = productName.getText();
            int stock = Integer.parseInt(productInventory.getText());
            double price = Double.parseDouble(productPrice.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());
            int index = Inventory.getAllProducts().indexOf(sendProd);

            if (name.isEmpty()){
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
            /*if (min > max) {
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
            Inventory.updateProduct(index, product);

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
     @param event The searchbar
     */
    @FXML
    private void searchHandler(ActionEvent event) {
        String q = searchTxt.getText();
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
        //search1.setText(""); empty's the search box upon search. may not need this.
    }
    /**
     @param event The remove button removes a selected associated part.
     */
    @FXML
    public void removeHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            tempProduct.deleteAssociatedPart(partTable2.getSelectionModel().getSelectedItem());
            System.out.println("This part was removed from the product");
        }
    }
    /**
     @param actionEvent The add button adds to the part to the bottom table.
     */
    @FXML
    private void addBtn(ActionEvent actionEvent) throws IOException{
        Part p = partTable.getSelectionModel().getSelectedItem();
        if (p == null) {
            return;
        }
        tempProduct.addAssociatedPart(partTable.getSelectionModel().getSelectedItem());
    }
}