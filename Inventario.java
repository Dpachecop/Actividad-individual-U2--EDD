
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Inventario {

    private List<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }

    // --- MÉTODOS DE GESTIÓN DE PRODUCTOS ---
    public void agregarProducto(Producto producto) {
        // Usamos el método de búsqueda funcional para verificar si existe
        if (buscarProducto(producto.getNombre()).isEmpty()) {
            this.productos.add(producto);
            System.out.println("Producto '" + producto.getNombre() + "' agregado al inventario.");
        } else {
            System.out.println("Error: El producto '" + producto.getNombre() + "' ya existe.");
        }
    }

    // --- MÉTODOS DE BÚSQUEDA Y MANIPULACIÓN CON STREAMS ---

    // 1. Búsqueda con Stream (Sustituye a la búsqueda lineal imperativa)
    public Optional<Producto> buscarProducto(String nombre) {
        return productos.stream()
                        .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                        .findFirst();
    }
    
    // 2. Filtrar: Obtener productos con stock bajo (ej. < 20)
    public List<Producto> buscarProductosConStockBajo(int umbral) {
        return productos.stream()
                        .filter(p -> p.getStockActual() < umbral)
                        .collect(Collectors.toList());
    }

    // 3. Mapear: Obtener solo los nombres de todos los productos
    public List<String> obtenerNombresDeProductos() {
        return productos.stream()
                        .map(Producto::getNombre)
                        .collect(Collectors.toList());
    }
    
    // 4. Reducir: Calcular el valor total del inventario (suma de stock de todos los productos)
    public int calcularStockTotal() {
        return productos.stream()
                        .mapToInt(Producto::getStockActual)
                        .sum();
    }

    // --- MOVIMIENTOS Y REPORTES (Refactorizados con Streams) ---
    public void registrarEntrada(String nombreProducto, int cantidad) {
        buscarProducto(nombreProducto).ifPresentOrElse(
            producto -> {
                producto.registrarEntrada(cantidad);
                System.out.println("Entrada registrada para '" + nombreProducto + "'.");
            },
            () -> System.out.println("Error: No se encontró el producto '" + nombreProducto + "'.")
        );
    }

    public void registrarSalida(String nombreProducto, int cantidad) {
        buscarProducto(nombreProducto).ifPresentOrElse(
            producto -> {
                if (!producto.registrarSalida(cantidad)) {
                    System.out.println("Error: Stock insuficiente para '" + nombreProducto + "'.");
                } else {
                    System.out.println("Salida registrada para '" + nombreProducto + "'.");
                }
            },
            () -> System.out.println("Error: No se encontró el producto '" + nombreProducto + "'.")
        );
    }
    
    public void generarReporteExistencias() {
        System.out.println("\n--- REPORTE DE EXISTENCIAS ---");
        if (productos.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("------------------------------------");
        System.out.printf("%-20s | %s\n", "Producto", "Stock Actual");
        System.out.println("------------------------------------");
        // Usamos forEach de Stream para imprimir
        productos.stream()
                 .sorted() // Ordena usando el método compareTo
                 .forEach(System.out::println);
        System.out.println("------------------------------------\n");
    }

    public void generarReporteMovimientos() {
        System.out.println("\n--- REPORTE DE MOVIMIENTOS ---");
        if (productos.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        // Usamos forEach para iterar sobre los productos
        productos.stream()
                 .sorted()
                 .forEach(p -> {
                    System.out.println("\n--- Historial de: " + p.getNombre() + " ---");
                    if (p.getHistorialMovimientos().isEmpty()) {
                        System.out.println("Sin movimientos registrados.");
                    } else {
                        // Iteramos sobre el historial de cada producto
                        p.getHistorialMovimientos().forEach(registro -> System.out.println("  - " + registro));
                    }
                 });
        System.out.println("\n--- FIN DEL REPORTE ---\n");
    }
}