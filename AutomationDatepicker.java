/*
 * Author : Riyansya Prananda
 * 
 * Email  : riyansyaprananda@gmail.com
 * 
 * Example Automation script for Datepicker
 * 
 * Note : - This script maybe not support to another datepicker because id, xpath, class name, and ext. i think have similar logic, may need some adjustments or additions
 * 		  - 
 */
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// Example parameter Date

int d_input = 22	//day
int y_input = 2015	//year
int m_input = 8 	//Moon

WebUI.openBrowser('https://demos.telerik.com/kendo-ui/datetimepicker/index') //Kendo UI Example Jquery Datepicker

Thread.sleep(500); //delay

ClickByXpath('//*[@id="example"]/div/span/span/span[2]/span[1]/span') // Clik button Datepicker

Thread.sleep(300);

String d = GetTextByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[2]') //Get text Moon and Year default 

String[] moonYear = d.split(" ")

String m_s = moonYear[0]

int y = Integer.parseInt(moonYear[1])

int m
// Change format Moon to int
String[] moonText = ['January', 'Febuary', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'] 

for(int t = 0; t < 12; t++){
	
	check : if(moonText[t] == m_s){
		
		m = t+1
		
		check: break
	}
}

boolean status = true

if(y_input == y){
	
	status = false
}	
ClickYear(y_input, y)
	
ClickMoon(m_input, m, status)
	
ClickDay(d_input)

public static ClickYear(int y_in, int y){

	int y_space

	if(y > y_in){
		
		y_space = y - y_in
		
		ClickByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[2]')
		
		for(int i = 0; i < y_space; i++){
			// Click back arrow years
			ClickByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[1]/span')
		}
		
	}
	else if(y < y_in){
		
		y_space = y_in - y
		
		ClickByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[2]')
		 
		 for(int i = 0; i < y_space; i++){
			 // Click back arrow years
			 ClickByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[3]/span')
		 }
		
	}

}

public static ClickMoon(int m_in, int m, boolean sts){
	
	if(sts == true){
		
		String[] moonText = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
		
		try{
			
			moons : for(int j = 1; j < 4; j++){
				
				WebDriver driver = DriverFactory.getWebDriver()
				
				WebElement moons = driver.findElement(By.xpath('//*[@id="datetimepicker_dateview"]/div[1]/div[2]/table/tbody/tr['+j+']'))
		
				List<WebElement> Rows = moons.findElements(By.tagName("td"))
				
				int rowSize = Rows.size()
				
				for (int i = 0; i < rowSize; i++) {
					
					if(Rows.get((i)).getText().equalsIgnoreCase(moonText[m_in -1])) {
						
						Rows.get(i).findElement(By.tagName("a")).click()
						
						days : break
					}
				}
			}
			
		}catch (Exception e) {}
		
	}else{
	
		int m_space
		if(m_in > m){
			
			m_space = m_in - m
			
			for(int i = 0; i < m_space; i++){
				// Click back arrow moons
				ClickByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[3]/span')
			}
		}
		else if(m_in < m){
			
			m_space = m - m_in
			
			for(int i = 0; i < m_space; i++){
				// Click back arrow moons
				ClickByXpath('//*[@id="datetimepicker_dateview"]/div[1]/div[1]/a[1]/span')
			}
		}
	}
}

public static ClickDay(int day){
	
	String dayS = Integer.toString(day)

	try{
	days : for(int j = 1; j < 7; j++){
			
			WebDriver driver = DriverFactory.getWebDriver()
			
			WebElement weeks = driver.findElement(By.xpath('//*[@id="datetimepicker_dateview"]/div[1]/div[2]/table/tbody/tr['+j+']'))
	
			List<WebElement> Rows = weeks.findElements(By.tagName("td"))
			
			int rowSize = Rows.size()
			
			 for (int i = 0; i < rowSize; i++) {
				 
				if(Rows.get((i)).getText().equalsIgnoreCase(dayS) && (Rows.get(j).getAttribute("class") != "k-other-month" || Rows.get(j).getAttribute("class") != "k-other-month k-weekend")) {
					
					
					Rows.get(i).findElement(By.tagName("a")).click()
					
					
					days : break
				}
				
			}
			
		}
	}catch (Exception e) {}
	
	
}

public static ClickByXpath(String xpath){

	int delay = 500

	if(xpath != ""){
		try {
			WebDriver driver = DriverFactory.getWebDriver();

			Thread.sleep(delay);

			driver.findElement(By.xpath(xpath)).click();

			Thread.sleep(delay);
		}
		catch (Exception e) {
			
		}
	}
}

public static GetTextByXpath(String xpath){
	
	int delay = 200

	if(xpath != ""){
		try{
			WebDriver driver = DriverFactory.getWebDriver()
			
			String texta = driver.findElement(By.xpath(xpath)).getText()
			Thread.sleep(delay)
			return texta
			
		}catch(Exception e){
			
		}
	}
}