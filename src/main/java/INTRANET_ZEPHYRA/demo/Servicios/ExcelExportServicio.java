package INTRANET_ZEPHYRA.demo.Servicios;

import INTRANET_ZEPHYRA.demo.Entidad.Cliente;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelExportServicio {
    public static void exportar(List<Producto> productos, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Productos");

        Row cabecera = hoja.createRow(0);
        cabecera.createCell(0).setCellValue("ID");
        cabecera.createCell(1).setCellValue("Nombre");
        cabecera.createCell(2).setCellValue("Detalle");
        cabecera.createCell(3).setCellValue("Precio");
        cabecera.createCell(4).setCellValue("Stock");
        cabecera.createCell(5).setCellValue("Color");
        cabecera.createCell(6).setCellValue("Tamano");

        int filaIdx = 1;
        for (Producto p : productos) {
            Row fila = hoja.createRow(filaIdx++);
            fila.createCell(0).setCellValue(p.getIdProducto());
            fila.createCell(1).setCellValue(p.getNombre());
            fila.createCell(2).setCellValue(p.getDetalle());
//            fila.createCell(3).setCellValue(p.getPrecio());
            // Convertir BigDecimal a double
            fila.createCell(3).setCellValue(p.getPrecio().doubleValue());

            fila.createCell(4).setCellValue(p.getStock());
            fila.createCell(5).setCellValue(p.getColor());
            fila.createCell(6).setCellValue(p.getTamaño());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public static void exportarClientes(List<Cliente> clientes, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Clientes");

        Row header = hoja.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Documento");
        header.createCell(2).setCellValue("Nombre");
        header.createCell(3).setCellValue("Correo");
        header.createCell(4).setCellValue("Roles");

        int idx = 1;
        for (Cliente cliente : clientes) {
            Row fila = hoja.createRow(idx++);
            fila.createCell(0).setCellValue(cliente.getId());
            fila.createCell(1).setCellValue(cliente.getDocumento());
            fila.createCell(2).setCellValue(cliente.getNombre());
            fila.createCell(3).setCellValue(cliente.getCorreo());
            fila.createCell(4).setCellValue("Cliente");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=clientes.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
