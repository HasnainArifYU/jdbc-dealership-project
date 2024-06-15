package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.*;

public class SalesDao {
    private DataSource dataSource;
    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void addSalesContract(SalesContract salesContract) {
        // TODO: Implement the logic to add a sales contract
        String sql = "INSERT INTO sales_contracts (VIN, sale_date, price) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, salesContract.getVin());
            preparedStatement.setDate(2, Date.valueOf(salesContract.getSaleDate()));
            preparedStatement.setDouble(3, salesContract.getPrice());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        salesContract.setContractId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Rows inserted: " + rows);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

