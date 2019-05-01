package Anotaciones.controlador;

import agenda.RubenAgendaMain;
import Anotaciones.modelo.Anotaciones;
import agenda.controlador.dialogoEditarPersonaController;
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
    private TableView<Anotaciones> tablaAnotaciones;
    @FXML
    private TableColumn<Anotaciones, String> columnaAnotaciones;
    @FXML
    private Label etiquetaTitulo;
    @FXML
    private Label etiquetaDescripcion;
    @FXML
    private Label etiquetaFechaCreacion;
    private ObservableList<Anotaciones> datosNotas = FXCollections.observableArrayList();
    // Reference to the main application.
    private RubenAgendaMain mainApp;

    FXMLLoader cargador = new FXMLLoader();
    
    BorderPane disenioRaiz = new BorderPane();
    
    public VisionGeneralController() {
    }
    
    @FXML
    private void initialize() {        
        
        datosNotas.add(new Anotaciones("1","a"));
        datosNotas.add(new Anotaciones("2","b"));
        datosNotas.add(new Anotaciones("3","c"));
        datosNotas.add(new Anotaciones("4","d"));
        datosNotas.add(new Anotaciones("5","e"));
        datosNotas.add(new Anotaciones("6","f"));
        datosNotas.add(new Anotaciones("8","g"));
        datosNotas.add(new Anotaciones("9","h"));        
        
        // Initialize the person table with the two columns.
        columnaAnotaciones.setCellValueFactory(cellData -> cellData.getValue().propiedadTitulo());

        // Clear person details.
        mostrarDetallesAnotaciones(null);

        //tablaAnotaciones.setItems(mainApp.getDatosAnotaciones());
        
        // Listen for selection changes and show the person details when changed.
        tablaAnotaciones.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetallesAnotaciones(newValue));        
        tablaAnotaciones.setItems(datosNotas);
    }
   
    /**
 * Fills all text fields to show details about the person.
 * If the specified person is null, all text fields are cleared.
 * 
 * @param person the person or null
 */
        public ObservableList<Anotaciones> getDatosAnotaciones() {
        return datosNotas;
    }
            
    private void mostrarDetallesAnotaciones(Anotaciones anotaciones) {
        if (anotaciones != null) {
        // Fill the labels with info from the person object.
           etiquetaTitulo.setText(anotaciones.getTitulo());
           etiquetaDescripcion.setText(anotaciones.getDescripcion());
           etiquetaFechaCreacion.setText(FechaUtil.format(anotaciones.getFechaCreacion()));

        // TODO: We need a way to convert the birthday into a String! 
        // birthdayLabel.setText(...);
        } else {
        // Notes is null, remove all the text.
           etiquetaTitulo.setText("");
           etiquetaDescripcion.setText("");
           etiquetaFechaCreacion.setText("");
        }
    }
    
    @FXML
    private void borrarAnotaciones() {
        int selectedIndex = tablaAnotaciones.getSelectionModel().getSelectedIndex();
        tablaAnotaciones.getItems().remove(selectedIndex);
        if (selectedIndex >= 0) {
        tablaAnotaciones.getItems().remove(selectedIndex);
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
private void handleNewNotes() {
    Anotaciones tempNota = new Anotaciones();
    boolean okClicked = showNoteEditDialog(tempNota);
    if (okClicked) {
        datosNotas.add(tempNota);
    }
}   

/**
 * Called when the user clicks the edit button. Opens a dialog to edit
 * details for the selected person.
 */
@FXML
private void handleEditNotes() {
    Anotaciones selectedNota = tablaAnotaciones.getSelectionModel().getSelectedItem();
    if (selectedNota != null) {
        boolean okClicked = showNoteEditDialog(selectedNota);
        if (okClicked) {
            mostrarDetallesAnotaciones(selectedNota);
        }

    } else {
        // Nothing selected.
           Alert alert = new Alert(AlertType.WARNING);
           alert.setTitle("No Selection");
           alert.setHeaderText(null);
           alert.setContentText("Please select a note in the table.");
           alert.showAndWait();
    }
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

      public boolean showNoteEditDialog(Anotaciones anotaciones) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RubenAgendaMain.class.getResource("../Anotaciones/vista/dialogoEditarAnotaciones.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Note");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(getEscenarioPrincipal());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        dialogoEditarAnotacionesController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setNote(anotaciones);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
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
