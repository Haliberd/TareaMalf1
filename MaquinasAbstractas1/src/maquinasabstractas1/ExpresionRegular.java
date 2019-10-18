/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinasabstractas1;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Maximo Hernandez
 * Esta clase permite formatear la expresion entregada por el usuario para que pueda ser leida por la clase que genera AFNDs
 */
class ExpresionRegular {
    
    private String er;
    private String[] cadena;
    private ArrayList<String> expresionFormateada;
    
    private String[] ordenDePrecedencias;
            
    
    public ExpresionRegular(String er) {
        
        this.er = er;
        
        //Formatear la expresion regular para separarla por caracteres
        this.cadena = new String[er.length()];
        this.cadena = er.split("");
        
        this.expresionFormateada = new ArrayList<>();
        
        //Orden de precedencias para poder aplicar el algoritmo Shuting Yard correctamente dentro de la cadena.
        this.ordenDePrecedencias = new String[] {"(1", "|2", ".3", "*4", "_5"};
        
        this.algoritmoShutingYard();
        
    }
    
    /*Esta funcion se encarga de aplicar una version del algoritmo Shuting Yard (vease https://es.wikipedia.org/wiki/Algoritmo_shunting_yard para más detalles)
    * la cual se encuentra modificada para este caso.
    */
    private void algoritmoShutingYard(){
        
        Stack<String> stack = new Stack<>();
        
        for(int e = 0; e<this.cadena.length; e++){
            
            /*Transformaremos los caracteres de tipo String a Integer, para poder evaluarlos usando los valores del codigo ASCII.
            *Cabe destacar que este codigo no funcionara para caracteres que no pertenezcan al codigo ASCII.
            */
            
            /*Otro metodo posible es usar getBytes() y usar el valor en la casilla 0, dado a que trabajamos con un solo caracter y por lo tanto entrega
            *un solo valor en la cadena byte[].
            */
            int nCaracter = (int) this.cadena[e].charAt(0);
            
            //Para poder determinar si es que nCaracter pertenece a alguno de los rangos en los que se encuentran los caracteres de nuestro alfabeto.
            if ((nCaracter > 64 && nCaracter <  91) || (nCaracter > 96 && nCaracter < 123) || (nCaracter > 47 && nCaracter < 58) || nCaracter == 95 || nCaracter == 126) {
                this.expresionFormateada.add(cadena[e]);
            }
            else{
                //Para proceder a ingresar datos a la pila
                if(cadena[e].equals("(") || stack.size() == 0)
                    stack.push(cadena[e]);
                else{
                    //Para retirar una expresion de tipo (S) donde S puede ser cualquier combinacion valida cuyos elementos pertenecen a Sigma o son alguno de los operadores permitidos.
                    if(cadena[e].equals(")")){
                        while(!stack.peek().equals("("))
                            this.expresionFormateada.add(stack.pop());
                        stack.pop();
                    }
                    else{
                        //Si es que el elemento no es un ")"
                        boolean bandera = true;
                        while(bandera && stack.size() > 0){
                            if(this.busquedaPrecedencia(stack.peek()) < this.busquedaPrecedencia(cadena[e])){
                                stack.push(cadena[e]);
                                bandera = false;
                            }
                            else{
                                //this.busquedaPrecedencia(stack.peek()) < this.busquedaPrecedencia(cadena[e])
                                this.expresionFormateada.add(stack.pop());
                            }
                        }
                        if(stack.isEmpty()){
                            stack.push(cadena[e]);
                        }
                    }
                    
                }
                            
            }
            
            
        }
        while(!stack.isEmpty())
            this.expresionFormateada.add(stack.pop());
        
    }
    
    /*
    * Funcion encargada de entregar el valor de la precedencia obtenido del char en la posicion 1 de nuestra lista de precedencias.
    */
    private int busquedaPrecedencia( String e ){
        for(String s : this.ordenDePrecedencias){
            if(s.charAt(0) == e.charAt(0))
                return s.charAt(1) - '0';
        }
        return -1;
    }
    
    public void imprimirExpresionRegular(){
        for(String s : this.expresionFormateada)
            System.out.printf("%s", s);
        System.out.printf("\n");
    }
    
    public String getEr() {
        return er;
    }

    public void setEr(String er) {
        this.er = er;
    }

    public String[] getCadena() {
        return cadena;
    }

    public void setCadena(String[] cadena) {
        this.cadena = cadena;
    }

    public ArrayList<String> getExpresionFormateada() {
        return expresionFormateada;
    }

    public void setExpresionFormateada(ArrayList<String> expresionFormateada) {
        this.expresionFormateada = expresionFormateada;
    }
    
}
