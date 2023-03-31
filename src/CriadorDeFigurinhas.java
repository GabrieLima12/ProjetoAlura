import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class CriadorDeFigurinhas {
    
    public void cria (InputStream inputStream, String nomeArquivo) throws Exception {

        // como será feita a leitura da Imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // criar uma nova imagem com uma parte com transparencia
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = (int) (altura * 0.20) + altura;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original para a nova imagem (na memoria)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // configurando a font
        var fonte = new Font("Impact", Font.BOLD, (int) (largura * 0.15));
        graphics.setColor(Color.yellow);
        graphics.setFont(fonte);
        

        // escrever uma frase na nova imagem na parte transparente
        String textoSticker = "TOPZERA";                  // string para armazenar o possivel nome que será aplicado a figurinha
        // if (ranking <= 3) {                     // nas seguintes linhas será as condições para os possíveis
        //     textoSticker = "TOPZERA";
        // } else if (ranking <= 7) {
        //     textoSticker = "TOPINHO";
        // } else {
        //     textoSticker = "NEH";
        // }
        FontMetrics fontMetrics = graphics.getFontMetrics();                    // metodo para descobrir o tamanho do texto da imagem
        Rectangle2D retangulo = fontMetrics.getStringBounds(textoSticker, graphics);   // foi criado um retangulo para calcular a altura e a largura desse retangulo que é o texto
        int larguraTexto = (int) retangulo.getWidth();                          // aqui é para pegar o tamanho do texto
        int posicaoTextoX = (int) (largura - larguraTexto)/2;                   // esse é o calculo para definir o valor da posição x para deixar o texto centralizado
        int posicaoTextoY = (int) ((int) novaAltura - (novaAltura * 0.05));     // aqui se calcula onde o texto irá se localizar no eixo y
        graphics.drawString(textoSticker, posicaoTextoX , posicaoTextoY);       // variáveis que irão demarcar a posição do texto no sticker

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();      // 
        var textLayout = new TextLayout(textoSticker, fonte, fontRenderContext);    // 
        
        Shape outline = textLayout.getOutline(null);                    // aqui esta importando o contorno
        AffineTransform transform = graphics.getTransform();               // está fazendo as tranformações das coordenadas
        transform.translate(posicaoTextoX, posicaoTextoY);                 // imprimindo o contorno utilizando as posições do texto
        graphics.setTransform(transform);                                  // aqui você seta as tranformações

        var outlineStroke = new BasicStroke(largura * 0.004f);             // aqui se cria o pincel que será utilizado para fazer o contorno
        graphics.setStroke(outlineStroke);                                 // aqui você aplica o pincel que será utilizado

        graphics.setColor(Color.black);                                    // definindo a cor que será usada para fazer o contorno
        graphics.draw(outline);                                            // pedindo para o graphics desenhar o contorno
        graphics.setClip(outline);                                         // ele define a área de aplicação do contorno

        // escrever a nova imagem em um arquivo
        File myDir = new File("saida"); // aqui se define o nome do diretório a ser criado
        if (!myDir.exists()) {                   // nesse momento ele checa se existe esse diretório ou não
            myDir.mkdirs();                      // nessa parte ele irá criar o diretório
        }
        ImageIO.write(novaImagem, "png", new File("saida/" + nomeArquivo));
    }
}