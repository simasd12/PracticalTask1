package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//h1[@class='product__title']")
    private WebElement productTittle;

    @FindBy(xpath = "//button[contains(@class, 'buy-button button button_')]")
    private WebElement buyButton;

    @FindBy(xpath = "//single-modal-window")
    private WebElement cartPopup;

    @FindBy(xpath = "//button[@class='compare-button']")
    private WebElement addToCompareButton;

    @FindBy(xpath = "//button[@aria-label='Списки сравнения']")
    private WebElement compareButton;

    @FindBy(xpath = "//span[@class='counter counter--gray']")
    private WebElement numberOfProductsInCompareList;

    @FindBy(xpath = "//button[contains(@class, 'remove')]")
    private WebElement removeProductFromCompareList;

    @FindBy(xpath = "//h3[@class='comparison-modal__heading']")
    private WebElement compareListIsEmptyMessage;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getProductTittle() {
        return productTittle;
    }

    public boolean isValueInTitle(String value) {
        return productTittle.getText().contains(value);
    }

    public void clickBuyButton() {
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

    public boolean numberOfProductsInListIsCorrect(String number) {
        return numberOfProductsInCompareList.getText().contains(number);
    }

    public WebElement getRemoveProductFromCompareList() {
        return removeProductFromCompareList;
    }

    public void clickRemoveProductFromCompareList() {
        removeProductFromCompareList.click();
    }

    public WebElement getCompareButton() {
        return compareButton;
    }

    public void clickCompareButton() {
        compareButton.click();
    }

    public WebElement getCompareListIsEmptyMessage() {
        return compareListIsEmptyMessage;
    }
}
