package tienda.carrito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import tienda.core.Component;

public class Carrito {
    private final List<Component> items = new ArrayList<>();

    public void agregar(Component prod) {
        items.add(Objects.requireNonNull(prod, "El producto no puede ser null."));
    }

    public boolean eliminarPorNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getNombre().equalsIgnoreCase(nombre)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Component> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        BigDecimal acumulador = BigDecimal.ZERO;
        for (Component c : items) {
            acumulador = acumulador.add(c.getPrecioFinal());
        }
        return acumulador;
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
