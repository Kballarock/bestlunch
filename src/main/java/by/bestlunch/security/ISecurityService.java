package by.bestlunch.security;

public interface ISecurityService {

    String findLoggedInUsername();

    void autoLogin(String email, String password);
}