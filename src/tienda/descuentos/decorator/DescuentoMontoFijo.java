package tienda.descuentos.decorator;

import java.math.BigDecimal;
import java.util.Objects;
import tienda.core.Component;

public class DescuentoMontoFijo extends Decorator {
    private final BigDecimal monto;

    public DescuentoMontoFijo(Component componente, BigDecimal monto) {
        super(componente);
        Objects.requireNonNull(monto, "El monto no puede ser null.");
        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo.");
        }
        this.monto = monto;
    }

    @Override
    protected BigDecimal aplicarSobre(BigDecimal precioActual) {
        Objects.requireNonNull(precioActual, "El precio actual no puede ser null.");
        return precioActual.subtract(monto);
    }
}
