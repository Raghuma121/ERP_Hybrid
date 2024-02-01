package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;
	String Msheet="MasterTestCases";
	String Inputpath="./FileInput/Controller.xlsx";
	String Outputpath="./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;

	public void startTest() throws Throwable
	{
		// create object for ExcelFileUtil class
		ExcelFileUtil xl= new ExcelFileUtil(Inputpath);
		//iterate all rows in Msheet
		for(int i=1;i<=xl.rowCount(Msheet);i++)
		{
			//Write as pass into Msheet flag as Y
			if(xl.getCellData(Msheet, i,2).equalsIgnoreCase("Y"))
			{
				// store all sheets into One variable
				String TCModule= xl.getCellData(Msheet, i, 1);
				report= new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+" "+".html");
				logger=report.startTest(TCModule);
				//iterate all rows in TCModule
				for (int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String Module_Status="";
					//read each cell from TCModule
					String Description=xl.getCellData(TCModule, j, 0);
					String object_Type=xl.getCellData(TCModule, j, 1);
					String Locator_Name=xl.getCellData(TCModule, j, 2);
					String Locator_Value=xl.getCellData(TCModule, j, 3);
					String Test_Data=xl.getCellData(TCModule, j, 4);
					try {
						if(object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(Locator_Name, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Locator_Name, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Locator_Name, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Locator_Name, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("stockCapture"))
						{
							FunctionLibrary.stockCapture(Locator_Name, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("captureSupplier"))

						{
							FunctionLibrary.captureSupplier(Locator_Name, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(Locator_Name, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("capturePurchase"))
						{
							FunctionLibrary.capturePurchase(Locator_Name, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("purchaseTable"))
						{
							FunctionLibrary.purchaseTable();
							logger.log(LogStatus.INFO,Description);
						}
						if(object_Type.equalsIgnoreCase("captureDate"))
						{
							FunctionLibrary.captureDate(Locator_Name, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}

						//write as pass into TCModule sheet
						xl.setCellData(TCModule, j, 5, "Pass", Outputpath);
						logger.log(LogStatus.PASS,Description);
						Module_Status="True";
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						//write as fail into TCModule sheet
						xl.setCellData(TCModule, i, j, "Fail", Outputpath);
						logger.log(LogStatus.FAIL,Description);
						Module_Status="False";
					}
					if(Module_Status.equalsIgnoreCase("True"))
					{
						//write pass into MSheet
						xl.setCellData(Msheet, i, 3, "Pass", Outputpath);
					}
					else
					{
						//write fail into MSheet
						xl.setCellData(Msheet, i, 3, "Fail", Outputpath);
					}
					report.endTest(logger);
					report.flush();
				}
			}
			else
			{
				//Write as blocked into Msheet flag as N
				xl.setCellData(Msheet, i, 3, "Blocked", Outputpath);
			}
		}
	}
}
