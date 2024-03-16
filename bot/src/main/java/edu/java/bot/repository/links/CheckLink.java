package edu.java.bot.repository.links;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CheckLink implements LinkValidation {

    private static final Pattern URL_PATTERN = Pattern.compile(
        "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

    @Override
    public Boolean validateLink(String link) {
        return URL_PATTERN.matcher(link).matches();
    }
}
