package json_model.user;


public class UserGenerator {
    public static User getDefaultUser() {
        return new User("praktikum-student@autotest.ru", "12345678", "praktikum-student");
    }

    public static User getUserWithoutEmail() {
        return new User("", "12345678", "praktikum-student");
    }
}
