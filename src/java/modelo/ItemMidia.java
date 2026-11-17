package modelo;

import java.io.Serializable;

public class ItemMidia implements Serializable {
    private int id;
    private String titulo;
    private String tipo;
    private String genero;
    private String autor;
    private Integer ano;
    private String sinopse;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
}