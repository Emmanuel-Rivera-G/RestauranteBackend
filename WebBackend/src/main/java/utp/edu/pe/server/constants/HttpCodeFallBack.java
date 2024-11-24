package utp.edu.pe.server.constants;

public enum HttpCodeFallBack {

    ERROR_FALLBACK_404(fallBack404()),
    ERROR_FALLBACK_405(fallBack405()),
    ERROR_FALLBACK_405_GET(fallBack405Get()),
    ERROR_FALLBACK_405_POST(fallBack405Post()),
    ERROR_FALLBACK_500(fallBack500()),;

    private static String fallBack404() { return centerInScreen("<h1>404</h1><h3>Not Found</h3>");}
    private static String fallBack405() { return centerInScreen("<h1>405</h1><h3>Method Not Allowed</h3>");}
    private static String fallBack405Get() { return centerInScreen("<h1>405</h1><h3>Method GET not allowed</h3>");}
    private static String fallBack405Post() { return centerInScreen("<h1>405</h1><h3>Method POST not allowed</h3>");}
    private static String fallBack500() { return centerInScreen("<h1>500</h1><h3>Internal Server Error</h3>");}


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
