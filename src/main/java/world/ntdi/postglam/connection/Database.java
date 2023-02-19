package world.ntdi.postglam.connection;

import lombok.Getter;
import lombok.NonNull;
import world.ntdi.postglam.data.CredentialStorage;
import world.ntdi.postglam.logger.Logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for creating database objects and connections
 */
public class Database extends Logging {

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
        super(Database.class);
        this.credentialStorage = new CredentialStorage(HOST, PORT, USERNAME, PASSWORD);
    }

    /**
     * Create a new connection to the Database.
     * <i>DO NOT</i> create a new connection without closing the previous one to prevent <b>memory leaks!</b>
     */
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            getLogger().info("Connecting to database at " + credentialStorage.getHOST());
            c = DriverManager.getConnection(
                    "jdbc:postgresql://" + credentialStorage.getHOST() + ":" + credentialStorage.getPORT() + "/postgres",
                    credentialStorage.getUSERNAME(), credentialStorage.getPASSWORD()
            );
            getLogger().info("Successfully connected to " + credentialStorage.getHOST());
            getLogger().info("Creating statement from connection");
            stmt = c.createStatement();
            getLogger().info("Successfully created statement");
        } catch (ClassNotFoundException e) {
            getLogger().error("Unable to find class org.postgresql.Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            getLogger().error("Connection failed. Credentials could be incorrect or SQL has gone seriously wrong!");
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
            getLogger().info("Successfully flushed out all connections.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
