package com.intnet.griddata.shared.sanitization.service;

import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Service
public class SanitizationServiceImpl implements SanitizationService {

    private final Safelist safelist;

    public SanitizationServiceImpl() {
        this.safelist = Safelist.none();

        this.safelist.addTags("b", "i", "u", "strong", "em", "sub", "sup", "blockquote", "ul", "ol", "li");

        this.safelist.addAttributes("a", "href");
    }

    public String sanitize(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, this.safelist);
    }
}
