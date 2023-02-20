package world.ntdi.postglam;

import world.ntdi.postglam.connection.Database;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Column;
import world.ntdi.postglam.sql.module.Row;
import world.ntdi.postglam.sql.module.Table;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Postglam {
    public static void main(String[] args) throws SQLException {
        Database database = new Database("168.138.68.234", 25597, "root", "root");
        database.connect();

        LinkedHashMap<String, DataTypes> keys = new LinkedHashMap<>();
        keys.put("level", DataTypes.INTEGER);
        keys.put("coins", DataTypes.INTEGER);

        Table table = new Table(database, "cool", Map.entry("id", DataTypes.UUID), keys);
        Column column = new Column(table, "level");
        Row row = new Row(table, "30edf1a0-fe10-4a83-b800-f5ef457035a2");
        row.update(column, "100");


    }
}
