package tienda.modelo;

import java.math.BigDecimal;
import java.util.Objects;
import tienda.core.Component;

public class Producto implements Component {
    private final String nombre;
    private final String categoria;
    private final BigDecimal precioBase;

    public Producto(String nombre, String categoria , BigDecimal precioBase) {
        this.nombre = validarTexto(nombre, "nombre");
        this.categoria = validarTexto(categoria, "categoria");
        Objects.requireNonNull(precioBase, "El precio base no puede ser null.");
        if (precioBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio base no puede ser negativo.");
        }
        this.precioBase = precioBase;
    }

    @Override
    public BigDecimal getPrecioFinal() {
        return precioBase;
    }

    @Override
    public String getCategoria() {
        return categoria;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " No puede ser vacio");
        }
        return valor.trim();
    }
}
