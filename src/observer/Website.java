package observer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Website extends Observable<Long> {
    private final String url;
    private final Set<Observer<? super Long>> observers = new HashSet<>();
    private final Observable<Long> observable;

    public Website(String url) {
        this.url = url;
        this.observable = Observable.just(url)
                .map(URL::new)
                .map(URL::openConnection)
                .map(URLConnection::getLastModified)
                .repeatWhen(completed -> completed.delay(1, TimeUnit.SECONDS));
    }

    @Override
    protected void subscribeActual(Observer<? super Long> observer) {
        if (observers.add(observer))
            observable.subscribe(observer);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
