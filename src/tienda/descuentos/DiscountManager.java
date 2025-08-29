package tienda.descuentos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import tienda.core.Component;
import tienda.descuentos.decorator.Decorator;
import tienda.descuentos.decorator.DescuentoMontoFijo;
import tienda.descuentos.decorator.DescuentoPorcentual;
import tienda.modelo.Producto;

public final class DiscountManager {

    private static final String ERR_PRECIO_NULL = "El parametro 'precio' no puede ser null.";
    private static final String ERR_CODIGO_NULL = "El parametro 'codigoPromo' no puede ser null.";
    //MONEDA
    private static final int SCALE_MONEDA = 0;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    //SINGLETON
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

        Component base = new Producto("GENERICO", "General", precio);

        Optional<Decorator> decoOpt = crearDecoradorPorCodigo(codigoPromo, base);
        Component decorado = decoOpt.<Component>map(d -> d).orElse(base);

        BigDecimal result = decorado.getPrecioFinal();
        result = pisoCero(result);
        return redondearMoneda(result);
    }

    public Component construirPipeline(Component base , List<? extends Function<Component, Component>> builders) {
        Objects.requireNonNull(base, "La base no puede ser null.");
        if (builders == null || builders.isEmpty()) return base;

        Component actual = base;
        for (Function<Component, Component> builder : builders) {
            if (builder != null) {
                actual = Objects.requireNonNull(builder.apply(actual), "Builder retornó null.");
            }
        }
        return actual;
    }
    
    public BigDecimal aplicarDescuentos(Component producto, List<? extends Function<Component, Component>> builders) {
        Component chain = construirPipeline(producto, builders);
        BigDecimal result = chain.getPrecioFinal();
        result = pisoCero(result);
        return redondearMoneda(result);
    }

    public Optional<Decorator> crearDecoradorPorCodigo(String codigoPromo, Component base) {
        Objects.requireNonNull(base, "La base no puede ser null.");
        if (codigoPromo == null) return Optional.empty();

        DiscountCode code = DiscountCode.from(codigoPromo);
        if (code == null) return Optional.empty();

        switch (code) {
            case PERCENT10:
                return Optional.of(new DescuentoPorcentual(base, new BigDecimal("0.10")));
            case PERCENT20:
                return Optional.of(new DescuentoPorcentual(base, new BigDecimal("0.20")));
            case FLAT500:
                return Optional.of(new DescuentoMontoFijo(base,new BigDecimal("500")));
            default:
                return Optional.empty();
        }
    }

    public List<Function<Component, Component>> crearCadenaPorCodigos(List<String> codigos) {
        List<Function<Component, Component>> builders = new ArrayList<>();
        if (codigos == null || codigos.isEmpty()) return builders;

        for (String cod : codigos) {
            Function<Component, Component> builder = mapBuilderPorCodigo(cod);
            if (builder != null) builders.add(builder);
        }
        return builders;
    }

    private Function<Component, Component> mapBuilderPorCodigo(String codigoPromo) {
        DiscountCode code = DiscountCode.from(codigoPromo);
        if (code == null) return null;

        switch (code) {
            case PERCENT10:
                return c -> new DescuentoPorcentual(c, new BigDecimal("0.10"));
            case PERCENT20:
                return c -> new DescuentoPorcentual(c, new BigDecimal("0.20"));
            case FLAT500:
                return c -> new DescuentoMontoFijo(c, new BigDecimal("500"));
            default:
                return null;
        }
    }

    private static BigDecimal redondearMoneda(BigDecimal valor) {
        return valor.setScale(SCALE_MONEDA, ROUNDING);
    }

    private static BigDecimal pisoCero(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : valor;
    }
}
