package observer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static final String incorrectArgs = "Incorrect number of arguments";
    private final ObserverFactory observerFactory = new ObserverFactory();
    private final Map<String, Observable<Long>> observableMap = new HashMap<>();

    public void parse(String line) {
        parse(line.split("\\s+"));
    }

    public void parse(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException(incorrectArgs);

        String url = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        Observable<Long> observable = new Website(url);
        Observer<Long> observer = observerFactory.createObserver(newArgs);
        Observable<Long> observableFromMap = observableMap.get(url);

        if (observableFromMap == null) {
            observable.subscribe(observer);
            observableMap.put(url, observable);
        } else
            observableFromMap.subscribe(observer);
    }

    public void parseFile(String file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            parse(line);
        }
        bufferedReader.close();
    }

    public Observable<Long> createObservable(String line) {
        return createObservable(line.split("\\s+"));
    }

    public Observable<Long> createObservable(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException(incorrectArgs);

        String url = args[0];
        return new Website(url);
    }

    public Observer<Long> createObserver(String line) {
        return createObserver(line.split("\\s+"));
    }

    public Observer<Long> createObserver(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException(incorrectArgs);

        return observerFactory.createObserver(args);
    }
}
