package by.it_academy.jd2.hw.example.messenger.storage.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class DBInitializer {

    private volatile static DBInitializer INSTANCE;
    private final ComboPooledDataSource cpds;

    private DBInitializer() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.postgresql.Driver");
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/myapp");
        cpds.setUser("postgres");
        cpds.setPassword("111");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);
    }

    public DataSource getDataSource() {
        return cpds;
    }

    public static DBInitializer getInstance() {
        if (INSTANCE == null) {
            synchronized (DBInitializer.class) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = new DBInitializer();
                    } catch (Exception exc) {
                        throw new RuntimeException("Cannot connect to DB: ", exc);
                    }
                }
            }
        }
        return INSTANCE;
    }
}
