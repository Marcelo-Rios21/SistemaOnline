package tienda.core;

import java.math.BigDecimal;

public interface Component {
    BigDecimal getPrecioFinal();
    String getCategoria();
    String getNombre();
}
