package com.pocvlad;

import com.codeborne.selenide.Selenide;
import com.pocvlad.data.Footer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TestPostmanWebPage {


    static Stream<Arguments> checkFooterContent() {
        return Stream.of(Arguments.of(Footer.PRODUCT, List.of("What is Postman?", "API Repository", "Tools", "Governance", "Workspace", "Integrations", "Enterprise", "Plans and pricing", "Download the app", "Support center")),
                Arguments.of(Footer.COMPANY, List.of("About", "Careers and Culture", "Press & Media", "Contact Us", "Partner program")),
                Arguments.of(Footer.SOCIAL, List.of("Twitter", "LinkedIn", "Github", "YouTube", "Twitch"))

        );
    }

    @BeforeEach
    void openPostmanWebSite() {
        Selenide.open("https://www.postman.com/");
    }

    @ValueSource(strings = {"Product", "Pricing", "Enterprise", "Resources and Support", "Explore", "Search Postman", "Sign In", "Sign Up for Free",})
    @ParameterizedTest(name = "Проверить, что в хэдере на главной странице есть элемент с именем {0}")
    void checkHeadersOnHomePage(String headerName) {
        $("#platform_shell_root").find(":first-child").shouldHave(text(headerName));

    }

    @CsvSource(value = {"Free | Start designing, developing, and testing APIs. | 0", "Basic | Collaborate with your team to design, develop, and test APIs faster. | 12", "Professional | Centrally manage the entire API workflow. | 29", "Enterprise | Securely manage, organize, and accelerate API-first development at scale. | Contact sales for Enterprise pricing, support, and benefits",}, delimiter = '|')
    @ParameterizedTest(name = "Проверить, что есть тариф {0}, описание {1} и цена {2}")
    void checkNameDescriptionPrice(String tariff, String description, String price) {
        $(".marketing-header-container__StyledHeaderContainer-sc-1d3vzqw-0").$(byText("Pricing")).click();
        $("#PlansSection").shouldHave(text(tariff));
        $("#PlansSection").shouldHave(text(description));
        $("#PlansSection").shouldHave(text(price));
    }

    @MethodSource
    @ParameterizedTest(name = "Проверить, что в футере заголовок {0} видим и под ним присутствуют элементы {1}")
    void checkFooterContent(Footer footer, List<String> expectedValues) {
        $("[data-test=homepage-footer]").$(byText(footer.getDesc())).shouldBe(visible);
        $("[data-test=homepage-footer]").$(byText(footer.getDesc())).parent().lastChild().$$("a").shouldHave(texts(expectedValues));

    }
}
