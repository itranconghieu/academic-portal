// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.*;

/**
 *
 * @author End User
 */
public abstract class Open {
    static Connection cn;
    public Open() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;"
                + "databaseName=PRJ301;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "Hieu2211#";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(driver);
        cn = DriverManager.getConnection(url, user, password);
    }
}
