package ec.edu.utpl.computacion.pa.view;

import ec.edu.utpl.computacion.pa.controller.RegistrationController;
import ec.edu.utpl.computacion.pa.model.RegisterDTO;

public class ViewCMD {
    public static void main(String[] args) {
        RegistrationController database = new RegistrationController();

        database.addRegistration(new RegisterDTO(1002, "Verónica", "Segarra", 39));
        database.close();
    }
}
