package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 *  Class AddPartController.java
 */

/**
 *
 * @author Justin Brown
 */
public class AddPartController implements Initializable {
    //@FXML
    //private ToggleGroup nHouse;
    @FXML
    private RadioButton radioNHouse;
    //@FXML
    //private RadioButton radioOSourced;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInventory;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partMachineId;
    @FXML
    private TextField partMin;
    @FXML
    private Label machineIDLbl;
    
    public static int partId = 6;
    /**
     @param resourceBundle initializes
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     @param actionEvent The save button adds a new Part.
     */
    @FXML
    public void saveHandler(ActionEvent actionEvent) throws IOException {

        try{
            int id = partId;
            String name = partName.getText();
            int stock = Integer.parseInt(partInventory.getText());
            double price = Double.parseDouble(partPrice.getText());
            int max = Integer.parseInt(partMax.getText());
            int min = Integer.parseInt(partMin.getText());

            if (name.isEmpty()){
                //Error box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Name should not be null");
                alert.showAndWait();
                return;
            }
            if (stock < min) {
                //Error box
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
/*
            if (min > max) {
                //confirmation box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory should be greater than Min");
                alert.showAndWait();
                return;
            }\
 */
            if(radioNHouse.isSelected()){
                int machineID = Integer.parseInt(partMachineId.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            }
            else{
                String companyName = partMachineId.getText();
                Inventory.addPart(new OutSource(id, name, price, stock, min, max, companyName));
            }

            partId++;

            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
        catch(NumberFormatException e){
            //This is printed to IntelliJ
           /* System.out.println("Enter valid values in the text fields!");
            System.out.println("Exception: " + e);
            System.out.println("Exception: " + e.getMessage());
            */

            //This is an Error dialog box
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please Enter a Valid Value for Each Text Field!");
            alert.showAndWait();
             */

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please Enter a Valid Value for Each Text Field!");
            alert.showAndWait();
        }
    }
    /**
     @param actionEvent The cancel button returns to home page.
     */
    @FXML
    private void cancelHandler(ActionEvent actionEvent) throws IOException{
        //confirmation box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All Text values will be cleared, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     @param actionEvent The radio button will display Machine ID.
     */
    @FXML
    private void nHouseRbutton(ActionEvent actionEvent) {
        machineIDLbl.setText("Machine ID");
    }
    /**
     @param actionEvent The cancel button displays the Company Name.
     */
    @FXML
    private void oHouseRButton(ActionEvent actionEvent) {
        machineIDLbl.setText("Company Name");
    }
}
