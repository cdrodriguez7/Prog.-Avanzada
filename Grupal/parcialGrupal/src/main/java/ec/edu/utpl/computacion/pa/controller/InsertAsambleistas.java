package ec.edu.utpl.computacion.pa.controller;

import ec.edu.utpl.computacion.pa.model.Asambleista;

import java.sql.*;
import java.util.ArrayList;

public class InsertAsambleistas implements Runnable{

    private ArrayList<Asambleista> asambleistas;

    public InsertAsambleistas(ArrayList<Asambleista> a){
        this.asambleistas = a;
    }

    @Override
    public void run() {
        String url = "jdbc:h2:C:\\Users\\crixx\\Downloads\\prueba_parcial/pp";
        String username = "pp_cd";
        String password = "123";

        String sql = "INSERT INTO Asambleista (TIPO) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement stmtAsambleista = connection.prepareStatement(sql)) {

            //connection.setAutoCommit(false);//buscar en chatgpt

            for (Asambleista asambleista : asambleistas) {
                // Insert into Asambleista
                stmtAsambleista.setString(1, asambleista.getTipo());
                stmtAsambleista.executeUpdate();
                System.out.println("Se inserto: " + asambleista.getTipo());

                stmtAsambleista.clearParameters(); // Clear the parameter for the next iteration
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Termino " + Thread.currentThread().getName());
    }

}
