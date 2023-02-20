package world.ntdi.postglam.sql.translator;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@UtilityClass
public class SQLIDColumnTranslator {
    /**
     * Get all the values and their associated primary keyin a column.
     *
     * @param table The table in which the column lies in
     * @param column A Map.entry() of the column's name and data type associated with it
     * @return A list of all the values in the column.
     * @throws SQLException Will throw errors if trying to access closed statement/connection.
     */
    public LinkedHashMap<String, Object> idColumnValuesTranslate(@NonNull final Table table, @NonNull final Map.Entry<String, DataTypes> column) throws SQLException {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>();

        ResultSet resultSet = table.getDatabase().getStmt().executeQuery("SELECT " + table.getPrimaryKey().getKey() + ", " + column.getKey() + " FROM " + table.getTableName());

        while (resultSet.next()) {
            values.put(resultSet.getString(table.getPrimaryKey().getKey()), resultSet.getObject(column.getKey()));
        }

        return values;
    }
}
