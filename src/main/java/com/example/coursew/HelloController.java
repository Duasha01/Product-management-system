package com.example.coursew;


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.bson.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

public class HelloController {
    //Accessing the database in mongodb compass
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase mongoDatabase = mongoClient.getDatabase("Priyalal_Stores");


    //Accessing the related collections
    MongoCollection<Document> collection = mongoDatabase.getCollection("Login");
    MongoCollection<Document> collection1 = mongoDatabase.getCollection("categories");
    MongoCollection<Document> collection2 = mongoDatabase.getCollection("products");

    //Login stage
    @FXML
    Label error;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button loginbtn;

    //Category stage
    @FXML
    Button Add_category;
    @FXML
    Button deleteUsingIDbtn;
    @FXML
    TextField deleteUsingID;
    @FXML
    TextField deleteUsingName;
    @FXML
    TextField category_ID;
    @FXML
    TextField category_Name;
    @FXML
    TextField category_Count;
    @FXML
    TextField category_Description;
    @FXML
    TableView category_table;
    @FXML
    TableColumn C_ID_column;
    @FXML
    TableColumn C_Name_column;
    @FXML
    TableColumn C_count_Column;
    @FXML
    TableColumn C_Description_column;
    @FXML
    TextField oldfield;
    @FXML
    TextField newfield;
    @FXML
    TextField oldfield1;
    @FXML
    TextField newfield1;
    @FXML
    TextField oldfield2;
    @FXML
    TextField newfield2;
    @FXML
    TextField oldfield3;
    @FXML
    TextField newfield3;

    //Product stage
    @FXML
    TextField product_ID;
    @FXML
    TextField P_Category_ID;
    @FXML
    TextField product_Name;
    @FXML
    TextField product_Count;
    @FXML
    TextField product_Description;
    @FXML
    TextField deleteProductID;
    @FXML
    TextField deleteProductNametxt;
    @FXML
    TableView product_table;
    @FXML
    TableColumn P_ID_column;
    @FXML
    TableColumn P_CID_column;
    @FXML
    TableColumn P_Name_column;
    @FXML
    TableColumn P_Count_column;
    @FXML
    TableColumn P_description_column;
    @FXML
    Label searched;
    @FXML
    ComboBox<String> Combocategories;
    @FXML
    TextField searchByID1;
    @FXML
    TextField oldedit;
    @FXML
    TextField newedit;
    @FXML
    TextField oldedit1;
    @FXML
    TextField newedit1;
    @FXML
    TextField oldedit2;
    @FXML
    TextField newedit2;
    @FXML
    TextField oldedit3;
    @FXML
    TextField newedit3;
    @FXML
    TextField oldedit4;
    @FXML
    TextField newedit4;
    @FXML
    TextField searchByID;
    @FXML
    TextField searchByName;
    @FXML
    Label searchName;
    @FXML
    Label searchName1;
    @FXML
    Label searchdesc;
    @FXML
    Label searchdesc1;
    @FXML
    TextField searchByName1;
    @FXML
    TextField deleteProductNametxt1;
    @FXML
    TextField deleteProductID1;
    @FXML
    ChoiceBox<String> choicebox;

    //Stocks
    @FXML
    TableView stockTable;
    @FXML
    TableColumn S_ID_Column;
    @FXML
    TableColumn S_Product_Column;
    @FXML
    TableColumn S_Count_Column;
    @FXML
    TextField oldCount;
    @FXML
    TextField newCount;
    @FXML
    TextField S_ID;


    //Logout stage
    @FXML
    Button closebtn;
    @FXML
    Button logoutbtn;

    //Login to Priyalal Stores
    public void login_page() throws IOException {
        String username_db = null;
        String password_db = null;

        FindIterable<Document> findIterable = collection.find();
        for (Document doc : findIterable) {
            username_db = (String) doc.get("username");
            password_db = (String) doc.get("password");
        }

        if (username.getText().equals(username_db) && password.getText().equals(password_db)) {
            onclick();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please enter a valid Username and Password");
            alert.show();

        }
        Stage stage = (Stage) loginbtn.getScene().getWindow();
        stage.close();
    }

