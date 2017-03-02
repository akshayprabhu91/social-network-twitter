/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PrabhuA6510
 */
public class DatabaseOperations {

    static final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/prabhua6510";
    static Connection conn = null;
    static Statement stat = null;
    static ResultSet rs = null;

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Internal Error!");
        }
    }

    public static boolean ifRecordsExist(String query) {
        try {
            loadDriver();
            conn = DriverManager.getConnection(DB_URL, "prabhua6510", "1441868");
            stat = conn.createStatement();
            rs = stat.executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stat != null) {
                    stat.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String fetchASingleValue(String query) {
        try {
            loadDriver();
            conn = DriverManager.getConnection(DB_URL, "prabhua6510", "1441868");
            stat = conn.createStatement();
            rs = stat.executeQuery(query);

            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stat != null) {
                    stat.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertUpdateDelete(String query) {
        try {
            loadDriver();
            conn = DriverManager.getConnection(DB_URL, "prabhua6510", "1441868");
            stat = conn.createStatement();
            stat.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Object> fetchResultSet(String query, String obj) {
        List<Object> arr = new ArrayList<>();
        try {
            loadDriver();
            conn = DriverManager.getConnection(DB_URL, "prabhua6510", "1441868");
            stat = conn.createStatement();
            rs = stat.executeQuery(query);

            if (obj.equalsIgnoreCase("AllUsers")) {
                arr.add(new CommonObject(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            if (obj.equalsIgnoreCase("LoggedInUser")) {
                arr.add(new CommonObject(rs.getString(1), rs.getBlob(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            if (obj.equalsIgnoreCase("Tweets")) {
                arr.add(new CommonObject(rs.getString(1), rs.getString(2), rs.getString(3)));
            }

            if (obj.equalsIgnoreCase("Following")
                    || obj.equalsIgnoreCase("MyFollowers")) {
                arr.add(new CommonObject(rs.getString(1), rs.getString(2)));
            }

            return arr;
        } catch (SQLException e) {
            e.printStackTrace();
            return arr;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stat != null) {
                    stat.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
