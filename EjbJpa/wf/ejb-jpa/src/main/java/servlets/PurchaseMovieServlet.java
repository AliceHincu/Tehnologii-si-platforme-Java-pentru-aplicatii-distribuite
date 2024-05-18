package servlets;

import entities.Movie;
import interfaces.IRepository;
import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseMovieServlet extends HttpServlet {

    private static final String PURCHASE_FORM_HTML_STRING = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <title>Purchase - %s</title>\n" +
        "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
        "</head>\n" +
        "<body>\n" +
        "    <div class=\"container mt-4\">\n" +
        "        <h2 class=\"text-center mb-4\">Purchase - %s</h2>\n" +
        "        <div class=\"row justify-content-center\">\n" +
        "            <div class=\"col-md-6\">\n" +
        "                <div class=\"card\">\n" +
        "                    <div class=\"card-body\">\n" +
        "                        <form method=\"POST\" action=\"purchase-movie\">\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"name\">Name:</label>\n" +
        "                                <input type=\"text\" class=\"form-control\" name=\"name\" id=\"name\" required>\n" +
        "                            </div>\n" +
        "                            <input type=\"hidden\" value=\"%s\" name=\"movieID\">\n" +
        "                            <button type=\"submit\" class=\"btn btn-primary\">Purchase Movie</button>\n" +
        "                        </form>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\n" +
        "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\n" +
        "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
        "</body>\n" +
        "</html>";

    private static final String PURCHASE_COMPLETE_HTML_STRING = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <title>Purchase Complete!</title>\n" +
        "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
        "</head>\n" +
        "<body>\n" +
        "    <div class=\"container mt-4\">\n" +
        "        <h1 class=\"text-success text-center mb-4\">You have successfully purchased the movie %s!</h1>\n" +
        "        <div class=\"text-center\">\n" +
        "            <form method=\"GET\" action=\"show-user-movies\" class=\"d-inline-block mr-2\">\n" +
        "                <input type=\"hidden\" value=\"%s\" name=\"username\">\n" +
        "                <button type=\"submit\" class=\"btn btn-info\">See Movie Collection</button>\n" +
        "            </form>\n" +
        "            <form method=\"GET\" action=\"index.html\" class=\"d-inline-block\">\n" +
        "                <button type=\"submit\" class=\"btn btn-secondary\">Return Home</button>\n" +
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

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Long movieID = Long.parseLong(request.getParameter("movieID"));
        final Movie movie = repo.findMovie(movieID);
        response.setContentType("text/html");
        final PrintWriter printWriter = response.getWriter();
        // Removed String.format to directly concatenate the variables into the HTML string
        String purchaseFormHtml = String.format(PURCHASE_FORM_HTML_STRING, movie.getTitle(), movie.getTitle(), movieID.toString());
        printWriter.print(purchaseFormHtml);
        printWriter.close();
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Long movieID = Long.parseLong(request.getParameter("movieID"));
        final String buyerName = request.getParameter("name");
        repo.savePurchase(movieID, buyerName, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        final PrintWriter printWriter = response.getWriter();
        // Removed String.format to directly concatenate the variables into the HTML string
        String purchaseCompleteHtml = String.format(PURCHASE_COMPLETE_HTML_STRING, repo.findMovie(movieID).getTitle(), buyerName);
        printWriter.print(purchaseCompleteHtml);
        printWriter.close();
    }

}