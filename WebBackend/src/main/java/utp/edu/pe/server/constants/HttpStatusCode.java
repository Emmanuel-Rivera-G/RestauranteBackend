package utp.edu.pe.server.constants;

public enum HttpStatusCode {
    ERROR_CODE_404(404),
    ERROR_CODE_405(405);

    private final int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
