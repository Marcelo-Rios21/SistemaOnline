package tienda.aplicacion;

import tienda.controlador.CarritoController;
import tienda.controlador.DescuentosController;
import tienda.controlador.ProductosController;
import tienda.descuentos.DiscountManager;
import tienda.modelo.Carrito;
import tienda.vista.CarritoView;
import tienda.vista.DescuentosView;
import tienda.vista.ProductosView;

public class App {
      public static void main(String[] args) {
        ProductosView productosView = new ProductosView();
        CarritoView carritoView = new CarritoView();
        DescuentosView descuentosView = new DescuentosView();

        Carrito carrito = new Carrito();

        ProductosController productosCtl = new ProductosController(productosView);
        CarritoController carritoCtl = new CarritoController(carrito, carritoView);
        DescuentosController descuentosCtl = new DescuentosController(DiscountManager.getInstance(), descuentosView);

        Menu menu = new Menu(productosCtl, carritoCtl, descuentosCtl);
        menu.mostrar();
    }
}
