package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.connection.Database;
import world.ntdi.postglam.data.DataTypes;

/**
 * Represents a table in a postgresql database.
 */
public class Table {
    @Getter
    private final Database database;

    @Getter
    private final String tableName;

    @Getter
    private final DataTypes primaryKey;

    @Getter
    private final DataTypes[] keys;

    public Table(Database database, String tableName, DataTypes primaryKey, DataTypes... keys) {
        this.database = database;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.keys = keys;
    }
}
