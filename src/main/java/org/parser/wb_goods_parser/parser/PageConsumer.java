package org.parser.wb_goods_parser.parser;

import com.microsoft.playwright.*;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PageConsumer.class);
    private final OkHttpClient httpClient = new OkHttpClient();
    @Value("${parser.search-postfix-page-num}")
    private String postfix;
    @Value("${parser.search-prefix-catalog}")
    private String prefix;
    @Value("${parser.base-url}")
    private String baseUrl;
    @Value("${parser.user-agent}")
    private String userAgent;

    public void consumePage(String request) {
        String startPageUrl = this.formRequestQuery(request);
        logger.info("Start page url created: {}", startPageUrl);
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setUserAgent(userAgent);
            BrowserContext context = browser.newContext(contextOptions);
            Page page = context.newPage();
            page.navigate(startPageUrl);

            // Ждем, пока элементы загрузятся
            page.waitForSelector(".catalog__content");

            // Получаем содержимое страницы
            String pageContent = page.content();
            Document document = Jsoup.parse(pageContent);
            extractAndPrintMainContent(document);

            browser.close();
            logger.info("Finished parsing.");
        }
    }

    private void extractAndPrintMainContent(@NotNull Document page) {
        // Используем атрибутный селектор для поиска элементов с data-tag="card"
        Elements productElements = page.select("div.product-card.catalog-card[data-tag=card]");
        logger.info("Product elements size: {}", productElements.size());
        for (Element productElement : productElements) {
            String productLink = productElement.select("[data-tag=link]").attr("href");
            String sellerName = productElement.select("[data-tag=brandName] .product-card__brand").text();
            String productName = productElement.select("[data-tag=brandName] .product-card__name").text();
            String productPrice = productElement.select("[data-tag=salePrice]").text() + " " + productElement.select("[data-tag=saleCurrency]").text();

            System.out.printf("Product Link: %s%nSeller Name: %s%nProduct Name: %s%nProduct Price: %s%n", productLink, sellerName, productName, productPrice);
        }
    }

    private @NotNull String formRequestQuery(String request) {
        String requestInUTF8 = URLEncoder.encode(request, StandardCharsets.UTF_8);
        return baseUrl + prefix + requestInUTF8 + postfix;
    }

    private boolean isPageEmpty(@NotNull Document page) {
        logger.info("is page empty call result: {}", page.select("product-snippet").isEmpty());
        return page.select(".product-card").isEmpty();
    }

    private Document getRequiredPage(String url) {
        Document document = null;

        try {
            document = Jsoup
                    .connect(url)
                    .userAgent(userAgent)
                    .get();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return document;
    }
}
