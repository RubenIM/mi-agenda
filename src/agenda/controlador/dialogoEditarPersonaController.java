package agenda.controlador;

import agenda.modelo.Persona;
import agenda.util.FechaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


//import org.controlsfx.dialog.Dialogs;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import ch.makery.address.model.Person;
//import ch.makery.address.util.DateUtil;




/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class dialogoEditarPersonaController {

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellido;
    @FXML
    private TextField campoCalle;
    @FXML
    private TextField campoCodigoPostal;
    @FXML
    private TextField campoCiudad;
    @FXML
    private TextField campoCumpleanios;


    private Stage dialogStage;
    private Persona persona;
    private boolean okClicked = false;
 
    @FXML
    private void initialize() {
    }
  
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPerson(Persona persona) {
        this.persona = persona;

        campoNombre.setText(persona.getNombre());
        campoApellido.setText(persona.getApellido());
        campoCalle.setText(persona.getCalle());
        campoCodigoPostal.setText(Integer.toString(persona.getCodigoPostal()));
        campoCiudad.setText(persona.getCiudad());
        campoCumpleanios.setText(FechaUtil.format(persona.getCumpleanios()));
        campoCumpleanios.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            persona.setNombre(campoNombre.getText());
            persona.setApellido(campoApellido.getText());
            persona.setCalle(campoCalle.getText());
            persona.setCodigoPostal(Integer.parseInt(campoCodigoPostal.getText()));
            persona.setCiudad(campoCiudad.getText());
            persona.setCumpleanios(FechaUtil.parse(campoCumpleanios.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        

        String errorMessage = "";

        if (campoNombre.getText() == null || campoNombre.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (campoApellido.getText() == null || campoApellido.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (campoCalle.getText() == null || campoCalle.getText().length() == 0) {
            errorMessage += "No valid street!\n"; 
        }

        if (campoCodigoPostal.getText() == null || campoCodigoPostal.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(campoCodigoPostal.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }

        if (campoCiudad.getText() == null || campoCiudad.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }

        if (campoCumpleanios.getText() == null || campoCumpleanios.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!FechaUtil.validDate(campoCumpleanios.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
           /* Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();*/
        
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Campos inválidos");
                alert.setHeaderText(null);
                alert.setContentText("Corrige campos inválidos");
                alert.showAndWait();
                return false;
        }
    
    }
}