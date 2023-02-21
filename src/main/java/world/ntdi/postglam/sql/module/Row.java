package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.sql.translator.SQLRowTranslator;

import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Represents a row in a table.
 */
public class Row {
    @Getter
    private final Table table;
    @Getter
    private final String primaryValue;

    /**
     * Create a representation of a row from a Table's primary key value.
     *
     * @param table The table you want to get a row in
     * @param primaryValue The value of the primary key in that table
     * @throws SQLException if trying to use closed statement/connect.
     */
    public Row(Table table, String primaryValue) throws SQLException {
        this.table = table;
        this.primaryValue = primaryValue;

        if (!table.doesRowExist(primaryValue)) {
            throw new SQLDataException("Unable to find row with primary value " + primaryValue);
        }
    }

    /**
     * Create a representation of a row from a Table's primary key value.
     * If it does not exist, this method will give the row the specified values.
     * It uses {@code table.insert()}, this is just for lazy people.
     *
     * @param table The table you want to get a row in
     * @param primaryValue The value of the primary key in that table
     * @throws SQLException if trying to use closed statement/connect.
     */
    public Row(Table table, String primaryValue, String... values) throws SQLException {
        this.table = table;
        this.primaryValue = primaryValue;

        if (!table.doesRowExist(primaryValue)) {
            table.insert(primaryValue, values);
        }
    }

    /**
     * Delete the current row.
     * <b>NOTE: This is PERMANENT and there is no undo!</b>
     *
     * @throws SQLException Will throw errors if trying to use closed statement/connection.
     */
    public void drop() throws SQLException {
        table.deleteAllRowsWhere(new Column(table, table.getPrimaryKey().getKey()), primaryValue);
    }

    /**
     * Fetch a point of data from a Row and Column.
     *
     * @param column The column that contains the targeted data
     * @return The data
     * @throws SQLException Will throw errors if trying to use closed statement/connection.
     */
    public Object fetch(Column column) throws SQLException {
        return SQLRowTranslator.rowFetch(table, this, column);
    }

    /**
     * Update a value inside the row.
     *
     * @param column The column that contains the targeted value
     * @param value The new value to replace the old one
     * @throws SQLException Will throw errors if trying to use closed connection/statement.
     */
    public void update(Column column, String value) throws SQLException {
        SQLRowTranslator.rowUpdateTranslate(table, this, column, value);
    }

    /**
     * Generate a string version of the row in json format.
     * @return a String representation of the row.
     */
    @Override
    public String toString() {
        Object[] values = new Object[0];
        try {
            values = SQLRowTranslator.rowTranslate(table, primaryValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        StringBuilder dataList = new StringBuilder();

        dataList.append("{\"").append(table.getPrimaryKey().getKey()).append("\":\"").append(values[0]).append("\", ");

        for (int i = 1; i < values.length; i++) {
            dataList.append("\"").append(table.getKeys().keySet().toArray()[i - 1]).append("\":\"").append(values[i]).append("\"");

            int j = i; // So we don't ++ i, breaking the loop
            if (j++ < values.length - 1) { // We minus 1 because length doesn't start from 0, but loops do. For whatever reason. This checks if it is the last loop
                dataList.append(", "); // Add comma after datatype if isn't last one.
            }
        }

        dataList.append("}");

        return dataList.toString();
    }
}
