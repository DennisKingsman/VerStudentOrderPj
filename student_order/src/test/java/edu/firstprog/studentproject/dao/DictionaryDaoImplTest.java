package edu.firstprog.studentproject.dao;

import edu.firstprog.studentproject.domain.CountryArea;
import edu.firstprog.studentproject.domain.PassportOffice;
import edu.firstprog.studentproject.domain.RegisterOffice;
import edu.firstprog.studentproject.domain.Street;
import edu.firstprog.studentproject.exception.DaoException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDaoImplTest
{
    private static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImplTest.class);

    @BeforeClass //running only once
    public static void startUP() throws Exception {
        DBInit.startUp();
    }

    @Test
    public void testStreet() throws DaoException {
        LocalDateTime ldt = LocalDateTime.now();
        logger.info("Test {}", ldt);
        List<Street> streetKit = new DictionaryDaoImpl().findStreets("про");
        Assert.assertTrue(streetKit.size() == 2);
    }

    @Test
    public void testPassportOffice() throws DaoException {
        List<PassportOffice>  po = new DictionaryDaoImpl().findPassportOffices("010020000000");
        Assert.assertTrue(po.size() == 2);
    }
    @Test
    public void testRegisterOffice() throws DaoException {
        List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices( "010010000000");
        Assert.assertTrue(ro.size() == 2);
    }

    @Test
    public void testArea() throws DaoException {
        List<CountryArea> countryAreas1 = new DictionaryDaoImpl().findAreas( " ");
        Assert.assertTrue(countryAreas1.size() == 2);
        List<CountryArea> countryAreas2 = new DictionaryDaoImpl().findAreas( "020000000000");
        Assert.assertTrue(countryAreas2.size() == 2);
        List<CountryArea> countryAreas3 = new DictionaryDaoImpl().findAreas( "020010000000");
        Assert.assertTrue(countryAreas3.size() == 2);
        List<CountryArea> countryAreas4 = new DictionaryDaoImpl().findAreas( "020010010000");
        Assert.assertTrue(countryAreas4.size() == 2) ;
    }
}