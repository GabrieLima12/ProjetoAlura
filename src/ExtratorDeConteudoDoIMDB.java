import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo{
    
    public List<Conteudo> extraiConteudos(String json) {
        
        // mudar a forma de mapear o Json utulizando streams e lambida
        // pegar somente os dados necessários (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        return listaDeAtributos.stream().map(atributos -> new Conteudo(atributos.get("title"), atributos.get("image"))).toList();

    }
}