package Anotaciones.controlador;

import Anotaciones.modelo.Anotaciones;
import agenda.util.FechaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import org.controlsfx.dialog.Dialogs;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import ch.makery.address.model.Note;
//import ch.makery.address.util.DateUtil;

/**
 * Dialog to edit details of a note.
 * 
 * @author Marco Jakob
 */
public class dialogoEditarAnotacionesController {

    @FXML
    private TextField campoTitulo;
    @FXML
    private TextField campoDescripcion;
    @FXML
    private TextField campoFechaCreacion;


    private Stage dialogStage;
    private Anotaciones anotaciones;
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
     * Sets the note to be edited in the dialog.
     * 
     * @param note
     */
    public void setNote(Anotaciones anotaciones) {
        this.anotaciones = anotaciones;

        campoTitulo.setText(anotaciones.getTitulo());
        campoDescripcion.setText(anotaciones.getDescripcion());
        campoFechaCreacion.setText(FechaUtil.format(anotaciones.getFechaCreacion()));
        campoFechaCreacion.setPromptText("dd.mm.yyyy");
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
            anotaciones.setTitulo(campoTitulo.getText());
            anotaciones.setDescripcion(campoDescripcion.getText());
            anotaciones.setFechaCreacion(FechaUtil.parse(campoFechaCreacion.getText()));

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

        if (campoTitulo.getText() == null || campoTitulo.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (campoDescripcion.getText() == null || campoDescripcion.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        
        if (campoFechaCreacion.getText() == null || campoFechaCreacion.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!FechaUtil.validDate(campoFechaCreacion.getText())) {
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