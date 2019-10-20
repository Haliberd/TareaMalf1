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
        System.out.printf("ER: ");
        expReg.imprimirExpresionRegular();
        System.out.printf("\n\n");
        
        //Creacion de AFND
        AFND afnd = new AFND(expReg.getExpresionFormateada());
        afnd.crearAFND();
        afnd.imprimirK();
        afnd.imprimirSigma();
        afnd.imprimirEstadoInicial();
        afnd.imprimirFinal();
        afnd.imprimirDelta();      
        System.out.printf("\n\n");
        //Creacion de AFD
        AFD afd = new AFD(afnd);
        afd.transformarAFNDaAFD();
        System.out.println("AFD");
        afd.imprimirK();
        afd.imprimirSigma();
        afd.imprimirS();
        afd.imprimirFinal();
        afd.imprimirDelta();
        System.out.printf("\n\n");
        
        System.out.println("Ocurrencias: ");
        Buscacalces bc = new Buscacalces(afd, cadena);
        
    }
    
}
