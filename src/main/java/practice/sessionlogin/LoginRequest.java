package practice.sessionlogin;

public record LoginRequest(
        String email,
        String password
) {
}
