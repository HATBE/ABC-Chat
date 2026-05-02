package ch.hatbe.cli;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.commons.cli.help.HelpFormatter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CliArguments {
    private static CliArguments INSTANCE;

    private final Options options = new Options();
    private final Map<String, String> arguments = new HashMap<>();

    private CliArguments() {}

    public static CliArguments getInstance() {
        if (CliArguments.INSTANCE == null) {
            CliArguments.INSTANCE = new CliArguments();
        }
        return CliArguments.INSTANCE;
    }

    public void registerOption(String shortName, String longName, String description, boolean required) {
        Option option = Option.builder(shortName)
                .longOpt(longName)
                .desc(description)
                .hasArg()
                .required(required)
                .get();

        this.options.addOption(option);
    }

    public boolean parse(String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(this.options, args);

            arguments.clear();

            for (Option option : cmd.getOptions()) {
                this.arguments.put(option.getLongOpt(), option.getValue());
            }

            return true;
        } catch(ParseException e) {
            log.warn(e.getMessage());
            this.printHelp();
            return false;
        }
    }

    public String getString(String name) {
        return this.arguments.get(name);
    }

    public int getInt(String name, int defaultValue) {
        String value = arguments.get(name);

        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("Argument \"{}\" could not be parsed to integer! Returning default {}", name, defaultValue);
            return defaultValue;
        }
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        String value = arguments.get(name);

        if (value == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    public boolean has(String name) {
        return arguments.containsKey(name);
    }

    private void printHelp() {
        HelpFormatter formatter = HelpFormatter.builder().get();

        try {
            formatter.printHelp("server", "", this.options, "", true);
        } catch (IOException e) {
            log.error("Could not print help", e);
        }
    }
}
