package tienda.modelo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Usuario {
    private final String id;
    private final String nombre;
    private final String email;

    private static final Pattern EMAIL_RE = Pattern.compile("^[\\w.!#$%&'*+/=?^`{|}~-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Usuario(String id, String nombre, String email) {
        this.id = validarNoVacio(id, "id");
        this.nombre = validarNoVacio(nombre, "nombre");
        this.email = validarEmail(email);
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    private static String validarNoVacio(String v, String campo) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede ser vacio");
        }
        return v.trim();
    }

    private static String validarEmail(String v) {
        Objects.requireNonNull(v, "email no puede ser null");
        String t = v.trim();
        if (!EMAIL_RE.matcher(t).matches()) {
            throw new IllegalArgumentException("Email no valido.");
        }
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return nombre + " <" + email +">";
    }
}
