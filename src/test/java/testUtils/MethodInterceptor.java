package testUtils;

import java.util.ArrayList;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import base.APITestBase;
import utils.ExcelUtils;

public class MethodInterceptor extends APITestBase implements IMethodInterceptor{

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		List<IMethodInstance> result = new ArrayList<IMethodInstance>();
		
		List<String>testCases=getTestCases();
		for (IMethodInstance method : methods) {
			Test testMethod = method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
			if (testMethod!=null&& testCases.contains(testMethod.testName())) {
				result.add(method);
			}
		}
		return result;
	}

	public List<String> getTestCases() {
		List<String> testCases = new ArrayList<String>();
		ExcelUtils excelUtils = new ExcelUtils(filePath);
		String sheetName=testScope;
		for (int i = 0; i < excelUtils.getRowCount(sheetName) - 1; i++) {
			excelUtils.row = excelUtils.sheet.getRow(i);
			if (excelUtils.getCellData(sheetName, "Execute", i).equalsIgnoreCase("Yes")) {
				testCases.add(excelUtils.getCellData(sheetName, 0, i) + "_" + excelUtils.getCellData(sheetName, 1, i));

			}
		}
	return testCases;
	}
//	@Override
//	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//		// TODO Auto-generated method stub
//		if (testsNeedtoRun == null || testsNeedtoRun.equalsIgnoreCase("")  || testsNeedtoRun.equalsIgnoreCase("All")) {
//			;
//		} else {
//			System.out.println("inside else");
//			List<String> tests=Arrays.asList(testsNeedtoRun.split(","));
//			if	(! tests.contains(testMethod.getAnnotation(Test.class).testName())) {
//				System.out.println("inside else if");
//				annotation.setEnabled(false);
//			}
//		}
//	}
}