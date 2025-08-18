package tienda.descuentos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DiscountManager {

    private static final String ERR_PRECIO_NULL = "El parametro 'precio' no puede ser null.";
    private static final String ERR_CODIGO_NULL = "El parametro 'codigoPromo' no puede ser null.";
    private static final DiscountManager INSTANCE = new DiscountManager();

    private DiscountManager(){

    }

    public static DiscountManager getInstance() {
        return INSTANCE;
    }

    public BigDecimal aplicarDescuento(BigDecimal precio, String codigoPromo) {
        if (precio == null) {
            throw new IllegalArgumentException(ERR_PRECIO_NULL);
        }
        if (codigoPromo == null) {
            throw new IllegalArgumentException(ERR_CODIGO_NULL);
        }

        DiscountCode code = DiscountCode.from(codigoPromo);
        
        BigDecimal resultado = (code == null) ? precio : code.apply(precio);
        
        if (resultado.compareTo(BigDecimal.ZERO) < 0) {
            resultado = BigDecimal.ZERO;
        }

        return resultado.setScale(0, RoundingMode.HALF_UP);
    }
}
