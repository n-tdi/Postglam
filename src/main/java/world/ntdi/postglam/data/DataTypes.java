package world.ntdi.postglam.data;

import lombok.Getter;
import lombok.NonNull;

/**
 * Refer to <a href="https://www.postgresql.org/docs/current/datatype.html">Postgresql Doc</a> for an explanation on each datatype.
 */
public enum DataTypes {
    BIGINT("bigint"),
    BIGSERIAL("bigserial"),
    BIT("bit"),
    BOOLEAN("boolean"),
    BOX("box"),
    BYTEA("bytea"),
    CHARACTER("character"),
    CIDR("cidr"),
    CIRCLE("circle"),
    DATE("date"),
    DOUBLE_PRECISION("double precision"),
    INET("inet"),
    INTEGER("integer"),
    INTERVAL("interval"),
    JSON("json"),
    JSONB("jsonb"),
    LINE("line"),
    LSEG("lseg"),
    MACADDR("macaddr"),
    MACADDR8("macaddr8"),
    MONEY("money"),
    PATH("path"),
    PG_LSN("pg_lsn"),
    PG_SNAPSHOT("pg_snapshot"),
    POINT("point"),
    POLYGON("polygon"),
    REAL("real"),
    SMALLINT("smallint"),
    SMALLSERIAL("smallserial"),
    SERIAL("serial"),
    TEXT("text"),
    TSQUERY("tsquery"),
    TSVECTOR("tsvector"),
    TXID_SNAPSHOT("txid_snapshot"),
    UUID("uuid"),
    XML("xml");

    private final String name;
    DataTypes(@NonNull final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
