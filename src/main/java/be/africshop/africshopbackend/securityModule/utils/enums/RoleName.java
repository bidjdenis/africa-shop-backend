package be.africshop.africshopbackend.securityModule.utils.enums;

public enum RoleName {
    ROLE_USER("user"),
    ROLE_ADMIN("admin"),
    ROLE_SUPER_ADMIN("super_admin");
    public final String value;

    RoleName(String value) {
        this.value = value;
    }
}
