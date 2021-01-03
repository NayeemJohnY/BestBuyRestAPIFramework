package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	public ExcelUtils(String filepath) {
		try {
			fis = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		if (!isSheetExists(sheetName))
			return 0;
		sheet = workbook.getSheet(sheetName);
		return sheet.getLastRowNum();
	}

	public int getColumnCountofRow(String sheetName) {
		if (!isSheetExists(sheetName))
			return 0;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return 0;
		return row.getLastCellNum();
	}

	public int getColumnCountofRow(String sheetName, int rowNum) {
		if (!isSheetExists(sheetName))
			return 0;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		return row.getLastCellNum();
	}

	public Object[][] readAllCellContents(String sheetName) {
		if (!isSheetExists(sheetName))
			return null;
		sheet = workbook.getSheet(sheetName);
		Object Object[][] = new Object[getRowCount(sheetName)][getColumnCountofRow(sheetName)];
		for (int i = 0; i < getRowCount(sheetName); i++) {
			for (int j = 0; j < getColumnCountofRow(sheetName, i) - 1; j++) {
				cell = sheet.getRow(i).getCell(j);
				Object[i][j] = getDataFromCellAsString(cell);
				System.out.printf("%-45s", Object[i][j]);
			}
			System.out.println();
		}
		return Object;
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		int colNum = -1;
		try {
			for (int i = 0; i < getColumnCountofRow(sheetName); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			cell = sheet.getRow(rowNum).getCell(colNum);
			return getDataFromCellAsString(cell);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}	
	}

	public String getCellData(String sheetName, String colName, String rowNane) {
		int colNum = -1;
		int rowNum = -1;
		try {
			for (int j = 0; j < getRowCount(sheetName); j++) {
				row = sheet.getRow(j);
				if (row.getCell(0).getStringCellValue().trim().equals(rowNane))
					rowNum = j;
			}
			row = sheet.getRow(rowNum);
			for (int i = 0; i < getColumnCountofRow(sheetName); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			cell = sheet.getRow(rowNum).getCell(colNum);
			return getDataFromCellAsString(cell);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		cell = sheet.getRow(rowNum).getCell(colNum);
		return getDataFromCellAsString(cell);
	}

	public String getDataFromCellAsString(XSSFCell cell) {
		String cellValue = "";
		try {
			switch (cell.getCellType()) {
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case BLANK:
				cellValue = "";
				break;
			case NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				break;
			case FORMULA:
				cellValue = cell.getCellFormula();
				break;
			case BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellValue;
	}

	public boolean isSheetExists(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;
		return true;
	}

	public static void main(String[] args) {
		ExcelUtils excelUtils = new ExcelUtils(".\\BestBuyAPITestManager.xlsx");
		System.out.println(excelUtils.getRowCount("Stores"));
		System.out.println(excelUtils.getColumnCountofRow("Stores"));
		excelUtils.readAllCellContents("Stores");
		System.out.println(excelUtils.getCellData("Product", "TestCase Description", "Product_TC_01"));
		System.out.println("**********");
	}
}
