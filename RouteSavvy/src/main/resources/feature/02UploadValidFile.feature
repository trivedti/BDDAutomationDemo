Feature: Verify route optimization functionality when user upload  valid file


@test1
Scenario Outline: Verify user is successfully upload location file
  Given User is on Login page
  When User enter token as <token>
  Then User click on SignIn Button
  Then User upload location csv file
  Then User click on Create Route button
  Then User click on Next button
  Then verify that added location display on route listing screen
  Then User click on Map section
  Then User verify upload file location data display on Map section
  
 
  
  
  
   Examples: 
    | token | 
    |  60467f1b79 |
      