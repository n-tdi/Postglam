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
     * Relies on the developer to know how to cast, and what type of data to cast the value to.
     */
    @Getter
    private final Object[] values;

    /**
     * Create a representation of a row from a Table's primary key value.
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

        this.values = SQLRowTranslator.rowTranslate(table, primaryValue);
    }

    /**
     * Generate a string version of the row in json format.
     * @return a String representation of the row.
     */
    @Override
    public String toString() {
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
