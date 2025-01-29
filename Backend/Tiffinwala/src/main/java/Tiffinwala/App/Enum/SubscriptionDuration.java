package Tiffinwala.App.Enum;

public enum SubscriptionDuration {
    SEVEN_DAYS(7),
    THIRTY_DAYS(30);

    private final int days;

    SubscriptionDuration(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
