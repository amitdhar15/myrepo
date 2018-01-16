#Technology stack
Java(TM) SE Runtime Environment (build 1.8.0_131-b11),Cucumber latest version ,selenium webdriver, maven

#How to run the tests

#Steps to Execute the Scenario on local machine and local brower in Eclipse:

Step 1: Clone the repository into your machine

Step 2: Navigate to repository 

Step 3: Import project in Eclipse

Step 4: Run runner.java

#Steps to Execute the Scenario on local machine and local browser in Command Line:

Step 1: Clone the repository into your machine

Step 2: Navigate to repository 

Step 3: In order to build locally just run: `mvn clean install`, this will run the test using Chrome


if you would like to use PhantomJS (Headless) just run:  

	mvn clean install -Dbrowser=phantomjs

If you would like to to run against another environment you can override the web frontend and backend CRM URLs with

	mvn clean install -Dweb-base-url=http://localhost:8000/iam -Dcrm-base-url=http://localhost:9900/idp-backend

or

	mvn clean install -Dweb-base-url=http://localhost:8000/iam -Dcrm-base-url=http://localhost:9900/idp-backend -Dbrowser=phantomjs



