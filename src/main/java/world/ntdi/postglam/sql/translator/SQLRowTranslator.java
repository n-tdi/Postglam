package world.ntdi.postglam.sql.translator;

import lombok.NonNull;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Column;
import world.ntdi.postglam.sql.module.Row;
import world.ntdi.postglam.sql.module.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Functional class to translate the representative objects into SQL
 */
public final class SQLRowTranslator {

    /**
     * Translate a table's values with a primary key into a Row representation.
     * This method is absolutely horrible, but works for some reason, so use it!
     * <b>NOTE: This does return the id column along side everything else</b>.
     *
     * @param table The table that holds the row
     * @param primaryValue The value of the primary key
     * @return Returns a list of all the row's values.
     * @throws SQLException if trying to access closed statement/connection.
     */
    public static Object[] rowTranslate(@NonNull final Table table, @NonNull final String primaryValue) throws SQLException {
        List<Object> columnValues = new LinkedList<>();

        // God damn, love java. This is why Postglam is glamorous, so you don't have to type out this one-liner shit.
        ResultSet resultSet = table.getDatabase().getStmt().executeQuery("SELECT * FROM " + table.getTableName() + " WHERE " + table.getPrimaryKey().getKey() + " = " + DataTypes.needQuotes(primaryValue, table.getPrimaryKey().getValue()));

        if (resultSet.next()) {
            for (int i = 1; i <= table.getKeys().size() + 1; i++) {
                columnValues.add(resultSet.getObject(i));
            }
        }
        return columnValues.toArray(Object[]::new);
    }

    /**
     * Check if a row exists within a table object.
     * Very important as the Row object will error if the row does not already exist in the table.
     *
     * @param table The table that may contain the row
     * @param primaryValue The value of the primary key
     * @return If the row exists within the table.
     * @throws SQLException if trying to access closed statement/connection.
     */
    public static boolean rowExists(@NonNull final Table table, @NonNull final String primaryValue) throws SQLException {
        ResultSet resultSet = table.getDatabase().getStmt().executeQuery("SELECT * FROM " + table.getTableName() + " WHERE " + table.getPrimaryKey().getKey() + " = " + DataTypes.needQuotes(primaryValue, table.getPrimaryKey().getValue()));
        return resultSet.next();
    }

    /**
     * Middleware method between {@code rowExists()} and {@code fetch()}
     *
     * @param table The table that holds the data
     * @param row The row in which the data lives
     * @param column The column in which the data lives
     * @return The data that was targeted. If no value is present then null will be returned.
     * @throws SQLException Will throw errors if trying to access close statement/connection.
     */
    public static Object rowFetch(@NonNull final Table table, @NonNull final Row row, @NonNull final Column column) throws SQLException {
        ResultSet resultSet = table.getDatabase().getStmt().executeQuery(rowFetchTranslate(table.getTableName(), column.getColumnName(), table.getPrimaryKey(), row.getPrimaryValue()));

        if (resultSet.next()) {
            return resultSet.getObject(1);
        }
        return null;
    }

    /**
     * Method that generates SQL for the fetching data.
     *
     * @param tableName Name of the table in which the data lies in
     * @param columnName Name of the column in which the data lies in
     * @param primaryKey Primary key values that contain the row in which the data lies in
     * @param primaryValue The value of the primary key
     * @return Returns a SQL statement.
     */
    public static String rowFetchTranslate(@NonNull final String tableName, @NonNull final String columnName, @NonNull final Map.Entry<String, DataTypes> primaryKey, @NonNull final String primaryValue) {
        return "SELECT " + columnName + " FROM " + tableName + " WHERE " + primaryKey.getKey() + " = " + DataTypes.needQuotes(primaryValue, primaryKey.getValue()) + ";";
    }
}
