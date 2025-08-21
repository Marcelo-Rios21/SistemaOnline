package tienda.descuentos.decorator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import tienda.core.Component;

public class DescuentoPorcentual extends Decorator {
    private final BigDecimal porcentaje;

    public DescuentoPorcentual(Component componente, BigDecimal porcentaje) {
        super(componente);
        this.porcentaje = validarPorcentaje(porcentaje);
    }

    public DescuentoPorcentual(Component componente, double porcentaje) {
        this(componente, BigDecimal.valueOf(porcentaje));
    }

    @Override
    protected BigDecimal aplicarSobre(BigDecimal precioActual) {
        Objects.requireNonNull(precioActual, "El precioActual no puede ser null.");
        BigDecimal factor = BigDecimal.ONE.subtract(porcentaje, MathContext.DECIMAL64);
        return precioActual.multiply(factor, MathContext.DECIMAL64);
    }

    private static BigDecimal validarPorcentaje(BigDecimal p) {
        Objects.requireNonNull(p, "El porcentaje no puede ser null");
        if (p.compareTo(BigDecimal.ZERO) < 0 || p.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("El porcentaje debe estar en rango.");
        }
        return p;
    }
}
