package dao;


import DB.My_Connection;

import Model.Data;

import java.awt.image.RescaleOp;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Data_DAO {
    public static List<Data> getAllFiles(String email) throws SQLException {
        Connection connection = My_Connection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from Data_Info where email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        List<Data> files = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String path = rs.getString(3);
            files.add(new Data(id, name, path));
        }
        return files;
    }
    public static int hideFile(Data file)throws SQLException, IOException {
        Connection connection = My_Connection.getConnection();
        PreparedStatement ps = connection.prepareStatement(
                "insert into Data_Info(File_Name, Path, email, Bin_Data) values(?,?,?,?)");
        ps.setString(1, file.getFileName());
        ps.setString(2, file.getPath());
        ps.setString(3, file.getEmail());
        File f = new File(file.getPath());
        FileReader fr = new FileReader(f);
        ps.setCharacterStream(4, fr, f.length());
        int ans = ps.executeUpdate();
        fr.close();
        f.delete();
        return ans;
    }
    public static void unhide(int id) throws SQLException, IOException {
        Connection connection = My_Connection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select Path, Bin_Data from Data_Info where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String path = rs.getString("path");
        Clob c = rs.getClob("bin_data");

        Reader r = c.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        int i;
        while ((i = r.read()) != -1) {
            fw.write((char) i);
        }
        fw.close();
        ps = connection.prepareStatement("delete from Data_Info where id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Successfully Unhidden");
    }
}
