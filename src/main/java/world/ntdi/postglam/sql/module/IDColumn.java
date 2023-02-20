package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.translator.SQLColumnTranslator;
import world.ntdi.postglam.sql.translator.SQLIDColumnTranslator;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class IDColumn {
    @Getter
    private final Table table;
    @Getter
    private final String columnName;
    @Getter
    private final Map.Entry<String, DataTypes> columnValues;
    @Getter
    private final LinkedHashMap<String, Object> values;

    /**
     * Create a representation of a column inside a table.
     * IDColumn differs from a regular column as it contains the Primary key value alongside the column's data.
     *
     * @param table The table where the column lies
     * @param column The name of the column
     * @throws SQLException Will error if trying to access closed statement/connection.
     */
    public IDColumn(Table table, String column) throws SQLException {
        this.table = table;
        this.columnName = column;
        this.columnValues = Map.entry(column, DataTypes.getDataTypeFromColum(table, column));
        this.values = SQLIDColumnTranslator.idColumnValuesTranslate(table, columnValues);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
