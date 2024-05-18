package servlets;

import entities.Movie;
import interfaces.IRepository;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAllMoviesServlet extends HttpServlet {

    private static final String HTML_STRING = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <title>Movie Collection</title>\n" +
        "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
        "    <link href=\"https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&display=swap\" rel=\"stylesheet\">\n" +
        "    <style>\n" +
        "        body { font-family: 'Nunito', sans-serif; }\n" +
        "        .card { margin-bottom: 20px; }\n" +
        "        .card:hover { box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2); }\n" +
        "        .card img { width: 100%; height: auto; }\n" +
        "        .card-body { text-align: center; }\n" +
        "        .navbar { margin-bottom: 30px; }\n" +
        "        .container { padding-top: 20px; }\n" +
        "    </style>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
        "        <a class=\"navbar-brand\" href=\"#\">Movie Collection</a>\n" +
        "    </nav>\n" +
        "    <div class=\"container\">\n" +
        "        <div class=\"row\">\n" +
        "%s" +
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
        response.setContentType("text/html");
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Movie movie : repo.findAllMovies()) {
            stringBuilder.append("<div class=\"col-md-4\">")
                         .append("<div class=\"card\">")
                         .append("<img src=\"").append(movie.getImageUrl()).append("\" class=\"card-img-top\" alt=\"").append(movie.getTitle()).append("\">")
                         .append("<div class=\"card-body\">")
                         .append("<h5 class=\"card-title\">").append(movie.getTitle()).append("</h5>")
                         .append("<p class=\"card-text\">Genre: ").append(movie.getGenre()).append("</p>")
                         .append("<p class=\"card-text\">Release Year: ").append(movie.getReleaseYear()).append("</p>")
                         .append("<form method=\"GET\" action=\"purchase-movie\">")
                         .append("<input type=\"hidden\" value=\"").append(movie.getMovieID()).append("\" name=\"movieID\">")
                         .append("<input type=\"submit\" class=\"btn btn-primary\" value=\"Purchase\">")
                         .append("</form>")
                         .append("</div>")
                         .append("</div>")
                         .append("</div>");
        }
        final PrintWriter printWriter = response.getWriter();
        printWriter.print(HTML_STRING.replace("%s", stringBuilder.toString())); // Replace the placeholder directly
        printWriter.close();
    }

}