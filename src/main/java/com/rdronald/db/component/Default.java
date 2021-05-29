package com.rdronald.db.component;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rdronald.db.model.Institute;
import com.rdronald.db.model.StudyWeek;
import com.rdronald.db.service.StudyWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rdronald.db.url.UrlTemplates.URL_TSU_STUDENTS_SCHEDULE;

@Component
public class Default {
    public static final String MY_TAG = "<myTag>";
    private static final Map<String, String> UNIQUE_LINKS = new LinkedHashMap<>();

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private StudyWeekService studyWeekService;

    @EventListener(ApplicationReadyEvent.class)
    public void addDefaults() {
        // getDefaultLinks();
        int resp = addStudentsSchedule();

        switch (resp) {
            case 1:
                System.out.println("parsText() error!");
                System.exit(SpringApplication.exit(ctx, () -> resp));
                break;
            case 2:
                System.out.println("addDefaultStudyWeek() error!");
                System.exit(SpringApplication.exit(ctx, () -> resp));
                break;
            default:
                System.out.println("Defaults added!");
        }
    }

//    public void getDefaultLinks() {
//        String html = parsText();
//        if (html == null) {
//            System.exit(SpringApplication.exit(ctx, () -> 0));
//            System.out.println("parsText() error!");
//        }
//
//        html = html.replaceAll("<h\\d+>|<b>", MY_TAG);
//
//        if (!findFileLinks(html)) {
//            System.exit(SpringApplication.exit(ctx, () -> 0));
//            System.out.println("findFileLinks() error! Unique link not found.");
//        }
//    }

    public String parsText(String url) {
        String html = "";
        try (WebClient wc = new WebClient()) {
            wc.getOptions().setJavaScriptEnabled(false);
            wc.getOptions().setCssEnabled(false);

            HtmlPage page = wc.getPage(url);
            html = page.asXml();
        } catch (Exception e) {
            return null;
        }

        html = html.replaceAll("<h\\d+>|<b>", MY_TAG)
                .replaceAll("\\s+", " ")
                .replaceAll("<br>|</br>|<br/>", "\n");

        return html;
    }

    public String getFileLink(String html, Institute institute, Integer weekNumber) {
        Pattern pattern = Pattern.compile(institute.getRegex().replace("{weekNumber}", weekNumber.toString()),
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(html);

        return matcher.find() ? matcher.group() : null;
//
//        List<String> links = new ArrayList<>();
//        while (matcher.find()) {
//            links.add(matcher.group());
//        }
//        Collections.reverse(links);
//
//        for (String link : links) {
//            pattern = Pattern.compile("%\\d+");
//            matcher = pattern.matcher(link);
//
//            if (matcher.find()) {
//                UNIQUE_LINKS.put(matcher.group(), link);
//            } else {
//                return false;
//            }
//        }
//
//        return true;
    }

    public int addStudentsSchedule() {
        String html = parsText(URL_TSU_STUDENTS_SCHEDULE);
        if (html == null) {
            return 1;
        }

        if (!addDefaultStudyWeek(html)) {
            return 2;
        }

        Institute[] institutes = Institute.values();
        Iterable<StudyWeek> studyWeeks = studyWeekService.findAll();

        for (StudyWeek studyWeek : studyWeeks) {
            for (int i = 0; i < institutes.length; i++) {
                String fileLink = getFileLink(html, institutes[i], studyWeek.getNumber());

                if (fileLink == null) {
                    System.out.println(studyWeek);
                    System.out.println(institutes[i].getName());
                } else {
                    System.out.println(fileLink);
                }
                System.out.println();
            }
        }


        return 0;
    }

    public boolean addDefaultStudyWeek(String html) {
        Pattern pattern = Pattern.compile(String.format("%s.+неделя.+\\)", MY_TAG),
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(html);

//        try {
//            Files.write(Paths.get("text.txt"), Collections.singleton(html));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        boolean resp = false;

        while (matcher.find()) {
            Optional<StudyWeek> studyWeek = studyWeekService.strToObj(matcher.group());
            studyWeek.ifPresent(studyWeekService::save);
            resp = true;
        }

        return resp;
    }
}
