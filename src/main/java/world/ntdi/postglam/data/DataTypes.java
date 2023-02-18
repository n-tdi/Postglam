package world.ntdi.postglam.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.security.InvalidParameterException;

/**
 * Refer to <a href="https://www.postgresql.org/docs/current/datatype.html">Postgresql Doc</a> for an explanation on each datatype.
 */
public enum DataTypes {
    BIGINT("bigint", false, false),
    BIGSERIAL("bigserial", false, false),
    BIT("bit", true, false),
    VAR_BIT("varbit", true, false),
    BOOLEAN("boolean", false, false),
    BOX("box", false, false),
    BYTEA("bytea", false, false),
    CHARACTER("char", true, true),
    VAR_CHARACTER("varchar", true, true),
    CIDR("cidr", false, true),
    CIRCLE("circle", false, true),
    DATE("date", false, true),
    DOUBLE_PRECISION("double precision", false, false),
    INET("inet", false, true),
    INTEGER("integer", false, false),
    INTERVAL("interval", true, false),
    JSON("json", false, true),
    JSONB("jsonb", false, true),
    LINE("line", false, true),
    LSEG("lseg", false, true),
    MACADDR("macaddr", false, true),
    MACADDR8("macaddr8", false, true),
    MONEY("money", false, false),
    PATH("path", false, true),
    PG_LSN("pg_lsn", false, true),
    PG_SNAPSHOT("pg_snapshot", false, true),
    POINT("point", false, true),
    POLYGON("polygon", false, true),
    REAL("real", false, false),
    SMALLINT("smallint", false, false),
    SMALLSERIAL("smallserial", false, false),
    SERIAL("serial", false, false),
    TIME("time", true, true),
    TEXT("text", false, true),
    TSQUERY("tsquery", false, true),
    TSVECTOR("tsvector", false, true),
    UUID("uuid", false, true),
    XML("xml", false, true);

    /**
     * Name of the SQL form of the data type
     */
    private final String name;
    /**
     * If the data type can take a defined value
     */
    private final boolean valuable;

    /**
     * If the data type needs to be surrounded in '' when written in sql
     */
    @Getter
    private final boolean needsQuotes;

    /**
     * The set value for that data type
     */
    @Getter
    private String value;

    /**
     * If the value will be classed with null or Not Null.
     * By default, it is not Null.
     * This is used for creation of tables and such.
     */
    @SuppressWarnings("NonFinalFieldInEnum")
    @Getter @Setter
    private boolean notNull = true;
    DataTypes(@NonNull final String name, final boolean valuable, final boolean needsQuotes) {
        this.name = name;
        this.valuable = valuable;
        this.needsQuotes = needsQuotes;
    }

    /**
     * Stringify the SQL form of each datatype.
     * <b>Note:</b> some data types are exclusive to Postgresql.
     * If there is a value given, this method will generate that as well.
     * Example: {@code char(36)} which is the equivalent to {@code uuid}.
     *
     * @return the SQL form of each datatype.
     */
    public String toString() {
        return (value != null ? name + "(" + value + ")" : name);
    }

    /**
     * Set the value of the data type.
     * This is not the same as setting a value in a column, rather a constraint instead.
     * @param value The value for the data type. E.g. 36
     */
    public void setValue(@NonNull final String value) {
        if (valuable) setValue(value);
        else throw new InvalidParameterException("Cannot apply a value to this data type");
    }

    /**
     * Get if there will be a NOT NULL in the data type or not
     * @return "NOT NULL" or "" if notNull is set to true/false
     */
    public String getNotNull() {
        return (notNull ? "NOT NULL" : "");
    }

    /**
     * Checks if a targeted value needs quotes around it. This is useful when inserting items into a table.
     * This method is meant just for table use.
     *
     * @param target The targeted string to adjust if needed
     * @param dataType The data type that the string references
     * @return A string with single quotes around it if the data type requests it
     */
    public static String needQuotes(String target, DataTypes dataType) {
        return (dataType.isNeedsQuotes() ? "'" + target + "'" : target);
    }
}
