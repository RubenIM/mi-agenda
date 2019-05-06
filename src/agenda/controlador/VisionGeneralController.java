package agenda.controlador;

import agenda.RubenAgendaMain;
import agenda.modelo.Persona;
import agenda.util.FechaUtil;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


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
    
    private ObservableList<Persona> datosPersona = FXCollections.observableArrayList();
    // Reference to the main application.
    private RubenAgendaMain mainApp;
    private BorderPane disenioRaiz;
    private FXMLLoader cargador;
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
        
        datosPersona.add(new Persona("Ruben", "Izquierdo"));
        datosPersona.add(new Persona("Laura", "Herrera"));
        datosPersona.add(new Persona("Manu", "Valverde"));
        datosPersona.add(new Persona("Laura", "Perez"));
        datosPersona.add(new Persona("Miguel", "Lopez"));
        datosPersona.add(new Persona("Alvaro", "Mayorga"));
        datosPersona.add(new Persona("Maribel", "Freire"));
        datosPersona.add(new Persona("Suliman", "Abdelkader"));
        datosPersona.add(new Persona("Marco", "Ruiz"));
        datosPersona.add(new Persona("Alberto", "Sampedro"));
        
        // Initialize the person table with the two columns.
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().propiedadNombre());
        columnaApellido.setCellValueFactory(cellData -> cellData.getValue().propiedadApellido());

        // Clear person details.
        mostrarDetallesPersona(null);

        // Listen for selection changes and show the person details when changed.
        tablaPersona.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetallesPersona(newValue));    
        
        tablaPersona.setItems(datosPersona);        
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
    private void borrarPersona() {
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
        boolean okClicked = showPersonEditDialog(tempPerson);
        if (okClicked) {
           datosPersona.add(tempPerson);
        }
    }
    
    public ObservableList<Persona> getDatosPersona() {
        return datosPersona;
    }
    
    private Stage getEscenarioPrincipal(){
        
        try {
            Stage escenario = new Stage();
        
            FXMLLoader cargador = new FXMLLoader();
            cargador.setLocation(RubenAgendaMain.class.getResource("vista/DisenioRaiz.fxml"));
            
            BorderPane disenioRaiz = (BorderPane) cargador.load();
            
            Scene escena = new Scene(disenioRaiz);
            escenario.setScene(escena);
            
            return escenario;
        } catch (Exception e) {
            return null;
        }        
    }
    
    public boolean showPersonEditDialog(Persona persona) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RubenAgendaMain.class.getResource("vista/dialogoEditarPersona.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(getEscenarioPrincipal());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        dialogoEditarPersonaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPerson(persona);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
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
        boolean okClicked = showPersonEditDialog(selectedPerson);
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
      public void iniciaDisenioRaiz() {
        try {

            cargador = new FXMLLoader();
            cargador.setLocation(RubenAgendaMain.class.getResource("vista/DisenioRaiz.fxml"));
            
            disenioRaiz = (BorderPane) cargador.load();   
            
        } catch (IOException e) {
            e.printStackTrace();
        }
      }
      
      @FXML
      private void volverPaginaPrincipal(){          
          try {         
            iniciaDisenioRaiz();
            cargador = new FXMLLoader();
            cargador.setLocation(agenda.RubenAgendaMain.class.getResource("VisionPrincipal.fxml"));
            
            AnchorPane personOverview = (AnchorPane) cargador.load();
            
            // Set person overview into the center of root layout.
            disenioRaiz.setCenter(personOverview);
            
            Stage primerEscenario = getEscenarioPrincipal();

            primerEscenario.setScene(new Scene(disenioRaiz));
            primerEscenario.show();
            /*VisionGeneralController controlador = cargador.getController();
            controlador.setMainApp(mainApp);*/

            } catch (IOException e) {
                e.printStackTrace();
            }    
      }
}