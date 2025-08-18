package tienda.aplicacion;

import java.math.BigDecimal;
import tienda.descuentos.DiscountManager;

public class App {
      public static void main(String[] args) {
        DiscountManager dm = DiscountManager.getInstance();

        // Casos de prueba mínimos (manuales)
        probarDescuento(dm, new BigDecimal("10000"), "PERCENT10"); // 10% off
        probarDescuento(dm, new BigDecimal("10000"), "PERCENT20"); // 20% off
        probarDescuento(dm, new BigDecimal("10000"), "FLAT500");   // -500
        probarDescuento(dm, new BigDecimal("10000"), "SIN_DESCUENTO"); // código inválido

        // Caso que quedaría negativo => se clamp a 0
        probarDescuento(dm, new BigDecimal("300"), "FLAT500");

        // Demostración de normalización (espacios y minúsculas)
        probarDescuento(dm, new BigDecimal("10000"), " percent10 ");
    }

    private static void probarDescuento(DiscountManager dm, BigDecimal precio, String codigo) {
        BigDecimal resultado = dm.aplicarDescuento(precio, codigo);
        System.out.printf("Precio base: %s | Código: '%s' -> Precio final: %s%n",
                precio.toPlainString(), codigo, resultado.toPlainString());
    }
}
