import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazendo uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // pegar somente os dados necessários (título, poster, classificação)
        JsonParserFilmes parser = new JsonParserFilmes();
        List<Map<String, String>> listaDeFilmes = parser.parse(body); 

        // mostrar e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[36mTítulo: \u001b[m" + filme.get("title"));
            System.out.println("\u001b[36mImagem: \u001b[m" + filme.get("image"));
            System.out.println("\u001b[36mAno: \u001b[m" + filme.get("year"));
            System.out.println("\u001b[36mRanking: \u001b[m" + filme.get("rank"));
            System.out.println("\u001b[36mNota: \u001b[m" + filme.get("imDbRating"));
            double numero = Math.floor(Double.parseDouble(filme.get("imDbRating")));
            int estrelas = (int) numero;
            for (int i = 1; i <= estrelas; i++) {
                System.out.print("⭐");
            }
            
            System.out.println("\n");
        }
    }
}
