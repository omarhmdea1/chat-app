package chatApp.Utils;

public enum Role {
    GUEST(5),
    USER(10),
    ADMIN(20);

    Role(int i) {
        this.value = i;
    }
    public int value;
}
