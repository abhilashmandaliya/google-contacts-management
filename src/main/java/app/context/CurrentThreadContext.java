package app.context;

public class CurrentThreadContext {
  private static final ThreadLocal<String> AUTH_CODE = new ThreadLocal<>();

  public static String getAuthCode() {
    return AUTH_CODE.get();
  }

  public static void setAuthCode(String authCode) {
    AUTH_CODE.set(authCode);
  }

  public static void resetAuthCode() {
    AUTH_CODE.remove();
  }
}
