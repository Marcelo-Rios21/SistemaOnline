package tienda.vista;

import java.math.BigDecimal;
import tienda.core.Component;
import tienda.modelo.Carrito;

public class CarritoView {
    
    public void mostrarCarrito(Carrito carrito) {
        if (carrito == null || carrito.isEmpty()) {
            System.out.println("El carrito esta vacio.");
            return;
        }
        System.out.println("\n--- Carrito de compras ---");
        int i = 1;
        for (Component c : carrito.items()) {
            System.out.println((i++) + ") " 
                                     + c.getNombre()
                                     + " | Categoria: " + c.getCategoria()
                                     + " | Precio: " + c.getPrecioFinal().toPlainString());
        }
    }

    public void mostrarTotal(BigDecimal total) {
        BigDecimal valor = (total == null) ? BigDecimal.ZERO : total;
        System.out.println("Total del carrito: " + valor.toPlainString());
    }
}
