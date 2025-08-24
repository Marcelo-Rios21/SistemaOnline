package tienda.descuentos.command;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import tienda.core.Component;
import tienda.descuentos.DiscountManager;

public class AplicarDescuentoCommand implements Command {
    private final DiscountManager dm;
    private final Component producto;
    private final List<String> codigos;
    private Optional<BigDecimal> resultado = Optional.empty();

    public AplicarDescuentoCommand( DiscountManager dm, Component producto, String codigo) {
        this(dm, producto, Collections.singletonList(Objects.requireNonNull(codigo, "El codigo no puede ser null.")));
    }

    public AplicarDescuentoCommand(DiscountManager dm, Component producto, List<String> codigos) {
        this.dm = dm;
        this.producto = producto;
        this.codigos = codigos;
    }

    @Override
    public void ejecutar() {
        List<? extends Function<Component, Component>> builders = dm.crearCadenaPorCodigos(codigos);
        BigDecimal valor = dm.aplicarDescuentos(producto, builders);
        this.resultado = Optional.of(valor);
    }

    @Override
    public Optional<BigDecimal> getResultado() {
        return resultado;
    }
}
