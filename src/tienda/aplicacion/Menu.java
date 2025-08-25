package tienda.aplicacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tienda.carrito.Carrito;
import tienda.core.Component;
import tienda.descuentos.DiscountCode;
import tienda.descuentos.DiscountManager;
import tienda.descuentos.command.AgregarAlCarritoCommand;
import tienda.descuentos.command.AplicarDescuentoCommand;
import tienda.descuentos.command.EliminarDelCarritoCommand;
import tienda.producto.Producto;

public class Menu {
    private static final Scanner input = new Scanner(System.in);
    private static final DiscountManager DM = DiscountManager.getInstance();
    private static final Carrito CARRITO = new Carrito();

    private Menu(){
    
    }

    public static void mostrar() {
        int opcion;

        do {
            imprimirMenu();
            opcion = leerEntero("Seleccione una opcion:");
            switch (opcion) {
                case 1 -> aplicarDescuento();
                case 2 -> mostrarCodigosDisponibles();
                case 3 -> agregarProductoCarrito();
                case 4 -> eliminarProductoCarrito();
                case 5 -> listarCarrito();
                case 6 -> totalCarrito();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private static void imprimirMenu() {
        System.out.println("\n--- MENÚ TIENDA ---");
        System.out.println("1. Aplicar descuento (Producto puntual)");
        System.out.println("2. Ver codigos disponibles");
        System.out.println("3. Agregar producto al carrito");
        System.out.println("4. Eliminar producto del carrito");
        System.out.println("5. Listar productos del carrito");
        System.out.println("6. Total del carrito");
        System.out.println("0. Salir");
    }

    private static void aplicarDescuento() {
        String nombre = leerTexto("Ingrese nombre del producto: ");
        String categoria = leerTexto("Ingrese categoria del producto: ");
        BigDecimal precioBase = leerBigDecimal("Ingrese precio base: ");
        String entrada = leerTexto("Ingrese codigo(s) separados por coma (Ej: PERCENT10, FLAT500)");
        
        List<String> codigos = parsearCodigos(entrada);
        
        try {
            Component p = new Producto(nombre, categoria, precioBase);
            AplicarDescuentoCommand cmd = codigos.size() == 1 ? new AplicarDescuentoCommand(DM, p, codigos.get(0))
                                                              : new AplicarDescuentoCommand(DM, p, codigos);
            cmd.ejecutar();
            BigDecimal precioFinal = cmd.getResultado().orElse(precioBase);
            mostrarResultado(p, precioBase, precioFinal);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarCodigosDisponibles() {
        System.out.println("Codigos disponibles: ");
        for (DiscountCode code : DiscountCode.values()) {
            switch (code) {
                case PERCENT10 ->  System.out.println("- PERCENT10  (10% off)");
                case PERCENT20 -> System.out.println("- PERCENT20  (20% off)");
                case FLAT500 -> System.out.println("- FLAT500    (resta 500)");
                default -> System.out.println("- " + code.name());
                    
            }
        }
    }

    private static void agregarProductoCarrito() {
        String nombre = leerTexto("Ingrese nombre del producto: ");
        String categoria = leerTexto("Ingrese categoria del producto: ");
        BigDecimal precioBase = leerBigDecimal("Ingrese precio base: ");

        try {
            Component p = new Producto(nombre, categoria, precioBase);
            new AgregarAlCarritoCommand(CARRITO, p).ejecutar();
            System.out.println("Producto agregado con exito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " +e.getMessage());
        }
    }

    private static void eliminarProductoCarrito() {
        String nombre = leerTexto("Ingrese nombre dle producto a eliminar: ");
        int antes = CARRITO.size();
        new EliminarDelCarritoCommand(CARRITO, nombre).ejecutar();
        int despues = CARRITO.size();
        System.out.println((despues < antes) ? "Producto eliminado." : "No se encontro el producto."); 
    }

    private static void listarCarrito() {
        if (CARRITO.isEmpty()) {
            System.out.println("El carrito está vacio.");
            return;
        }
        System.out.println("\n--- Carrito ---");
        int i = 1;
        for (Component c : CARRITO.items()) {
            System.out.println(i++ + ") " + c.getNombre() 
            + " | Categoria: " + c.getCategoria()
            + " | Precio: " + c.getPrecioFinal().toPlainString());
        }
    } 

    private static void totalCarrito() {
        BigDecimal total = CARRITO.total();
        System.out.println("El total del carrito: " + total.toPlainString());
    }

    private static List<String> parsearCodigos(String entrada) {
        List<String> out = new ArrayList<>();
        if (entrada == null) return out;

        for (String s : entrada.split(",")) {
            String t = s.trim();
            if (t.isEmpty()) continue;

            DiscountCode code = DiscountCode.from(t);
            if (code == null) continue; 

            String canonical = code.name(); 

            boolean repetido = false;
            for (String ya : out) {
                if (ya.equals(canonical)) { 
                    repetido = true; break; 
                }
            }
            if (!repetido) out.add(canonical);
        }
        return out;
    }

    private static void mostrarResultado(Component p, BigDecimal base, BigDecimal fin) {
        BigDecimal ahorro = base.subtract(fin);
        if (ahorro.compareTo(BigDecimal.ZERO) < 0) ahorro = BigDecimal.ZERO;
        System.out.println("\nResultado:");
        System.out.println("- Producto: " + p.getNombre() + " (Categoria: " + p.getCategoria() + ")");
        System.out.println("- Base : " + base.toPlainString());
        System.out.println("- Ahorro : " + ahorro.toPlainString());
        System.out.println("- Final : " + fin.toPlainString());

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

    public static String leerTexto(String msg) {
        while (true) { 
            System.out.println(msg);
            String s = input.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("El texto no puede estar vacio.");
        }
    }
}
