package utp.edu.pe.server.constants;

public enum HttpCodeFallBack {

    ERROR_FALLBACK_404("<h1>404 Not Found</h1>"),
    ERROR_FALLBACK_405("<h1>405 Method Not Allowed</h1>"),
    ERROR_FALLBACK_405_GET("<h1>Method GET not allowed</h1>"),
    ERROR_FALLBACK_405_POST("<h1>Method POST not allowed</h1>"),
    ERROR_FALLBACK_500("<h1>500 Internal Server Error</h1>"),;

    private final String fallBack;

    HttpCodeFallBack(String fallBack) {
        this.fallBack = fallBack;
    }

    public String getFallBack() {
        return fallBack;
    }
}
