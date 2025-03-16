package rabbit.api;

public enum MessageType {

    /** Rapid Message: No reliability guarantee, no confirmation required */
    RAPID("0"),

    /** Confirm Message: No reliability guarantee, but confirmation is required */
    CONFIRM("1"),

    /** Reliable Message: Ensures 100% delivery reliability (atomicity with DB) */
    RELIANT("2");

    private final String code;

    MessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static MessageType fromCode(String code) {
        for (MessageType type : MessageType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid MessageType code: " + code);
    }

}

