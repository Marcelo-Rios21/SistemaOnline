package tienda.descuentos.decorator;

import java.math.BigDecimal;
import java.util.Objects;
import tienda.core.Component;


public abstract class Decorator implements Component {
    protected final Component componente;

    protected Decorator(Component componente) {
        this.componente = Objects.requireNonNull(componente, "El componente no puede ser null.");
    }

    @Override
    public String getCategoria() {
        return componente.getCategoria();
    }

    @Override
    public String getNombre() {
        return componente.getNombre();
    }

    @Override
    public BigDecimal getPrecioFinal() {
        BigDecimal precioActual = componente.getPrecioFinal();
        BigDecimal modificado = aplicarSobre(precioActual);

        if (modificado.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return modificado;
    }

    protected abstract BigDecimal aplicarSobre(BigDecimal precioActual);
}
