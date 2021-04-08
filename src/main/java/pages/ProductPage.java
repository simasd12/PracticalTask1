package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage{

    @FindBy(xpath = "//h1[@class='product__title']")
    private WebElement productTittle;

    @FindBy(xpath = "//a[text()=' Характеристики ']")
    private WebElement specificationsTabButton;

    @FindBy(xpath = "//button[contains(@class, 'buy-button button button_')]")
    private WebElement buyButton;

    @FindBy(xpath = "//single-modal-window")
    private WebElement cartPopup;

    @FindBy(xpath = "//button[@class='compare-button']")
    private WebElement addToCompareButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getProductTittle() {
        return productTittle;
    }

    public boolean isValueInTitle(String value){return productTittle.getText().contains(value);}

    public void clickSpecificationsTabButton(){specificationsTabButton.click();}

    public void clickBuyButton(){
        buyButton.click();
    }

    public WebElement getBuyButton() {
        return buyButton;
    }

    public WebElement getCartPopup() {
        return cartPopup;
    }

    public WebElement getAddToCompareButton() {
        return addToCompareButton;
    }

    public void clickAddToCompareButton() {
        addToCompareButton.click();
    }
}