    //Moving on to the home page
    private void onclick() throws IOException {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setTitle("Home");
        stage1.setScene(scene);
        stage1.show();
    }

    //Moving to categories page
    public void loadcategory() throws IOException {
        Stage stage2 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("categories.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2.setTitle("Categories");
        stage2.setScene(scene);
        stage2.show();


    }

    //Adding categories to the database
    public void Add_category() {
        collection1 = mongoDatabase.getCollection("categories");
        Document category = new Document();
        category.put("id", category_ID.getText());
        category.put("Name", category_Name.getText());
        category.put("count", Integer.parseInt(category_Count.getText()));
        category.put("description", category_Description.getText());
        collection1.insertOne(category);
        category_ID.setText("");
        category_Name.setText("");
        category_Count.setText("");
        category_Description.setText("");
    }
    //Edit category data
    public void Editfields() {
        collection1 = mongoDatabase.getCollection("categories");
        BasicDBObject query = new BasicDBObject();
        query.put("id", oldfield.getText());
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("id", newfield.getText());
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection1.updateOne(newquery, updatequery);
        oldfield.setText("");
        newfield.setText("");

        collection1 = mongoDatabase.getCollection("categories");
        BasicDBObject query1 = new BasicDBObject();
        query1.put("Name", oldfield1.getText());
        BasicDBObject newquery1 = new BasicDBObject();
        newquery1.put("Name", newfield1.getText());
        BasicDBObject updatequery1 = new BasicDBObject();
        updatequery1.put("$set", newquery1);
        collection1.updateOne(query1, updatequery1);
        oldfield1.setText("");
        newfield1.setText("");

        collection1 = mongoDatabase.getCollection("categories");
        BasicDBObject query2 = new BasicDBObject();
        query2.put("count", oldfield2.getText());
        BasicDBObject newquery2 = new BasicDBObject();
        newquery2.put("count", newfield2.getText());
        BasicDBObject updatequery2 = new BasicDBObject();
        updatequery2.put("$set", newquery2);
        collection1.updateOne(query2, updatequery2);
        oldfield2.setText("");
        newfield2.setText("");

        collection1 = mongoDatabase.getCollection("categories");
        BasicDBObject query3 = new BasicDBObject();
        query3.put("description", oldfield3.getText());
        BasicDBObject newquery3 = new BasicDBObject();
        newquery3.put("description", newfield3.getText());
        BasicDBObject updatequery3 = new BasicDBObject();
        updatequery3.put("$set", newquery3);
        collection1.updateOne(query3, updatequery3);
        oldfield3.setText("");
        newfield3.setText("");
    }
    //View categories in a table
    public void view_tabledata() {
        collection1 = mongoDatabase.getCollection("categories");

        C_ID_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        C_Name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        C_count_Column.setCellValueFactory(new PropertyValueFactory<>("count"));
        C_Description_column.setCellValueFactory(new PropertyValueFactory<>("description"));

        FindIterable<Document> categoryIterable = collection1.find();
        ObservableList<categories> categorylist = FXCollections.observableArrayList();
        for (Document doc : categoryIterable) {
            categories category = new categories();
            category.setId(doc.getString("id"));
            category.setName(doc.getString("Name"));
            category.setCount(doc.getInteger("count"));
            category.setDescription(doc.getString("description"));
            categorylist.add(category);
        }
        category_table.setItems(categorylist);
    }

    //Deleting a category
    public void deleteUsingID() {
        collection1 = mongoDatabase.getCollection("categories");
        collection1.deleteOne(Filters.eq("id", deleteUsingID.getText()));
        deleteUsingID.setText("");
    }

    public void deleteUsingName() {
        collection1 = mongoDatabase.getCollection("categories");
        collection1.deleteOne(Filters.eq("Name", deleteUsingName.getText()));
        deleteUsingID.setText("");
    }
    //Moving to the products page
    public void products() throws IOException {
        Stage stage3 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("products.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage3.setTitle("Products");
        stage3.setScene(scene);
        stage3.show();
    }

    //Adding products to the database
    public void add_product() {
        collection2 = mongoDatabase.getCollection("products");
        Document category1 = new Document();
        category1.put("id", product_ID.getText());
        category1.put("Category_id", P_Category_ID.getText());
        category1.put("Name", product_Name.getText());
        category1.put("Count", Integer.parseInt(product_Count.getText()));
        category1.put("Description", product_Description.getText());
        collection2.insertOne(category1);
        product_ID.setText("");
        P_Category_ID.setText("");
        product_Name.setText("");
        product_Count.setText("");
        product_Description.setText("");
    }

    //Using a combo box to select
    public void search1() {
        collection1 = mongoDatabase.getCollection("categories");
        FindIterable<Document> categoryIterabledocument = collection1.find();
        ObservableList<String> categoryIDList = FXCollections.observableArrayList();
        for (Document doc : categoryIterabledocument) {
            categoryIDList.add((String) doc.get("Name"));
        }
        choicebox.setItems(categoryIDList);
    }

    //search products by id or name
    public void search() {
        collection2 = mongoDatabase.getCollection("products");
        FindIterable<Document> searchID = collection2.find();

        for (Document doc : searchID) {
            String search = searchByID.getText();
            if (search.equals((String) doc.get("id"))) {
                searchName.setText((String) doc.get("Name"));
                searchdesc.setText((String) doc.get("Description"));
            }
        }
    }

    public void searchName() {
        collection2 = mongoDatabase.getCollection("products");
        FindIterable<Document> searchByNamedoc = collection2.find();

        for (Document doc : searchByNamedoc) {
            String search = searchByName.getText();
            if (search.equals((String) doc.get("Name"))) {
                searchName1.setText((String) doc.get("Name"));
                searchdesc1.setText((String) doc.get("Description"));
            }
        }
    }

    public void cleartext1() {
        searchName.setText("");
        searchdesc.setText("");
    }

    //search stock count by id or name
    public void searchStockID() {
        collection2 = mongoDatabase.getCollection("products");
        FindIterable<Document> searchID = collection2.find();

        for (Document doc : searchID) {
            String search = searchByID1.getText();
            if (search.equals((String) doc.get("id"))) {
                searchName1.setText((String) doc.get("Name"));
                searchdesc1.setText("" + (Integer) doc.get("Count"));
            }
        }
    }

    public void searchStockName() {
        collection2 = mongoDatabase.getCollection("products");
        FindIterable<Document> searchID = collection2.find();

        for (Document doc : searchID) {
            String search = searchByName1.getText();
            if (search.equals((String) doc.get("Name"))) {
                searchName1.setText((String) doc.get("Name"));
                searchdesc1.setText("" + (Integer) doc.get("Count"));
            }
        }
    }

    public void cleartext2() {
        searchName1.setText("");
        searchdesc1.setText("");
    }

    //Editing products in database
    public void editID() {
        collection2 = mongoDatabase.getCollection("products");
        BasicDBObject query = new BasicDBObject();
        query.put("id", oldedit.getText());
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("id", newedit.getText());
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection2.updateOne(query, updatequery);
    }

    public void editC_ID() {
        collection2 = mongoDatabase.getCollection("products");
        BasicDBObject query = new BasicDBObject();
        query.put("CategoryID", oldedit1.getText());
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("CategoryID", newedit1.getText());
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection2.updateOne(query, updatequery);
    }

    public void editName() {
        collection2 = mongoDatabase.getCollection("products");
        BasicDBObject query = new BasicDBObject();
        query.put("Name", oldedit2.getText());
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("Name", newedit2.getText());
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection2.updateOne(query, updatequery);
    }

    public void editcount() {
        collection2 = mongoDatabase.getCollection("products");
        BasicDBObject query = new BasicDBObject();
        query.put("Count", Integer.parseInt(oldedit3.getText()));
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("Count", Integer.parseInt(newedit3.getText()));
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection2.updateOne(query, updatequery);
    }

    public void editdesc() {
        collection2 = mongoDatabase.getCollection("products");
        BasicDBObject query = new BasicDBObject();
        query.put("Description", oldedit4.getText());
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("Description", newedit4.getText());
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection2.updateOne(query, updatequery);
    }

    //Deleting a product
    public void deleteProductID() {
        collection2 = mongoDatabase.getCollection("products");
        collection2.deleteOne(Filters.eq("id", deleteProductID1.getText()));
    }

    public void deleteProductName() {
        collection2 = mongoDatabase.getCollection("products");
        collection2.deleteOne(Filters.eq("Name", deleteProductNametxt1.getText()));
    }

    //Clear data in text fields
    public void clearid() {
        deleteProductID.clear();
    }

    public void clearname() {
        deleteProductNametxt.clear();
    }

    //View data in the table
    public void viewdata() {
        collection2 = mongoDatabase.getCollection("products");

        P_ID_column.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        P_CID_column.setCellValueFactory(new PropertyValueFactory<>("product_C_id"));
        P_Name_column.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        P_Count_column.setCellValueFactory(new PropertyValueFactory<>("product_count"));
        P_description_column.setCellValueFactory(new PropertyValueFactory<>("product_description"));

        FindIterable<Document> categoryIterable = collection2.find();
        ObservableList<products> categorylist = FXCollections.observableArrayList();
        for (Document doc1 : categoryIterable) {
            products Products = new products();
            Products.setProduct_id(doc1.getString("id"));
            Products.setProduct_C_id(doc1.getString("Category_id"));
            Products.setProduct_name(doc1.getString("Name"));
            Products.setProduct_count(doc1.getInteger("Count"));
            Products.setProduct_description(doc1.getString("Description"));
            categorylist.add(Products);
        }
        product_table.setItems(categorylist);
    }

    //View stock details in a table
    public void viewStocks() {
        collection2 = mongoDatabase.getCollection("products");

        S_ID_Column.setCellValueFactory(new PropertyValueFactory<>("S_id"));
        S_Product_Column.setCellValueFactory(new PropertyValueFactory<>("S_Name"));
        S_Count_Column.setCellValueFactory(new PropertyValueFactory<>("S_Count"));

        FindIterable<Document> categoryIterable = collection2.find();
        ObservableList<stocks> stockList = FXCollections.observableArrayList();
        for (Document doc1 : categoryIterable) {
            stocks Stocks = new stocks();
            Stocks.setS_id(doc1.getString("id"));
            Stocks.setS_Name(doc1.getString("Name"));
            Stocks.setS_Count(doc1.getInteger("Count"));
            stockList.add(Stocks);
        }
        stockTable.setItems(stockList);
    }
    //Add to stock
    public void addStock() {
        collection1 = mongoDatabase.getCollection("products");
        collection1 = mongoDatabase.getCollection("products");
        BasicDBObject query = new BasicDBObject();
        query.put("Count", Integer.parseInt(oldCount.getText()));
        BasicDBObject newquery = new BasicDBObject();
        newquery.put("Count", Integer.parseInt(newCount.getText()));
        BasicDBObject updatequery = new BasicDBObject();
        updatequery.put("$set", newquery);
        collection1.updateOne(query, updatequery);

    }
    //Removing stock of a product
    public void remove() {
        collection1 = mongoDatabase.getCollection("products");
        collection2.deleteOne(Filters.eq("count", S_ID.getText()));
    }
    //Logout of application
    public void Logout() throws IOException {
        Stage stage5 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Logout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage5.setTitle("Logout");
        stage5.setScene(scene);
        stage5.show()
        ;
        Stage stage = (Stage) logoutbtn.getScene().getWindow();
        stage.close();
    }
    public void Login_again() throws IOException {
        Stage stage =new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login_Area");
        stage.setScene(scene);
        stage.show();
    }
    public void close_stage(){
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }
}















