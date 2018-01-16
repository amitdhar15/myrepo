#Technology stack
Java(TM) SE Runtime Environment (build 1.8.0_131-b11),Cucumber latest version ,selenium webdriver, maven

#How to run the tests
In order to build locally just run:  
`mvn clean install`, this will run the test using Chrome / FireFox against DevQA

if you would like to use PhantomJS (Headless) just run:  

	mvn clean install -Dbrowser=phantomjs

If you would like to to run against another environment you can override the web frontend and backend CRM URLs with

	mvn clean install -Dweb-base-url=http://localhost:8000/iam -Dcrm-base-url=http://localhost:9900/idp-backend

or

	mvn clean install -Dweb-base-url=http://localhost:8000/iam -Dcrm-base-url=http://localhost:9900/idp-backend -Dbrowser=phantomjs


Note that the latest version of Mozilla / Firefox (49) doesn't work with this version of selenium so you need to downgrade to 47.0.1. 

Note that if there are failures when running with PhantomJS then often there are phantomjs processes left running, which can use considerable resources. You will probably need to kill them manually.

Note for Linux users: For this static build, the binary is self-contained. There is no requirement to install Qt, WebKit, or any other libraries. It however still relies on Fontconfig (the package fontconfig or libfontconfig, depending on the distribution). The system must have GLIBCXX_3.4.9 and GLIBC_2.7.
