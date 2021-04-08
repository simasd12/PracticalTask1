package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class SearchResultPage extends BasePage{

    @FindBy(xpath = "//a[@class='goods-tile__picture']")
    private List<WebElement> productsOnPageList;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnProduct(int number){
        productsOnPageList.get(number - 1).click();
    }

    public WebElement getProductOnPage(int number) {
        return productsOnPageList.get(number - 1);
    }
}
