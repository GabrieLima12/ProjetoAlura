import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // chamando a URL da api juntamente com os seus respectivos extratores de conteúdos        
        UnindoAPIs unindoAPIs = UnindoAPIs.NASA;        
        String url = unindoAPIs.getUrl();
        ExtratorDeConteudo extrator = unindoAPIs.getExtrator();

        // chamando a classe referente ao conteúdo de ClientHTTP
        ClienteHttp http = new ClienteHttp();
        String json = http.buscaDados(url); 

        // mostrar e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var criadora = new CriadorDeFigurinhas();

        for (int i = 0 ; i < 3; i++) {  

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.urlImage()).openStream();
            String nomeArquivo = conteudo.titulo() + ".png";

            criadora.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.titulo());
            System.out.println("\n");
        }   
    }
}
