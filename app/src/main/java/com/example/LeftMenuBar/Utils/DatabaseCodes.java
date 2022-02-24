package com.example.LeftMenuBar.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCodes {

    static UserLogin U = new UserLogin();

    public static class getLoginInofrmation {

        public static void getLoginInofrmation() {

            System.out.println("Working");

            String settingUser = U.getLoginUsername();
            String settingPass = U.getLoginPassword();

            Connection conn = null;
            Statement ps = null;
            ResultSet rs = null;

            try
            {
                conn = DriverManager.getConnection("jdbc:mysql://62.108.35.72:3306/CarPollution?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "java", "m1a2l3i4Q%i6");
                String query = ("SELECT * FROM user WHERE username = '" + settingUser + "' and password = '" + settingPass + "'");

                ps = conn.createStatement();
                rs = ps.executeQuery(query);
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String userLogin1 = rs.getString("username");
                    String passLogin1 = rs.getString("password");
                    String countryLogin1 = rs.getString("country");
                    String numberLogin1 = rs.getString("number");
                    String emailLogin1 = rs.getString("email");
                    String fullnameLogin1 = rs.getString("fullname");

                    U.setLoginID(id);
                    U.setLoginUsername(userLogin1);
                    U.setLoginPassword(passLogin1);
                    U.setLoginCountry(countryLogin1);
                    U.setLoginNumber(numberLogin1);
                    U.setLoginEmail(emailLogin1);
                    U.setLoginFullname(fullnameLogin1);

                    //We do not need this
                    //It is just to check if the input is correct
                    //We are going delete them!
                    System.out.println(U.getLoginID());
                    System.out.format("%s, %s, %s, %s, %s, %s, %s\n", id, userLogin1, passLogin1, countryLogin1, numberLogin1,emailLogin1,fullnameLogin1);

                    System.out.println("Kjo eshte DB " + U.getLoginUsername());
                    System.out.println("Kjo eshte DB " + U.getLoginEmail());


                }
                ps.close();
            }
            catch (Exception e)
            {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());

            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) { /* Ignored */}
                }
            }
        }

    }
}
