package world.ntdi.postglam.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.security.InvalidParameterException;

/**
 * Refer to <a href="https://www.postgresql.org/docs/current/datatype.html">Postgresql Doc</a> for an explanation on each datatype.
 */
public enum DataTypes {
    BIGINT("bigint", false),
    BIGSERIAL("bigserial", false),
    BIT("bit", true),
    VAR_BIT("varbit", true),
    BOOLEAN("boolean", false),
    BOX("box", false),
    BYTEA("bytea", false),
    CHARACTER("char", true),
    VAR_CHARACTER("varchar", true),
    CIDR("cidr", false),
    CIRCLE("circle", false),
    DATE("date", false),
    DOUBLE_PRECISION("double precision", false),
    INET("inet", false),
    INTEGER("integer", false),
    INTERVAL("interval", true),
    JSON("json", false),
    JSONB("jsonb", false),
    LINE("line", false),
    LSEG("lseg", false),
    MACADDR("macaddr", false),
    MACADDR8("macaddr8", false),
    MONEY("money", false),
    PATH("path", false),
    PG_LSN("pg_lsn", false),
    PG_SNAPSHOT("pg_snapshot", false),
    POINT("point", false),
    POLYGON("polygon", false),
    REAL("real", false),
    SMALLINT("smallint", false),
    SMALLSERIAL("smallserial", false),
    SERIAL("serial", false),
    TIME("time", true),
    TEXT("text", false),
    TSQUERY("tsquery", false),
    TSVECTOR("tsvector", false),
    TXID_SNAPSHOT("txid_snapshot", false),
    UUID("uuid", false),
    XML("xml", false);

    /**
     * Name of the SQL form of the data type
     */
    private final String name;
    /**
     * If the data type can take a defined value
     */
    private final boolean valuable;

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
    @Getter @Setter
    private boolean notNull = true;
    DataTypes(@NonNull final String name, final boolean valuable) {
        this.name = name;
        this.valuable = valuable;
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

    public void setValue(@NonNull final String value) {
        if (valuable) setValue(value);
        else throw new InvalidParameterException("Cannot apply a value to this data type");
    }
}
