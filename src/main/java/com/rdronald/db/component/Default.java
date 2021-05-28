package com.rdronald.db.component;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rdronald.db.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rdronald.db.url.UrlTemplates.URL_TSU_SCHEDULE;

@Component
public class Default {
    public static final String MY_TAG = "<myTag>";
    private static final Map<String, String> UNIQUE_LINKS = new LinkedHashMap<>();
    public static final Long DAYS_BEFORE_UPDATE = 1000L * 60 * 60 * 24 * 5;

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private GroupService groupService;

    @EventListener(ApplicationReadyEvent.class)
    public void addDefaults() {
        groupService.addDefaults();
        getDefaultLinks();

        System.out.println("Defaults added!");
    }

    public void getDefaultLinks() {
        String html = parsText();
        if (html == null) {
            System.exit(SpringApplication.exit(ctx, () -> 0));
            System.out.println("parsText() error!");
        }

        html = html.replaceAll("<h\\d+>|<b>", MY_TAG);

        if (!findFileLinks(html)) {
            System.exit(SpringApplication.exit(ctx, () -> 0));
            System.out.println("findFileLinks() error! Unique link not found.");
        }
    }

    public String parsText() {
        try (WebClient wc = new WebClient()) {
            wc.getOptions().setJavaScriptEnabled(false);
            wc.getOptions().setCssEnabled(false);

            HtmlPage page = wc.getPage(URL_TSU_SCHEDULE);
            return page.asXml();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean findFileLinks(String html) {
        Pattern pattern = Pattern.compile("[^\"]+имфит[^\"]+",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(html);

        List<String> links = new ArrayList<>();
        while (matcher.find()) {
            links.add(matcher.group());
        }
        Collections.reverse(links);

        for (String link : links) {
            pattern = Pattern.compile("%\\d+");
            matcher = pattern.matcher(link);

            if (matcher.find()) {
                UNIQUE_LINKS.put(matcher.group(), link);
            } else {
                return false;
            }
        }

        return true;
    }

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void reGetFileLinks() {
        try {
            Thread.sleep(DAYS_BEFORE_UPDATE);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        getDefaultLinks();
        reGetFileLinks();
    }
}
