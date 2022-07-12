Feature: Login to appication

  Scenario: Check user is able to create Text file
    Given user is logged in and landed on home page
    When user creates a text file
    Then text file must be created

  Scenario: Check user is able to delete Text file
    Given user is logged in and text file must have been created
    When user deletes a text file
    Then text file must be deleted

  Scenario: Check user is able to restore Text file
    Given user is loged in and text file must have been deleted
    When user restores a text file
    Then text file must be restored

  Scenario: Check the download links are not broken
    Given user must be on home page
    When user click on linux file download
    Then file download links must be working
