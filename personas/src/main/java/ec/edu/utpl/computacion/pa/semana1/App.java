package ec.edu.utpl.computacion.pa.semana1;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        try (Connection con =
                     DriverManager.getConnection("jdbc:h2:~/demo1", "sa", "")) {

            //1. Crear la tabla
            createTable(con);

            //2. Agregar datos
            addData(con);

            //3. Consultar datos
            getAllData(con);

            //4. Obtener el promedio
            System.out.println(avgAge(con));

            //5. Obtener nombres inician con J
            searchBy(con);

            //6. Buscar por ID
            Scanner lector = new Scanner(System.in);
            System.out.print("Ingrese el id: ");
            String id = lector.nextLine();
            searchById(id, con);

            List<Person> people = List.of(
                    new Person(0, "Tais", "Valarezo", 18),
                    new Person(0, "Kevin", "Regalado", 22),
                    new Person(0, "Ronin", "Montero", 20),
                    new Person(0, "Hermin", "Espinoza", 20),
                    new Person(0, "Jeremy", "Escudero", 23),
                    new Person(0, "Oliver", "Chuquimarca", 21)
            );

            for(Person p : people) {
                registry(p, con);
            }

        }
    }
    private static void registry(Person person, Connection con) throws SQLException{
        var insert = """
                INSERT INTO REGISTRATION VALUES (?, ?, ?, ?)
                """;
        try(PreparedStatement pstmt = con.prepareStatement(insert)) {

            int maxId = getId(con);
            pstmt.setInt(1, maxId + 1);
            pstmt.setString(2, person.fName());
            pstmt.setString(3, person.lName());
            pstmt.setInt(4, person.age());

            pstmt.executeUpdate();
        }
    }

    private static int getId(Connection con) throws SQLException{
        var select = """
                SELECT MAX(ID) FROM REGISTRATION
                """;
        try(Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(select)) {

            rs.next();
                return rs.getInt("MAX(ID)");

        }
    }
    private static void searchById(String id, Connection con) throws SQLException {
        var select = """
                SELECT ID, FIRST_NAME, LAST_NAME, AGE 
                FROM REGISTRATION
                WHERE ID = ? 
                """;


        try (PreparedStatement pStmt = con.prepareStatement(select)) {
            pStmt.setString(1, id);
            try(ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%d - %s %s (%d)%n",
                            rs.getInt("ID"),
                            rs.getString("LAST_NAME"),
                            rs.getString("FIRST_NAME"),
                            rs.getInt("AGE"));
                }
            }
        }
    }

    private static void searchBy(Connection con) throws SQLException {
        var selectAVG = """
                SELECT FIRST_NAME 
                FROM REGISTRATION 
                WHERE FIRST_NAME LIKE 'J%'
                """;
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(selectAVG)
        ) {
            while (rs.next()) {
                System.out.printf("%s%n", rs.getString("FIRST_NAME"));
            }
        }
    }

    private static double avgAge(Connection con) throws SQLException {
        var selectAVG = "SELECT AVG(AGE) FROM REGISTRATION";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(selectAVG)
        ) {
            rs.next();
            return rs.getDouble("AVG(AGE)");
        }
    }

    private static void getAllData(Connection con) throws SQLException {
        var select = "SELECT ID, FIRST_NAME, LAST_NAME, AGE FROM REGISTRATION";
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(select)
        ) {
            while (rs.next()) {
                System.out.printf("%d - %s %s (%d)%n",
                        rs.getInt("ID"),
                        rs.getString("LAST_NAME"),
                        rs.getString("FIRST_NAME"),
                        rs.getInt("AGE"));
            }
        }
    }

    private static void addData(Connection con) throws SQLException {
        var insert = """
                INSERT INTO REGISTRATION VALUES (2, 'JUAN', 'MOROCHO', 47);
                INSERT INTO REGISTRATION VALUES (3, 'RENÉ', 'ELIZALDE', 39);
                INSERT INTO REGISTRATION VALUES (4, 'MARÍA', 'CABRERA', 40);
                INSERT INTO REGISTRATION VALUES (5, 'ELIZABETH', 'CADME', 45);
                INSERT INTO REGISTRATION VALUES (6, 'AUDREY', 'ROMERO', 44);
                """;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(insert);
        }
    }

    private static void createTable(Connection con) throws SQLException {
        var createTable = """
                CREATE TABLE IF NOT EXISTS REGISTRATION
                (
                ID INTEGER NOT NULL,
                FIRST_NAME VARCHAR(255),
                LAST_NAME VARCHAR(255),
                AGE INTEGER,
                CONSTRAINT REGISTRATION_pkey PRIMARY KEY (ID)
                );
                """;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createTable);
        }
    }
}
