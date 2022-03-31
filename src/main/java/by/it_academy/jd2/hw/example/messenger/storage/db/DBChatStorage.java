package by.it_academy.jd2.hw.example.messenger.storage.db;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.db.api.DBInitializer;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBChatStorage implements IChatStorage {

    private static final IChatStorage INSTANCE = new DBChatStorage();
    private final DataSource ds;

    private DBChatStorage() {
        ds = DBInitializer.getInstance().getDataSource();
    }

    @Override
    public List<Message> get(String login) {
        String query = "SELECT * FROM messenger.messages WHERE \"from\" = ? OR \"to\" = ?";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(getMessage(rs));
            }
            return messages;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void addMessage(Message message) {
        String query = "INSERT INTO messenger.messages (\"from\", \"to\", send_date, text) VALUES (?, ?, ?, ?);";
        try (Connection connection = ds.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, message.getFrom());
            ps.setString(2, message.getTo());
            ps.setObject(3, message.getSendDate());
            ps.setString(4, message.getText());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add message: ", e);
        }
    }

    @Override
    public long getCount() {
        String query = "SELECT COUNT(1) FROM messenger.messages;";
        try (Connection connection = ds.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot execute query: ", e);
        }
    }

    private static Message getMessage(ResultSet rs) throws SQLException {
        Long id = rs.getLong(1);
        String from = rs.getString(2);
        String to = rs.getString(3);
        LocalDateTime sendDate = rs.getObject(4, LocalDateTime.class);
        String text = rs.getString(5);
        Message message = new Message();
        message.setId(id);
        message.setFrom(from);
        message.setTo(to);
        message.setSendDate(sendDate);
        message.setText(text);
        return message;
    }

    public static IChatStorage getInstance() {
        return INSTANCE;
    }
}
