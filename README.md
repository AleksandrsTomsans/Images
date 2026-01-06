#Selenium Web Automation – Image Validation Scanner (Kotlin)
#This project is a Kotlin + Selenium WebDriver automation script that crawls an e-commerce website and detects product pages with missing images.

#The script:
      Navigates through top menu categories
      Iterates pagination
      Opens each product detail page
      Detects missing product images
      Exports all failures to a CSV report
  
It is designed to simulate real user navigation and handle unstable pages (reloads, 404s, slow loading elements).

Purpose

This project demonstrates:
      Web UI automation
      Test resilience for flaky UI
      Dynamic element handling
      Data extraction and reporting
      Real-world QA problem solving

Typical use cases:
      Regression testing
      Content validation
      E-commerce quality monitoring
      Tech Stack
      Language: Kotlin
      Automation: Selenium WebDriver
      Browser: Google Chrome
      Driver Management: WebDriverManager
      Build Tool: (add if applicable, e.g. Gradle)
      Output: CSV file

Key Features

Robust Navigation

Handles:
      Page reloads
      404 / “Page not found” states
      Dynamic content loading
      Uses explicit waits for stability

Full Site Traversal

Loops through:
      Top navigation menu
      Multiple category pages
      All visible products per page

✔ Intelligent Element Handling
        Retries element loading
        Waits for clickability
        Counts elements dynamically

✔ Failure Detection
      Identifies missing product images
      Captures:
            Category
            Page number
            Item index
            Product URL
            Product name
            Missing image count

✔ Reporting
      Exports results into a CSV file
      Easy to analyze or attach to defect reports

📂 Output Example (CSV)
Category,Page,Item,URL,Product Name,Images Not Found
HOME DECOR,Page 2,Item 5,https://example.com/product,Porcelain Cake Stand,2

How to Run

Prerequisites
    Java 11+
    Google Chrome installed
    IntelliJ IDEA recommended

Steps
      Clone the repository
      Open the project in IntelliJ
      (Optional) Enable headless mode:
            chromeOptions.addArguments("--headless")

Run main()

CSV report will be generated at:
      C:\Users\<username>\Desktop\Programs\file.csv

Design Notes
      Uses explicit waits instead of implicit waits
      Includes retry logic for unstable pages
      Functions are designed to be reusable
      Focused on real-world UI instability, not ideal conditions

Possible Improvements
      Parameterize URLs and output path
      Add logging framework
      Add TestNG / JUnit structure
      Parallel execution
      Screenshot capture on failures
      Dockerized execution
Author

Aleksandrs
QA Automation
