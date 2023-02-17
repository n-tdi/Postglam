package world.ntdi.postglam.sql.helper;

import lombok.NonNull;
import world.ntdi.postglam.Postglam;
import world.ntdi.postglam.data.DataTypes;

import java.util.LinkedHashMap;
import java.util.Map;

public final class SQLHelper {

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

        Postglam.getLogger(SQLHelper.class).debug(statement.toString());
        return statement.toString();
    }
}
