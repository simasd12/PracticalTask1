package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@name='search']")
    private WebElement searchField;

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

}
