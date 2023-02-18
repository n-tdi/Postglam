package world.ntdi.postglam.sql.translator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import world.ntdi.postglam.data.DataTypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class SQLTableTranslatorTest {
    @BeforeAll
    static void announce() {
        System.out.println("Running tests for SQL Table Translation");
    }

    @Test
    void tableTranslate() {
        String name = "test";
        Map.Entry<String, DataTypes> primaryKey = Map.entry("id", DataTypes.UUID);
        LinkedHashMap<String, DataTypes> keys = new LinkedHashMap<>();
        keys.put("level", DataTypes.INTEGER);
        assertEquals(SQLTableTranslator.tableTranslate(name, primaryKey, keys), "CREATE TABLE IF NOT EXISTS test (id uuid NOT NULL PRIMARY KEY, level integer NOT NULL);");
        keys.put("name", DataTypes.TEXT);
        assertEquals(SQLTableTranslator.tableTranslate(name, primaryKey, keys), "CREATE TABLE IF NOT EXISTS test (id uuid NOT NULL PRIMARY KEY, level integer NOT NULL, name text NOT NULL);");
    }

    @Test
    void tableInsertTranslate() {
        String name = "test";
        Map.Entry<String, DataTypes> primaryKey = Map.entry("id", DataTypes.UUID);
        String primaryValue = "fd93885e-906e-4fc9-8fc9-0982ac3a1fd5";
        LinkedHashMap<String, DataTypes> keys = new LinkedHashMap<>();
        keys.put("level", DataTypes.INTEGER);
        String[] values = new String[]{"1"};

        assertEquals(SQLTableTranslator.tableInsertTranslate(name, primaryKey, primaryValue, keys, values), "INSERT INTO test VALUES ('fd93885e-906e-4fc9-8fc9-0982ac3a1fd5', 1);");

        keys.put("name", DataTypes.TEXT);
        String[] values2 = new String[]{"1", "ntdi"};
        assertEquals(SQLTableTranslator.tableInsertTranslate(name, primaryKey, primaryValue, keys, values2), "INSERT INTO test VALUES ('fd93885e-906e-4fc9-8fc9-0982ac3a1fd5', 1, 'ntdi');");
    }

    @AfterEach
    public void passed() {
        System.out.println("Test passed");
    }

    @AfterAll
    public static void done() {
        System.out.println("Finished running test for SQL Table Translation");
    }
}