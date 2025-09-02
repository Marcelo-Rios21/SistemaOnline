package tienda.controlador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tienda.modelo.Producto;
import tienda.vista.ProductosView;

public class ProductosController {
    private final ProductosView view;
    private final List<Producto> catalogo;

    public ProductosController(ProductosView view) {
        this.catalogo = new ArrayList<>();
        this.view = Objects.requireNonNull(view, "La view no puede ser null.");
    }

    public ProductosController(ProductosView view, List<Producto> catalogoInicial) {
        this.view = Objects.requireNonNull(view, "La view no puede ser null.");
        this.catalogo = new ArrayList<>(Objects.requireNonNull(catalogoInicial, "El catalogo inicial no puede ser null."));
    }

    public Producto crearProducto(String nombre, String categoria, BigDecimal precioBase) {
        Producto p = new Producto(nombre, categoria, precioBase);
        catalogo.add(p);
        return p;
    }

    public void listarProductos() {
        view.mostrarProductos(catalogo);
    }

    public void mostrarDetalleProducto(String nombre) {
        view.mostrarDetalle(buscarPorNombre(nombre));
    }

    public Producto buscarPorNombre(String nombre) {
        if (nombre == null) return null;
        for (Producto p : catalogo) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    public List<Producto> getCatalogo() {
        return new ArrayList<>(catalogo);
    }
}
