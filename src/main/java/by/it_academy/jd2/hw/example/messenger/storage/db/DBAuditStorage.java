package by.it_academy.jd2.hw.example.messenger.storage.db;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.model.audit.AuditUser;
import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBAuditStorage implements IAuditStorage {

    private static final IAuditStorage INSTANCE = new DBAuditStorage();
    private final DataSource ds;

    private DBAuditStorage() {
        ds = DBInitializer.getInstance().getDataSource();
    }

    @Override
    public void create(AuditUser audit) {
        if (audit == null) {
            throw new RuntimeException("Audit cannot be null");
        }
        String query = "INSERT INTO messenger.audit_users (author, dt_create, \"user\", info)" +
                "VALUES (?, ?, ?, ?);";
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"})) {
            ps.setString(1, audit.getAuthor() != null ? audit.getAuthor().getLogin() : null);
            ps.setObject(2, audit.getCreated());
            ps.setString(3, audit.getUser().getLogin());
            ps.setString(4, audit.getInfo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DBAudit error: ", e);
        }
    }

    @Override
    public List<AuditUser> read() {
        List<AuditUser> audits = new ArrayList<>();
        String query =
                "SELECT audit.id, " +
                "audit.author, " +
                "audit.dt_create, " +
                "audit.user, " +
                "audit.info, " +
                "author.dt_reg AS author_dt_reg, " +
                "author.birthday AS author_birthday, " +
                "author.fio AS author_fio, " +
                "change.dt_reg AS change_dt_reg, " +
                "change.birthday AS change_birthday, " +
                "change.fio AS change_fio " +
                "FROM messenger.audit_users AS audit " +
                "LEFT JOIN messenger.users AS author ON audit.author = author.login " +
                "LEFT JOIN messenger.users AS change ON audit.user = change.login;";
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long id = rs.getLong("id");
                User author = new User();
                author.setLogin(rs.getString("author"));
                author.setRegistration(rs.getObject("author_dt_reg", LocalDateTime.class));
                author.setBirthday(rs.getObject("author_birthday", LocalDate.class));
                author.setFio(rs.getString("author_fio"));
                User changed = new User();
                changed.setLogin(rs.getString("user"));
                changed.setRegistration(rs.getObject("change_dt_reg", LocalDateTime.class));
                changed.setBirthday(rs.getObject("change_birthday", LocalDate.class));
                changed.setFio(rs.getString("change_fio"));
                LocalDateTime created = rs.getObject("dt_create", LocalDateTime.class);
                String info = rs.getString("info");
                AuditUser audit = new AuditUser();
                audit.setAuthor(author);
                audit.setUser(changed);
                audit.setId(id);
                audit.setInfo(info);
                audit.setCreated(created);
                audits.add(audit);
            }
            return audits;
        } catch (SQLException e) {
            throw new RuntimeException("DBAudit error: ", e);
        }
    }

    public static IAuditStorage getInstance() {
        return INSTANCE;
    }
}
