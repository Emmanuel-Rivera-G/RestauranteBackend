package utp.edu.pe.server.constants;

public enum HttpCode {
    ERROR_CODE_404(404),
    ERROR_CODE_405(405);

    private final int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
