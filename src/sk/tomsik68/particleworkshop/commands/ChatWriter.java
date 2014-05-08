package sk.tomsik68.particleworkshop.commands;

import java.io.IOException;
import java.io.Writer;

import org.bukkit.command.CommandSender;

public class ChatWriter extends Writer {

    private final CommandSender sender;

    public ChatWriter(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void close() throws IOException {
        
    }

    @Override
    public void flush() throws IOException {
        
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        String[] msg = String.copyValueOf(cbuf, off, len).split(System.getProperty("line.separator"));
        sender.sendMessage(msg);
    }


}
