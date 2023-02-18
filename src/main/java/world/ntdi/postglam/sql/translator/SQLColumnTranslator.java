package world.ntdi.postglam.sql.translator;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@UtilityClass
public class SQLColumnTranslator {
    /**
     * Get all the values in a column.
     *
     * @param table The table in which the column lies in
     * @param column A Map.entry() of the column's name and data type associated with it
     * @return A list of all the values in the column.
     * @throws SQLException Will throw errors if trying to access closed statement/connection.
     */
    public Object[] columnValuesTranslate(@NonNull final Table table, @NonNull final Map.Entry<String, DataTypes> column) throws SQLException {
        LinkedList<Object> values = new LinkedList<>();

        ResultSet resultSet = table.getDatabase().getStmt().executeQuery("SELECT " + column.getKey() + " FROM " + table.getTableName());

        while (resultSet.next()) {
            values.add(resultSet.getObject(column.getKey()));
        }

        return values.toArray(Object[]::new);
    }
}
