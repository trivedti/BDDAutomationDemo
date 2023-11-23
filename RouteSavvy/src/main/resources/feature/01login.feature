Feature: Verify login functionality with valid and invalid Token

@test1
Scenario Outline: Verify login functionality with Valid Token
 Given User is on Login page
  When User enter token as <token>
  Then User click on SignIn Button
  
  Examples:
     |token|
     |60467f1b79 |
 
 
 
@test2
Scenario Outline: Verify login functionality with InValid Token
 Given User is on Login page
  When User enter token as <token>
  Then User click on SignIn Button and verify error message 
  
  Examples:
     |token|
     |60467f1b794 |
   

