package edu.firstprog.studentproject.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBInit
{
    public static void startUp() throws Exception {
        URL url1 = DictionaryDaoImplTest.class.getClassLoader().getResource("launcher_script.sql");

        URL url2 = DictionaryDaoImplTest.class.getClassLoader().getResource("student_data.sql");

        try (Connection connection = ConnectionBuilder.getConnection();
             Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(getSqlString(url1)); // ??
            stmt.executeUpdate(getSqlString(url2));
        }
    }

    private static String getSqlString(URL url1) throws IOException, URISyntaxException {
        List<String> str = Files.readAllLines(Paths.get(url1.toURI()));
        return str.stream().collect(Collectors.joining());
    }
}
