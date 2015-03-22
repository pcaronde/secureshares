package ro.panzo.secureshares.util;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by pcaron on 21.03.15.
 */
public class SMTPAuthenticatorTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SMTPAuthenticatorTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}