package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import manager.PageFactoryManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.ProductPage;
import pages.RegistrationPage;
import pages.SearchResultPage;

import java.io.File;
import java.io.IOException;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertTrue;

public class HometaskStepDefinitions {

    private static final long DEFAULT_TIMEOUT = 30;
    private static final long DEFAULT_POLLING_TIMEOUT = 2;
    WebDriver driver;
    PageFactoryManager pageFactoryManager;
    HomePage homePage;
    ProductPage productPage;
    SearchResultPage searchResultPage;
    RegistrationPage registrationPage;
    private static final Logger logger = Logger.getLogger(HometaskStepDefinitions.class.getName());

    @Before
    public void testsSetUp() {
        chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        pageFactoryManager = new PageFactoryManager(driver);
        homePage = pageFactoryManager.getHomePage();
        productPage = pageFactoryManager.getProductPage();
        searchResultPage = pageFactoryManager.getSearchResultPage();
        registrationPage = pageFactoryManager.getRegistrationPage();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                double screenID = (Math.random() * (9901)) + 100;
                File screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File("./Screenshots/" + screenID + "ErrorScreen.png"));
            } catch (IOException e) {
                logger.error("Screenshot error: " + e.getMessage());
            }
        }
        logger.info("Scenario: " + scenario.getName() + " is " + scenario.getStatus());
        driver.close();
    }

    @Given("User opens {string} page")
    public void openHomePage(final String url) {
        homePage.openHomePage(url);
    }

    @When("User enters the {string} in the search field")
    public void userEntersTheValueInTheSearchField(final String value) {
        homePage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, homePage.getSearchField());
        homePage.enterValueAndDoSearch(value);
    }

    @And("User clicks on the {int}th product on the page")
    public void userClicksOnTheNumberProductOnThePage(int number) {
        searchResultPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getFilterSubmitButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getFilterSubmitButton());
        searchResultPage.clickOnProduct(number);
    }

    @And("User checks the presence of the {string} in the product name")
    public void userChecksThePresenceOfTheSearchValueInTheProductName(final String value) {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getProductTittle());
        assertTrue(productPage.isValueInTitle(value));
    }

    @Then("User clicks the buy product button")
    public void userClickTheBuyProductButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getBuyButton());
        productPage.clickBuyButton();
    }

    @And("User checks that the shopping cart window has appeared")
    public void userChecksThatTheShoppingCartWindowHasAppeared() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCartPopup());
        assertTrue(productPage.elementIsEnabled(productPage.getCartPopup()));
    }

    @And("User clicks profile button")
    public void userClicksProfileButton() {
        homePage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        homePage.clickUserProfileButton();
    }

    @When("User clicks registration button")
    public void userClicksRegistrationButton() {
        homePage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, homePage.getRegisterButton());
        homePage.clickRegisterButton();
    }

    @And("User enters the {string} in the name field")
    public void userEntersTheNameInTheNameField(final String name) {
        registrationPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        registrationPage.enterDataInNameField(name);
    }

    @And("User enters the {string} in the surname field")
    public void userEntersTheSurnameInTheSurnameField(final String surname) {
        registrationPage.enterDataInSurnameField(surname);
    }

    @And("User enters the {string} in the phone field")
    public void userEntersThePhoneInThePhoneField(final String phone) {
        registrationPage.enterDataInPhoneField(phone);
    }

    @And("And User enters random invalid data in the email field")
    public void andUserEntersRandomInvalidDataInTheEmailField() {
        registrationPage.enterDataInEmailField();
    }

    @And("User enters the {string} in the password field")
    public void userEntersThePasswordInThePasswordField(final String password) {
        registrationPage.enterDataInPasswordField(password);
    }

    @Then("User clicks green registration button")
    public void userClicksGreenRegistrationButton() {
        registrationPage.clickRegistrationGreenButton();
    }

    @And("User checks error message about invalid email")
    public void userChecksErrorMessageAboutInvalidEmail() {
        registrationPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, registrationPage.getInvalidEmailMessage());
        assertTrue(registrationPage.elementIsEnabled(registrationPage.getInvalidEmailMessage()));
    }

    @Then("User checks that the product catalog is empty")
    public void userChecksThatTheProductCatalogIsEmpty() {
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getEmptyCatalog());
        assertTrue(searchResultPage.elementIsEnabled(searchResultPage.getEmptyCatalog()));
    }

    @And("User choose sort in descending order")
    public void userChooseSortInDescendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.clickDescendingSortButton();
    }

    @And("User checks that {string} sorting works correctly")
    public void userChecksThatSortingWorksCorrectly(final String sort) {
        searchResultPage.refreshPage();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        assertTrue(searchResultPage.checkSorting(sort));
    }

    @Then("User choose sort in ascending order")
    public void userChooseSortInAscendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.clickAscendingSortButton();
    }

    @And("User clicks add to compare button")
    public void userClicksAddToCompareButton() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getAddToCompareButton());
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getAddToCompareButton());
        if (productPage.elementIsEnabled(productPage.getPopupAdvCloseButton())) {
            productPage.clickPopupAdvCloseButton();
        }
        productPage.clickAddToCompareButton();
    }

    @And("User checks that the libra icon appeared in the right corner")
    public void userChecksThatTheLibraIconAppearedInTheRightCorner() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareButton());
        assertTrue(productPage.elementIsEnabled(productPage.getCompareButton()));
    }

    @And("User checks that the number near the libra icon is equal to {string}")
    public void userChecksThatTheNumberNearTheLibraIconIsEqualToCount(String count) {
        assertTrue(productPage.numberOfProductsInListIsCorrect(count));
    }

    @Then("User clicks compare button")
    public void userClicksCompareButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareButton());
        productPage.clickCompareButton();
    }

    @And("User removes product from list")
    public void userRemovesProductFromList() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getRemoveProductFromCompareList());
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getRemoveProductFromCompareList());
        productPage.clickRemoveProductFromCompareList();
    }

    @And("User checks that list is empty")
    public void userChecksThatListIsEmpty() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareListIsEmptyMessage());
        assertTrue(productPage.elementIsEnabled(productPage.getCompareListIsEmptyMessage()));
    }
}
