package MovieTicketSystem;

public class Audience {
    private final String name;
    private final GenderType gender;
    private static String phoneNumber;

    public Audience(String name, GenderType gender) {
        this.name = name;
        this.gender = gender;
    }


    public static void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() == 10) {
            Audience.phoneNumber = phoneNumber;
        }else {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public GenderType getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
}
