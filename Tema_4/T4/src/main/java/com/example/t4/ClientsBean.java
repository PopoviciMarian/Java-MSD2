package com.example.t4;


import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ClientsBean implements Serializable {
    private Client selectedClient;
    @Resource(name = "routingResource")
    private DataSource ds;

    public List<Client> getClients() throws SQLException {

        List<Client> clients = new ArrayList<Client>();
        try (Connection con = ds.getConnection()) {
            String sql = "select * from Clients ORDER BY ID ASC";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clients.add(
                        Client.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .email(resultSet.getString("email"))
                                .phone(resultSet.getString("phone"))
                                .x(resultSet.getInt("x"))
                                .y(resultSet.getInt("y"))
                                .build()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void createNewClient() {
        this.selectedClient = Client.builder().build();
    }
    public void createEditClient(){
        System.out.println("createEditClient");
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void updateClient() {

        try (Connection con = ds.getConnection()) {
            String sql = "update clients set name=?, email=?, phone=?, x=?, y=? where id=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, selectedClient.getName());
            preparedStatement.setString(2, selectedClient.getEmail());
            preparedStatement.setString(3, selectedClient.getPhone());
            preparedStatement.setInt(4, selectedClient.getX());
            preparedStatement.setInt(5, selectedClient.getY());
            preparedStatement.setInt(6, selectedClient.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveClient() {
        if  (selectedClient.getId() != null && selectedClient.getId() != 0)  {
            updateClient();
            return;
        }
        try (Connection con = ds.getConnection()) {

            String sql = "insert into clients(name, email, phone, x, y) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, selectedClient.getName());
            preparedStatement.setString(2, selectedClient.getEmail());
            preparedStatement.setString(3, selectedClient.getPhone());
            preparedStatement.setInt(4, selectedClient.getX());
            preparedStatement.setInt(5, selectedClient.getY());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }
}

