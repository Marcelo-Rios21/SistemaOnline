package tienda.descuentos.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import tienda.core.Component;
import tienda.descuentos.DiscountManager;
import tienda.modelo.Carrito;

public class AplicarDescuentosCarritoCommand implements Command {
    private final DiscountManager dm;
    private final Carrito carrito;
    private final List<String> codigos;

    private Optional<BigDecimal> resultado = Optional.empty();

    public AplicarDescuentosCarritoCommand(DiscountManager dm, Carrito carrito, List<String> codigos) {
        this.dm = Objects.requireNonNull(dm, "DiscountManager no puede ser null.");
        this.carrito = Objects.requireNonNull(carrito, "El carrito no puede ser null.");
        this.codigos = Objects.requireNonNull(codigos, "La lista de codigos no puede ser null.");
    }

    @Override
    public void ejecutar() {
        if (carrito.isEmpty()) {
            this.resultado = Optional.of(BigDecimal.ZERO);
            return;
        }
        List<? extends Function<Component, Component>> builders = dm.crearCadenaPorCodigos(codigos);

        BigDecimal total = BigDecimal.ZERO;
        for (Component item : carrito.items()) {
            BigDecimal conDescuento = dm.aplicarDescuentos(item, builders);
            total = total.add(conDescuento);
        }
        this.resultado = Optional.of(total);
    }

    @Override
    public Optional<BigDecimal> getResultado() {
        return resultado;
    }
}
