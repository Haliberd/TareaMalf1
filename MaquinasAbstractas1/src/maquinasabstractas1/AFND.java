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
 * Clase encargada de generar el AFND recibiendo la informacion que contiene la clase ExpresionRegular
 */
class AFND {
    
    private ArrayList<String> k;
    private ArrayList<String> sigma;
    private ArrayList<Transicion> delta;
    
    private ArrayList<String> expresionFormateada;
    
    private int nNodo;

    public AFND(ArrayList<String> expresionFormateada) {
        
        this.k = new ArrayList<>();
        this.sigma = new ArrayList<>();
        this.delta = new ArrayList<>();
        this.expresionFormateada = expresionFormateada;
        this.nNodo = 0;
        
    }
    
    public void crearAFND(){
        
        for(int e = 0; e < this.expresionFormateada.size(); e++){
            int nCaracter = (int) this.expresionFormateada.get(e).charAt(0);
            
            //Si es que el caracter pertenece al rango de letras que aparecen en nuestro abecedario permitido
            if ((nCaracter > 64 && nCaracter <  91) || (nCaracter > 96 && nCaracter < 123) || (nCaracter > 47 && nCaracter < 58) || nCaracter == 95 || nCaracter == 126) {
                if(!this.sigma.contains(this.expresionFormateada.get(e)))
                    this.sigma.add(this.expresionFormateada.get(e));
                /*if(!this.delta.isEmpty())
                    this.crearTransicionEpsilon();*/
                this.crearTransicion( this.expresionFormateada.get(e) );
            }
            else{
                
                
            
                switch(this.expresionFormateada.get(e)){
                    case ".":
                        //Verificamos que el nodo que sale no llega a ninguna parte.
                        String nodoSinEntrada = "";
                        for(int a = this.delta.size()-1; a>=0 && nodoSinEntrada.length() == 0; a--){
                            Transicion t = this.delta.get(a);
                            if(!this.comprobarNodoPoseeEntrada(t.getNodoSalida()))
                                nodoSinEntrada = t.getNodoSalida();
                        }
                        
                        String nodoSinSalida = "";
                        //Para evitar las clausuras de kleen, usamos un auxiliar para no concatenar un elemento si es que este pertenece a una clausura
                        String nodoAuxiliar = "";
                        for(int a = this.delta.size()-1; a>=0 && nodoSinSalida.length() == 0; a--){
                            Transicion t = this.delta.get(a);
                            if(!this.comprobarNodoPoseeSalida(t.getNodoEntrada())){
                                if(nodoAuxiliar.length() == 0){
                                    nodoAuxiliar = t.getNodoEntrada();
                                }
                                else{
                                    if(!t.getNodoEntrada().equals(nodoAuxiliar)){
                                        nodoSinSalida = t.getNodoEntrada();
                                    }
                                }
                            }
                        }
                        
                        this.crearTransicion(nodoSinSalida, "_", nodoSinEntrada);
                        
                        break;
                    case "|":
                        //Para la instruccion de o, necesitamos encontrar los dos nodos que vamos a conectar.
                        
                        String nodoSinEntrada1 = "";
                        String nodoSinEntrada2 = "";
                        for(int a = this.delta.size()-1; a>=0 && nodoSinEntrada2.length() == 0; a--){
                            Transicion t = this.delta.get(a);
                            if(!this.comprobarNodoPoseeEntrada(t.getNodoSalida())){
                                if(nodoSinEntrada1.length() == 0){
                                    nodoSinEntrada1 = t.getNodoSalida();
                                }
                                else{
                                    if(!t.getNodoSalida().equals(nodoSinEntrada1)){
                                        nodoSinEntrada2 = t.getNodoSalida();
                                    }
                                }
                            }
                        }
                        
                        String nodoSinSalida1 = "";
                        String nodoSinSalida2 = "";
                        for(int a = this.delta.size()-1; a>=0 && nodoSinSalida2.length() == 0; a--){
                            Transicion t = this.delta.get(a);
                            if(!this.comprobarNodoPoseeSalida(t.getNodoEntrada())){
                                if(nodoSinSalida1.length() == 0){
                                    nodoSinSalida1 = t.getNodoEntrada();
                                }
                                else{
                                    if(!t.getNodoEntrada().equals(nodoSinSalida1)){
                                        nodoSinSalida2 = t.getNodoEntrada();
                                    }
                                }
                            }
                        }
                        
                   
                        this.crearTransicion("n" + this.nNodo, "_", nodoSinEntrada1);
                        this.crearTransicion("n" + this.nNodo, "_", nodoSinEntrada2);
                        
                        this.k.add("n" + this.nNodo);
                        
                        this.nNodo++;
                        this.crearTransicion(nodoSinSalida1, "_", "n" + this.nNodo );
                        this.crearTransicion(nodoSinSalida2, "_", "n" + this.nNodo );
                        
                        this.k.add("n" + this.nNodo);
                        
                        this.nNodo++;
                        
                        break;
                    case "*":
                        
                            switch(this.expresionFormateada.get(e-1)){
                                case ".":
                                    //Verificamos que el nodo que sale no llega a ninguna parte.
                                    nodoSinEntrada = "";
                                    for(int a = this.delta.size()-1; a>=0 && nodoSinEntrada.length() == 0; a--){
                                        Transicion t = this.delta.get(a);
                                        if(!this.comprobarNodoPoseeEntrada(t.getNodoSalida()))
                                            nodoSinEntrada = t.getNodoSalida();
                                    }

                                    nodoSinSalida = "";
                                   
                                    for(int a = this.delta.size()-1; a>=0 && nodoSinSalida.length() == 0; a--){
                                        Transicion t = this.delta.get(a);
                                        if(!this.comprobarNodoPoseeSalida(t.getNodoEntrada())){
                                            
                                                    nodoSinSalida = t.getNodoEntrada();
                                                
                                            
                                        }
                                    }

                                    this.crearTransicion(nodoSinSalida, "_", nodoSinEntrada);
                                    
                                    String nodoInicio = "n" + this.nNodo;
                                    
                                    this.k.add(nodoInicio);
                                    
                                    this.nNodo++;
                                    this.crearTransicion(nodoInicio, "_", nodoSinEntrada);
                                    this.crearTransicion(nodoSinSalida, "_", "n"+this.nNodo);
                                    this.crearTransicion(nodoInicio, "_", "n"+this.nNodo);
                                    this.k.add("n"+this.nNodo);
                                    this.nNodo++;
                                    
                                    break;
                                case "|":
                                    
                                    nodoSinSalida = this.listarEstadosSinSalida().get(0);
                                    nodoSinEntrada = this.listarEstadosSinEntrada().get(0);
                                    this.crearTransicion(nodoSinSalida, "_", nodoSinEntrada);
                                    
                                    nodoInicio = "n" + this.nNodo;
                                    
                                    this.k.add(nodoInicio);
                                    
                                    this.nNodo++;
                                    this.crearTransicion(nodoInicio, "_", nodoSinEntrada);
                                    this.crearTransicion(nodoSinSalida, "_", "n"+this.nNodo);
                                    this.crearTransicion(nodoInicio, "_", "n"+this.nNodo);
                                    this.k.add("n"+this.nNodo);
                                    this.nNodo++;
                                    
                                    break;
                                    
                                default:
                                    nCaracter = (int) this.expresionFormateada.get(e-1).charAt(0);
                                    if((nCaracter > 64 && nCaracter <  91) || (nCaracter > 96 && nCaracter < 123)){
                                        Transicion tUltima = this.delta.get(this.delta.size()-1);
                                        this.crearTransicion(tUltima.getNodoEntrada(), "_", tUltima.getNodoSalida());
                                        nodoInicio = "n" + this.nNodo;
                                    
                                        this.k.add(nodoInicio);
                                        this.nNodo++;
                                        
                                        this.crearTransicion(nodoInicio, "_", tUltima.getNodoSalida());
                                        this.crearTransicion(tUltima.getNodoEntrada(), "_", "n"+this.nNodo);
                                        this.crearTransicion(nodoInicio, "_", "n"+this.nNodo);
                                        this.k.add("n"+this.nNodo);
                                        this.nNodo++;
                                    }
                                    break;
                            }
                        
                        break;
                    case "~":
                        this.k.add("n"+this.nNodo);
                        this.nNodo++;
                        this.k.add("n"+this.nNodo);
                        break;
                }
            }
        }
        
    }
    
