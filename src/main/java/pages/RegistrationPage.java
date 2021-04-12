package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class RegistrationPage extends BasePage {

    @FindBy(xpath = "//input[@formcontrolname='name']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@formcontrolname='surname']")
    private WebElement surnameField;

    @FindBy(xpath = "//input[@formcontrolname='phone']")
    private WebElement phoneNumberField;

    @FindBy(xpath = "//input[@formcontrolname='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@formcontrolname='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(@class, 'button button_size_large')]")
    private WebElement registrationGreenButton;

    @FindBy(xpath = "//p[text()=' Введите свою эл. почту ' or @class='validation-message']")
    private WebElement invalidEmailMessage;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void enterDataInNameField(String value) {
        nameField.sendKeys(value);
    }

    public void enterDataInSurnameField(String value) {
        surnameField.sendKeys(value);
    }

    public void enterDataInPhoneField(String value) {
        phoneNumberField.sendKeys(value);
    }

    public void enterDataInEmailField() {
        List<Integer> a = new Random()
                .ints(10, 0, 9)
                .boxed()
                .collect(Collectors.toList());
        for (Integer number : a) {
            emailField.sendKeys(number.toString());
        }
    }

    public void enterDataInPasswordField(String value) {
        passwordField.sendKeys(value);
    }

    public void clickRegistrationGreenButton() {
        registrationGreenButton.click();
    }
}
