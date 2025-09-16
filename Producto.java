
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Comparable<Producto> {
    private String nombre;
    private int stockActual;
    private List<String> historialMovimientos;

    public Producto(String nombre, int stockInicial) {
        this.nombre = nombre;
        this.stockActual = stockInicial;
        this.historialMovimientos = new ArrayList<>();
        registrarMovimiento("Creación de producto con stock inicial", stockInicial);
    }

    // Gestiona los métodos del stock del producto
    public void registrarEntrada(int cantidad) {
        if (cantidad > 0) {
            this.stockActual += cantidad;
            registrarMovimiento("Entrada", cantidad);
        }
    }

    public boolean registrarSalida(int cantidad) {
        if (cantidad > 0 && cantidad <= this.stockActual) {
            this.stockActual -= cantidad;
            registrarMovimiento("Salida", cantidad);
            return true;
        }
        return false;
    }

    private void registrarMovimiento(String tipo, int cantidad) {
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String registro = String.format("[%s] %s: %d unidades. Stock resultante: %d", fechaHora, tipo, cantidad, this.stockActual);
        this.historialMovimientos.add(registro);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getStockActual() {
        return stockActual;
    }

    public List<String> getHistorialMovimientos() {
        return historialMovimientos;
    }

    // Método compareTo para permitir el ordenamiento nativo por nombre
    @Override
    public int compareTo(Producto otro) {
        return this.nombre.compareTo(otro.getNombre());
    }

    @Override
    public String toString() {
        return String.format("%-20s | %d", this.nombre, this.stockActual);
    }
}