import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACdbc752f24bff991c9f5da1c09bbbe428";
    public static final String AUTH_TOKEN = "1331b11bf940bd69c545231e86f0fd18";

    // FROM NUMBER
    private static final String FROM_NUMBER = "+16046704770";
//
//    public static void main(String[] args) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        Message message = Message
//                .creator(new PhoneNumber("+17783205275"), // to
//                        new PhoneNumber("+16046704770"), // from
//                        "Where's Wallace?")
//                .create();
//
//        System.out.println(message.getSid());
//    }

    public static void sendSMS(String receivingNumber, String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(receivingNumber), // to
                        new PhoneNumber(FROM_NUMBER), // from
                        text)
                .create();

        System.out.println(message.getSid());

    }
}
