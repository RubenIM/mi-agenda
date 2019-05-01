package Anotaciones.modelo;

import agenda.util.LocalDateAdapter;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Anotaciones {

    private final StringProperty titulo;
    private final StringProperty descripcion;
    private final ObjectProperty<LocalDate> fechaCreacion;

    public Anotaciones() {
        this(null, null);
    }
   
    public Anotaciones(String titulo, String descripcion) {
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.fechaCreacion = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }
    
    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }
    
    public StringProperty propiedadTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }
    
    public StringProperty propiedadDescripcion() {
        return descripcion;
    }    

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFechaCreacion() {
        return fechaCreacion.get();
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }
    
    public ObjectProperty<LocalDate> propiedadFechaCreacion() {
        return fechaCreacion;
    }
}

