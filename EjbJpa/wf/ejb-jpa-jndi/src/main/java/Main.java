import interfaces.IRepositoryRemote;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {

    public static void main(String[] args) throws NamingException {
        final Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        final IRepositoryRemote remoteRepo = (IRepositoryRemote) new InitialContext(properties).lookup("ejb:/ejb-jpa-1.0/Repository!interfaces.IRepositoryRemote");
//        remoteRepo.saveMovieRemote("Terminator", 1984, "Action", "https://upload.wikimedia.org/wikipedia/en/7/70/Terminator1984movieposter.jpg");
        remoteRepo.findAllMoviesRemote().forEach(System.out::println);
        remoteRepo.findAllPurchasesRemote().forEach(System.out::println);
    }
}