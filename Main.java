
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Inventario miInventario = new Inventario();

        System.out.println("--- Cargando productos iniciales ---");
        miInventario.agregarProducto(new Producto("Teclado Mecánico", 25));
        miInventario.agregarProducto(new Producto("Laptop Gamer", 10));
        miInventario.agregarProducto(new Producto("Mouse Inalámbrico", 50));
        miInventario.agregarProducto(new Producto("Monitor Curvo", 15));
        System.out.println();

        // Generar reporte inicial
        miInventario.generarReporteExistencias();
        
        System.out.println("\n--- Realizando operaciones de inventario ---");
        miInventario.registrarEntrada("Laptop Gamer", 5);
        miInventario.registrarSalida("Mouse Inalámbrico", 15);
        System.out.println();

        // --- DEMOSTRACIÓN DE MANIPULACIÓN AVANZADA CON STREAMS ---
        
        // 1. FILTRAR: Mostrar productos con stock bajo (menor a 20)
        System.out.println("\n--- (FILTRAR) Productos con Stock Bajo (< 20) ---");
        List<Producto> productosBajosStock = miInventario.buscarProductosConStockBajo(20);
        if (productosBajosStock.isEmpty()) {
            System.out.println("No hay productos con bajo stock.");
        } else {
            productosBajosStock.forEach(System.out::println);
        }

        // 2. MAPEAR: Obtener y mostrar solo los nombres de los productos
        System.out.println("\n--- (MAPEAR) Nombres de todos los productos en inventario ---");
        List<String> nombres = miInventario.obtenerNombresDeProductos();
        System.out.println(nombres);

        // 3. REDUCIR: Calcular y mostrar el total de unidades en el inventario
        System.out.println("\n--- (REDUCIR) Total de unidades en el inventario ---");
        int totalStock = miInventario.calcularStockTotal();
        System.out.println("El stock total de todos los productos es: " + totalStock + " unidades.");

        // Generar reportes finales
        miInventario.generarReporteExistencias();
        miInventario.generarReporteMovimientos();
    }
}