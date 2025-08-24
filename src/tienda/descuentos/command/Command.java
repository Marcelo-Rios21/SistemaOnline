package tienda.descuentos.command;

import java.math.BigDecimal;
import java.util.Optional;

public interface Command {
    void ejecutar();

    default Optional<BigDecimal> getResultado() {
        return Optional.empty();
    }
}
