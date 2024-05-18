import interfaces.IRepositoryRemote;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {

    public static void main(String[] args) throws NamingException {
        final Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        final IRepositoryRemote remoteRepo = (IRepositoryRemote) new InitialContext(properties).lookup("java:global/ejb-jpa-1.0/Repository!interfaces.IRepositoryRemote");
//        remoteRepo.saveMovieRemote("The Fallout", 2021, "Horror", "https://upload.wikimedia.org/wikipedia/en/a/a4/The_Fallout_2021.jpg");
        remoteRepo.findAllMoviesRemote().forEach(System.out::println);
        remoteRepo.findAllPurchasesRemote().forEach(System.out::println);
    }
}