package agenda;

import agenda.controlador.DisenioRaizController;
import agenda.controlador.VisionGeneralController;
import agenda.controlador.dialogoEditarPersonaController;
import agenda.modelo.PersonListWrapper;
import agenda.modelo.Persona;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class RubenAgendaMain extends Application {

    private Stage primerEscenario;
    private BorderPane disenioRaiz;
    private FXMLLoader cargador;

    @Override
    public void start(Stage primerEscenario) {
        this.primerEscenario = primerEscenario;
        this.primerEscenario.setTitle("AddressApp");
        
        this.primerEscenario.getIcons().add(new Image("file:resources/images/bat.png")); 
        iniciaDisenioRaiz();

        muestraVistasPersona();
    }
    
 
    public void iniciaDisenioRaiz() {
        try {

            cargador = new FXMLLoader();
            cargador.setLocation(RubenAgendaMain.class.getResource("vista/DisenioRaiz.fxml"));
            
            disenioRaiz = (BorderPane) cargador.load();   
            
            Scene escena = new Scene(disenioRaiz);
            primerEscenario.setScene(escena);
            primerEscenario.show();
            
            DisenioRaizController controller = cargador.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
                        
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }        
    }

    
    public void muestraVistasPersona() {
        /*try {
   
            cargador = new FXMLLoader();
            cargador.setLocation(RubenAgendaMain.class.getResource("agenda.vista/VisionGeneral.fxml"));
            AnchorPane vistasPersona = (AnchorPane) cargador.load();
            
            disenioRaiz.setCenter(vistasPersona);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
                try {
            // Load person overview.
            cargador = new FXMLLoader();
            cargador.setLocation(agenda.RubenAgendaMain.class.getResource("vista/VisionGeneral.fxml"));
            
            AnchorPane personOverview = (AnchorPane) cargador.load();
            
            // Set person overview into the center of root layout.
            disenioRaiz.setCenter(personOverview);
            VisionGeneralController controlador = cargador.getController();
            controlador.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }      
    }
    
    
    public Stage getPrimerEscenario() {
        return primerEscenario;
    }

    public static void main(String[] args) {
        launch(args);
    }
       
    private ObservableList<Persona> datosPersona = FXCollections.observableArrayList();

    
    public RubenAgendaMain() {
       
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
    }
  

    public ObservableList<Persona> getDatosPersona() {
        return datosPersona;
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
        dialogStage.initOwner(primerEscenario);
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
    public File getPersonFilePath() {
    Preferences prefs = Preferences.userNodeForPackage(RubenAgendaMain.class);
    String filePath = prefs.get("filePath", null);
    if (filePath != null) {
        return new File(filePath);
    } else {
        return null;
    }
}

/**
 * Sets the file path of the currently loaded file. The path is persisted in
 * the OS specific registry.
 * 
 * @param file the file or null to remove the path
 */
public void setPersonFilePath(File file) {
    Preferences prefs = Preferences.userNodeForPackage(RubenAgendaMain.class);
    if (file != null) {
        prefs.put("filePath", file.getPath());

        // Update the stage title.
        primerEscenario.setTitle("AddressApp - " + file.getName());
    } else {
        prefs.remove("filePath");

        // Update the stage title.
        primerEscenario.setTitle("AddressApp");
    }
}
    

public void loadPersonDataFromFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(PersonListWrapper.class);
        Unmarshaller um = context.createUnmarshaller();

        // Reading XML from the file and unmarshalling.
        PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

        datosPersona.clear();
        datosPersona.addAll(wrapper.getPersons());

        // Save the file path to the registry.
        setPersonFilePath(file);

    } catch (Exception e) { // catches ANY exception
     
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load data from file:\n" + file.getPath());
        alert.setContentText("Please select a person in the table.");
        alert.showAndWait();
    }
}

/**
 * Saves the current person data to the specified file.
 * 
 * @param file
 */
public void savePersonDataToFile(File file) {
    try {
        JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Wrapping our person data.
        PersonListWrapper wrapper = new PersonListWrapper();
        wrapper.setPersons(datosPersona);

        // Marshalling and saving XML to the file.
        m.marshal(wrapper, file);

        // Save the file path to the registry.
        setPersonFilePath(file);
    } catch (Exception e) { // catches ANY exception
        
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data from file:\n" + file.getPath());
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
    }
}


}