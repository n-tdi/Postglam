package world.ntdi.postglam.sql.translator;

import lombok.NonNull;
import world.ntdi.postglam.data.DataTypes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Functional class to translate the representative objects into SQL
 */
public final class SQLTableTranslator {

    /**
     * Translate arguements into SQL for creating a table.
     *
     * @param tableName  name of the table to create
     * @param primaryKey primary key values
     * @param keys       rest of the keys
     * @return
     */
    public static String tableTranslate(@NonNull final String tableName, @NonNull final Map.Entry<String, DataTypes> primaryKey, @NonNull final LinkedHashMap<String, DataTypes> keys) {
        StringBuilder statement = new StringBuilder();

        statement.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        statement.append(primaryKey.getKey()).append(" ").append(primaryKey.getValue().toString()).append(" ").append(primaryKey.getValue().getNotNull()).append(" PRIMARY KEY, ");

        int i = 0; // This is for checking when reached the last item in the list
        for (Map.Entry<String, DataTypes> entry : keys.entrySet()) {
            statement.append(entry.getKey()).append(" ").append(entry.getValue()).append(" ").append(entry.getValue().getNotNull());

            if (i != keys.entrySet().size() - 1) {
                statement.append(", ");
                i++;
            }
        }

        statement.append(");");
        return statement.toString();
    }

    /**
     * Translate the insert table function from a table's properties.
     *
     * @param tableName The name of the targeted table
     * @param primaryKey The primary key entry
     * @param primaryValue The primary value entry that will be inserted
     * @param keys The rest of the table's keys
     * @param values The values for each key that will be inserted
     * @return A SQL statement to insert the provided arguments into the targeted table
     */
    public static String tableInsertTranslate(@NonNull final String tableName, @NonNull final Map.Entry<String, DataTypes> primaryKey, @NonNull final String primaryValue, @NonNull final LinkedHashMap<String, DataTypes> keys, @NonNull final String[] values) {
        StringBuilder statement = new StringBuilder();

        statement.append("INSERT INTO ").append(tableName).append(" VALUES (");

        statement.append(DataTypes.needQuotes(primaryValue, primaryKey.getValue())).append(", ");

        for (int i = 0; i < values.length; i++) {
            DataTypes datatype = (DataTypes) keys.values().toArray()[i]; // Grab the datatype from the list of keys
            statement.append(DataTypes.needQuotes(values[i], datatype)); // Append the data value and check if it needs the needQuotes function

            int j = i; // So we don't ++ i, breaking the loop
            if (j++ < values.length - 1) { // We minus 1 because length doesn't start from 0, but loops do. For whatever reason. This checks if it is the last loop
                statement.append(", "); // Add comma after datatype if isn't last one.
            }
        }

        statement.append(");");

        return statement.toString();
    }
}
