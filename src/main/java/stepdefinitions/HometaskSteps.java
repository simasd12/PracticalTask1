package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j;
import manager.PageFactoryManager;
import org.apache.commons.io.FileUtils;
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

@Log4j
public class HometaskSteps {

    private static final long DEFAULT_TIMEOUT = 10;
    private static final long DEFAULT_POLLING_TIMEOUT = 2;
    WebDriver driver;
    PageFactoryManager pageFactoryManager;
    HomePage homePage;
    ProductPage productPage;
    SearchResultPage searchResultPage;
    RegistrationPage registrationPage;

    @Before
    public void testsSetUp(Scenario scenario) {
        chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        pageFactoryManager = new PageFactoryManager(driver);
        homePage = pageFactoryManager.getHomePage();
        productPage = pageFactoryManager.getProductPage();
        searchResultPage = pageFactoryManager.getSearchResultPage();
        registrationPage = pageFactoryManager.getRegistrationPage();
        log.info("################################################################################################");
        log.info("############################## Start scenario: " + scenario.getName() + " ##############################");
        log.info("################################################################################################");
        log.info("@Before method works correctly");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                double screenID = (Math.random() * (9901)) + 100;
                File screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File("./screenshots/" + screenID + "errorScreen.png"));
            } catch (IOException e) {
                log.error("Screenshot error: " + e.getMessage());
            }
        }
        driver.close();
        log.info("@After method works correctly");
        log.info("Scenario: " + scenario.getName() + " is " + scenario.getStatus());
    }

    @Given("open {string} page")
    public void openHomePage(final String url) {
        homePage.openHomePage(url);
        log.info("Open page " + url);
    }

    @When("enter {string} in search field")
    public void enterValueInSearchField(final String value) {
        homePage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, homePage.getSearchField());
        homePage.enterValueAndDoSearch(value);
        log.info("Enter '" + value + "' and do search");
    }

    @And("click {string}st product on page")
    public void clickProductOnPage(final String number) {
        searchResultPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getFilterSubmitButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getFilterSubmitButton());
        searchResultPage.clickOnProduct(Integer.parseInt(number));
        log.info("Click " + number + "st on page");
    }

    @Then("{string} is presence in product name")
    public void checkPresenceOfSearchValueInProductName(final String value) {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getProductTittle());
        assertTrue(productPage.isValueInTitle(value));
        log.info(value + " is presence in product name");
    }

    @When("click buy product button")
    public void clickBuyProductButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getSpecificationsButton());
        productPage.clickSpecificationsButton();
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getBuyButton());
        productPage.clickBuyButton();
        log.info("Click buy product button");
    }

    @Then("cart window has appeared")
    public void checkThatCartWindowHasAppeared() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCartPopup());
        assertTrue(productPage.elementIsEnabled(productPage.getCartPopup()));
        log.info("Cart window has appeared");
    }

    @When("click profile button")
    public void clickProfileButton() {
        homePage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        homePage.clickUserProfileButton();
        log.info("Click profile button");
    }

    @And("click registration button")
    public void clickRegistrationButton() {
        homePage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, homePage.getRegisterButton());
        homePage.clickRegisterButton();
        log.info("Click registration button");
    }

    @And("enter {string} in name field")
    public void enterNameInNameField(final String name) {
        registrationPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        registrationPage.enterDataInNameField(name);
        log.info("Enter '" + name + "' in name field");
    }

    @And("enter {string} in surname field")
    public void enterSurnameInSurnameField(final String surname) {
        registrationPage.enterDataInSurnameField(surname);
        log.info("Enter '" + surname + "' in surname field");
    }

    @And("enter {string} in phone field")
    public void enterPhoneInPhoneField(final String phone) {
        registrationPage.enterDataInPhoneField(phone);
        log.info("Enter '" + phone + "' in phone field");
    }

    @And("enter random invalid data in email field")
    public void enterRandomInvalidDataInEmailField() {
        registrationPage.enterDataInEmailField();
        log.info("Enter random number in email field");
    }

    @And("enter {string} in password field")
    public void enterPasswordInPasswordField(final String password) {
        registrationPage.enterDataInPasswordField(password);
        log.info("Enter '" + password + "' in password field");
    }

    @And("click green registration button")
    public void clickGreenRegistrationButton() {
        registrationPage.clickRegistrationGreenButton();
        log.info("Click green registration button");
    }

    @Then("error message about invalid email")
    public void checkErrorMessageAboutInvalidEmail() {
        registrationPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, registrationPage.getInvalidEmailMessage());
        assertTrue(registrationPage.elementIsEnabled(registrationPage.getInvalidEmailMessage()));
        log.info("Error message about invalid email");
    }

    @Then("product catalog is empty")
    public void checkThatProductCatalogIsEmpty() {
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getEmptyCatalog());
        assertTrue(searchResultPage.elementIsEnabled(searchResultPage.getEmptyCatalog()));
        log.info("Product catalog is empty");
    }

    @And("choose sort in descending order")
    public void chooseSortInDescendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.clickDescendingSortButton();
        log.info("Choose sort in descending order");
    }

    @Then("{string} sorting works correctly")
    public void checkThatSortingWorksCorrectly(final String sort) {
        searchResultPage.refreshPage();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        assertTrue(searchResultPage.checkSorting(sort));
        log.info(sort + " sorting works correctly");
    }

    @When("choose sort in ascending order")
    public void chooseSortInAscendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.clickAscendingSortButton();
        log.info("Choose sort in ascending order");
    }

    @And("click add to compare button")
    public void clickAddToCompareButton() {
        try {
            productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getPopupAdvCloseButton());
            if (productPage.elementIsEnabled(productPage.getPopupAdvCloseButton())) {
                productPage.clickPopupAdvCloseButton();
            }
        } catch (NoSuchElementException e) {
            log.error("Popup adv is not appeared: " + e.getMessage());
        } catch (TimeoutException e) {
            log.error("Popup adv time error: " + e.getMessage());
        } catch (StaleElementReferenceException e) {
            log.error("Popup adv error: " + e.getMessage());
        } finally {
            productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getAddToCompareButton());
            productPage.clickAddToCompareButton();
            log.info("Click add to compare button");
        }
    }

    @Then("libra icon appeared in right corner")
    public void checkThatLibraIconAppearedInRightCorner() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareButton());
        assertTrue(productPage.elementIsEnabled(productPage.getCompareButton()));
        log.info("Libra icon appeared in right corner");
    }

    @And("number near libra icon is equal to {string}")
    public void checkThatNumberNearLibraIconIsEqualToCount(final String count) {
        assertTrue(productPage.numberOfProductsInListIsCorrect(count));
        log.info("Number near libra icon is equal to " + count);
    }

    @When("click compare button")
    public void clickCompareButton() {
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareButton());
        productPage.clickCompareButton();
        log.info("Click compare button");
    }

    @And("remove product from list")
    public void removeProductFromList() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getRemoveProductFromCompareList());
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getRemoveProductFromCompareList());
        productPage.clickRemoveProductFromCompareList();
        log.info("Remove product from list");
    }

    @Then("list is empty")
    public void checkThatListIsEmpty() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, DEFAULT_POLLING_TIMEOUT, productPage.getCompareListIsEmptyMessage());
        assertTrue(productPage.elementIsEnabled(productPage.getCompareListIsEmptyMessage()));
        log.info("List is empty");
    }
}
