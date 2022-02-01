package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *  Class Inventory.java
 */

/**
 *
 * @author Justin Brown
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * @param newPart the addPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**
     * @param newProduct the addProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * @return the lookupPart
     */
    public static Part lookupPart(int partId) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId) {
                return allParts.get(i);
            }
        }
        return null;
    }
    /**
     * @return the lookupProduct
     */
    public static Product lookupProduct(int productId){
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productId) {
                return allProducts.get(i);
            }
        }
        return null;
    }
    /**
     * @return the lookupPart
     */
    public static ObservableList<Part> lookupPart(String partialName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part tempPart: allParts){
            if(tempPart.getName().contains(partialName)){
                namedParts.add(tempPart);
            }
        }
        return namedParts;
    }
    /**
     * @return the lookupProduct
     */
    public static ObservableList<Product> lookupProduct(String partialName){
            ObservableList<Product> namedProds = FXCollections.observableArrayList();

            ObservableList<Product> allProducts = Inventory.getAllProducts();

            for(Product tempProd: allProducts){
                if(tempProd.getName().contains(partialName)){
                    namedProds.add(tempProd);
                }
            }
            return namedProds;
    }
    /**
     * @param index the updatePart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /**
     * @param index the updateProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * @param selectedPart the deletePart
     */
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    /**
     * @param selectedProduct the deletePart
     */
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }

    /**
     * @return the getAllParts
     */
    public static Part getAllParts(int id) {
        ObservableList<Part> allParts = getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part tempPart = allParts.get(i);

            if (tempPart.getId() == id) {
                return tempPart;
            }
        }
        return null;
    }
    /**
     * @return the getAllProducts
     */
    public static Product getAllProducts(int partialId) {
        ObservableList<Product> allProducts = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product tempProd = allProducts.get(i);

            if (tempProd.getId() == partialId) {
                return tempProd;
            }
        }
        return null;
    }
    /**
     * @return the getAllParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     * @return the getAllProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}


