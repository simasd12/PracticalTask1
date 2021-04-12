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
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.ProductPage;
import pages.RegistrationPage;
import pages.SearchResultPage;

import java.io.File;
import java.io.IOException;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertTrue;

public class HometaskSteps {

    private static final long DEFAULT_TIMEOUT = 30;
    private static final long DEFAULT_POLLING_TIMEOUT = 2;
    WebDriver driver;
    PageFactoryManager pageFactoryManager;
    HomePage homePage;
    ProductPage productPage;
    SearchResultPage searchResultPage;
    RegistrationPage registrationPage;
    private static final Logger logger = Logger.getLogger(HometaskSteps.class.getName());

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

    @Given("open {string} page")
    public void openHomePage(final String url) {
        homePage.openHomePage(url);
    }

    @When("enter {string} in search field")
    public void EnterValueInSearchField(final String value) {
        homePage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, homePage.getSearchField());
        homePage.enterValueAndDoSearch(value);
    }

    @And("click {string}st product on page")
    public void clickProductOnPage(String number) {
        searchResultPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getFilterSubmitButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getFilterSubmitButton());
        searchResultPage.clickOnProduct(Integer.parseInt(number));
    }

    @Then("check presence of {string} in product name")
    public void checkPresenceOfSearchValueInProductName(final String value) {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getProductTittle());
        assertTrue(productPage.isValueInTitle(value));
    }

    @When("click buy product button")
    public void clickBuyProductButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getSpecificationsButton());
        productPage.clickSpecificationsButton();
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getBuyButton());
        productPage.clickBuyButton();
    }

    @Then("check that cart window has appeared")
    public void checkThatCartWindowHasAppeared() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCartPopup());
        assertTrue(productPage.elementIsEnabled(productPage.getCartPopup()));
    }

    @When("click profile button")
    public void clickProfileButton() {
        homePage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        homePage.clickUserProfileButton();
    }

    @And("click registration button")
    public void clickRegistrationButton() {
        homePage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, homePage.getRegisterButton());
        homePage.clickRegisterButton();
    }

    @And("enter {string} in name field")
    public void enterNameInNameField(final String name) {
        registrationPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        registrationPage.enterDataInNameField(name);
    }

    @And("enter {string} in surname field")
    public void enterSurnameInSurnameField(final String surname) {
        registrationPage.enterDataInSurnameField(surname);
    }

    @And("enter {string} in phone field")
    public void enterPhoneInPhoneField(final String phone) {
        registrationPage.enterDataInPhoneField(phone);
    }

    @And("enter random invalid data in email field")
    public void enterRandomInvalidDataInEmailField() {
        registrationPage.enterDataInEmailField();
    }

    @And("enter {string} in password field")
    public void enterPasswordInPasswordField(final String password) {
        registrationPage.enterDataInPasswordField(password);
    }

    @And("click green registration button")
    public void clickGreenRegistrationButton() {
        registrationPage.clickRegistrationGreenButton();
    }

    @Then("check error message about invalid email")
    public void checksErrorMessageAboutInvalidEmail() {
        registrationPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, registrationPage.getInvalidEmailMessage());
        assertTrue(registrationPage.elementIsEnabled(registrationPage.getInvalidEmailMessage()));
    }

    @Then("check that product catalog is empty")
    public void checkThatProductCatalogIsEmpty() {
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getEmptyCatalog());
        assertTrue(searchResultPage.elementIsEnabled(searchResultPage.getEmptyCatalog()));
    }

    @And("choose sort in descending order")
    public void chooseSortInDescendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.clickDescendingSortButton();
    }

    @Then("check that {string} sorting works correctly")
    public void checksThatSortingWorksCorrectly(final String sort) {
        searchResultPage.refreshPage();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        assertTrue(searchResultPage.checkSorting(sort));
    }

    @And("choose sort in ascending order")
    public void chooseSortInAscendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.clickAscendingSortButton();
    }

    @And("click add to compare button")
    public void clickAddToCompareButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getSpecificationsButton());
        productPage.clickSpecificationsButton();
        try {
            productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getPopupAdvCloseButton());
            if (productPage.elementIsEnabled(productPage.getPopupAdvCloseButton())) {
                productPage.clickPopupAdvCloseButton();
            }
        } catch (NoSuchElementException e) {
            logger.error("Popup adv is not appeared: " + e.getMessage());
        } catch (StaleElementReferenceException e) {
            logger.error("Popup adv error: " + e.getMessage());
        }
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getAddToCompareButton());
        productPage.clickAddToCompareButton();
    }

    @Then("check that libra icon appeared in right corner")
    public void checkThatLibraIconAppearedInRightCorner() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareButton());
        assertTrue(productPage.elementIsEnabled(productPage.getCompareButton()));
    }

    @And("check that number near libra icon is equal to {string}")
    public void checkThatNumberNearLibraIconIsEqualToCount(String count) {
        assertTrue(productPage.numberOfProductsInListIsCorrect(count));
    }

    @When("click compare button")
    public void clickCompareButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareButton());
        productPage.clickCompareButton();
    }

    @And("remove product from list")
    public void removeProductFromList() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getRemoveProductFromCompareList());
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getRemoveProductFromCompareList());
        productPage.clickRemoveProductFromCompareList();
    }

    @Then("check that list is empty")
    public void checkThatListIsEmpty() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareListIsEmptyMessage());
        assertTrue(productPage.elementIsEnabled(productPage.getCompareListIsEmptyMessage()));
    }
}
