package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate(("CREATE TABLE IF NOT EXISTS mysql.users" +
                    "(id mediumint not null auto_increment," +
                    " name VARCHAR(50), " +
                    "lastname VARCHAR(50), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))"));
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = conn.createStatement()){
            statement.executeUpdate(("Drop table if exists mysql.users"));
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO mysql.users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = conn.createStatement()) {
            String sql = "DELETE FROM mysql.users where id";
            statement.executeUpdate(sql);
            System.out.println("User удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from mysql.users";

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }
    public void cleanUsersTable() {
        String sql = "DELETE FROM mysql.users";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("ДАННЫЕ УДАЛЕНЫ");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ДАННЫЕ НЕ УДАЛЕНЫ");
        }
    }
}
