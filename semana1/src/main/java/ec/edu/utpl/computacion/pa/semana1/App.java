package ec.edu.utpl.computacion.pa.semana1;


import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Connection conn =
                DriverManager.getConnection("jdbc:h2:~/dbpa", "sa", "");

        //1. Crear la tabla
        //createTable(conn);

        //2. Insertar los datos
        //insertData(conn);

        //3. Consultar datos
        getAllData(conn);

        //4. obtener promedio
        System.out.println(prom(conn));

        //5. obtener nombre iniciado con J
        searchByLetter(conn);

        //6. obtener nombre con id ingresado
        Scanner lector = new Scanner(System.in);
        System.out.print("Ingrese ID: ");
        String id2Search = lector.nextLine();
        searchByID(id2Search, conn);

        conn.close();

    }



    private static void searchByID(String id, Connection conn) throws SQLException {
        var select = """
            SELECT ID, FIRST_NAME, LAST_NAME, AGE
            FROM REGISTRATION r
            WHERE ID = ?
            """;
        //var select = String.format(selectBase, id);
        try (PreparedStatement pStmt = conn.prepareStatement(select)){
            pStmt.setString(1,id);
             try(ResultSet rs = pStmt.executeQuery())
        //try (Statement stmt = conn.createStatement();
             //ResultSet rs = stmt.executeQuery(select))
            {
                while (rs.next()){
                    System.out.printf("%d - %s - %s (%d)\n",
                            rs.getInt("ID"),
                            rs.getString("LAST_NAME"),
                            rs.getString("FIRST_NAME"),
                            rs.getInt("AGE"));
                }
            }
        }
    }


    private static void searchByLetter(Connection conn) throws SQLException {
        var selectLetter = """
            SELECT r.FIRST_NAME
            FROM REGISTRATION r
            WHERE r.FIRST_NAME LIKE 'J%'
            """;

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectLetter)
            ){
            while (rs.next()){
                System.out.printf("%s%n",
                        rs.getString("FIRST_NAME"));
                }
            }

    }

    private static double prom(Connection conn) throws SQLException {
        var selectAVG = """
            SELECT AVG(AGE) FROM REGISTRATION
            """;
        double output = -1;
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectAVG)
        ) {
            rs.next();
            output = rs.getDouble("AVG(AGE)");
        }
        return output;
    }

    private static void getAllData(Connection conn) throws SQLException {
        String select = "SELECT ID, FIRST_NAME, LAST_NAME, AGE FROM REGISTRATION";
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select)
        ) {
            while(rs.next()) {
                System.out.printf("%d - %s %s (%d)\n",
                        rs.getInt("ID"),
                        rs.getString("LAST_NAME"),
                        rs.getString("FIRST_NAME"),
                        rs.getInt("AGE"));
            }
        }
    }
    private static void insertData(Connection conn) throws SQLException{
        var data = """
                INSERT INTO REGISTRATION VALUES (1, 'JORGE', 'LÓPEZ', 45);
                INSERT INTO REGISTRATION VALUES (2, 'JUAN', 'MOROCHO', 47);
                INSERT INTO REGISTRATION VALUES (3, 'RENÉ', 'ELIZALDE', 40);
                INSERT INTO REGISTRATION VALUES (4, 'MARÍA', 'CABRERA', 40);
                INSERT INTO REGISTRATION VALUES (5, 'ELIZABETH', 'CADME', 43);
                INSERT INTO REGISTRATION VALUES (6, 'AUDREY', 'ROMERO', 44);
                """;
        try(Statement stmt = conn.createStatement()) {
            int count = stmt.executeUpdate(data);
            System.out.println(count);
        }
    }
    private static void createTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            var sqlCreateTable = """
                    CREATE TABLE IF NOT EXISTS REGISTRATION
                    (
                    ID INTEGER NOT NULL,
                    FIRST_NAME VARCHAR(255),
                    LAST_NAME VARCHAR(255),
                    AGE INTEGER,
                    CONSTRAINT REGISTRATION_pkey PRIMARY KEY (ID)
                    );
                    """;

            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabla creada");
        }
    }
}
