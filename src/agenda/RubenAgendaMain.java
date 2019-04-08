package agenda;

import agenda.controlador.VisionGeneralController;
import agenda.modelo.Persona;
import java.io.IOException;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RubenAgendaMain extends Application {

    private Stage primerEscenario;
    private BorderPane disenioRaiz;
    private FXMLLoader cargador;

    @Override
    public void start(Stage primerEscenario) {
        this.primerEscenario = primerEscenario;
        this.primerEscenario.setTitle("AddressApp");

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
        } catch (IOException e) {
            e.printStackTrace();
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
  
    
    public ObservableList<Persona> getPersonData() {
        return datosPersona;
    }

    public ObservableList<Persona> getDatosPersona() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}