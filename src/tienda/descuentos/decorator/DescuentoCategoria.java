package tienda.descuentos.decorator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Locale;
import java.util.Objects;
import tienda.core.Component;

public class DescuentoCategoria extends Decorator {
    private final String categoriaObjetivo;
    private final BigDecimal porcentaje;

    public DescuentoCategoria(Component componente, String categoriaObjetivo, BigDecimal porcentaje) {
        super(componente);
        this.categoriaObjetivo = normalizarCategoria(categoriaObjetivo);
        this.porcentaje = validarPorcentaje(porcentaje);
    }

    public DescuentoCategoria(Component componente, String categoriaObjetivo, double porcentaje) {
        this(componente, categoriaObjetivo, BigDecimal.valueOf(porcentaje));
    }

    @Override
    protected BigDecimal aplicarSobre(BigDecimal precioActual) {
        Objects.requireNonNull(precioActual, "El precio actual no puede ser null.");
        String categoriaProducto = normalizarCategoria(componente.getCategoria());
        if (categoriaProducto.equals(categoriaObjetivo)) {
            BigDecimal factor = BigDecimal.ONE.subtract(porcentaje, MathContext.DECIMAL64);
            return precioActual.multiply(factor, MathContext.DECIMAL64);
        }
        return precioActual;
    }

    private static String normalizarCategoria(String c) {
        if (c == null || c.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoria objetivo no puede estar vacia.");
        }
        return c.trim().toLowerCase(Locale.ROOT);
    }

    private static BigDecimal validarPorcentaje(BigDecimal p) {
        Objects.requireNonNull(p, "El porcentaje no puede ser null.");
        if (p.compareTo(BigDecimal.ZERO) < 0 || p.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException(" El porcentaje debe estar en el rango.");
        }
        return p;
    }
}