    //Lista los estados que no poseen una entrada, usando la funcion comprobarNodoPoseeEntrada
    public ArrayList<String> listarEstadosSinEntrada(){
        ArrayList <String> listadoDeEstadosSinEntrada = new ArrayList<>();
        for(String estado : this.k){
            if(!this.comprobarNodoPoseeEntrada(estado))
                listadoDeEstadosSinEntrada.add(estado);
                
        }
        return listadoDeEstadosSinEntrada;
    }
    
    //Lista los estados que no poseen una salida, usando la funcion comprobarNodoPoseeSalida
    public ArrayList<String> listarEstadosSinSalida(){
        ArrayList <String> listadoDeEstadosSinSalida = new ArrayList<>();
        for(String estado : this.k){
            if(!this.comprobarNodoPoseeSalida(estado))
                listadoDeEstadosSinSalida.add(estado);
                
        }
        return listadoDeEstadosSinSalida;
    }
    
    //Comprueba si es que el nodo posee una entrada, buscando en las transiciones si es que por ejemplo, el nodo a recibe alguna arista de algun otro nodo.
    private boolean comprobarNodoPoseeEntrada( String nodo ){
        for(Transicion t : this.delta){
            if(t.getNodoEntrada().equals(nodo))
                return true;
        }
        return false;
    }
    
