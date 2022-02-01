package Controller;

import Model.*;
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
 *  Class ModifyPartController.java
 */

/**
 *
 * @author Justin Brown
 */
public class ModifyPartController implements Initializable {

    private Part sendPart;

    @FXML
    private Label companyLabel;
    @FXML
    private TextField partId;
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
    private RadioButton radioN;

    /**
     @param part sends information to main.
     */
    public void sendParts(Part part) {
        sendPart = part;
        partId.setText(String.valueOf(sendPart.getId()));
        partName.setText(sendPart.getName());
        partInventory.setText((String.valueOf(sendPart.getStock())));
        partPrice.setText(String.valueOf(sendPart.getPrice()));
        partMax.setText(String.valueOf(sendPart.getMax()));
        partMin.setText(String.valueOf(sendPart.getMin()));

        //string
        if(sendPart instanceof OutSource) {
            partMachineId.setText(((OutSource) sendPart).getCompanyName());
        }

        /** ERROR.
         @param part could not use an integer.
         Needed to add 2nd line, int machineID = ((InHouse) part).getMachineID;. 
         */
        //integer
        else if(sendPart instanceof InHouse) {
            int machineID = ((InHouse) sendPart).getMachineID();
            partMachineId.setText(String.valueOf(machineID));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     @param actionEvent The cancel button returns to home page.
     */
    @FXML
    public void ModpartCancelbtn(ActionEvent actionEvent) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All changes will not be saved, do you want to continue?");
    Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }
}
    /**
     @param actionEvent The radio button will display Machine ID.
     */
    @FXML
    private void radioNHouse(ActionEvent actionEvent) {
        companyLabel.setText("Machine ID");
    }
    /**
     @param actionEvent The cancel button displays the Company Name.
     */
    @FXML
    private void radioOsourced(ActionEvent actionEvent) {
        companyLabel.setText("Company Name");
    }

    /**
     @param actionEvent The save button updates a Product.
     */
    @FXML
    private void ModpartSavebtn(ActionEvent actionEvent) throws IOException {
            try{
                int id = sendPart.getId();
                String name = partName.getText();
                int stock = Integer.parseInt(partInventory.getText());
                double price = Double.parseDouble(partPrice.getText());
                int max = Integer.parseInt(partMax.getText());
                int min = Integer.parseInt(partMin.getText());

                if(radioN.isSelected()){
                    int machineID = Integer.parseInt(partMachineId.getText());
                    int index = Inventory.getAllParts().indexOf(sendPart);
                    Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineID));
                }
                else{
                    String companyName = partMachineId.getText();
                    int index = Inventory.getAllParts().indexOf(sendPart);
                    Inventory.updatePart(index, new OutSource(id, name, price, stock, min, max, companyName));
                }

                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main");
                stage.setScene(scene);
                stage.show();
            }
            catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please Enter a Valid Value for Each Text Field!");
                alert.showAndWait();
            }
        }
    }
