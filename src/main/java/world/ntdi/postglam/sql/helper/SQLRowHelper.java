package world.ntdi.postglam.sql.helper;

import lombok.NonNull;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class SQLRowHelper {

    /**
     * Translate a table's values with a primary key into a Row representation.
     * This method is absolutely horrible, but works for some reason, so use it!
     * <b>NOTE: This does return the id column along side everything else</b>.
     *
     * @param table The table that holds the row
     * @param primaryValue The value of the primary key
     * @return Returns a list of all the row's values.
     * @throws SQLException if trying to access closed statement/connection.
     */
    public static Object[] rowTranslate(@NonNull final Table table, @NonNull final String primaryValue) throws SQLException {
        List<Object> columnValues = new LinkedList<>();

        // God damn, love java. This is why Postglam is glamorous, so you don't have to type out this one-liner shit.
        ResultSet resultSet = table.getDatabase().getStmt().executeQuery("SELECT * FROM " + table.getTableName() + " WHERE " + table.getPrimaryKey().getKey() + " = " + DataTypes.needQuotes(primaryValue, table.getPrimaryKey().getValue()));

        int i = 1;
        while (resultSet.next()) {
            columnValues.add(resultSet.getObject(i));
            i++;
        }

        return columnValues.toArray(Object[]::new);
    }

    /**
     * Check if a row exists within a table object.
     * Very important as the Row object will error if the row does not already exist in the table.
     *
     * @param table The table that may contain the row
     * @param primaryValue The value of the primary key
     * @return If the row exists within the table.
     * @throws SQLException if trying to access closed statement/connection.
     */
    public static boolean rowExists(@NonNull final Table table, @NonNull final String primaryValue) throws SQLException {
        ResultSet resultSet = table.getDatabase().getStmt().executeQuery("SELECT * FROM " + table.getTableName() + " WHERE " + table.getPrimaryKey().getKey() + " = " + DataTypes.needQuotes(primaryValue, table.getPrimaryKey().getValue()));
        return resultSet.next();
    }
}
