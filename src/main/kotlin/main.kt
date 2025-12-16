package org.example


import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.time.Duration



fun main() {
	
	
	WebDriverManager.chromedriver().setup()
	val chromeOptions = ChromeOptions() // Set Chrome options 	https://peter.sh/experiments/chromium-command-line-switches/
	//chromeOptions.addArguments("--headless") // "start-maximized", "window-size=1920,1080", Uncomment the line below if you want to run Chrome in headless mode
	chromeOptions.addArguments("start-maximized")
	val www = ChromeDriver(chromeOptions)
	val web = WebDriverWait(www, Duration.ofSeconds(5))	// for all elements www.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	
	// Reusable Functions
	fun refreshPage(repeat:Int = 1) {
		
		val page = www.currentUrl
		
		repeat(repeat){
			www.get(page)
			println("Refresh page $page")
		}
		
	}
	fun press(xpath: String){
		try{
			web.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
			www.findElement(By.xpath(xpath)).click()
		}catch (e: Exception){
			refreshPage()
			www.findElement(By.xpath(xpath)).click()
		}
		
	}
	
	fun getItemCount(xpath: String): Int {

		try{
			// Find all elements matching the XPath expression
			val elements: List<WebElement> = www.findElements(By.xpath(xpath))

			// Get the count of elements
			return elements.size
		}catch (e:TimeoutException){
			print("getItemCount Exception, ")
			return 0
		}

	}
	fun pnf(): Boolean {
		try{
			val h1Element: WebElement? = www.findElement(By.tagName("h1"))
			val pElement: WebElement? = www.findElement(By.className("NoMatch-Subtitle"))

			if (h1Element?.text == "404" || pElement?.text == "Page not found") {
				print("pnf() = ")
				return true
			}else{
				return false
			}
		}catch (_:NoSuchElementException){
			return false
		}

	}
	fun loadAllElements(xpath:String ) {
		
		val web1 = WebDriverWait(www, Duration.ofSeconds(3))
		
		try {
			web1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
		}catch (e:TimeoutException){
			print("loadAllElements() Exception no elements ")
		}

		val index = getItemCount(xpath)

		try {
			for (plp in 1..index) {
				web.until(ExpectedConditions.elementToBeClickable(By.xpath("$xpath[$plp]")))
			}

		}catch (e:TimeoutException){
			print("loadAllElements() Exception, ")
		}

	}
	fun loadSingleElement(item:String){
		val web1 = WebDriverWait(www, Duration.ofSeconds(1))
		web1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(item)))
	}
	fun String.text():String{
		
		fun string (): Boolean {
			val vowel = setOf('.')
			val string = this.count{ it in vowel }
			
			return string<1
		}
		
		return if(string()){
			www.findElement(By.xpath(this)).text
			
		}else{
			www.findElement(By.cssSelector(this)).text
		}
		
		
		
		
	}

	fun String.itemCount(num: Int): Int {
		
		var elementsList = 0
		var countReloads = 1
		val page = www.currentUrl
		
		
		try
		{
			while (countReloads <= 5)
			{
				web.until(ExpectedConditions.elementToBeClickable(By.xpath(this)))
				
				val elements: List<WebElement> = www.findElements(By.xpath(this))
				val itemsCounted = elements.size
				
				if (itemsCounted > num) {
					elementsList = itemsCounted
					println("$itemsCounted items found. Reloading page...")
					
					www.get(page)
					
					//www.navigate().refresh()
					countReloads++
				} else {
					println("xpath.itemCount $itemsCounted items")
					return itemsCounted  // when "return" is executed it immediately exits the function,
									//  which also stops all loops
				}
			}
		} catch (e: Exception) {
			println("string.ItemCount Exception: ${e.message}")
		}
		
		return elementsList
	}
	
	
	
	
	val mainPage = "https://qatest-dev.indvp.com/"
	val imageNotFound = "(/html/body/div[1]/main/div/section/div/div[1]/div[1]/div/div/button/div/span)"
	val topMenuElement = "(//li[@class='MenuOverlay-Item'])" // 8 items in menu with same xpath
	val nextPage = "//a[@class='CategoryPagination-Arrow CategoryPagination-Arrow_direction_next']"
	val product =   "(//p[@itemprop='name' and contains(@class, 'ProductCard-Name') and contains(@class, 'ProductCard-Name_isLoaded')])"
	//			  <  p  itemprop="name" class="ProductCard-Name ProductCard-Name_isLoaded">100% Irish Linen Tea Towels - Set of Two</p>
	val pldProduct = "p.ProductActions-Title"
	//               <p itemprop="name" class="ProductActions-Title">Amor Porcelain Cake Stand - 32cm</p>
	val prev__1_2_3_4_5__next = intArrayOf(4, 5, 5, 1, 5, 5, 1, 3)


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

	
	www.get(mainPage)
	
	val listListData = mutableListOf<List<String>>()
	
	// Loop through MENU TOP BAR
	for((page, element) in topMenuList.withIndex()) { //
		
		
		val index = page+1
		
		if(pnf()){ refreshPage(3) }
		if(pnf()){
			
			println("Page not found, Skipp $element 1st loop")
			press("$topMenuElement[$index]")
			
			continue
			
			
		}
		else{
			println(element)
			press("$topMenuElement[$index]") // "(//li[@class='MenuOverlay-Item'])[1]" -> "new in" first plp menu

			println("clicksNeeded = ${prev__1_2_3_4_5__next[page]} 1st loop")
			
			var pageNum = 1
			
			// Loop through pages of each MENU
			for(num in 1..prev__1_2_3_4_5__next[page]) {
				println("Page number = $pageNum  2nd loop" )
				Thread.sleep(1000)
				
				val itemCount = product.itemCount(12)
				
				if(pnf()){ refreshPage() }
				if(pnf()){
					println("Page not found, Skipp 2nd loop")
					continue
				}
				else{
					Thread.sleep(3000)
					val plpURL = www.currentUrl
					
					
					println("Number of items: $itemCount")
					
					if(pnf()){ refreshPage(3) }
					if(pnf()){
						println("Page not found, Skipp page ")
						www.get(plpURL)
						continue
						
					}else{
						// Loop through each item .click() items for every page
						for(itemNum in 1..itemCount) {
							
							if(pnf()) { refreshPage(3) }
							if(pnf()) {
								println("PLD PNF Skipp Item ")
								www.get(plpURL)
								continue
							}else{
								Thread.sleep(3000)
								
								print("Press ")
								loadAllElements(product)
								
								print("item $itemNum	     3rd loop ")
								
								if(pnf()){refreshPage(3)}
								if(pnf()){
									www.get(plpURL)
									println("Item $itemNum")
									continue
								}else{
									
									press("$product[$itemNum]")
									loadAllElements(imageNotFound)
									val infNum = getItemCount(imageNotFound)
								
									
									// Images not found will count and report all details
									if( infNum>0 )
									{
										val pageRow = mutableListOf<String>()
										
										pageRow.add(element)
										pageRow.add(" Page $pageNum ")
										pageRow.add("Item $itemNum")
										pageRow.add(www.currentUrl)
										pageRow.add(pldProduct.text())
										pageRow.add("Images not found $infNum")
										println("Images not found $infNum")
										
										listListData.add(pageRow)
										
									
										
									}else{
										println("")
									}
									
									
									
									www.get(plpURL)
								}
								
							}
							
						}
						// println("LLD $listListData")
					}
					
					
					
					try {
						
						loadSingleElement(nextPage)
						press(nextPage)
						
					}catch (_:TimeoutException){}

					pageNum++


				}
			}


			
			
			
		}
		
	}
	
	println(listListData)
	//Thread.sleep(10000)
	
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

	// Create CSV file
	val filePath = "C:\\Users\\besty\\Desktop\\Programs\\file.csv"
	createCSVFile(filePath, listListData)
	www.quit()
	
	
	
}


