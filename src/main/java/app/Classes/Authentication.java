package app.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Authentication {
    DataBaseConnection dataBaseConnection;
    private String password;

   public Authentication(String password){
        this.password = password;
    }
    public Authentication(){

    }


    public boolean check(){
        dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from auth where password = ?";
        Connection connection = dataBaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,Cypher.hashPassword(getPassword()));

            ResultSet resultSet = statement.executeQuery();
            int counter = 0;
            while (resultSet.next())
                counter++;
            return counter > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean passwordSeted(){
        dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);
        String query = "select * from auth ";
        List<Map<String,String>> list =dataBaseConnection.select(query);
        return  !(list== null || list.isEmpty());
    }

    public void setPass(String newPassword){
        dataBaseConnection = new DataBaseConnection(DataBaseConnection.dbPath);

       if (passwordSeted()){
        dataBaseConnection.excute("delete from auth;");
       }

           Connection connection = dataBaseConnection.getConnection();
           String query = "insert into auth(password) values (?)";
           try {
               PreparedStatement statement = connection.prepareStatement(query);
               statement.setString(1,Cypher.hashPassword(newPassword));
//               statement.setString(1,newPassword);
               statement.execute();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }

    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
