package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegForm extends JDialog {
    private JTextField tfName;
    private JTextField tfLastName;
    private JTextField tfEmailAddress;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;
    private JButton btnShow;
    private JButton btnDelete;




    final String DB_URL = "jdbc:mysql://localhost/sys";
    final String USERNAME = "root";
    final String PASSWORD = "password";

    public RegForm(JFrame parent) {
        super(parent);
        setTitle("Registration Form");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(400, 350));
        // setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowTable showTable = new ShowTable();
            }
        });

    }


    private void registerUser() {
        String name = tfName.getText();
        String lastName = tfLastName.getText();
        String emailAddress = tfEmailAddress.getText();

        if (name.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(name, lastName, emailAddress);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "User registered", "Good Job", JOptionPane.PLAIN_MESSAGE);
            tfName.setText("");
            tfLastName.setText("");
            tfEmailAddress.setText("");
        }
    }

    public User user;

    private User addUserToDatabase(String name, String lastName, String emailAddress) {
        User user = null;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO user (name, lastName,emailAddress)" + " Values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, emailAddress);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.lastName = lastName;
                user.emailAddress = emailAddress;
            }

            stmt.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}
