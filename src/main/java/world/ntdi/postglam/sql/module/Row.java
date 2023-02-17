package world.ntdi.postglam.sql.module;

import lombok.Getter;
import world.ntdi.postglam.sql.helper.SQLRowHelper;

import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Represents a row in a table.
 */
public class Row {
    @Getter
    private final Table table;
    @Getter
    private final String primaryValue;

    /**
     * Relies on the developer to know how to cast, and what type of data to cast the value to.
     */
    @Getter
    private final Object[] keys;

    /**
     * Create a representation of a row from a Table's primary key value.
     * @param table The table you want to get a row in
     * @param primaryValue The value of the primary key in that table
     * @throws SQLException if trying to use closed statement/connect.
     */
    public Row(Table table, String primaryValue) throws SQLException {
        this.table = table;
        this.primaryValue = primaryValue;

        if (!table.doesRowExist(primaryValue)) {
            throw new SQLDataException("Unable to find row with primary value " + primaryValue);
        }

        this.keys = SQLRowHelper.rowTranslate(table, primaryValue);
    }
}
