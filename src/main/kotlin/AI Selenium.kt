import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

// AI GENERATED

val mainPage = "https://qatest-dev.indvp.com/"
val imageNotFound = "/html/body/div[1]/main/div/section/div/div[1]/div[1]/div/div/button/div/span"
val topMenuElement = "(//li[@class='MenuOverlay-Item'])"
val nextPage = "//a[@class='CategoryPagination-Arrow CategoryPagination-Arrow_direction_next']"
val product = "(//p[@itemprop='name' and contains(@class, 'ProductCard-Name') and contains(@class, 'ProductCard-Name_isLoaded')])"
val pldProduct = "p.ProductActions-Title"
val prev__1_2_3_4_5__next = intArrayOf(1, 5, 5, 1, 5, 5, 1, 3)

val topMenuList = arrayOf(
	"NEW IN",
	"PORTMEIRION",
	"KITCHEN & DINNING",
	"HOME DECOR",
	"BED & BATH",
	"GARDEN & OUTDOORS",
	"GIFTS",
	"SALES ROOM"
)

lateinit var driver: WebDriver
lateinit var wait: WebDriverWait
var totalMissingImages = 0

fun main() {
	
	driver = ChromeDriver()
	wait = WebDriverWait(driver, Duration.ofSeconds(10))
	driver.get(mainPage)
	
	
	
	for (i in topMenuList.indices) {
		val sectionName = topMenuList[i]
		println("Opening section: $sectionName")
		
		clickWithRetry("($topMenuElement)[${i + 1}]")
		Thread.sleep(1000)
		
		if (pnf()) {
			println("Page not found for section: $sectionName")
			continue
		}
		
		val totalPages = prev__1_2_3_4_5__next[i]
		navigateThroughPages(totalPages, sectionName)
	}
	
	println("Total 'Image Not Found' across all sections: $totalMissingImages")
	
	driver.quit()
	
	
}

fun navigateThroughPages(totalPages: Int, sectionName: String) {
	for (page in 1..totalPages) {
		println("Section: $sectionName | Page: $page")
		
		if (pnf()) {
			println("Page not found on $sectionName page $page.")
			break
		}
		
		checkProducts(sectionName, page)
		
		if (!clickNextPage()) {
			println("No more pages for $sectionName at page $page.")
			break
		}
		
		Thread.sleep(1000)
	}
}
fun checkProducts(sectionName: String, page: Int) {
	var productList = driver.findElements(By.xpath(product))
	
	if (productList.size > 12) {
		println("Too many products (${productList.size}) on $sectionName page $page. Reloading...")
		refreshPage()
		Thread.sleep(1000)
		productList = driver.findElements(By.xpath(product))
		
	} else if (productList.isEmpty()) {
		println("Empty products list")
		refreshPage()
		Thread.sleep(1000)
		val productListReload = driver.findElements(By.xpath(product))
		productList = productListReload
	}
	
	println("Found ${productList.size} products on $sectionName page $page")
	
	for (index in productList.indices) {
		val productXpath = "($product)[${index + 1}]"
		clickWithRetry(productXpath)
		Thread.sleep(1000)
		
		val missingCount = countImageNotFound(sectionName, page, index + 1)
		totalMissingImages += missingCount
		
		driver.navigate().back()
		Thread.sleep(1000)
		try{
			val element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(product)))
		} catch (e: Exception){
			
			refreshPage()
			println("Refresh Page $page items loading")
		}
		
		
	}
}
fun countImageNotFound(sectionName: String, page: Int, productIndex: Int): Int {
	val missingImages = driver.findElements(By.xpath(imageNotFound))
	val count = missingImages.size
	if (count > 0) {
		println("Section: $sectionName | Page: $page | Product: $productIndex | Missing images: $count")
	}
	return count
}
fun clickNextPage(): Boolean {
	return try {
		clickWithRetry(nextPage)
		true
	} catch (e: Exception) {
		false
	}
}

/**
 * Safe click that retries once if *any* exception occurs.
 * Waits 1 second between attempts.
 */
fun clickWithRetry(xpath: String) {
	try {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)))
		
		val element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
		     element.click()
	} catch (e: Exception) {
		println("Click failed on $xpath. Retrying after 1s. Reason: ${e::class.simpleName}")
		Thread.sleep(1000)
		try {
			val retryElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
				retryElement.click()
		} catch (e2: Exception) {
			println("Retry failed on $xpath. Skipping. Reason: ${e2::class.simpleName}")
		}
	}
}
fun refreshPage() {
	driver.navigate().refresh()
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(topMenuElement)))
}
fun pnf(): Boolean {
	return try {
		val h1Element: WebElement? = driver.findElement(By.tagName("h1"))
		val pElement: WebElement? = driver.findElement(By.className("NoMatch-Subtitle"))
		if (h1Element?.text == "404" || pElement?.text == "Page not found") {
			println("pnf() = true")
			true
		} else false
	} catch (_: NoSuchElementException) {
		false
	}
}
