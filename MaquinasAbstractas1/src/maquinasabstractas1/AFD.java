/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinasabstractas1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Maximo Hernandez
 */
public class AFD {
    
    //AFND creado anteriormente
    private AFND afnd;
    
    //Componentes de definicion del AFD a crear
    private ArrayList<Transicion> deltaAFD;
    private ArrayList<String> KAFD;
    private ArrayList<String> sigmaAFD;
    private ArrayList<String> FAFD;
    private String kInicioAFD;
    
    /*Ambos estados ayudan a determinar cuales de los nuevos estados seran estados de inicio o finales
    * no confundir ccn kInicioAFD y FAFD.
    */
    private String primerEstadoInicial;
    private String primerEstadoFinal;
    
    /*
    * Tabla, similar a la que se uso en clases, para determinar los estados que nuestro AFD tendra.
    */
    private HashMap<String, ArrayList<ArrayList<String>>> tabla;
    
    /*
    *
    */
    
    

    
    
}
