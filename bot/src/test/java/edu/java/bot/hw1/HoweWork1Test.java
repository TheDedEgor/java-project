package edu.java.bot.hw1;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.BotCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.TrackCommand;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HoweWork1Test {

    @Test
    public void emptyListCommandTest() {
        var update = getUpdate(List.of("/list", ""));
        var command = new ListCommand();
        var sendMessage = command.handle(update);
        var text = sendMessage.getParameters().get("text");
        assertThat(text).isEqualTo("Список ссылок пуст");
    }

    @Test
    public void listCommandTest() {
        var update = getUpdate(List.of("/track https://yandex.ru/", "/list"));
        BotCommand command = new TrackCommand();
        var sendMessage = command.handle(update);
        var text = sendMessage.getParameters().get("text");
        assertThat(text).isEqualTo("Ссылка успешно добавлена");

        command = new ListCommand();
        sendMessage = command.handle(update);
        text = sendMessage.getParameters().get("text");
        assertThat(text).isEqualTo("Список отслеживаемых ссылок:\nhttps://yandex.ru/");
    }

    private Update getUpdate(List<String> commands) {
        var update = Mockito.mock(Update.class);
        var chat = Mockito.mock(Chat.class);
        var message = Mockito.mock(Message.class);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn(commands.get(0)).thenReturn(commands.get(1));
        Mockito.when(update.message()).thenReturn(message);
        return update;
    }
}
