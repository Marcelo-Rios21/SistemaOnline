package tienda.vista;

import java.math.BigDecimal;
import tienda.descuentos.DiscountCode;
import tienda.modelo.Producto;

public class DescuentosView {
    
    public void mostrarCodigosDisponibles() {
        System.out.println("\n--- Codigos de descuento disponibles ---");
        for (DiscountCode code : DiscountCode.values()) {
            switch (code) {
                case PERCENT10 -> System.out.println("- PERCENT10 (10% off)");
                case PERCENT20 -> System.out.println("- PERCENT20 (20% off)"); 
                case FLAT500 -> System.out.println("- FLAT500 (resta 500)");
                default -> System.out.println("- " + code.name());
            }
        }
    }

    public void mostrarResultado(Producto p, BigDecimal base, BigDecimal finalPrecio) {
        if (p == null) {
            System.out.println("Producto no encontrado para aplicar descuentos.");
            return;
        }
        BigDecimal b = (base == null) ? BigDecimal.ZERO : base;
        BigDecimal f = (finalPrecio == null) ? b : finalPrecio;

        BigDecimal ahorro = b.subtract(f);
        if (ahorro.compareTo(BigDecimal.ZERO) < 0) ahorro = BigDecimal.ZERO;

        System.out.println("\nResultado:");
        System.out.println("- Producto: " + p.getNombre() + " (Categoria: " + p.getCategoria() + ")");
        System.out.println("- Base  : " + b.toPlainString());
        System.out.println("- Ahorro: " + ahorro.toPlainString());
        System.out.println("- Final : " + f.toPlainString());
    }
}
