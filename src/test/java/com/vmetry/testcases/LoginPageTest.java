package com.vmetry.testcases;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.vmetry.testBase.TestBase;
import com.vmety.testUtil.TestUtilityClass;

public class LoginPageTest extends TestBase {

	@BeforeClass
	public void loginBfrcls() throws IOException {

		if (initialize == false) {
			initialization();
		}

	}

@Test(dataProvider = "data")
public void testlogin(String TestCaseID, String UAN, String Password, String Type) 
		throws InterruptedException, IOException {
	
		String expectederrorlbl = "Sign in login error - Invalid Credentials";
		getElementByXpath(loc.getProperty("lg_uan_txtbox")).sendKeys(UAN);
		getElementByXpath(loc.getProperty("lg_password_txtbox")).sendKeys(Password);
		getElementByXpath(loc.getProperty("lg_signin_clkbutton")).submit();
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

if (Type.equalsIgnoreCase("valid")) {
	String expectedname = "Welcome Vasanth Shanmugam" + "\n" + "UAN   100411794217";
	System.out.println("exp:"+expectedname);
	System.out.println(getElementByXpath(loc.getProperty("hp_welcomename")).getText());

	if (((getElementByXpath(loc.getProperty("hp_welcomename")).getText()).equals(expectedname))) {

		System.out.println("Write pass");
		map.put(TestCaseID, "Passed");
		getElementByXpath(loc.getProperty("hp_logout_button")).click();
		TestUtilityClass.alertchkandaccept();
		Thread.sleep(3000);
	} else {

		map.put(TestCaseID, "Failed");
		System.out.println("Write Fail");
		getElementByXpath(loc.getProperty("hp_logout_button")).click();
		TestUtilityClass.alertchkandaccept();
		Thread.sleep(3000);
	}
} 
else if (Type.equalsIgnoreCase("invalid")) {

	
	if(TestUtilityClass.isAlertPresent() == true) {
		System.out.println("Alert checking loop");
		String alrtmsg = "Please enter Username.";
		System.out.println(alrtmsg);
		if (TestUtilityClass.closeAlertAndGetItsText().equalsIgnoreCase(alrtmsg)) {
			map.put(TestCaseID, "Passed");
		} else {
			map.put(TestCaseID, "Failed");
		}
	} 		
	else if (TestUtilityClass.errorlabeltext((loc.getProperty("lg_errorlabel")),expectederrorlbl)){
		
		System.out.println("error label is satisfied with actual");
	
		if (getElementByXpath(loc.getProperty("lg_errorlabel")).getText().equalsIgnoreCase(expectederrorlbl)) {

			map.put(TestCaseID, "passed");
		} else {
			map.put(TestCaseID, "Failed");
		}

	}
}else { System.out.println("Nothing to execute");}

}

	@DataProvider(name="data")
	public static Object[][] getdata() throws IOException {
		if (TestUtilityClass.runModeVerify("LoginPage") == true) {
			Object[][] data = TestUtilityClass.getExcelData("Login");

			return data;
		} else {
			return null;
		}

	}

	@AfterClass
	public void afrcls() throws IOException {

		TestUtilityClass.writeExcelFile("Login");
		wd.close();

	}

}
