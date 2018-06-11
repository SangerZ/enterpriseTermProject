package com.mycompany.termprojectmaven;

import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloAppEngine extends HttpServlet {

    public class ProductItem {

        public int id;
        public String name;
        public double price;
        public String description;
        public String last_update;
        public int categoryId;

        ProductItem(int id, String name, double price, String description, String last_update, int categoryId) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.last_update = last_update;
            this.categoryId = categoryId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public String getLast_update() {
            return last_update;
        }

        public int getCategoryId() {
            return categoryId;
        }
    }

    public class CategoryItem {

        public int id;
        public String name;

        CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

    Connection conn;
    String url = "";
    String status = "peanut";
    final String selectSQL = "SELECT * FROM mydatabase.category";
    final String selectCategorySQL = "SELECT * FROM mydatabase.category";
    final String selectProductSQL = "SELECT * FROM mydatabase.product";

    List<ProductItem> productItems = new ArrayList<ProductItem>();
    List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();

    public void selectProduct(HttpServletRequest req) throws ServletException {
        try {
            PreparedStatement stmt = conn.prepareStatement(selectProductSQL);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                productItems.add(new ProductItem(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getDouble("price"),
                        result.getString("description"),
                        result.getString("last_update"),
                        result.getInt("category_id")));
            }
        } catch (SQLException e) {

        }
    }

    public void selectCategory(HttpServletRequest req) throws ServletException {
        try {
            PreparedStatement stmt = conn.prepareStatement(selectCategorySQL);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                categoryItems.add(new CategoryItem(
                        result.getInt("id"),
                        result.getString("name")));
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void init() throws ServletException {
        url = System.getProperty("cloudsql");
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            conn = DriverManager.getConnection("jdbc:google:mysql://sangerz-project:us-central1:inventory/mydatabase?user=root&password=admin");
            status = "connected";
        } catch (Exception e) {
            status = "unable to connect " + e;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Properties properties = System.getProperties();

        PrintWriter out = response.getWriter();

        response.setContentType("text/plain");
        //response.getWriter().println("peanut");
        response.getWriter().println("Hello App Engine - Standard using " + SystemProperty.version.get() + " Java " + properties.get("java.specification.version"));
        try {
            PreparedStatement stmt = conn.prepareStatement(selectProductSQL);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                productItems.add(new ProductItem(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getDouble("price"),
                        result.getString("description"),
                        result.getString("last_update"),
                        result.getInt("category_id")));
                response.getWriter().print(productItems.get(productItems.size()-1).id + " ");
                response.getWriter().print(productItems.get(productItems.size()-1).name + " ");
                response.getWriter().print(productItems.get(productItems.size()-1).price + " ");
                response.getWriter().print(productItems.get(productItems.size()-1).description + " ");
                response.getWriter().println(productItems.get(productItems.size()-1).categoryId);
            }
        } catch (Exception e) {

        }
    }

    public static String getInfo() {
        return "Version: " + System.getProperty("java.version")
                + " OS: " + System.getProperty("os.name")
                + " User: " + System.getProperty("user.name");
    }

}
