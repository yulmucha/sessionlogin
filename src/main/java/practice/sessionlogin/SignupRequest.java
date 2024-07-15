package practice.sessionlogin;

// signup.html의 input 태그의 name 값과 변수 이름이 일치해야 데이터가 잘 들어옴
public record SignupRequest(
        String email,
        String nickname,
        String password
) {
}
