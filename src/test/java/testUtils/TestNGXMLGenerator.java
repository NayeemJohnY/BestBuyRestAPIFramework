package testUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;
import org.testng.internal.ClassHelper;
import org.testng.internal.PackageUtils;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import utils.ExcelUtils;
import utils.ReadProperties;

public class TestNGXMLGenerator {
	

	public static void createTestNGXml(List<String> testCases) {
		System.out.println(testCases);
		XmlSuite suite = new XmlSuite();
		suite.setName("suite1");
		XmlTest test = new XmlTest(suite);
		test.setName("Test1");
		test.setThreadCount(10);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		try {
			String[] testClasses = PackageUtils.findClassesInPackage("tests", new ArrayList<String>(),
					new ArrayList<String>());
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			for (String eachClass : testClasses) {
				Class<?> currentClass = cl.loadClass(eachClass);
				XmlClass xmlclass = new XmlClass(currentClass);
				classes.add(xmlclass);
				Set<Method> allMethods = ClassHelper.getAvailableMethods(currentClass);
				Iterator<Method> iMethods = allMethods.iterator();
				List<XmlInclude> includeMethods = new ArrayList<XmlInclude>();
				while (iMethods.hasNext()) {
					Method eachMethod = iMethods.next();
					Test testmethod = eachMethod.getAnnotation(Test.class);
					if (testmethod != null && testCases.contains(testmethod.testName())) {
						XmlInclude include = new XmlInclude(eachMethod.getName());
						include.setDescription(testmethod.testName());
						includeMethods.add(include);
						xmlclass.setIncludedMethods(includeMethods);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		test.setXmlClasses(classes);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("testng.xml"));
			bw.write(suite.toXml());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getTestCases(String sheetName) {
		List<String> testCases = new ArrayList<String>();
		ExcelUtils excelUtils = new ExcelUtils(ReadProperties.getProperties("filePath"));
		for (int i = 0; i < excelUtils.getRowCount(sheetName) - 1; i++) {
			excelUtils.row = excelUtils.sheet.getRow(i);
			if (excelUtils.getCellData(sheetName, "Execute", i).equalsIgnoreCase("Yes")) {
				testCases.add(excelUtils.getCellData(sheetName, 0, i) + "_" + excelUtils.getCellData(sheetName, 1, i));

			}
		}
		return testCases;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		createTestNGXml(getTestCases("Products"));
	}

}
