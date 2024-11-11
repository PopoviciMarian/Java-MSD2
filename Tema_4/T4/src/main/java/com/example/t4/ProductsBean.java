package com.example.t4;

import javax.faces.context.FacesContext;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@SessionScoped
@Named
public class ProductsBean implements Serializable {
    private Product selectedProduct;
    @Resource(name = "routingResource")
    private DataSource ds;


    public ProductsBean(){
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();
        try (Connection con = ds.getConnection()) {
            String sql = "select * from products ORDER BY ID";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(
                        Product.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .price(resultSet.getDouble("price"))
                                .quantity(resultSet.getInt("quantity"))
                                .build()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;

    }

    public void createNewProduct() {
        this.selectedProduct = Product.builder().build();
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void updateProduct() {

        try (Connection con = ds.getConnection()) {
            String sql = "update products set name=?, price=?, quantity=? where id=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, selectedProduct.getName());
            preparedStatement.setDouble(2, selectedProduct.getPrice());
            preparedStatement.setInt(3, selectedProduct.getQuantity());
            preparedStatement.setInt(4, selectedProduct.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProduct() {
        if ( selectedProduct.getId() != 0) {
            updateProduct();
            return;
        }
        try (Connection con = ds.getConnection()) {
            String sql = "insert into products(name, price, quantity) values(?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, selectedProduct.getName());
            preparedStatement.setDouble(2, selectedProduct.getPrice());
            preparedStatement.setInt(3, selectedProduct.getQuantity());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEditProduct(){

    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }




}
