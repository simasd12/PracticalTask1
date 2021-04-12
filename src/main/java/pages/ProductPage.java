package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ProductPage extends BasePage {

    @FindBy(xpath = "//h1[@class='product__title']")
    private WebElement productTittle;

    @FindBy(xpath = "//button[contains(@class, 'buy-button button button_')]")
    private WebElement buyButton;

    @FindBy(xpath = "//single-modal-window")
    private WebElement cartPopup;

    @FindBy(xpath = "//button[@class='compare-button']")
    private WebElement addToCompareButton;

    @FindBy(xpath = "//rz-comparison[@class='header-actions__component']//button[@class='header__button']")
    private WebElement compareButton;

    @FindBy(xpath = "//span[@class='counter counter--gray']")
    private WebElement numberOfProductsInCompareList;

    @FindBy(xpath = "//button[contains(@class, 'remove')]")
    private WebElement removeProductFromCompareList;

    @FindBy(xpath = "//h3[@class='comparison-modal__heading']")
    private WebElement compareListIsEmptyMessage;

    @FindBy(xpath = "//span[@class='exponea-close-cross']")
    private WebElement popupAdvCloseButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isValueInTitle(String value) {
        return productTittle.getText().contains(value);
    }

    public void clickBuyButton() {
        buyButton.click();
    }

    public void clickAddToCompareButton() {
        addToCompareButton.click();
    }

    public boolean numberOfProductsInListIsCorrect(String number) {
        return numberOfProductsInCompareList.getText().contains(number);
    }

    public void clickRemoveProductFromCompareList() {
        removeProductFromCompareList.click();
    }

    public void clickCompareButton() {
        compareButton.click();
    }

    public void clickPopupAdvCloseButton() {
        popupAdvCloseButton.click();
    }
}
