package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.connection.Database;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.translator.SQLRowTranslator;
import world.ntdi.postglam.sql.translator.SQLTableTranslator;

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
     * @param keys The rest of the columns you may want to have in your table
     * @throws SQLException Will throw errors if trying to access closed statements/connections.
     */
    public Table(Database database, String tableName, Map.Entry<String, DataTypes> primaryKey, LinkedHashMap<String, DataTypes> keys) throws SQLException {
        this.database = database;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.keys = keys;

        executeStatement(SQLTableTranslator.tableTranslate(tableName, primaryKey, keys));
    }

    /**
     * Insert a row into the table.
     * You should supply this method with the primary value and the rest of them.
     * Example: {@code Table#insert("12", "cat", "true")}.
     * This method will handle adding single quotes around values that require it when translated to SQL.
     * Don't fear strings, SQL will translate them into the types they should be.
     * <b>NOTE: your values passed in should be the correct amount</b>.
     *
     * @param primaryValue The primary key's value
     * @param values The rest of the values that will be inserted
     * @throws SQLException Will throw errors if trying to access closed statements/connections
     */
    public void insert(String primaryValue, String... values) throws SQLException {
        executeStatement(SQLTableTranslator.tableInsertTranslate(tableName, primaryKey, primaryValue, keys, values));
    }

    private void executeStatement(final String statement) throws SQLException {
        database.getStmt().execute(statement);
    }

    /**
     * Check if a row exists before creating a new Row() object of it.
     * This method just takes the value of a primary key and checks if it exists.
     *
     * @param primaryValue The value of the primary key
     * @return If the row exists.
     * @throws SQLException Throws errors if trying to access closed statements/connections
     */
    public boolean doesRowExist(String primaryValue) throws SQLException {
        return SQLRowTranslator.rowExists(this, primaryValue);
    }

    /**
     * Drop the table from the database.
     * <b>NOTE: This action is PERMANENT and there is no undo!</b>
     *
     * @throws SQLException Throws errors if trying to access closed statements/connections
     */
    public void drop() throws SQLException {
        database.getStmt().execute(SQLTableTranslator.tableDropTranslate(tableName));
    }

    /**
     * Delete all the rows in a table.
     * <b>NOTE: This action is PERMANENT and there is no undo!</b>
     *
     * @throws SQLException Throws errors if trying to access closed statements/connections
     */
    public void deleteAllRows() throws SQLException {
        database.getStmt().execute(SQLTableTranslator.tableDeleteAllTranslate(tableName));
    }

    /**
     * Delete all the rows where a condition is met.
     * This method uses the table's columns that you supplied when you first made it and searches for it.
     * Then it used the passed value and removes all rows where the condition is met.
     *
     * @param column The name of the column that you're targeting for a condition
     * @param value The value of the column for the condition
     * @throws SQLException Throws errors if trying to access closed statements/connections
     */
    public void deleteAllRowsWhere(Column column, String value) throws SQLException {
        database.getStmt().execute(SQLTableTranslator.tableDeleteAllRowsWhereTranslate(tableName, column.getColumnName(), value, column.getColumnValues().getValue()));
    }
}
