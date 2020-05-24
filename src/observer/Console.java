package observer;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Date;

public class Console implements Observer<Long> {
    private Long lastModified;
    private boolean firstRun = true;
    private Disposable disposable;

    public void output() {
        Date date = new Date(lastModified);
        String subject = "Change Detected";
        String text = "Date: " + date.toString();
        System.out.println(subject);
        System.out.println(text);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext(Long lastModified) {
        if (firstRun) {
            this.lastModified = lastModified;
            this.firstRun = false;
        } else if (this.lastModified < lastModified) {
            this.lastModified = lastModified;
            output();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
    }

    @Override
    public int hashCode() {
        return "Console".hashCode();
    }
}
