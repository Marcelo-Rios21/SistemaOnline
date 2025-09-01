package tienda.controlador;

import java.math.BigDecimal;
import java.util.Objects;
import tienda.descuentos.command.AgregarAlCarritoCommand;
import tienda.descuentos.command.EliminarDelCarritoCommand;
import tienda.modelo.Carrito;
import tienda.modelo.Producto;
import tienda.vista.CarritoView;

public class CarritoController {
    private final Carrito carrito;
    private final CarritoView view;

    public CarritoController(Carrito carrito, CarritoView view) {
        this.carrito = Objects.requireNonNull(carrito, "El carrito no puede ser null.");
        this.view = Objects.requireNonNull(view, "La view no puede ser null.");
    }

    public void agregarProducto(Producto p) {
        Objects.requireNonNull(p, "El producto no puede ser null.");
        new AgregarAlCarritoCommand(carrito, p).ejecutar();
        view.mostrarCarrito(carrito);
    }

    public void eliminarProductoPorNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null.");
        new EliminarDelCarritoCommand(carrito, nombre).ejecutar();
        view.mostrarCarrito(carrito);
    }

    public void mostrarCarrito() {
        view.mostrarCarrito(carrito);
    }

    public BigDecimal totalCarrito() {
        BigDecimal total = carrito.total();
        view.mostrarTotal(total);
        return total;
    }

    public Carrito getCarrito() {
        return carrito;
    }   
}
