package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 *  Class MainController.java
 */

/**
 *
 * @author Justin Brown
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partsIdColumn;
    @FXML
    private TableColumn<Part, String> partsNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsIntegerTableColumn;
    @FXML
    private TableColumn<Part, Double> partsPriceColumn;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productsIdColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Integer> productsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productsPriceColumn;
    @FXML
    private TextField searchPart;
    @FXML
    private TextField searchProd;
    /**
     @param resources sets up table values
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        partsTable.setItems(Inventory.getAllParts());

        partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsIntegerTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     @param actionEvent The searchbar for part
     */
    @FXML
    private void searchPartHandler(ActionEvent actionEvent) {

    String q = searchPart.getText();
    ObservableList<Part> parts = Inventory.lookupPart(q);

    if(parts.size() == 0){
        try {
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
        catch(NumberFormatException e) {
            //This is the Error dialog box when no matches are found.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no Part Names that match this entry!");
            alert.showAndWait();
            return;
        }
    }
    partsTable.setItems(parts);
}
    /**
     @param actionEvent The searchbar for product
     */
    @FXML
    private void searchPodHandler(ActionEvent actionEvent) {

        String q = searchProd.getText();
        ObservableList<Product> products = Inventory.lookupProduct(q);

        if(products.size() == 0){
            try {
                int id = Integer.parseInt(q);
                Product tempProd = Inventory.getAllProducts(id);
                if (tempProd != null)
                    products.add(tempProd);
                else{
                    //This is the Error dialog box when no matches are found.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("There are no Product ID's that match this entry!" +
                            " Try entering the full ID.");
                    alert.showAndWait();
                    return;
                }
            }
            catch(NumberFormatException e) {
                //This is the Error dialog box when no matches are found.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("There are no Product Names that match this entry!");
                alert.showAndWait();
                return;
            }
        }
        productsTable.setItems(products);
    }


    /**
     @param actionEvent The add button moves to the addPart page.
     */
    @FXML
    public void Onactionmainpartaddbtn(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/AddPart.fxml")));
        loader.load();

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene= loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     @param actionEvent The add button moves to the the modifyPart page.
     */
    @FXML
    private void onActionmainpartmodbtn(ActionEvent actionEvent) throws IOException  {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/ModifyPart.fxml")));
        loader.load();
        ModifyPartController MPController = loader.getController();
        MPController.sendParts(partsTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene= loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
     @param mouseEvent Exit application.
     */
    @FXML
    private void exitBtn(MouseEvent mouseEvent) {
        System.exit(0);
    }
    /**
     @param actionEvent the delete button removes the particular part.
     */
    @FXML
    private void onActionmainpartdeletebtn(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            partsTable.getItems().removeAll(partsTable.getSelectionModel().getSelectedItem());
            System.out.println("Part was successfully removed");
        }
    }
    /**
     @param actionEvent The add button adds to the product to the product table.
     */
    @FXML
    private void Onactionmainprodaddbtn(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/AddProduct.fxml")));
        loader.load();

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     @param actionEvent The delete button removes the particular product.
     */
    @FXML
    private void onActionmainproddeletebtn(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();

        Product product = productsTable.getSelectionModel().getSelectedItem();

        ObservableList associatedParts = FXCollections.observableArrayList();
        associatedParts = product.getAllAssociatedParts();

        if(associatedParts.isEmpty() && associatedParts != null){
            Inventory.deleteProduct(product);
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Warning Dialog");
            alert1.setContentText("The Product was successfully deleted!");
            alert1.showAndWait();
            return;
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setContentText("The product has an associated part. " +
                    "The product will not be deleted until the associated part is removed.");
            alert2.showAndWait();
            return;
            //if(result.isPresent() && result.get() == ButtonType.OK)
            //if(result.isPresent() && Product.getAssociatedProduct.get() == ButtonType.OK )/////////////////////////
        }
        //{
           //productsTable.getItems().removeAll(productsTable.getSelectionModel().getSelectedItem());
        //}
    }

    //an exception checks to see if any associated parts accepted to the product, if so an exception is associated.
    //how to delete a product with an associated part?????

    /**
     @param actionEvent The modify button brings the user to the modify page.
     */
    @FXML
    private void onActionmainprodmodbtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/ModifyProduct.fxml")));
        loader.load();
        ModifyProductController MPRController = loader.getController();
        MPRController.sendProducts(productsTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene= loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}