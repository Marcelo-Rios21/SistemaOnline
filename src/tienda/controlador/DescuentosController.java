package tienda.controlador;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import tienda.descuentos.DiscountManager;
import tienda.descuentos.command.AplicarDescuentoCommand;
import tienda.modelo.Producto;
import tienda.vista.DescuentosView;

public class DescuentosController {
    private final DiscountManager dm;
    private final DescuentosView view;

    public DescuentosController(DiscountManager dm, DescuentosView view) {
        this.dm = Objects.requireNonNull(dm, "DiscountManager no puede ser null.");
        this.view = Objects.requireNonNull(view, "La view no puede ser null.");
    }

    public void mostrarCodigosDisponibles() {
        view.mostrarCodigosDisponibles();
    }

    public BigDecimal aplicarDescuento(Producto producto, String codigo) {
        Objects.requireNonNull(producto, "El producto no puede ser null.");
        Objects.requireNonNull(codigo, "El codigo no puede ser null.");

        AplicarDescuentoCommand cmd = new AplicarDescuentoCommand(dm, producto, codigo);
        cmd.ejecutar();

        BigDecimal base = producto.getPrecioBase();
        BigDecimal fin = cmd.getResultado().orElse(base);
        
        view.mostrarResultado(producto, base, fin);
        return fin;
    }

    public BigDecimal aplicarDescuentos(Producto producto, List<String> codigos) {
        Objects.requireNonNull(producto, "El producto no puede ser null.");
        List<String> lista = (codigos == null) ? Collections.emptyList() : codigos;

        AplicarDescuentoCommand cmd = new AplicarDescuentoCommand(dm, producto, lista);
        cmd.ejecutar();

        BigDecimal base = producto.getPrecioBase();
        BigDecimal fin = cmd.getResultado().orElse(base);

        view.mostrarResultado(producto, base, fin);
        return fin;
    }
}
