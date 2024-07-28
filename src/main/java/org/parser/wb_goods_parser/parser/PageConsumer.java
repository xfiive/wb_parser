package org.parser.wb_goods_parser.parser;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PageConsumer.class);

    @Value("${parser.search-postfix-page-num}")
    private String postfix;

    @Value("${parser.search-prefix-catalog}")
    private String prefix;

    @Value("${parser.base-url}")
    private String baseUrl;

    @Value("${parser.user-agent}")
    private String userAgent;

    public Document consumePage(String request) {
        String startPageUrl = this.formRequestQuery(request);

        logger.info("Start page url created: {}", startPageUrl);

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(startPageUrl);

        String pageSource = driver.getPageSource();
        Document page = Jsoup.parse(pageSource);

        extractAndPrintMainContent(page);

        driver.quit();

        return null;
    }

    private @NotNull String formRequestQuery(String request) {
        String requestInUTF8 = URLEncoder.encode(request, StandardCharsets.UTF_8);
        return baseUrl + prefix + requestInUTF8 + postfix;
    }

    private void extractAndPrintMainContent(@NotNull Document page) {
        Elements mainElements = page.select("main.main");
        for (Element mainElement : mainElements) {
            Elements allElements = mainElement.getAllElements();
            for (Element element : allElements) {
                String tagName = element.tagName();
                String classes = element.className();
                System.out.println("Tag: " + tagName + ", Classes: " + classes);
            }
        }
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
