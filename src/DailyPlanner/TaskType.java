package DailyPlanner;

public enum TaskType {
    PERSONAL("личное"),
    WORKING("по работе");
    private final String type;

    TaskType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType();
    }
}
