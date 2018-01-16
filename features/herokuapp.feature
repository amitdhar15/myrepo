Feature: HerokuApp


Background:
    Given User is on "Home-Page"
    
@regression
Scenario: Successful Login with Valid Credentials
	When User navigated to "Login-Page"
	And enter "HerotxtUsernameField" as "tomsmith"
    And enter "HerotxtPasswordField" as "SuperSecretPassword!"
    And Click on "HeroSubmitbutton"
    Then text should exist as "You logged into a secure area!" 

@regression
Scenario: Validate Username Field
	When User navigated to "Login-Page"
	And enter "HerotxtUsernameField" as "WrongUsername"
	And enter "HerotxtPasswordField" as "SuperSecretPassword!"
    And Click on "HeroSubmitbutton"
    Then text should exist as "Your username is invalid!" 

@regression
Scenario: Validate Password Field
	When User navigated to "Login-Page"
	And enter "HerotxtUsernameField" as "tomsmith"
    And enter "HerotxtPasswordField" as "WrongPassword"
    And Click on "HeroSubmitbutton"
    Then text should exist as "Your password is invalid!" 

@regression 
Scenario: Hovers
	When User navigated to "Hover-Page"
	Given move mouse on "FirstPicture"
	Then text should exist as "name: user1" 
	Given move mouse on "SecondPicture"
	Then text should exist as "name: user2" 
	Given move mouse on "ThirdPicture"
	Then text should exist as "name: user3" 

@regression	
Scenario: sortTables
	
	When User navigated to "Login-table"
    Given table sort "table2"
	
	
@0ff
Scenario Outline: Successful Login with Valid Credentials
	Given User is on "Home-Page"
	When User navigated to "Login-Page"
	And enter value "<path>" and "<value>" 
	And Click on "HeroSubmitbutton"
    Then text should exist as "You logged into a secure area!" 
	
	Examples:
	|path				  |value               |
	|HerotxtUsernameField |tomsmith            |
	|HerotxtPasswordField |SuperSecretPassword!|








	