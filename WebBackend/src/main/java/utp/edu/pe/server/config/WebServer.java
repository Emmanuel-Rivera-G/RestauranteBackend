package utp.edu.pe.server.config;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    
    public static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_BACKLOG = 0;
    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final String DEFAULT_CONTEXT_PATH = "/";
    
    private static HttpServer server;
    private int backlog = -1;
    private int port = -1;
    private String hostname = null;
    private String contextPath = null;
    
    public WebServer() {
        this
            .setDefaultPort()
            .setDefaultBacklog()
            .setDefaultHostname();
    }
    
    public WebServer(boolean startServer) throws IOException {
        this();
        if (startServer) {
            iniciarServidor();
        }
    }
    
    public WebServer(int port) throws IOException {
        this
            .setPort(port)
            .setDefaultBacklog()
            .setDefaultHostname();
    }
    
    public WebServer(boolean startServer, int port) throws IOException {
        this(port);
        if (startServer) {
            iniciarServidor();
        }
    }
    
    public WebServer(int port, int backlog) throws IOException {
        this
            .setPort(port)
            .setBacklog(backlog)
            .setDefaultHostname();
    }
    
    public WebServer(boolean startServer, int port, int backlog) throws IOException {
        this(port, backlog);
        if (startServer) {
            iniciarServidor();
        }
    }
    
    public WebServer(int port, int backlog, String hostname) throws IOException {
        this
            .setPort(port)
            .setBacklog(backlog)
            .setHostname(hostname);
    }
    
    public WebServer(boolean startServer, int port, int backlog, String hostname) throws IOException {
        this(port, backlog, hostname);
        if (startServer) {
            iniciarServidor();
        }
    }
    
    public void iniciarServidor() throws IOException {
        InetSocketAddress webSocket = new InetSocketAddress(this.hostname, this.port);
        WebServer.server = HttpServer.create(webSocket, this.backlog);
        
        if (this.contextPath == null)
            setDefaultContextPath();
        
        WebServer.server.createContext(this.contextPath, new ServletHandler(contextPath));
        
        WebServer.server.start();
    }
    
    private WebServer setPort(int port) {
        this.port = port;
        return this;
    }
    
    private WebServer setBacklog(int backlog) {
        this.backlog = backlog;
        return this;
    }
    
    private WebServer setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }
    
    public WebServer setContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }
    
    private WebServer setDefaultPort() {
        setPort(DEFAULT_PORT);
        return this;
    }
    
    private WebServer setDefaultBacklog() {
        setBacklog(DEFAULT_BACKLOG);
        return this;
    }
    
    private WebServer setDefaultHostname() {
        setHostname(DEFAULT_HOSTNAME);
        return this;
    }
    
    public WebServer setDefaultContextPath() {
        setContextPath(DEFAULT_CONTEXT_PATH);
        return this;
    }
}
