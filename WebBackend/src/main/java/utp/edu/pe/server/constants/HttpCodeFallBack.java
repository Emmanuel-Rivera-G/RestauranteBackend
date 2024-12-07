package utp.edu.pe.server.constants;

public enum HttpCodeFallBack {

    // 4xx Client Errors
    ERROR_FALLBACK_400(fallBack400()),
    ERROR_FALLBACK_401(fallBack401()),
    ERROR_FALLBACK_403(fallBack403()),
    ERROR_FALLBACK_404(fallBack404()),
    ERROR_FALLBACK_405(fallBack405()),
    ERROR_FALLBACK_405_GET(fallBack405Get()),
    ERROR_FALLBACK_405_POST(fallBack405Post()),

    // 5xx Server Errors
    ERROR_FALLBACK_500(fallBack500()),
    ERROR_FALLBACK_502(fallBack502()),
    ERROR_FALLBACK_503(fallBack503());

    private static String fallBack400() {
        return centerInScreen("<h1>400</h1><h3>Bad Request</h3>");
    }

    private static String fallBack401() {
        return centerInScreen("<h1>401</h1><h3>Unauthorized</h3>");
    }

    private static String fallBack403() {
        return centerInScreen("<h1>403</h1><h3>Forbidden</h3>");
    }

    private static String fallBack404() {
        return centerInScreen("<h1>404</h1><h3>Not Found</h3>");
    }

    private static String fallBack405() {
        return centerInScreen("<h1>405</h1><h3>Method Not Allowed</h3>");
    }

    private static String fallBack405Get() {
        return centerInScreen("<h1>405</h1><h3>Method GET Not Allowed</h3>");
    }

    private static String fallBack405Post() {
        return centerInScreen("<h1>405</h1><h3>Method POST Not Allowed</h3>");
    }

    private static String fallBack500() {
        return centerInScreen("<h1>500</h1><h3>Internal Server Error</h3>");
    }

    private static String fallBack502() {
        return centerInScreen("<h1>502</h1><h3>Bad Gateway</h3>");
    }

    private static String fallBack503() {
        return centerInScreen("<h1>503</h1><h3>Service Unavailable</h3>");
    }


    private static String centerInScreen(String content) {
        return """
                    <body style='background-color: black;'
                        <div style='
                         display: flex;
                         justify-content: center;
                         align-items: center;
                         height: 100vh;
                         font-family: Arial, sans-serif;
                         color: #eee;
                        '>
                        """ + content + """
                        </div>
                    </body>
                """;
    }

    private final String fallBack;

    HttpCodeFallBack(String fallBack) {
        this.fallBack = fallBack;
    }

    public String getFallBack() {
        return fallBack;
    }
}
