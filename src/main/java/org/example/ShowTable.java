package org.example;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ShowTable extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton btnDelete;

    DefaultTableModel model = (DefaultTableModel) table1.getModel();


    public void DeleteUser() {


        try {
            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "password");
            Statement stmt = con2.createStatement();
            int row = table1.getSelectedRow();
            String value = (table1.getModel().getValueAt(row, 0).toString());
            String query = "DELETE FROM user WHERE userId=" + value;
            PreparedStatement preparedStatement = con2.prepareStatement(query);
            preparedStatement.executeUpdate();
            String query2 = "ALTER TABLE user AUTO_INCREMENT = 1";
            PreparedStatement preparedStatement1 = con2.prepareStatement(query2);
            preparedStatement1.executeUpdate();
            this.dispose();
            ShowTable showTable = new ShowTable();


            con2.close();
            stmt.close();


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    public ShowTable() {
        setTitle("ShowData");
        setContentPane(panel1);
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "password");
            Statement stmt2 = con2.createStatement();
            String query = "SELECT * FROM user";
            ResultSet rs = stmt2.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            for (int i = 0; i < cols; i++)
                colName[i] = rsmd.getColumnName(i + 1);
            model.setColumnIdentifiers(colName);

            String userId, name, lastName, emailaddress;
            while (rs.next()) {
                userId = rs.getString(1);
                name = rs.getString(2);
                lastName = rs.getString(3);
                emailaddress = rs.getString(4);
                String[] row = {userId, name, lastName, emailaddress};
                model.addRow(row);

            }

            stmt2.close();
            con2.close();


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteUser();
            }
        });
    }
}
