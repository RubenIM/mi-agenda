/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import agenda.controlador.DisenioRaizController;
import agenda.controlador.VisionGeneralController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class VisionPrincipalController {

    private BorderPane disenioRaiz;
    private FXMLLoader cargador;
    private RubenAgendaMain mainApp;   //solo este
    private VisionGeneralController personController;
    /**
     * Initializes the controller class.
     */
    public void setMainApp(RubenAgendaMain mainApp) {
          this.mainApp = mainApp;
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
        
    public void muestraVistasPersona() {
            try {                 
                    
            iniciaDisenioRaiz();
            cargador = new FXMLLoader();
            cargador.setLocation(agenda.RubenAgendaMain.class.getResource("vista/VisionGeneral.fxml"));
            
            AnchorPane personOverview = (AnchorPane) cargador.load();
            
            // Set person overview into the center of root layout.
            disenioRaiz.setCenter(personOverview);
            
            Stage primerEscenario = mainApp.getPrimerEscenario();

            primerEscenario.setScene(new Scene(disenioRaiz));
            primerEscenario.show();
            /*VisionGeneralController controlador = cargador.getController();
            controlador.setMainApp(mainApp);*/

            } catch (IOException e) {
                e.printStackTrace();
            }      
    }
    
    public void muestraVistasAnotaciones() {
        try {
            iniciaDisenioRaiz();
            cargador = new FXMLLoader();
            cargador.setLocation(agenda.RubenAgendaMain.class.getResource("/Anotaciones/vista/VisionGeneral.fxml"));
            
            AnchorPane noteOverview = (AnchorPane) cargador.load();
            
            // Set note overview into the center of root layout.
            disenioRaiz.setCenter(noteOverview);
            
            Stage primerEscenario = mainApp.getPrimerEscenario();
            
            primerEscenario.setScene(new Scene(disenioRaiz));
            primerEscenario.show();
            
            /*VisionGeneralController controlador = cargador.getController();
            controlador.setMainApp(mainApp);*/

        } catch (IOException e) {
            e.printStackTrace();
        }      
    }
}

