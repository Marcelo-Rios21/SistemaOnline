package tienda.descuentos.command;

import java.util.Objects;
import tienda.core.Component;
import tienda.modelo.Carrito;


public class AgregarAlCarritoCommand implements Command {
    private final Carrito carrito;
    private final Component producto;

    public AgregarAlCarritoCommand(Carrito carrito, Component producto) {
        this.carrito = Objects.requireNonNull(carrito, "El carrito no puede ser null.");
        this.producto = Objects.requireNonNull(producto, "El producto no puede ser null.");
    }

    @Override
    public void ejecutar() {
        carrito.agregar(producto);
    }
}
