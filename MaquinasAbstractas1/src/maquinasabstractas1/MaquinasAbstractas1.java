/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinasabstractas1;

import java.util.Scanner;

/**
 *
 * @author Maximo Hernandez
 */
public class MaquinasAbstractas1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Lectura por consola de los parametros de entrada, expresion regular y la cadena de texto
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Ingrese la expresion regular: ");
        String er = scanner.nextLine();
        
        System.out.println("Ingrese la cadena a revisar: ");
        String cadena = scanner.nextLine();
        
        //Creacion de la expresion regular
        ExpresionRegular expReg = new ExpresionRegular(er);
        
        //Imprimir la expresion regular
        expReg.imprimirExpresionRegular();
        
        //Creacion del AFND
        AFND afnd = new AFND(expReg.getExpresionFormateada());
        afnd.crearAFND();
        afnd.imprimirDelta();
        afnd.imprimirK();
        afnd.imprimirSigma();
        afnd.imprimirEstadoInicial();
        afnd.imprimirFinal();
        
        AFD afd = new AFD(afnd);
        afd.transformarAFNDaAFD();
        afd.imprimirK();
        afd.imprimirSigma();
        afd.imprimirS();
        afd.imprimirFinal();
        afd.imprimirDelta();
        
        
        
        
    }
    
}
