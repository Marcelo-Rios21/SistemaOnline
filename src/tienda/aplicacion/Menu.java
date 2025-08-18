package tienda.aplicacion;

import java.math.BigDecimal;
import java.util.Scanner;
import tienda.descuentos.DiscountManager;

public class Menu {
    private static final Scanner input = new Scanner(System.in);
    private static final DiscountManager DM = DiscountManager.getInstance();

    private Menu(){
    
    };

    public static void mostrar() {
        int opcion;

        do {
            imprimirMenu();
            opcion = leerEntero("Seleccione una opcion:");
            switch (opcion) {
                case 1 -> aplicarDescuento();
                case 2 -> mostrarCodigosDisponibles();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private static void imprimirMenu() {
        System.out.println("\n--- MENÚ DE DESCUENTOS ---");
        System.out.println("1. Aplicar descuento");
        System.out.println("2. Ver codigos disponibles");
        System.out.println("0. Salir");
    }

    private static void aplicarDescuento() {
        BigDecimal precio = leerBigDecimal("Ingrese precio base: ");
        System.out.println("Ingrese codigo promocional: ");
        String codigo = input.nextLine();
        
        try {
            BigDecimal finalPrecio = DM.aplicarDescuento(precio, codigo);
            System.out.println("Precio final : " + finalPrecio.toPlainString());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarCodigosDisponibles() {
        System.out.println("Codigos: ");
        System.out.println("- PERCENT10  (10% off)");
        System.out.println("- PERCENT20  (20% off)");
        System.out.println("- FLAT500    (resta 500)");
    }

    private static int leerEntero(String msg) {
        while (true) {
            System.out.println(msg);
            String s = input.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static BigDecimal leerBigDecimal(String msg) {
        while (true) {
            System.out.println(msg);
            String s = input.nextLine().trim().replace(",", ".");
            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}
