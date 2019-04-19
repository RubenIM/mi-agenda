package agenda.controlador;

import agenda.RubenAgendaMain;
import agenda.modelo.Persona;
import agenda.util.FechaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
 import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class VisionGeneralController {
    @FXML
    private TableView<Persona> tablaPersona;
    @FXML
    private TableColumn<Persona, String> columnaNombre;
    @FXML
    private TableColumn<Persona, String> columnaApellido;

    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaApellido;
    @FXML
    private Label etiquetaCalle;
    @FXML
    private Label etiquetaCodigoPostal;
    @FXML
    private Label etiquetaCiudad;
    @FXML
    private Label etiquetaCumpleanios;

    // Reference to the main application.
    private RubenAgendaMain mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public VisionGeneralController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().propiedadNombre());
        columnaApellido.setCellValueFactory(cellData -> cellData.getValue().propiedadApellido());
        
        
        // Initialize the person table with the two columns.
        columnaNombre.setCellValueFactory(
               cellData -> cellData.getValue().propiedadNombre());
        columnaApellido.setCellValueFactory(
               cellData -> cellData.getValue().propiedadApellido());

        // Clear person details.
        mostrarDetallesPersona(null);

        // Listen for selection changes and show the person details when changed.
        tablaPersona.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetallesPersona(newValue));               
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(RubenAgendaMain mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        tablaPersona.setItems(mainApp.getDatosPersona());
    }
    
    
    /**
 * Fills all text fields to show details about the person.
 * If the specified person is null, all text fields are cleared.
 * 
 * @param person the person or null
 */
    private void mostrarDetallesPersona(Persona persona) {
        if (persona != null) {
        // Fill the labels with info from the person object.
           etiquetaNombre.setText(persona.getNombre());
           etiquetaApellido.setText(persona.getApellido());
           etiquetaCalle.setText(persona.getCalle());
           etiquetaCodigoPostal.setText(Integer.toString(persona.getCodigoPostal()));
           etiquetaCiudad.setText(persona.getCiudad());
           etiquetaCumpleanios.setText(FechaUtil.format(persona.getCumpleanios()));

        // TODO: We need a way to convert the birthday into a String! 
        // birthdayLabel.setText(...);
        } else {
        // Person is null, remove all the text.
           etiquetaNombre.setText("");
           etiquetaApellido.setText("");
           etiquetaCalle.setText("");
           etiquetaCodigoPostal.setText("");
           etiquetaCiudad.setText("");
           etiquetaCumpleanios.setText("");
        }
    }
    
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tablaPersona.getSelectionModel().getSelectedIndex();
        tablaPersona.getItems().remove(selectedIndex);
        if (selectedIndex >= 0) {
        tablaPersona.getItems().remove(selectedIndex);
        } else {
           // Nothing selected.
           Alert alert = new Alert(AlertType.WARNING);
           alert.setTitle("No Selection");
           alert.setHeaderText(null);
           alert.setContentText("Please select a person in the table.");
           alert.showAndWait();
        }                     
    }
    
    
    @FXML
private void handleNewPerson() {
    Persona tempPerson = new Persona();
    boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
    if (okClicked) {
        mainApp.getDatosPersona().add(tempPerson);
    }
}

/**
 * Called when the user clicks the edit button. Opens a dialog to edit
 * details for the selected person.
 */
@FXML
private void handleEditPerson() {
    Persona selectedPerson = tablaPersona.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
        boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
        if (okClicked) {
            mostrarDetallesPersona(selectedPerson);
        }

    } else {
        // Nothing selected.
           Alert alert = new Alert(AlertType.WARNING);
           alert.setTitle("No Selection");
           alert.setHeaderText(null);
           alert.setContentText("Please select a person in the table.");
           alert.showAndWait();
    }
}
    
}