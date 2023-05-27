package service;

import model.chirias.Chirias;
import model.cladire.Cladire;
import model.persoana.Angajat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Service {
    private Map<String, String> users;
    private ArrayList<Cladire> cladiri;
    private ArrayList<Chirias> chiriasi;
    private ArrayList<Angajat> angajati;
    private static Service instanta = null;
    private static final String DB_URL = "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8621755";
    private static final String USERNAME = "sql8621755";
    private static final String PASSWORD = "fQT6UYrabi";
    public static Connection connection;

    private static void Con() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    private Service() {
        users = new HashMap<>();
        // Add some sample users and their passwords
        users.put("admin", "admin123");
        users.put("chirias", "chirias123");
        users.put("sef", "sef123");
        try{
            Con();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Service getInstance() {
        if (instanta == null) {
            instanta = new Service();
        }
        return instanta;
    }

    public Object login(String username, String password, Integer id) {
        String storedPassword = users.get(username);
        if (storedPassword != null && storedPassword.equals(password))
        if (username.equals("admin")) {
            return adminMethod(id);
        } else if (username.equals("chirias")) {
            return chiriasMethod(id);
        } else if (username.equals("sef")) {
            return sefMethod();
        }
        return null;
    }

    public Object adminMethod(Integer id) {
        System.out.println("This is the admin method.");
        return new Admin(cladiri.get(id));
    }

    public Object chiriasMethod(Integer id) {
        System.out.println("This is the chirias method.");
        return new Chir(chiriasi.get(id));
    }

    public Object sefMethod() {
        System.out.println("This is the sef method.");
        return Sef.getInstance(cladiri, angajati);
    }
}