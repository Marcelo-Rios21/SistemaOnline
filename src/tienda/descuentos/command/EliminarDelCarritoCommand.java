package tienda.descuentos.command;

import java.util.Objects;
import tienda.carrito.Carrito;

public class EliminarDelCarritoCommand implements Command {
    private final Carrito carrito;
    private final String nombreProducto;

    public EliminarDelCarritoCommand(Carrito carrito, String nombreProducto) {
        this.carrito = Objects.requireNonNull(carrito, "El carrito no puede ser null.");
        this.nombreProducto = Objects.requireNonNull(nombreProducto, "El nombre del producto no puede ser null.");
    }
    
    @Override
    public void ejecutar() {
        carrito.eliminarPorNombre(nombreProducto);
    }
}
