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
    * Atributos del AFND
    */
    private ArrayList<String> sigmaAFND;
    private ArrayList<String> KAFND;
    private ArrayList<Transicion> deltaAFND;
    
    /*
    * Contador para los nodos
    */
    private int contador = 0;
    
    public AFD(AFND afnd){
        
        this.afnd = afnd;
        this.deltaAFD = new ArrayList<>();
        this.KAFD = new ArrayList<>();
        this.sigmaAFD = afnd.getSigma();
        this.FAFD = new ArrayList<>();
        this.tabla = new HashMap<>();
        this.deltaAFND = afnd.getDelta();
        this.KAFND = afnd.getK();
        this.sigmaAFND = afnd.getSigma();
        this.primerEstadoInicial = this.obtenerEstadoInicial();
        this.primerEstadoFinal = this.obtenerEstadoFinal();
        
    }

    private String obtenerEstadoInicial() {
        String nuevoEstadoInicial = "";
        for(int e = 0; this.KAFND.size() > e; e++){
            int coincidencias = 0;
            for (int i = 0; this.deltaAFND.size() > i && coincidencias == 0; i++){
                if(this.KAFND.get(e).equals(this.deltaAFND.get(i).getNodoEntrada()))
                    coincidencias++;
            }
            if( coincidencias == 0 )
                nuevoEstadoInicial = this.KAFND.get(e);
        }
        return nuevoEstadoInicial;
    }

    private String obtenerEstadoFinal() {
        String nuevoEstadoFinal = "";
        for(int e = 0; this.KAFND.size() > e; e++){
            int coincidencias = 0;
            for (int i = 0; this.deltaAFND.size() > i && coincidencias == 0; i++){
                if(this.KAFND.get(e).equals(this.deltaAFND.get(i).getNodoSalida()))
                    coincidencias++;
            }
            if( coincidencias == 0 )
                nuevoEstadoFinal = this.KAFND.get(e);
        }
        return nuevoEstadoFinal;
    }
    
    private ArrayList<String> obtenerNodosAdyacentes(String estado, String caracter){
        ArrayList<String> listaDeAdyacencia = new ArrayList<>();
        
        for(Transicion t : this.deltaAFND){
            if(t.getNodoSalida().equals(estado) && t.getUnion().equals(caracter))
                listaDeAdyacencia.add(t.getNodoEntrada());
        }
        
        return listaDeAdyacencia;
    }

    private String obtenerEstadoSiEsQueEsteHaSidoAgregadoAnteriormente(ArrayList<String> estados){
        
        ArrayList<ArrayList<String>> listaDeEstadosAgregados = this.tabla.get("estados");
        for(ArrayList<String> lista : listaDeEstadosAgregados){
            int contador = 0;
            for (String estado : lista){
                if(estados.contains(estado)){
                    contador++;
                }
            }
            if (contador == estados.size())
                return lista.get(lista.size()-1);
        }
        return null;
    }
    
    /*
    * Sea N el caracter apunta hacia un nodo, el cual esta compuesto de la siguiente forma
    * N -> como se describe anteriormente, determina que es un nodo.
    * X -> un numero entero positivo o 0
    * un ejemplo de nodo es N0
    */
    public void transformarAFNDaAFD(){
        //primero creamos la tabla, colocando un total de N nodos, siendo N la cantidad de nodos presente en el AFND
        tabla.put("estados", new ArrayList<ArrayList<String>>());
        for (String estado : this.sigmaAFND)
            tabla.put(estado, new ArrayList<ArrayList<String>>());
        //agregar los estados de inicio a "estados"
        tabla.get("estados").add(new ArrayList<String>());
        tabla.get("estados").get(0).add(this.primerEstadoInicial);
        
        for(String estado : tabla.get("estados").get(0)){
            ArrayList<String> adyacencias = this.obtenerNodosAdyacentes(estado, "_");
            for(String adyacente : adyacencias){
                if(!estado.contains(adyacente))
                    tabla.get("estados").get(0).add(adyacente);
            }
        }
        tabla.get("estados").get(0).add("n" + this.contador);
        this.KAFD.add("n" + this.contador);
        this.contador++;
        
        
        for ( int contadorMagico = 0; contadorMagico < tabla.get("estados").size();){
            ArrayList<String> listaDeEstados =  tabla.get("estados").get(contadorMagico);
            for ( String letra : this.sigmaAFND ){
                this.tabla.get(letra).add(new ArrayList<String>());
                for( String letraAdyacente : listaDeEstados){
                    for ( String estadosAdyacentes : this.obtenerNodosAdyacentes
                                                        (letraAdyacente, letra) ){
                        if(!this.tabla.get(letra).get(contadorMagico).contains(estadosAdyacentes))
                            this.tabla.get(letra).get(contadorMagico).add(estadosAdyacentes);
                    }
                }
                for(int l = 0; l<tabla.get(letra).get(contadorMagico).size(); l++  ){
                    String letraDeEstado = tabla.get(letra).get(contadorMagico).get(l);
                    for ( String estadoProximo : this.obtenerNodosAdyacentes(letraDeEstado, 
                            "_")){
                        if(!this.tabla.get(letra).get(contadorMagico).contains(estadoProximo))
                            this.tabla.get(letra).get(contadorMagico).add(estadoProximo);
                    }
                }
                if(this.tabla.get(letra).get(contadorMagico).isEmpty())
                    this.tabla.get(letra).get(contadorMagico).add("sumidero");
                if(this.obtenerEstadoSiEsQueEsteHaSidoAgregadoAnteriormente(this.tabla.get(letra).get(contadorMagico))
                        == null){
                    this.tabla.get(letra).get(contadorMagico).add("n" + this.contador);
                    this.KAFD.add("n" + this.contador);
                    this.contador++;
                    this.tabla.get("estados").add(this.tabla.get(letra).get(contadorMagico));
                }
                else{
                    this.tabla.get(letra).get(contadorMagico)
                            .add(this.obtenerEstadoSiEsQueEsteHaSidoAgregadoAnteriormente
                            (this.tabla.get(letra).get(contadorMagico)));
                }
                int pos = this.tabla.get(letra).get(contadorMagico).size()-1;
                this.deltaAFD.add(new Transicion
                                (listaDeEstados.get(listaDeEstados.size()-1), letra,
                                this.tabla.get(letra).get(contadorMagico).get(pos)));
                
            }
            contadorMagico++;
        }
        //Agregar principio y fin
        ArrayList<ArrayList<String>> listaDeEstados = this.tabla.get("estados");
        for(ArrayList<String> estados : listaDeEstados){
            if(estados.contains(this.primerEstadoInicial)){
                this.kInicioAFD = estados.get(estados.size()-1);
            }
            if(estados.contains(this.primerEstadoFinal)){
                this.FAFD.add(estados.get(estados.size()-1));
            }
        }
        
    }
    
    public void imprimirDelta(){
        for(Transicion t : this.deltaAFD)
            System.out.println("(" + t.getNodoSalida() + "," + t.getUnion()
            + "," + t.getNodoEntrada() + ")");
    }
    
    public void imprimirSigma(){
        System.out.printf("sigma: ");
        for(String s : this.sigmaAFD){
            System.out.printf(" " + s + " ");
        }
        System.out.println("");
    }
    
    public void imprimirFinal(){
        System.out.printf("F: ");
        for(String s : this.FAFD){
            System.out.printf(" " + s + " ");
        }
        System.out.println("");
    }
    
    public void imprimirK(){
        System.out.printf("K: ");
        for(String s : this.KAFD){
            System.out.printf(" " + s + " ");
        }
        System.out.println("");
    }
    
    public void imprimirS(){
        System.out.println("s: " + this.kInicioAFD);
    }
    
    public ArrayList<Transicion> getDeltaAFD() {
        return deltaAFD;
    }

    public void setDeltaAFD(ArrayList<Transicion> deltaAFD) {
        this.deltaAFD = deltaAFD;
    }

    public ArrayList<String> getKAFD() {
        return KAFD;
    }

    public void setKAFD(ArrayList<String> KAFD) {
        this.KAFD = KAFD;
    }

    public ArrayList<String> getSigmaAFD() {
        return sigmaAFD;
    }

    public void setSigmaAFD(ArrayList<String> sigmaAFD) {
        this.sigmaAFD = sigmaAFD;
    }

    public ArrayList<String> getFAFD() {
        return FAFD;
    }

    public void setFAFD(ArrayList<String> FAFD) {
        this.FAFD = FAFD;
    }

    public String getkInicioAFD() {
        return kInicioAFD;
    }

    public void setkInicioAFD(String kInicioAFD) {
        this.kInicioAFD = kInicioAFD;
    }

    public String getPrimerEstadoInicial() {
        return primerEstadoInicial;
    }

    public void setPrimerEstadoInicial(String primerEstadoInicial) {
        this.primerEstadoInicial = primerEstadoInicial;
    }

    public String getPrimerEstadoFinal() {
        return primerEstadoFinal;
    }

    public void setPrimerEstadoFinal(String primerEstadoFinal) {
        this.primerEstadoFinal = primerEstadoFinal;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<String, ArrayList<ArrayList<String>>> tabla) {
        this.tabla = tabla;
    }

    public ArrayList<String> getSigmaAFND() {
        return sigmaAFND;
    }

    public void setSigmaAFND(ArrayList<String> sigmaAFND) {
        this.sigmaAFND = sigmaAFND;
    }

    public ArrayList<String> getKAFND() {
        return KAFND;
    }

    public void setKAFND(ArrayList<String> KAFND) {
        this.KAFND = KAFND;
    }

    public ArrayList<Transicion> getDeltaAFND() {
        return deltaAFND;
    }

    public void setDeltaAFND(ArrayList<Transicion> deltaAFND) {
        this.deltaAFND = deltaAFND;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
    
}
