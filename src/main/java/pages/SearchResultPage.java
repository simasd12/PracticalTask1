package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


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

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnProduct(int number) {
        productsOnPageList.get(number - 1).click();
    }

    public WebElement getProductOnPage(int number) {
        return productsOnPageList.get(number - 1);
    }

    public WebElement getEmptyCatalog() {
        return emptyCatalog;
    }

    public WebElement getSortMenuButton() {
        return sortMenuButton;
    }

    public void clickSortMenuButton() {
        sortMenuButton.click();
    }

    public WebElement getDescendingSortButton() {
        return descendingSortButton;
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

    public WebElement getAscendingSortButton() {
        return ascendingSortButton;
    }

    public void clickAscendingSortButton() {
        ascendingSortButton.click();
    }

}
