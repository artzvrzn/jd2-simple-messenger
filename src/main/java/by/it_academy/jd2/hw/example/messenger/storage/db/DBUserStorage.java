package by.it_academy.jd2.hw.example.messenger.storage.db;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.model.audit.AuditUser;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.db.api.DBInitializer;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class DBUserStorage implements IUserStorage {

    private static final IUserStorage INSTANCE = new DBUserStorage();
    private final DataSource ds;

    private DBUserStorage() {
        this.ds = DBInitializer.getInstance().getDataSource();
    }

    @Override
    public User get(String login) {
        String query = "SELECT * FROM messenger.users WHERE login = ?;";
        try (Connection connection = ds.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return getUser(rs);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO messenger.users (login, password, dt_reg, fio, birthday)" +
                "VALUES (?, ?, now(), ?, ?); " +
                "INSERT INTO messenger.audit_users (author, dt_create, \"user\", info) " +
                "VALUES (?, ?, ?, ?);";
        try (Connection connection = ds.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFio());
            ps.setObject(4, user.getBirthday());

            AuditUser audit = new AuditUser();
            audit.setUser(user);
            audit.setInfo(String.format("User %s has been successfully created", user.getLogin()));
            audit.setCreated(LocalDateTime.now());

            ps.setString(5, audit.getAuthor() != null ? audit.getAuthor().getLogin() : null);
            ps.setObject(6, audit.getCreated());
            ps.setString(7, audit.getUser().getLogin());
            ps.setString(8, audit.getInfo());

            ps.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Cannot add user %s to DB", user), e);
        }
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> userCollection = new ArrayList<>();
        String query = "SELECT * FROM messenger.users;";
        try (Connection connection = ds.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                userCollection.add(getUser(rs));
            }
            return userCollection;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot execute query: ", e);
        }
    }

    @Override
    public long getCount() {
        String query = "SELECT COUNT(login) FROM messenger.users;";
        try (Connection connection = ds.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot execute query: ", e);
        }
    }

    public static IUserStorage getInstance() {
        return INSTANCE;
    }

    private static User getUser(ResultSet rs) throws SQLException {
        String user_login = rs.getString(1);
        String password = rs.getString(2);
        LocalDateTime dt_reg = rs.getObject(3, LocalDateTime.class);
        String fio = rs.getString(4);
        LocalDate birthday = rs.getObject(5, LocalDate.class);
        User user = new User();
        user.setLogin(user_login);
        user.setPassword(password);
        user.setRegistration(dt_reg);
        user.setFio(fio);
        user.setBirthday(birthday);
        return user;
    }
}
