package servlets;

import interfaces.IRepository;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserMoviesServlet extends HttpServlet {

    private final static String HTML_TEMPLATE = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <title>%s's Movie Collection</title>\n" +
        "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
        "    <style>\n" +
        "        .container { padding-top: 20px; }\n" +
        "        .list-group-item { background-color: transparent; border: none; }\n" +
        "        .btn-home { margin-top: 20px; }\n" +
        "    </style>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <div class=\"container\">\n" +
        "        <h3 class=\"text-center mb-4\">%s's Movie Collection</h3>\n" +
        "        <ul class=\"list-group list-group-flush\">\n" +
        "%s" +
        "        </ul>\n" +
        "        <div class=\"text-center btn-home\">\n" +
        "            <form method=\"GET\" action=\"index.html\">\n" +
        "                <button type=\"submit\" class=\"btn btn-primary\">Return Home</button>\n" +
        "            </form>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\n" +
        "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\n" +
        "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
        "</body>\n" +
        "</html>";

    @EJB
    private IRepository repo;

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String username = request.getParameter("username");
        final StringBuilder stringBuilder = new StringBuilder();
        repo.findAllPurchases().stream().filter(purchase -> purchase.getBuyerName().equals(username)).forEach(purchase -> {
            stringBuilder.append(String.format("<li class=\"list-group-item\">%s, purchased on %s</li>\n", purchase.getMovie().getTitle(), purchase.getPurchaseDate()));
        });
        final PrintWriter printWriter = response.getWriter();
        String htmlContent = String.format(HTML_TEMPLATE, username, username, stringBuilder.toString());
        printWriter.print(htmlContent);
        printWriter.close();
    }

}