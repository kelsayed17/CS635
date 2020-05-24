import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import observer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ObserverTests {

    @Test
    void testFactoryMethods() {
        String url = "http://www.eli.sdsu.edu/courses/fall18/cs635/notes/index.html ";
        String mail = "mail testemail@gmail.com";
        String sms = "sms 1234567890 tmobile";
        String console = "console";

        Parser parser = new Parser();
        ObserverFactory observerFactory = new ObserverFactory();

        // Testing Email
        Observer<Long> observerA = observerFactory.createObserver(mail);
        Observer<Long> observerB = new Email("testemail@gmail.com");
        assertEquals(observerA.hashCode(), observerB.hashCode());

        // Testing SMS
        Observer<Long> observerC = observerFactory.createObserver(sms);
        Observer<Long> observerD = new Email("1234567890@tmomail.net");
        assertEquals(observerC.hashCode(), observerD.hashCode());

        // Testing Console
        Observer<Long> observerE = observerFactory.createObserver(console);
        Observer<Long> observerF = new Console();
        assertEquals(observerE.hashCode(), observerF.hashCode());

        // Testing Email
        Observable<Long> observableA = parser.createObservable(url + mail);
        Observable<Long> observableB = new Website(url);
        assertEquals(observableA.hashCode(), observableB.hashCode());

        // Test sms
        Observable<Long> observableC = parser.createObservable(url + sms);
        Observable<Long> observableD = new Website(url);
        assertEquals(observableC.hashCode(), observableD.hashCode());

        // Test console
        Observable<Long> observableE = parser.createObservable(url + console);
        Observable<Long> observableF = new Website(url);
        assertEquals(observableE.hashCode(), observableF.hashCode());
    }

    @Test
    void testEmailObserver() {
        String mail = "testemail@gmail.com";

        Email observer = new Email(mail);
        Email spy = spy(observer);

        spy.onNext(1L);
        spy.onNext(1L);
        spy.onNext(2L);
        spy.onNext(2L);
        spy.onNext(3L);
        spy.onNext(3L);

        verify(spy, times(2)).onNext(1L);
        verify(spy, times(2)).onNext(2L);
        verify(spy, times(2)).onNext(3L);
        verify(spy, times(2)).sendEmail();
    }

    @Test
    void testConsoleObserver() {
        Console observer = new Console();
        Console spy = spy(observer);

        spy.onNext(1L);
        spy.onNext(1L);
        spy.onNext(2L);
        spy.onNext(2L);
        spy.onNext(3L);
        spy.onNext(3L);

        verify(spy, times(2)).onNext(1L);
        verify(spy, times(2)).onNext(2L);
        verify(spy, times(2)).onNext(3L);
        verify(spy, times(2)).output();
    }
}
