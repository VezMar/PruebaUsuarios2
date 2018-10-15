package com.example.pcgamer.pruebausuarios.Entidades;

/**
 * Created by Desarrollo 2 on 12/10/2018.
 */

public class Usuario {

    private Integer IDUsuario;
    private String Nombre;
    private String Direccion;
    private String Imagen;
    private String FechaCreate;
    private String FechaUpdate;

    public Integer getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(Integer IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getFechaCreate() {
        return FechaCreate;
    }

    public void setFechaCreate(String fechaCreate) {
        FechaCreate = fechaCreate;
    }

    public String getFechaUpdate() {
        return FechaUpdate;
    }

    public void setFechaUpdate(String fechaUpdate) {
        FechaUpdate = fechaUpdate;
    }
}
