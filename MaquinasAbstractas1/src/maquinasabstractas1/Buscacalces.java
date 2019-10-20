/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinasabstractas1;

import java.util.ArrayList;

/**
 *
 * @author Maximo Hernandez
 */
public class Buscacalces {
    private AFD afd;
    private int contador = 0;
    private int nInicio = -1;
    private int nFinal = -1;
    private boolean autoActivo = false;
    private boolean nEFinal = false;
    private String estadoInicial;
    private String casiSumidero;
    private String letra;
    private String eActual;
    private String eSiguiente;
    private String cadena;
    private ArrayList<Transicion> transiciones;
    private ArrayList<String> listaDeEstadosFinales;
    
    public Buscacalces(AFD afd, String cadena){
        
        this.afd = afd;
        this.cadena = cadena + "&&";
        this.transiciones = new ArrayList<>();
        this.estadoInicial = afd.getkInicioAFD();
        this.eActual = afd.getkInicioAFD();
        this.listaDeEstadosFinales = new ArrayList<>();
        this.eSiguiente = "";
        this.procesar();
        
    }
    
    //Procesa la cadena entregada usando el AFD generado
    private void procesar(){
        int cMagic = 0;
        //Busca sumideros dentro del AFD
        for(ArrayList<String> tAFD : this.afd.getTabla().get("estados")){
            for(String s : tAFD){
                if(s.equals("sumidero"))
                    this.casiSumidero = "n" + cMagic;
                cMagic++;
            }
        }
        //Agrega transiciones que no llevan a un sumidero por ninguna de sus transiciones
        for(Transicion t : this.afd.getDeltaAFD()){
            if(!t.getNodoSalida().equals(this.casiSumidero) && 
                    !t.getNodoEntrada().equals(this.casiSumidero)){
                this.transiciones.add(t);
            }
        }
        //Agrega los nodos finales que son sumideros
        for(String sFinal : this.afd.getFAFD()){
            if(!sFinal.equals(this.casiSumidero))
                this.listaDeEstadosFinales.add(sFinal);
        }
        /*Busca ocurrencias navegando dentro de la cadena
        * Se encarga de tomar el comienzo y el final de este.
        */
        for(int e = 0; e < this.cadena.length(); e++){
            
            this.contador = 0;
            this.letra = Character.toString(this.cadena.charAt(e));
            
            for(Transicion tr : this.transiciones){
                if(tr.getNodoSalida().equals(this.eActual) && tr.getUnion().equals(this.letra)){
                    this.eSiguiente = tr.getNodoEntrada();
                    this.contador = 1;
                }
            }
            
            if(this.contador == 0)
                this.eSiguiente = "";
            if(this.eSiguiente.equals("")){
                if(this.autoActivo){
                    e--;
                }
                if(this.nInicio !=-1 && this.nFinal !=-1)
                    System.out.println(this.nInicio + " " + this.nFinal);
                this.nInicio = -1;
                this.nFinal = -1;
                this.autoActivo = false;
                this.eActual = this.afd.getkInicioAFD();
            }
            else{
                if(!this.autoActivo){
                    this.nInicio = e;
                    for(String eFin : this.listaDeEstadosFinales){
                        if(eFin.equals(this.eSiguiente))
                            this.nEFinal = true;
                    }
                    if(nEFinal){
                        this.nFinal = e;
                    }
                    this.nEFinal = false;
                }
                else{
                    for(String eFin : this.listaDeEstadosFinales){
                        if(eFin.equals(this.eSiguiente))
                            this.nEFinal = true;
                    }
                    if(this.nEFinal)
                        this.nFinal = e;
                    this.nEFinal = false;
                }
                this.autoActivo = true;
                this.eActual = this.eSiguiente;
            }
        }
    }
}