    //Comprueba si es que el nodo posee una salida, buscando en las transiciones si es que por ejemplo, el nodo a sale hacia otro nodo por alguna arista.
    private boolean comprobarNodoPoseeSalida( String nodo ){
        for(Transicion t : this.delta){
            if(t.getNodoSalida().equals(nodo))
                return true;
        }
        return false;
    }
    
    //Sea n la abreviatura de nodo y nNodo el contador de los numeros de los nodos
    private void crearTransicion(String caracter) {
        if(this.delta.size()==0){
            //Creacion del nodo inicio
            String nodoInicio = "n" + Integer.toString(this.nNodo);
            this.k.add(nodoInicio);
            this.nNodo++;

            //Creacion del nodo final
            String nodoFinal = "n" + Integer.toString(this.nNodo);
            this.k.add(nodoFinal);
            this.nNodo++;


            //Creacion de la transicion usando la variable caracter, que va desde el nodoInicio hasta el nodoFinal.
            Transicion transicion = new Transicion(nodoInicio, caracter, nodoFinal);
            this.delta.add(transicion);
        }
        else{
            //Creacion del nodo inicio
            String nodoInicio = "n" + Integer.toString(this.nNodo);
            this.k.add(nodoInicio);
            this.nNodo++;

            //Creacion del nodo final
            String nodoFinal = "n" + Integer.toString(this.nNodo);
            this.k.add(nodoFinal);
            this.nNodo++;


            //Creacion de la transicion usando la variable caracter, que va desde el nodoInicio hasta el nodoFinal.
            Transicion transicion = new Transicion(nodoInicio, caracter, nodoFinal);
            this.delta.add(transicion);
        }
        
    }
    
    //Funcion desechada, se ocupa un metodo que se incluye dentro de los metodos que poseen switch.
    private void crearTransicionEpsilon(){
        Transicion t = this.delta.get(this.delta.size()-1);
        this.delta.add((new Transicion(t.getNodoEntrada(), "_", "n"+this.nNodo)));
    }
    
    private void crearTransicion(String nodoSalida, String caracter, String nodoEntrada){
        
        Transicion transicion = new Transicion(nodoSalida, caracter, nodoEntrada);
        this.delta.add(transicion);
        
    }
    
    public void imprimirDelta(){
        System.out.printf("Delta: [\n");
        for(Transicion t : this.delta){
            System.out.printf("(%s, %s, %s),\n", t.getNodoSalida(), t.getUnion(), t.getNodoEntrada());
        }
        Transicion d = this.delta.get(this.delta.size()-1);
        System.out.printf("(%s, %s, %s)]\n", d.getNodoSalida(), d.getUnion(), d.getNodoEntrada());
    }
    
    public void imprimirK(){
        System.out.printf("K: [");
        for(String s : this.k)
            System.out.printf("%s, ", s);
        System.out.printf("%s]\n", this.k.get(this.k.size()-1));
    }
    
    public void imprimirSigma(){
            System.out.printf("Sigma: [");
        for(String s : this.sigma)
            System.out.printf("%s, ", s);
        System.out.printf("%s]\n", this.sigma.get(this.sigma.size()-1));
    }
    
    public void imprimirEstadoInicial(){
        System.out.printf("s: %s\n", this.listarEstadosSinEntrada().get(0));
    }
    
    public void imprimirFinal(){
        ArrayList<String> listadoK = this.listarEstadosSinSalida();
        System.out.printf("F: [");
        for(String t : listadoK.subList(0, listadoK.size()-1)){
            System.out.printf("%s, ", t);
        }
        System.out.printf("%s]\n", listadoK.get(listadoK.size()-1));
    }

    public ArrayList<String> getK() {
        return k;
    }

    public void setK(ArrayList<String> k) {
        this.k = k;
    }

    public ArrayList<String> getSigma() {
        return sigma;
    }

    public void setSigma(ArrayList<String> sigma) {
        this.sigma = sigma;
    }

    public ArrayList<Transicion> getDelta() {
        return delta;
    }

    public void setDelta(ArrayList<Transicion> delta) {
        this.delta = delta;
    }   

    
    
}
