package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@name='search']")
    private WebElement searchField;

    @FindBy(xpath = "//rz-user[@class='header-actions__component']/button[@class='header__button']")
    private WebElement userProfileButton;

    @FindBy(xpath = "//a[@class='auth-modal__register-link']")
    private WebElement registerButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage(String url) {
        driver.get(url);
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public void enterValueAndDoSearch(String value){
        searchField.sendKeys(value, Keys.ENTER);
    }

    public void clickUserProfileButton() {
        userProfileButton.click();
    }

    public WebElement getRegisterButton() {
        return registerButton;
    }

    public void clickRegisterButton() {
        registerButton.click();
    }
}
