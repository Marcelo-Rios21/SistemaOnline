package tienda.descuentos;

import java.math.BigDecimal;

public enum DiscountCode {
    PERCENT10 {
        @Override
        public BigDecimal apply(BigDecimal precio) {
            return precio.multiply(new BigDecimal("0.90"));
        }
    },
    PERCENT20 {
        @Override
        public BigDecimal apply(BigDecimal precio) {
            return precio.multiply(new BigDecimal("0.80"));
        }
    },
    FLAT500 {
        @Override
        public BigDecimal apply(BigDecimal precio) {
            return precio.subtract(new BigDecimal("500"));
        }
    };

    public abstract BigDecimal apply(BigDecimal precio);

    public static DiscountCode from(String code) {
        if (code == null) return null; 
        String normalized = code.trim().toUpperCase();
        try {
            return DiscountCode.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
