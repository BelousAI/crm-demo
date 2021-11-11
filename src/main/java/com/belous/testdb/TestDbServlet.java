package com.belous.testdb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // setup connection variable
        String user = "springuser";
        String pass = "springuser";

        String jdbcUrl = "jdbc:mysql://localhost:3306/crm_customer_tracker?useSSL=false&serverTimezone=UTC";
        String driver = "com.mysql.jdbc.Driver";

        try {
            PrintWriter out = resp.getWriter();
            out.println("Connecting to database: " + jdbcUrl);

            Class.forName(driver);

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);

            out.println("Success!!!");

            myConn.close();
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
