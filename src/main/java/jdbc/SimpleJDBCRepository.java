package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "INSERT INTO myusers (firstname, lastname, age) VALUES (?, ?, ?)";
    private static final String updateUserSQL = "UPDATE myusers SET firstname = ?, lastname = ?, age = ? WHERE id = ?";
    private static final String deleteUser = "DELETE FROM myusers WHERE id = ?";
    private static final String findUserByIdSQL = "SELECT * FROM myusers WHERE id = ?";
    private static final String findUserByNameSQL = "SELECT * FROM myusers WHERE firstname = ?";
    private static final String findAllUserSQL = "SELECT * FROM myusers";

    public Long createUser() {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(createUserSQL);
            String userFirstName = "Tom";
            String userLastName = "Peterson";
            int userAge = 29;
            ps.setString(1, userFirstName);
            ps.setString(2, userLastName);
            ps.setInt(3, userAge);
            long result = ps.executeLargeUpdate();
            ps.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUserById(Long userId) throws SQLException {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findUserByIdSQL);
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                ps.close();
                connection.close();
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new SQLException("Can't find user with id = " + userId);
    }

    public User findUserByName(String userName) throws SQLException {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findUserByNameSQL);
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                ps.close();
                connection.close();
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new SQLException("Can't find user with name = " + userName);
    }

    public List<User> findAllUser() {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(findAllUserSQL);
            List<User> allUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                allUsers.add(user);
                ps.close();
                connection.close();
            }
            return allUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser() {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(updateUserSQL);
            String firstName = "Ann";
            String lastName = "Wood";
            int age = 33;
            long userId = 1;
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.setLong(4, userId);
            ResultSet resultSet = ps.executeQuery();
            User updatedUser = new User();
            updatedUser.setId(resultSet.getLong(1));
            updatedUser.setFirstName(resultSet.getString(2));
            updatedUser.setLastName(resultSet.getString(3));
            updatedUser.setAge(resultSet.getInt(4));
            ps.close();
            connection.close();
            return updatedUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Long userId) {
        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(deleteUser);
            ps.setLong(1, userId);
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


