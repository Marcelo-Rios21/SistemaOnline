package tienda.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Pedido {
    private final String idPedido;
    private final Usuario usuario;
    private final List<Producto> items = new ArrayList<>();
    private boolean confirmado = false;

    public Pedido(String idPedido, Usuario usuario) {
        this.idPedido = (idPedido == null || idPedido.isBlank()) ? UUID.randomUUID().toString() : idPedido.trim();
        this.usuario = Objects.requireNonNull(usuario, "El usuario no puede ser null");

        
    }

    public String getIdPedido() {
        return idPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void agregar(Producto p) {
        asegurarNoConfirmado();
        items.add(Objects.requireNonNull(p, "El producto no puede ser null"));
    }

    public boolean eliminarPorNombre(String nombreProducto) {
        asegurarNoConfirmado();
        Objects.requireNonNull(nombreProducto, "El nombre del producto no puede ser null");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getNombre().equalsIgnoreCase(nombreProducto)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Producto> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        BigDecimal acc = BigDecimal.ZERO;
        for (Producto p : items) {
            acc = acc.add(p.getPrecioFinal());
        }
        return acc;
    }

    public void confirmar() {
        if (items.isEmpty()) {
            throw new IllegalStateException("No se puede confirmar un pedido sin items.");
        }
        this.confirmado = true;
    }

    private void asegurarNoConfirmado() {
        if (confirmado) {
            throw new IllegalStateException("Pedido ya confirmado, no se puede modificar");
        }
    }    
}
