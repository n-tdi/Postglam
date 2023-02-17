package world.ntdi.postglam.sql.helper;

import lombok.NonNull;
import world.ntdi.postglam.data.DataTypes;

public final class SQLHelper {

    public static void tableTranslate(@NonNull final String tableName, @NonNull final DataTypes primaryKey, @NonNull final DataTypes... keys) {
        StringBuilder statement = new StringBuilder();

        statement.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");



        statement.append(");");
    }
}
