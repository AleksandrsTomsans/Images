package org.example

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
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


