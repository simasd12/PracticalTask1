package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import manager.PageFactoryManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.ProductPage;
import pages.RegistrationPage;
import pages.SearchResultPage;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertTrue;

public class HometaskStepDefinitions {

    private static final long DEFAULT_TIMEOUT = 60;
    WebDriver driver;
    PageFactoryManager pageFactoryManager;
    HomePage homePage;
    ProductPage productPage;
    SearchResultPage searchResultPage;
    RegistrationPage registrationPage;

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
    public void tearDown() {
        driver.close();
    }

    @Given("User opens {string} page")
    public void openHomePage(final String url) {
        homePage.openHomePage(url);
    }

    @When("User enters the {string} in the search field")
    public void userEntersTheValueInTheSearchField(final String value) {
        homePage.waitVisibilityOfElement(DEFAULT_TIMEOUT, homePage.getSearchField());
        homePage.enterValueAndDoSearch(value);
    }

    @And("User clicks on the {int}th product on the page")
    public void userClicksOnTheNumberProductOnThePage(int number) {
        searchResultPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickOnProduct(number);
    }

    @And("User checks the presence of the {string} in the product name")
    public void userChecksThePresenceOfTheSearchValueInTheProductName(final String value) {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, productPage.getProductTittle());
        assertTrue(productPage.isValueInTitle(value));
    }

    @Then("User clicks the buy product button")
    public void userClickTheBuyProductButton() {
        productPage.clickSpecificationsTabButton(); // нужен, потому что иначе картинка товара закрывает кнопку и тест падает
        productPage.waitClickableOfElement(DEFAULT_TIMEOUT, productPage.getBuyButton());
        productPage.clickBuyButton();
    }

    @And("User checks that the shopping cart window has appeared")
    public void userChecksThatTheShoppingCartWindowHasAppeared() {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, productPage.getCartPopup());
        assertTrue(productPage.elementIsEnabled(productPage.getCartPopup()));
    }

    @And("User clicks profile button")
    public void userClicksProfileButton() {
        homePage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        homePage.clickUserProfileButton();
    }

    @When("User clicks registration button")
    public void userClicksRegistrationButton() {
        homePage.waitClickableOfElement(DEFAULT_TIMEOUT, homePage.getRegisterButton());
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
        registrationPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, registrationPage.getInvalidEmailMessage());
        assertTrue(registrationPage.elementIsEnabled(registrationPage.getInvalidEmailMessage()));
    }

    @Then("User checks that the product catalog is empty")
    public void userChecksThatTheProductCatalogIsEmpty() {
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultPage.getEmptyCatalog());
        assertTrue(searchResultPage.elementIsEnabled(searchResultPage.getEmptyCatalog()));
    }

    @And("User choose sort in descending order")
    public void userChooseSortInDescendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, searchResultPage.getDescendingSortButton());
        searchResultPage.clickDescendingSortButton();
    }

    @And("User checks that {string} sorting works correctly")
    public void userChecksThatSortingWorksCorrectly(final String sort) {
        searchResultPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, searchResultPage.getSortMenuButton());
        assertTrue(searchResultPage.checkSorting(sort));
    }

    @Then("User choose sort in ascending order")
    public void userChooseSortInAscendingOrder() {
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, searchResultPage.getSortMenuButton());
        searchResultPage.clickSortMenuButton();
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.waitClickableOfElement(DEFAULT_TIMEOUT, searchResultPage.getAscendingSortButton());
        searchResultPage.clickAscendingSortButton();
    }
}
