package org.example

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths
import java.time.Duration

// REUSABLE FUNCTIONS


fun WebDriver.press(xpath: String){
	this.findElement(By.xpath(xpath)).click();
}
fun WebDriver.getItemCount(xpath: String): Int {
	// Find all elements matching the XPath expression
	val elements: List<WebElement> = this.findElements(By.xpath(xpath))

	// Get the count of elements
	return elements.size
}
fun WebDriver.pnf(): Boolean {
	try{
		val h1Element: WebElement? = this.findElement(By.tagName("h1"))
		val pElement: WebElement? = this.findElement(By.className("NoMatch-Subtitle"))

		if (h1Element?.text == "404" || pElement?.text == "Page not found") {
			println("pnf()")
			return true
		}else{
			return false
		}
	}catch (_: NoSuchElementException){
		return false
	}

}
fun WebDriver.loadAllElements(xpath:String ) {
	val web = WebDriverWait(this, Duration.ofSeconds(5))

	try{
		web.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
	}catch (e: NoSuchElementException){
		println("loadAllElements() Exception 1")
	}

	val index = this.getItemCount(xpath)

	try {
		for (plp in 1..index) {
			web.until(ExpectedConditions.elementToBeClickable(By.xpath("$xpath[$plp]")))
		}

	}catch (e: TimeoutException){
		println("loadAllElements() Exception")
	}

}
fun WebDriver.loadSingleElement(item:String){
	val web = WebDriverWait(this, Duration.ofSeconds(5))
	web.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(item)))
}

/**
 * Creates a CSV file from a 2D list of strings.
 *
 * @param filePath The full path where the CSV file will be saved
 * @param data A list of rows, where each row is a list of string values
 * @return Unit
 *
 * Example:
 * val data = listOf(
 *
 *     listOf("Name", "Age", "Town"),
 *     listOf("Name", "Age", "Town"),
 *     listOf("Name", "Age", "Town"),
 *
 * )
 * createCSVFile("C:\\Users\\name\\Desktop\\output.csv",  data)
 * // Creates a CSV file at the specified path
 */
fun createCSVFile(filePath: String, data: List<List<String>>) {
	val file = File(filePath)

	file.bufferedWriter().use { out ->
		// Write data to CSV file
		data.forEach { row ->
			out.write(row.joinToString(","))
			out.newLine()
		}
	}
	println("CSV file created successfully at $filePath")
}

fun generateDesktopCSVReport(dataRows: List<List<String>>) {
	// 1. Get the current logged-in user's home directory (e.g., C:\Users\JohnDoe or /Users/johndoe)
	val userHome = System.getProperty("user.home")
	
	// 2. Build a cross-platform pathway straight to the Desktop
	val csvFilePath = Paths.get(userHome, "Desktop", "missing_images_report.csv").toString()
	
	// 3. Open the file writer and process the data rows
	FileWriter(csvFilePath).use { writer ->
		// Write the CSV Header column layout
		writer.append("Category,Page,Item,URL,Product Name,Images Not Found\n")
		
		// Loop and write data records
		for (row in dataRows) {
			writer.append(row.joinToString(separator = ",")).append("\n")
		}
	}
	
	println("📊 Report successfully saved to your Desktop at: $csvFilePath")
}
