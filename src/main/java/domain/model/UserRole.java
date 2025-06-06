package domain.model;

public enum UserRole {
    ADMIN,
    USER;

    public static UserRole fromString(String text) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Role inválida: " + text);
    }
}
