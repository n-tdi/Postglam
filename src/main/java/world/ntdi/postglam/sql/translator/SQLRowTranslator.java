package world.ntdi.postglam.sql.translator;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Column;
import world.ntdi.postglam.sql.module.Row;
import world.ntdi.postglam.sql.module.Table;

import java.sql.PreparedStatement;
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
     * @return Returns a list of all the row's values.
     * @throws SQLException if trying to access closed statement/connection.
     */
    public static Object[] rowTranslate(@NonNull final Table table) throws SQLException {
        List<Object> columnValues = new LinkedList<>();

        PreparedStatement preparedStatement = table.getDatabase().getC().prepareStatement("SELECT * FROM ? WHERE ? = ?");
        preparedStatement.setString(1, table.getTableName());
        preparedStatement.setString(2, table.getPrimaryKey().getKey());
        preparedStatement.setObject(3, table.getPrimaryKey().getValue());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            for (int i = 1; i <= table.getKeys().size() + 1; i++) {
                columnValues.add(resultSet.getObject(i));
            }
        }
        return columnValues.toArray();
    }

    /**
     * Check if a row exists within a table object.
     * Very important as the Row object will error if the row does not already exist in the table.
     *
     * @param table The table that may contain the row
     * @return If the row exists within the table.
     * @throws SQLException if trying to access closed statement/connection.
     */
    public static boolean rowExists(@NonNull final Table table) throws SQLException {
        PreparedStatement preparedStatement = table.getDatabase().getC().prepareStatement("SELECT * FROM ? WHERE ? = ?");
        preparedStatement.setString(1, table.getTableName());
        preparedStatement.setString(2, table.getPrimaryKey().getKey());
        preparedStatement.setObject(3, table.getPrimaryKey().getValue());

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    /**
     * Middleware method between {@code rowExists()} and {@code fetch()}
     *
     * @param table The table that holds the data
     * @param row The row in which the data lives
     * @param column The column in which the data lives
     * @return The data that was targeted. If no value is present then null will be returned.
     * @throws SQLException Will throw errors if trying to access closed statement/connection.
     */
    public static Object rowFetch(@NonNull final Table table, @NonNull final Row row, @NonNull final Column column) throws SQLException {
        PreparedStatement preparedStatement = table.getDatabase().getC().prepareStatement("SELECT ? FROM ? WHERE ? = ?");
        preparedStatement.setString(1, table.getTableName());
        preparedStatement.setString(2, column.getColumnName());
        preparedStatement.setString(3, table.getPrimaryKey().getKey());
        preparedStatement.setObject(4, row.getPrimaryValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getObject(1);
        }
        return null;
    }

    /**
     * Update a value inside a table.
     *
     * @param table The table in which the value lies in
     * @param row The row in which the value lies in
     * @param column The column in which the value lies in
     * @param value The new value to replace the old one
     * @throws SQLException Will throw errors if trying to access closed statement/connection.
     */
    public static void rowUpdateTranslate(@NonNull final Table table, @NonNull final Row row, @NonNull final Column column, @Nullable final String value) throws SQLException {
        PreparedStatement preparedStatement = table.getDatabase().getC().prepareStatement("UPDATE ? SET ? = ? WHERE ? = ?");
        preparedStatement.setString(1, table.getTableName());
        preparedStatement.setString(2, column.getColumnName());
        preparedStatement.setObject(3, value);
        preparedStatement.setString(4, table.getPrimaryKey().getKey());
        preparedStatement.setObject(5, row.getPrimaryValue());

        preparedStatement.execute();
    }
}
