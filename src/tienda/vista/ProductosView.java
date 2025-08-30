package tienda.vista;

import java.util.List;
import tienda.modelo.Producto;

public class ProductosView {
    
    public void mostrarProductos(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        System.out.println("\n--- Lista de productos ---");
        int i = 1;
        for (Producto p : productos) {
            System.out.println((i++) + ") " + p.getNombre()
                                     + " | Categoria: " + p.getCategoria()
                                     + " | Precio base: " + p.getPrecioBase().toPlainString());
        }
    }

    public void mostrarDetalle(Producto producto) {
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("\n--- Detalle del producto ---");
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Categoria: " + producto.getCategoria());
        System.out.println("Precio: " + producto.getPrecioBase().toPlainString());
    }
}
