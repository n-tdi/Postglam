package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.translator.SQLColumnTranslator;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

/**
 * Represents a column in a table
 */
public class Column {
    @Getter
    private final Table table;
    @Getter
    private final String columnName;
    @Getter
    private final Map.Entry<String, DataTypes> columnValues;
    @Getter
    private final Object[] values;

    /**
     * Create a representation of a column inside a table.
     *
     * @param table The table where the column lies
     * @param column The name of the column
     * @throws SQLException Will error if trying to access closed statement/connection.
     */
    public Column(Table table, String column) throws SQLException {
        this.table = table;
        this.columnName = column;
        this.columnValues = Map.entry(column, DataTypes.getDataTypeFromColum(table, column));
        this.values = SQLColumnTranslator.columnValuesTranslate(table, columnValues, null);
    }

    /**
     * Create a representation of a column inside a table.
     *
     * @param table The table where the column lies
     * @param column The name of the column
     * @param ordering Should the columns be given in Ascending or Descending order?
     * @throws SQLException Will error if trying to access closed statement/connection.
     */
    public Column(Table table, String column, Ordering ordering) throws SQLException {
        this.table = table;
        this.columnName = column;
        this.columnValues = Map.entry(column, DataTypes.getDataTypeFromColum(table, column));
        this.values = SQLColumnTranslator.columnValuesTranslate(table, columnValues, ordering);
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    public enum Ordering {
        DESCENDING("DESC"), ASCENDING("ASC");

        @Getter
        private final String sqlValue;

        Ordering(String sqlValue) {
            this.sqlValue = sqlValue;
        }
    }
}
