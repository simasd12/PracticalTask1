package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class SearchResultPage extends BasePage {

    @FindBy(xpath = "//a[@class='goods-tile__picture']")
    private List<WebElement> productsOnPageList;

    @FindBy(xpath = "//div[@class='catalog-empty']")
    private WebElement emptyCatalog;

    @FindBy(xpath = "//select[@_ngcontent-rz-client-c30]")
    private WebElement sortMenuButton;

    @FindBy(xpath = "//option[@value='1: cheap']")
    private WebElement ascendingSortButton;

    @FindBy(xpath = "//option[@value='2: expensive']")
    private WebElement descendingSortButton;

    @FindBy(xpath = "//div[@class='goods-tile']//span[@class='goods-tile__price-value']")
    private List<WebElement> productsPricesList;

    @FindBy(xpath = "//button[contains(@class, \"filter__button\")]")
    private WebElement filterSubmitButton;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnProduct(int number) {
        productsOnPageList.get(number - 1).click();
    }

    public void clickSortMenuButton() {
        sortMenuButton.click();
    }

    public void clickDescendingSortButton() {
        descendingSortButton.click();
    }

    public boolean checkSorting(String sort) {
        double[] array = productsPricesList.stream()
                .map(s -> s.getText().replace(" ", ""))
                .mapToDouble(Double::parseDouble)
                .toArray();
        for (int i = 0; i < array.length - 1; i++) {
            if (sort.equals("desc") && array[i] < array[i + 1]) return false;
            if (sort.equals("asc") && array[i] > array[i + 1]) return false;
        }
        return true;
    }

    public void clickAscendingSortButton() {
        ascendingSortButton.click();
    }

}
