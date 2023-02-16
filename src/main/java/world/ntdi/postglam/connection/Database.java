package world.ntdi.postglam.connection;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import world.ntdi.postglam.Postglam;
import world.ntdi.postglam.helper.CredentialStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for creating database objects and connections
 */
public class Database {

    /* STATEMENT for executing SQL Queries */
    @Getter
    private Statement stmt = null;

    /* CONNECTION for opening and closing connections to SQL */
    @Getter
    private Connection c = null;

    /* CREDENTIAL STORAGE for storing log in credentials systematically */
    private final CredentialStorage credentialStorage;

    /**
     * Create a new Database object for connecting and using the rest of Postglam.
     * You're required to use the {@code connect()} function to actually create the connection to the database.
     *
     * @param HOST The database's IP Address, E.g. {@code localhost}
     * @param PORT The port the database is running on, E.g. {@code 5432}
     * @param USERNAME The username for root connection to the database, E.g. {@code Admin}
     * @param PASSWORD The password for root connection to the database, E.g. {@code 1234}
     */
    public Database(@NonNull final String HOST, final int PORT, @NonNull final String USERNAME, @NonNull final String PASSWORD) {
        this.credentialStorage = new CredentialStorage(HOST, PORT, USERNAME, PASSWORD);
    }

    /**
     * Create a new connection to the Database.
     * <i>DO NOT</i> create a new connection without closing the previous one to prevent <b>memory leaks!</b>
     */
    public void connect() {
        Logger logger = Postglam.getLogger(Database.class);

        try {
            Class.forName("org.postgresql.Driver");
            logger.info("Connecting to database at " + credentialStorage.HOST());
            c = DriverManager.getConnection(
                    "jdbc:postgresql://" + credentialStorage.HOST() + ":" + credentialStorage.PORT() + "/postgres",
                    credentialStorage.USERNAME(), credentialStorage.PASSWORD()
            );
            logger.info("Successfully connected to " + credentialStorage.HOST());
            logger.info("Creating statement from connection");
            stmt = c.createStatement();
            logger.info("Successfully created statement");
        } catch (ClassNotFoundException e) {
            logger.error("Unable to find class org.postgresql.Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error("Connection failed. Credentials could be incorrect or SQL has gone seriously wrong!");
            e.printStackTrace();
        }
    }

    /**
     * Disconnect and flush out the existing connections.
     */
    public void disconnect() {
        try {
            stmt.close();
            c.close();

            c = null;
            stmt = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
