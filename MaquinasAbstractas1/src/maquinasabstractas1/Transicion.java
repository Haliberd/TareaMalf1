/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinasabstractas1;

/**
 *
 * @author Maximo Hernandez
 */
class Transicion {
    
    /*
    *El formato de crear una transicion es el mismo visto que en la clase, es decir
    *(n1, a, n2) significa que, desde el nodo 1 (llamese n a la abreviatura de nodo y 1 al numero correspondiente de aquel nodo)
    *consumiendo el caracter a de la cadena, llega al nodo 2.
    */
    
    private String nodoSalida;
    private String union;
    private String nodoEntrada;

    public Transicion(String nodoSalida, String union, String nodoEntrada) {
        this.nodoSalida = nodoSalida;
        this.union = union;
        this.nodoEntrada = nodoEntrada;
    }

    public String getNodoSalida() {
        return nodoSalida;
    }

    public void setNodoSalida(String nodoSalida) {
        this.nodoSalida = nodoSalida;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getNodoEntrada() {
        return nodoEntrada;
    }

    public void setNodoEntrada(String nodoEntrada) {
        this.nodoEntrada = nodoEntrada;
    }

    
    
    
    
}
