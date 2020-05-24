package observer;

import io.reactivex.rxjava3.core.Observer;

public class ObserverFactory {

    public Observer<Long> createObserver(String line) {
        return createObserver(line.split("\\s+"));
    }

    Observer<Long> createObserver(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException(Parser.incorrectArgs);

        String type = args[0].toLowerCase();

        switch (type) {
            case "mail":
                return new Email(getEmail(args));
            case "sms":
                return new Email(getSMS(args));
            case "console":
                return new Console();
            default:
                throw new IllegalArgumentException("Invalid notification type");
        }
    }

    private String getEmail(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException(Parser.incorrectArgs);

        return args[1];
    }

    private String getSMS(String[] args) {
        if (args.length != 3)
            throw new IllegalArgumentException(Parser.incorrectArgs);

        String tell = args[1];
        String providerName = args[2];

        switch (providerName) {
            case "alltel":
                return (tell + "@mms.alltelwireless.com");

            case "att":
                return (tell + "@mms.att.net");

            case "boostmobile":
                return (tell + "myboostmobile.com");

            case "cricketwireless":
                return (tell + "@mms.cricketwireless.net");

            case "projectfi":
                return (tell + "@msg.fi.google.com");

            case "sprint":
                return (tell + "@pm.sprint.com");

            case "tmobile":
                return (tell + "@tmomail.net");

            case "uscellular":
                return (tell + "@mms.uscc.net");

            case "verizon":
                return (tell + "@vzwpix.com");

            case "virginmobile":
                return (tell + "@vmpix.com");

            default:
                throw new IllegalArgumentException("Invalid carrier: " + providerName);
        }
    }
}
