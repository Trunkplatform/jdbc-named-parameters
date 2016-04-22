package com.trunk.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.trunk.jdbc.NamedParameterPreparedStatement.ParseResult;

public class TestNamedParameterPreparedStatement {
    @Test
    public void testParsing(){
        String testQuery = "SELECT * FROM table WHERE afield = ':not me' AND bfield = :param1 AND cfield = :param2 and dfield = :param2;";
        String expectedParsedQuery = "SELECT * FROM table WHERE afield = ':not me' AND bfield = ? AND cfield = ? and dfield = ?;";
        List<String> expectedParameterList = Lists.newArrayList("param1", "param2", "param2");
        ParseResult parseResult = NamedParameterPreparedStatement.parse(testQuery);
        assertEquals(expectedParsedQuery, parseResult.getSql());
        assertThat(expectedParameterList, is(parseResult.getOrderedParameters()));
    }

    @Test
    public void testParsing2() throws IOException{
        String testQuery = FileUtil.readFile(new File("src/test/resources/test.sql"));
        String expectedParsedQuery = FileUtil.readFile(new File("src/test/resources/expected.sql"));
        List<String> expectedParameterList = Lists.newArrayList("named_parameter1", "named_parameter2");
        ParseResult parseResult = NamedParameterPreparedStatement.parse(testQuery);
        assertEquals(expectedParsedQuery, parseResult.getSql());
        assertThat(expectedParameterList, is(parseResult.getOrderedParameters()));
    }
}
