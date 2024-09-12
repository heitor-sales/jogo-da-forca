package jogodaforca;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {

    private List<String[]> palavras = new ArrayList<>();
    private String palavraSorteada;
    private String dicaSorteada;
    private List<String> letrasAdivinhadas = new ArrayList<>();
    private int penalidade;

    public JogoDaForca() throws Exception {
    	//carregamento do arquivo de palavras
    	InputStream stream = this.getClass().getResourceAsStream("/dados/palavras.txt");
        if (stream == null)
            throw new Exception("Arquivo de palavras inexistente");
        Scanner arquivo = new Scanner(stream);

        String linha;
        while (arquivo.hasNext()) {
            linha = arquivo.nextLine().toUpperCase();
            String[] partes = linha.split(";");
            //se partes.length == 2 ok, senão lança mensagem de erro
            if (partes.length == 2) {
                palavras.add(new String[]{partes[0], partes[1]});
            } else {
                throw new Exception("Formato inválido na linha: " + linha);
            }
        }
        arquivo.close();
    }

    public void iniciar() {
        //sorteio da palavra
        Random rand = new Random();
        //número sorteado
        int indiceSorteado = rand.nextInt(palavras.size());
        //pega a palavra e a dica de acordo com o número sorteado
        String[] palavraDicaSorteada = palavras.get(indiceSorteado);
        //pega a palavra que está no índice 0
        palavraSorteada = palavraDicaSorteada[0];
        //pega a dica que está no índice 1
        dicaSorteada = palavraDicaSorteada[1];
        letrasAdivinhadas.clear();
        penalidade = 0;
    }

    public String getDica() {
        return dicaSorteada;
    }

    public int getTamanho() {
        return palavraSorteada.length();
    }

    public ArrayList<Integer> getOcorrencias(String letra) throws Exception {
    	//verifica o caractere fornecido pelo usuário
        if (letra.isEmpty() || letra.length() > 1 || !letra.matches("\\p{L}")) {
            throw new Exception("O caractere fornecido é inválido");
        }
        
        //converte para letra minúscula, para facilitar a comparação
        String letraMinuscula = letra.toLowerCase();
        
        //caso a letra já tenha sido fornecida anteriormente
        if (letrasAdivinhadas.contains(letraMinuscula)) {
            throw new Exception("Letra já adivinhada anteriormente");
        }
        
        //se estiver tudo certo, a letra é adicionada a letrasAdvinhadas
        letrasAdivinhadas.add(letraMinuscula);
        
        //verificação da ocorrência da letra na palavra
        ArrayList<Integer> ocorrencias = new ArrayList<>();
        for (int i = 0; i < palavraSorteada.length(); i++) {
            String caractere = palavraSorteada.substring(i, i + 1).toLowerCase();
            if (caractere.equals(letraMinuscula)) {
                ocorrencias.add(i + 1); //adiciona a ocorrência
            }
        }
        
        //caso não haja ocorrência, a penalidade é incrementada
        if (ocorrencias.isEmpty()) {
            penalidade++;
        }

        return ocorrencias;
    }

    public boolean terminou() {
    	//quando o número de penalidade é 6, o jogo acaba
        if (penalidade == 6) {
            return true;
        }
        for (int i = 0; i < palavraSorteada.length(); i++) {
            String caractere = palavraSorteada.substring(i, i + 1).toLowerCase();
            //se qualquer letra não foi adivinhada, o jogo não terminou
            if (!letrasAdivinhadas.contains(caractere)) {
                return false;
            }
        }
        return true;
    }

    public String getPalavraAdivinhada() {
        StringBuilder palavraAdivinhada = new StringBuilder();
        //apresenta a palavra sorteada preenchendo as letras não advinhadas com *
        for (int i = 0; i < palavraSorteada.length(); i++) {
            String caractere = palavraSorteada.substring(i, i + 1).toLowerCase();
            if (letrasAdivinhadas.contains(caractere)) {
                palavraAdivinhada.append(palavraSorteada.substring(i, i + 1));
            } else {
                palavraAdivinhada.append("*");
            }
        }
        return palavraAdivinhada.toString();
    }

    public int getAcertos() {
        int acertos = 0;
        //verificação de quantas letras foram acertadas até o momento
        for (int i = 0; i < palavraSorteada.length(); i++) {
            String caractere = palavraSorteada.substring(i, i + 1).toLowerCase();
            if (letrasAdivinhadas.contains(caractere)) {
                acertos++;
            }
        }
        return acertos;
    }

    public int getNumeroPenalidade() {
        return penalidade;
    }

    public String getNomePenalidade() {
    	//apresenta as penalidades de acordo com o valor do atributo penalidade
        String[] nomesPenalidades = {"", "cabeça", "tronco", "braço direito", "braço esquerdo", "perna direita", "perna esquerda"};
        return (penalidade > 0 && penalidade <= 6) ? nomesPenalidades[penalidade] : "";
    }

    public String getResultado() {
    	//se o jogo terminou, uma verificação do número de acertos é feita
    	//caso os acertos sejam igual ao tamanho da palavra, a menagem "Você venceu!" é exibida
    	//caso contrário, a mensagem "Você foi enforcado!" é exibida
        if (terminou()) {
            return (getAcertos() == getTamanho()) ? "Você venceu!" : "Você foi enforcado!";
        //se o jogo não terminou, a mensagem de "Jogo iniciado" é mostrada
        } else {
            return "Jogo iniciado";
        }
    }
}
