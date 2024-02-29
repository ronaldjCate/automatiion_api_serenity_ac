package collection.utils.Excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class ExcelReader {
    private ExcelReader() {
    }

    public static List<Map<String, String>> getData(String rutaRelativaExcel, String nombreHoja) {

        List<Map<String, String>> mydata = new ArrayList<>();

        FileInputStream fs = null;
        XSSFWorkbook workbook = null;

        try {

            String ruta = System.getProperty("user.dir") + "/src/test/java/resources/" + rutaRelativaExcel;
            File rutaFile = new File(ruta);

            if (!rutaFile.exists()) throw new Exception("El archivo " + rutaFile.getName() + " no existe!");

            fs = new FileInputStream(rutaFile);

            workbook = new XSSFWorkbook(fs);

            XSSFSheet sheet = workbook.getSheet(nombreHoja);

            if (sheet == null) throw new Exception("La hoja " + nombreHoja + " no existe!");

            Row headerRow = sheet.getRow(0);

            int nroFilas = sheet.getPhysicalNumberOfRows();
            int nroColumnas = headerRow.getPhysicalNumberOfCells();

            for (int i = 1; i < nroFilas; i++) {

                Row currentRow = sheet.getRow(i);

                if (currentRow == null) continue;

                HashMap<String, String> currentHash = new HashMap<>();

                for (int j = 0; j < nroColumnas; j++) {

                    Cell currentCell = currentRow.getCell(j);
                    if (currentCell != null) {

                        if (currentCell.getCellType().equals(CellType.STRING)) {

                            currentHash.put(
                                    StringUtils.trimToEmpty(headerRow.getCell(j).getStringCellValue()),
                                    StringUtils.trimToEmpty(currentCell.getStringCellValue()));

                        } else if (currentCell.getCellType().equals(CellType.BLANK)) {

                            currentHash.put(
                                    StringUtils.trimToEmpty(headerRow.getCell(j).getStringCellValue()),
                                    StringUtils.EMPTY);

                        } else if (currentCell.getCellType().equals(CellType.NUMERIC)) {

                            double valor = currentCell.getNumericCellValue();

                            double input = Math.abs(valor);

                            String inputString;

                            if (input - (int) input > 0) {
                                inputString = String.valueOf(valor);
                            } else {
                                inputString = String.valueOf((int) valor);
                            }

                            currentHash.put(
                                    StringUtils.trimToEmpty(headerRow.getCell(j).getStringCellValue()),
                                    inputString);

                        } else {
                            currentHash.put(
                                    StringUtils.trimToEmpty(headerRow.getCell(j).getStringCellValue()),
                                    StringUtils.EMPTY);
                        }

                    } else {
                        currentHash.put(
                                StringUtils.trimToEmpty(headerRow.getCell(j).getStringCellValue()),
                                StringUtils.EMPTY);
                    }
                }

                mydata.add(currentHash);
            }


        } catch (Exception e) {

            System.out.println("[ERROR] No se pudo leer el archivo excel: " + e.getMessage());
            try {
                throw e;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        } finally {
            assert workbook != null;
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return mydata;
    }

    public static void setData(String rutaRelativaExcel, String sheetName, int rowNumber, int cellNumber, String resultText) {

        FileInputStream fs = null;
        XSSFWorkbook newWorkbook = null;

        try {

            String ruta = System.getProperty("user.dir") + "/src/test/java/resources/" + rutaRelativaExcel;

            File rutaFile = new File(ruta);

            if (!rutaFile.exists()) throw new Exception("El archivo " + rutaFile.getName() + " no existe!");

            fs = new FileInputStream(rutaFile);

            newWorkbook = new XSSFWorkbook(fs);

            XSSFSheet newSheet = newWorkbook.getSheet(sheetName);

            XSSFRow row = newSheet.getRow(rowNumber);

            XSSFCell nextCell = row.createCell(cellNumber);

            CellStyle style = newWorkbook.createCellStyle();

            Font font = newWorkbook.createFont();

            style.setFont(font);

            nextCell.setCellValue(resultText);

            fs.close();

            FileOutputStream outputStream = new FileOutputStream(rutaFile);

            newWorkbook.write(outputStream);

            outputStream.flush();

            outputStream.close();

        }catch (Exception e){
            Logger.getLogger(" Shows: "+ e.getMessage());
        }
        finally {
            assert newWorkbook != null;
            try {
                newWorkbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
