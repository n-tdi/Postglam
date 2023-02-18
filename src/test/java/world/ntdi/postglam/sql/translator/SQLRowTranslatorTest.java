package world.ntdi.postglam.sql.translator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import world.ntdi.postglam.data.DataTypes;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SQLRowTranslatorTest {
    @BeforeAll
    public static void before() {
        System.out.println("Running tests for SQLRowTranslator");
    }

    @Test
    void rowFetchTranslate() {
        assertEquals("SELECT level FROM stats WHERE id = '1';", SQLRowTranslator.rowFetchTranslate("stats", "level", Map.entry("id", DataTypes.TEXT), "1"));
    }

    @AfterEach
    public void each() {
        System.out.println("Test passed");
    }

    @AfterAll
    public static void after() {
        System.out.println("Finished running tests for SQLRowTranslator");
    }
}