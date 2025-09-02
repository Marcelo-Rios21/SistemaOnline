package tienda.aplicacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import tienda.controlador.CarritoController;
import tienda.controlador.DescuentosController;
import tienda.controlador.ProductosController;
import tienda.descuentos.DiscountCode;
import tienda.modelo.Producto;
import tienda.vista.CarritoView;

public class Menu {
    private static final Scanner input = new Scanner(System.in);

    private final ProductosController productosCtl;
    private final CarritoController carritoCtl;
    private final DescuentosController descuentosCtl;

    public Menu(ProductosController productosCtl, CarritoController carritoCtl, DescuentosController descuentosCtl) {
        this.productosCtl = Objects.requireNonNull(productosCtl);
        this.carritoCtl = Objects.requireNonNull(carritoCtl);
        this.descuentosCtl = Objects.requireNonNull(descuentosCtl);
    }

    public void mostrar() {
        int opcion;

        do {
            imprimirMenu();
            opcion = leerEntero("Seleccione una opcion:");
            switch (opcion) {
                case 1 -> vistaProductos();
                case 2 -> vistaCarrito();
                case 3 -> vistaDescuentos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private static void imprimirMenu() {
        System.out.println("\n--- MENÚ TIENDA ---");
        System.out.println("1. Productos");
        System.out.println("2. Carrito");
        System.out.println("3. Descuentos");
        System.out.println("0. Salir");
    }

    private void vistaProductos() {
        int opcion;
        do { 
            System.out.println("\n--- Productos ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear producto");
            System.out.println("3. Detalle por nombre");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1 -> productosCtl.listarProductos();
                case 2 -> crearProductoFlow();
                case 3 -> {
                    String nombre = leerTexto("Nombre del producto:");
                    productosCtl.mostrarDetalleProducto(nombre);
                }
                case 0 -> {}
                default -> System.out.println("Opcion invalida."); 
            }
        } while (opcion != 0);
    }

    private void vistaCarrito() {
        int opcion;
        do { 
            System.out.println("\n--- Carrito ---");
            System.out.println("1. Ver carrito");
            System.out.println("2. Agregar producto por nombre");
            System.out.println("3. Eliminar producto por nombre");
            System.out.println("4. Total (sin descuentos)");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1 -> carritoCtl.mostrarCarrito();
                case 2 -> {
                    String nombre = leerTexto("Nombre del producto a agregar: ");
                    Producto p = productosCtl.buscarPorNombre(nombre);
                    if (p != null) carritoCtl.agregarProducto(p);
                    else System.out.println("Producto no encontrado.");
                }
                case 3 -> {
                    String nombre = leerTexto("Nombre del producto a eliminar: ");
                    carritoCtl.eliminarProductoPorNombre(nombre);
                }
                case 4 -> carritoCtl.totalCarrito();
                case 0 -> {}
                default -> System.out.println("Opcion invalida."); 
            }
        } while (opcion != 0);
    }

    private void vistaDescuentos() {
        int opcion;
        do { 
            System.out.println("\n--- Descuentos ---");
            System.out.println("1. Ver codigos disponibles");
            System.out.println("2. Aplicar a producto puntual");
            System.out.println("3. Aplicar a TODO el carrito");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1 -> descuentosCtl.mostrarCodigosDisponibles();
                case 2 -> aplicarDescuentoProductoFlow();
                case 3 -> aplicarDescuentosCarritoFlow();
                case 0 -> {}
                default -> System.out.println("Opcion invalida."); 
            }
        } while (opcion != 0);
    }

    private void crearProductoFlow() {
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoria: ");
        BigDecimal precio = leerBigDecimal("Precio base: ");
        productosCtl.crearProducto(nombre, categoria, precio);
        System.out.println("Producto creado.");
    }

    private void aplicarDescuentoProductoFlow() {
        String nombre = leerTexto("Producto: ");
        Producto p = productosCtl.buscarPorNombre(nombre);
        if (p == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        List<String> codigos = leerCodigosNormalizados();
        if (codigos.isEmpty()) {
            System.out.println("No se ingresaron codigos validos.");
            return;
        }
        if (codigos.size() == 1) {
            descuentosCtl.aplicarDescuento(p, codigos.get(0));
        } else {
            descuentosCtl.aplicarDescuentos(p, codigos);
        }
    }

    private void aplicarDescuentosCarritoFlow() {
        List<String> codigos = leerCodigosNormalizados();
        if (codigos.isEmpty()) {
            System.out.println("No se ingresaron codigos validos.");
            return;
        }
        descuentosCtl.aplicarDescuentosAlCarrito(
                carritoCtl.getCarrito(),
                codigos,
                new CarritoView()
        );
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

    private static String leerTexto(String msg) {
        while (true) { 
            System.out.println(msg);
            String s = input.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("El texto no puede estar vacio.");
        }
    }

    private List<String> leerCodigosNormalizados() {
        String entrada = leerTexto("Ingrese codigos separados por coma (Ej: PERCENT10, FLAT500): ");
        if (entrada == null || entrada.isBlank()) return Collections.emptyList();

        LinkedHashSet<String> set = Arrays.stream(entrada.split(","))
                .map(t -> t.trim().toUpperCase())
                .map(DiscountCode::from)         
                .filter(Objects::nonNull)
                .map(DiscountCode::name)         
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new ArrayList<>(set);
    }
}
