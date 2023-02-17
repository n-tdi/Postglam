package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.Postglam;
import world.ntdi.postglam.connection.Database;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.helper.SQLHelper;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a table in a postgresql database.
 */
public class Table {
    @Getter
    private final Database database;

    @Getter
    private final String tableName;

    @Getter
    private final Map.Entry<String, DataTypes> primaryKey;

    @Getter
    private final LinkedHashMap<String, DataTypes> keys;

    /**
     * Create a Table representation.
     * This constructor will create a table with the given data if it does not already exist.
     *
     * @param database The database connection you want to use to create the table on
     * @param tableName The name of the table
     * @param primaryKey The primary key of the table
     * @param keys The rest of the columns you may want to have in your table.
     */
    public Table(Database database, String tableName, Map.Entry<String, DataTypes> primaryKey, LinkedHashMap<String, DataTypes> keys) {
        this.database = database;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.keys = keys;

        try {
            database.getStmt().execute(SQLHelper.tableTranslate(tableName, primaryKey, keys));
        } catch (SQLException e) {
            Postglam.getLogger(Table.class).error("Unable to execute statement, is the connection closed?");
            throw new RuntimeException(e);
        }
    }
}
