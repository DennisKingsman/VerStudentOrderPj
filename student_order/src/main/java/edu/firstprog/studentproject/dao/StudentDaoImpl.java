package edu.firstprog.studentproject.dao;

import edu.firstprog.studentproject.config.Config;
import edu.firstprog.studentproject.domain.*;
import edu.firstprog.studentproject.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDaoImpl implements StudentOrderDao
{
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(  " +
                    "            student_order_status, student_order_date, h_sur_name,  " +
                    "            h_given_name, h_patronymic, h_date_of_birth, h_passport_seria,  " +
                    "            h_passport_number, h_passport_date, h_passport_office_id, h_post_index,  " +
                    "            h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, w_sur_name,  " +
                    "            w_given_name, w_patronymic, w_date_of_birth, w_passport_seria,  " +
                    "            w_passport_number, w_passport_date, w_passport_office_id, w_post_index,  " +
                    "            w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, certificate_id,  " +
                    "            register_office_id, marriage_date) " +
                    "    VALUES (?, ?, ?,  " +
                    "            ?, ?, ?, ?,  " +
                    "            ?, ?, ?, ?,  " +
                    "            ?, ?, ?, ?, ?, ?, ?,  " +
                    "            ?, ?, ?, ?,  " +
                    "            ?, ?, ?, ?,  " +
                    "            ?, ?, ?, ?, ?, ?, ?,  " +
                    "            ?, ?); ";

    private static final String INSERT_CHILD =
            "INSERT INTO jc_student_child(" +
                    "            student_order_id, c_sur_name, c_given_name, " +
                    "            c_patronymic, c_date_of_birth, c_certificate_number, c_certificate_date, " +
                    "            c_register_office_id, c_post_index, c_street_code, c_building, " +
                    "            c_extension, c_apartment)" +
                    "    VALUES (?, ?, ?, " +
                    "            ?, ?, ?, ?, " +
                    "            ?, ?, ?, ?, " +
                    "            ?, ?);";

    private static final String SELECT_ORDERS =
            "  SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "                    po_h.p_office_area_id as h_p_office_area_id, "  +
                    "                    po_h.p_office_name as h_p_office_name, " +
                    "                    po_w.p_office_area_id as w_p_office_area_id, " +
                    "                    po_w.p_office_name as w_p_office_name " +
                    "                    FROM jc_student_order so " +
                    "                    INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "                    INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "                    INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "                    WHERE student_order_status = ? ORDER BY student_order_date LIMIT ? "; // ORDER BY like sort in de queue

    private static final String SELECT_CHILD =
            "select soc.* , ro.r_office_area_id, ro.r_office_name " +
                    "from jc_student_child soc " +
                    "inner join jc_register_office ro on ro.r_office_id = soc.c_register_office_id " +
                    "where soc.student_order_id in ";

    private static final String SELECT_ORDERS_FULL =
            "  SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "                    po_h.p_office_area_id as h_p_office_area_id, "  +
                    "                    po_h.p_office_name as h_p_office_name, " +
                    "                    po_w.p_office_area_id as w_p_office_area_id, " +
                    "                    po_w.p_office_name as w_p_office_name, " +
                    "                    soc.* , ro_c.r_office_area_id, ro_c.r_office_name " +
                    "                    FROM jc_student_order so " +
                    "                    INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "                    INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "                    INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "                    INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
                    "                    inner join jc_register_office ro_c on ro_c.r_office_id = soc.c_register_office_id " +
                    "                    WHERE student_order_status = ? ORDER BY so.student_order_id limit ? ";

    private Connection getConnection() throws SQLException
    {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public Long saveStudentOrder(StudentOrder sO) throws DaoException
    {
        Long result = -1L;

        logger.debug("SO:{} ", sO);

        try (Connection connection = getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) // generic keys
            {
                connection.setAutoCommit(false); // transaction control

                try {
                    //Header
                    stmt.setInt(1, StudentOrderStatus.START.ordinal());
                    stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                    //Husband and Wife
                    int h_start = 3; // first column with husband's data
                    setParamsForAdult(stmt, h_start, sO.getHusband());

                    int w_start = 18; // first column with wife's data
                    setParamsForAdult(stmt, w_start, sO.getWife());

                    //Marriage
                    stmt.setString(33, sO.getMarriageCertificateId());
                    stmt.setLong(34, sO.getMarriageOffice().getOfficeId());
                    stmt.setDate(35, java.sql.Date.valueOf(sO.getMarrigeDate()));

                    stmt.executeUpdate();

                    ResultSet gKres = stmt.getGeneratedKeys();
                    if (gKres.next())
                    {
                        result = gKres.getLong(1);
                    }

                    gKres.close();

                    saveChildren(connection, sO, result);

                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                    throw ex;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }

        return result;
    }

    private void saveChildren(Connection connection, StudentOrder sO, Long result) throws SQLException
    {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_CHILD))
        {
            for (Child child : sO.getChildren())
            {
                stmt.setLong(1, result);
                setParamsForChild(stmt, child);
                stmt.addBatch(); //when small count of data
            }
            stmt.executeBatch();
        }
    }

    private void setParamsForAdult(PreparedStatement stmt, int start, Adult person) throws SQLException
    {
        setParamsForPerson(stmt, start, person);
        stmt.setString(start + 4, person.getPassportSeria());
        stmt.setString(start + 5, person.getPassportNumber());
        stmt.setDate(start + 6, java.sql.Date.valueOf(person.getIssueDate()));
        stmt.setLong(start + 7, person.getIssueDepartment().getOfficeID());
        setParamsForAddress(stmt, start + 8, person);
        stmt.setLong(start + 13, person.getUniversity().getUniversityId());
        stmt.setString(start + 14, person.getStudentID());
    }

    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException
    {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, 9, child);
    }

    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException
    {
        stmt.setString(start, person.getSurName());
        stmt.setString(start + 1, person.getGivenName());
        stmt.setString(start + 2, person.getPatronymic());
        stmt.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }
    
    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException
    {
        Address p_address = person.getAddress();
        stmt.setString(start, p_address.getPostCode());
        stmt.setLong(start + 1, p_address.getStreet().getStreetCode());
        stmt.setString(start + 2, p_address.getBuilding());
        stmt.setString(start + 3, p_address.getExtension());
        stmt.setString(start + 4, p_address.getApartment());
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException
    {
        return getStudentOrdersOneSelect();
//        return getStudentOrdersTwoSelect();
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException
    {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ORDERS))
        {
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            stmt.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next())
            {
                StudentOrder sO = getFullStudentOrder(resultSet);


                result.add(sO);
            }
            findChildren(connection, result);

            resultSet.close();
        }catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }

        return result;
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException
    {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ORDERS_FULL))
        {
            Map <Long, StudentOrder> maps = new HashMap<>(); // create checker existence student order

            stmt.setInt(1, StudentOrderStatus.START.ordinal());

            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            stmt.setInt(2, limit);

            ResultSet resultSet = stmt.executeQuery();
            int counter = 0;

            while (resultSet.next())
            {
                Long soId = resultSet.getLong("student_order_id");
                if(!maps.containsKey(soId)) // if not exist
                {
                    StudentOrder sO = getFullStudentOrder(resultSet);

                    result.add(sO);
                    maps.put(soId, sO);
                }

                StudentOrder so = maps.get(soId); // take one which already exist
                so.addChild(fillChild(resultSet));
                ++counter;
            }

            if(counter >= limit) //throw away last order
            {
                result.remove(result.size() - 1);
            }

            resultSet.close();
        }catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }

        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet resultSet) throws SQLException
    {
        StudentOrder sO = new StudentOrder();
        fillStudentOrder(resultSet, sO);
        fillWedding(resultSet, sO);

        sO.setHusband(fillAdult(resultSet, "h_"));
        sO.setWife(fillAdult(resultSet, "w_"));
        return sO;
    }

    private void fillStudentOrder(ResultSet resultSet, StudentOrder sO) throws SQLException
    {
        sO.setStudentOrderID(resultSet.getLong("student_order_id"));
        sO.setStudentOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
        sO.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));

    }

    private void fillWedding(ResultSet resultSet, StudentOrder sO) throws SQLException
    {
        sO.setMarriageCertificateId(resultSet.getString("certificate_id"));
        sO.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());

        Long registerOID = resultSet.getLong("register_office_id");
        String areaId = resultSet.getString("r_office_area_id");
        String officeName = resultSet.getString("r_office_name");
        RegisterOffice rO = new RegisterOffice(registerOID, areaId, officeName);
        sO.setMarriageOffice(rO);
    }

    private Adult fillAdult(ResultSet resultSet, String pref) throws SQLException
    {
        Adult adult = new Adult();
        adult.setSurName(resultSet.getString(pref + "sur_name"));
        adult.setGivenName(resultSet.getString(pref + "given_name"));
        adult.setPatronymic(resultSet.getString(pref + "patronymic"));
        adult.setDateOfBirth(resultSet.getDate(pref + "date_of_birth").toLocalDate());
        adult.setPassportSeria(resultSet.getString(pref + "passport_seria"));
        adult.setPassportNumber(resultSet.getString(pref + "passport_number"));
        adult.setIssueDate(resultSet.getDate(pref + "passport_date").toLocalDate());

        Long poId = resultSet.getLong(pref + "passport_office_id");
        String poArea = resultSet.getString(pref + "p_office_area_id");
        String poName = resultSet.getString(pref + "p_office_name");
        PassportOffice po = new PassportOffice(poId, poArea, poName);
        adult.setIssueDepartment(po);
        Address address = new Address();
        Street street = new Street(resultSet.getLong(pref + "street_code"), ""); // need to finish
        address.setStreet(street);
        address.setPostCode(resultSet.getString(pref + "post_index"));
        address.setBuilding(resultSet.getString(pref + "building"));
        address.setExtension(resultSet.getString(pref + "extension"));
        address.setApartment(resultSet.getString(pref + "apartment"));
        adult.setAddress(address);

        University university = new University(resultSet.getLong(pref + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentID(resultSet.getString(pref + "student_number"));

        return adult;
    }

    private void findChildren(Connection connection, List<StudentOrder> result) throws SQLException
    {
        String childCol = "(" + result.stream().map(sO -> String.valueOf(sO.getStudentOrderID())).collect(Collectors.joining(", ")) + ")"; //read more

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors.toMap(sO -> sO.getStudentOrderID(), sO -> sO));

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CHILD + childCol))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                Child child = fillChild(resultSet);
                StudentOrder sO = maps.get(resultSet.getLong("student_order_id"));
                sO.addChild(child);
            }
        }
    }

    private Child fillChild(ResultSet resultSet) throws SQLException
    {
        String surName = resultSet.getString("c_sur_name");
        String givenName = resultSet.getString("c_given_name");
        String patronymic = resultSet.getString("c_patronymic");
        LocalDate dateOfBitth = resultSet.getDate("c_date_of_birth").toLocalDate();

        Child child = new Child(surName,givenName,patronymic,dateOfBitth);

        child.setCertificateNumber(resultSet.getString("c_certificate_number"));
        child.setIssueDate(resultSet.getDate("c_certificate_date").toLocalDate());

        Long roID = resultSet.getLong("c_register_office_id");
        String roArea = resultSet.getString("r_office_area_id");
        String roName = resultSet.getString("r_office_name");
        RegisterOffice registerOffice = new RegisterOffice(roID,roArea,roName);
        child.setIssueDepartment(registerOffice);

        Address address = new Address();
        Street street = new Street(resultSet.getLong("c_street_code"), ""); // need to finish
        address.setStreet(street);
        address.setPostCode(resultSet.getString("c_post_index"));
        address.setBuilding(resultSet.getString("c_building"));
        address.setExtension(resultSet.getString("c_extension"));
        address.setApartment(resultSet.getString("c_apartment"));
        child.setAddress(address);
        return child;
    }
}
