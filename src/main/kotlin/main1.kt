



import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.time.Duration



fun main1() {

	
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\besty\\Desktop\\Programs\\W11\\chromedriver.exe")
	val chromeOptions = ChromeOptions() // Set Chrome options 	https://peter.sh/experiments/chromium-command-line-switches/
	//chromeOptions.addArguments("--headless") // "start-maximized", "window-size=1920,1080", Uncomment the line below if you want to run Chrome in headless mode
	chromeOptions.addArguments("start-maximized")
	val www = ChromeDriver(chromeOptions)
	val web = WebDriverWait(www, Duration.ofSeconds(5))     // for all elements www.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	

	
}
	

