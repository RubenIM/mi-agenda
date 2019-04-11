package agenda.modelo;

import agenda.util.LocalDateAdapter;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Persona {

    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty calle;
    private final IntegerProperty codigoPostal;
    private final StringProperty ciudad;
    private final ObjectProperty<LocalDate> cumpleanios;

    public Persona() {
        this(null, null);
    }
   
    public Persona(String nombre, String apellido) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        
        this.calle = new SimpleStringProperty("Padre Claret");
        this.codigoPostal = new SimpleIntegerProperty(1234);
        this.ciudad = new SimpleStringProperty("Madrid");
        this.cumpleanios = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }
    
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    
    public StringProperty propiedadNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }
    
    public StringProperty propiedadApellido() {
        return apellido;
    }

    public String getCalle() {
        return calle.get();
    }

    public void setCalle(String calle) {
        this.calle.set(calle);
    }
    
    public StringProperty propiedadCalle() {
        return calle;
    }

    public int getCodigoPostal() {
        return codigoPostal.get();
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal.set(codigoPostal);
    }
    
    public IntegerProperty postalCodeProperty() {
        return codigoPostal;
    }

    public String getCiudad() {
        return ciudad.get();
    }

    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }
    
    public StringProperty propiedadCiudad() {
        return ciudad;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getCumpleanios() {
        return cumpleanios.get();
    }

    public void setCumpleanios(LocalDate cumpleanios) {
        this.cumpleanios.set(cumpleanios);
    }
    
    public ObjectProperty<LocalDate> propiedadCumpleanios() {
        return cumpleanios;
    }
}
