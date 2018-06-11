/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.cart.ShoppingCart;
import com.mycompany.entity.Category;
import com.mycompany.entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mycompany.session.OrderManager;
import com.mycompany.session.CategoryFacade;
import com.mycompany.session.ProductFacade;

/**
 *
 * @author SangerZ
 */
public class ControllerServlet extends HttpServlet {
    
    private String surcharge;
    
    private CategoryFacade categoryFacade;
    private ProductFacade productFacade;
    private OrderManager orderManager;
    

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
    
    public void selectProduct() throws ServletException {
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

    public void selectCategory() throws ServletException {
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
    public void init(ServletConfig servletConfig) throws ServletException {
        
        super.init(servletConfig);
        surcharge = servletConfig.getServletContext().getInitParameter("deliveruSurcharge");
        
        
        url = System.getProperty("cloudsql");
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            conn = DriverManager.getConnection("jdbc:google:mysql://sangerz-project:us-central1:inventory/mydatabase?user=root&password=admin");
            status = "connected";
        } catch (Exception e) {
            status = "unable to connect " + e;
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Category selectedCategory;
        //Collection<Product> categoryProduct;
        
        String url = "";
        if (userPath.equals("/")) {
            url = userPath;
            selectCategory();
            request.setAttribute("categoryItems", categoryItems);
            response.setContentType("text/html");
            RequestDispatcher jsp = request.getRequestDispatcher("/index.jsp");
            jsp.forward(request, response);
        }

        // if category page is requested
        if (userPath.equals("/category")) {
            // TODO: Implement category request
            
            String categoryId = request.getQueryString();
            
            
            
            url = "/WEB-INF/view" + userPath + ".jsp";
            selectCategory();
            selectProduct();
            request.setAttribute("productItems", productItems);
            request.setAttribute("categoryItems", categoryItems);
            //session.setAttribute("productItems", productItems);
            //session.setAttribute("categoryItems", categoryItems);
            response.setContentType("text/html");

            // if cart page is requested
        } else if (userPath.equals("/viewCart")) {
            // TODO: Implement cart page request

            userPath = "/cart";
            url = "/WEB-INF/view" + userPath + ".jsp";

            // if checkout page is requested
        } else if (userPath.equals("/checkout")) {
            // TODO: Implement checkout page request
            url = "/WEB-INF/view" + userPath + ".jsp";

            // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {
            // TODO: Implement language request
            url = "/WEB-INF/view" + userPath + ".jsp";
        }

        // use RequestDispatcher to forward request internally
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {
            if(cart == null){
                cart = new ShoppingCart();
                //session.setAttribute("cart", cart);
                request.setAttribute("cart", cart);
            }
            String productId = request.getParameter("productId");
            if(!productId.isEmpty()){
                Product product = productFacade.find(Integer.parseInt(productId));
                
                cart.addItem(product);
            }
            userPath = "/category";
            
        } else if (userPath.equals("/updateCart")) {
            // TODO: Implement update cart action
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");
            

            userPath = "/cart";

            // if purchase action is called
        } else if (userPath.equals("/purchase")) {
            // TODO: Implement purchase action

            userPath = "/confirmation";
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
