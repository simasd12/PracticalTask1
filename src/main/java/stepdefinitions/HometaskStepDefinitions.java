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
import pages.*;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertTrue;

public class HometaskStepDefinitions {

    private static final long DEFAULT_TIMEOUT = 60;
    WebDriver driver;
    PageFactoryManager pageFactoryManager;
    HomePage homePage;
    ProductPage productPage;
    SearchResultPage searchResultPage;

    @Before
    public void testsSetUp() {
        chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        pageFactoryManager = new PageFactoryManager(driver);
        homePage = pageFactoryManager.getHomePage();
        productPage = pageFactoryManager.getProductPage();
        searchResultPage = pageFactoryManager.getSearchResultPage();
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
        searchResultPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultPage.getProductOnPage(number));
        searchResultPage.clickOnProduct(number);
    }

    @And("User checks the presence of the {string} in the product name")
    public void userChecksThePresenceOfTheSearchValueInTheProductName(final String value) {
        productPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, productPage.getProductTittle());
        assertTrue(productPage.isValueInTitle(value));
    }

    @Then("User click the buy product button")
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
}
