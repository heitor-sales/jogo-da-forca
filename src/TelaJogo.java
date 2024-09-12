import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jogodaforca.JogoDaForca;

public class TelaJogo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JogoDaForca jogo;
    private ArrayList<Integer> ocorrencias; // posições adivinhadas
    private int contador;
    private JLabel lblImagemForca;
    private JLabel lblDica;
    private JLabel lblAcertos;
    private JLabel lblPenalidades;
    private JLabel lblPalavra;
    private JLabel lblTamanhoPalavra;
    private JLabel lblStatus;
    private JButton btnAdivinhar;
    private JTextField txtLetra;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaJogo frame = new TelaJogo();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TelaJogo() {
        setTitle("Jogo da Forca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblAcertos = new JLabel("Acertos: ");
        lblAcertos.setBounds(144, 14, 59, 13);
        contentPane.add(lblAcertos);
        lblAcertos.setVisible(false);

        lblPenalidades = new JLabel("Penalidades: ");
        lblPenalidades.setText("Penalidades: 0");
        lblPenalidades.setBounds(213, 14, 201, 13);
        contentPane.add(lblPenalidades);
        lblPenalidades.setVisible(false);

        lblDica = new JLabel("Dica: ");
        lblDica.setBounds(10, 54, 263, 13);
        contentPane.add(lblDica);

        JLabel lblNewLabel = new JLabel("Letra: ");
        lblNewLabel.setBounds(10, 105, 45, 13);
        contentPane.add(lblNewLabel);

        txtLetra = new JTextField();
        txtLetra.setBounds(53, 102, 30, 19);
        contentPane.add(txtLetra);
        txtLetra.setColumns(10);

        lblPalavra = new JLabel("Palavra: ");
        lblPalavra.setBounds(10, 141, 100, 13);
        contentPane.add(lblPalavra);

        lblStatus = new JLabel("Status");
        lblStatus.setText("Status");
        lblStatus.setBounds(10, 213, 193, 13);
        contentPane.add(lblStatus);
        
        lblImagemForca = new JLabel("");
        lblImagemForca.setBounds(273, 79, 93, 121);
        contentPane.add(lblImagemForca);
        lblStatus.setVisible(false);
        
        lblTamanhoPalavra = new JLabel("Tamanho da palavra: ");
        lblTamanhoPalavra.setBounds(10, 176, 188, 13);
        contentPane.add(lblTamanhoPalavra);

        btnAdivinhar = new JButton("Adivinhar");
        btnAdivinhar.setBounds(105, 101, 93, 21);
        contentPane.add(btnAdivinhar);
        btnAdivinhar.setEnabled(false);
        btnAdivinhar.setText("Adivinhar");
        btnAdivinhar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verificarLetra();
            }
        });

        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });
        btnIniciar.setBounds(10, 10, 85, 21);
        contentPane.add(btnIniciar);
    }
    
    public void iniciarJogo() {
        try {
            jogo = new JogoDaForca();
            jogo.iniciar();
            contador = 6;
            lblAcertos.setVisible(true);
            lblPenalidades.setVisible(true);
            lblStatus.setVisible(true);
            btnAdivinhar.setEnabled(true);
            lblStatus.setText(jogo.getResultado());
            atualizarInterface();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    public void atualizarInterface() {
        lblDica.setText("Dica: " + jogo.getDica());
        lblAcertos.setText("Acertos: " + String.valueOf(jogo.getAcertos()));
        lblPenalidades.setText("Penalidades: " + String.valueOf(jogo.getNumeroPenalidade()) + " - " + jogo.getNomePenalidade());
        lblPalavra.setText("Palavra: " + jogo.getPalavraAdivinhada());
        lblTamanhoPalavra.setText("Tamanho da palavra: " + jogo.getTamanho() + " letras");
        atualizarImagemForca();
        if (jogo.terminou()) {
            lblStatus.setText(jogo.getResultado());
            btnAdivinhar.setEnabled(false);
        }
    }

    public void verificarLetra() {
        try {
            ocorrencias = jogo.getOcorrencias(txtLetra.getText());
            if (ocorrencias.size() > 0) {
                lblStatus.setText("Você acertou a letra " + txtLetra.getText());
            } else {
                lblStatus.setText("Você errou a letra " + txtLetra.getText());
                contador--;
            }
            atualizarInterface();
        } catch (Exception e1) {
            lblStatus.setText(e1.getMessage());
        }
    }

    public void atualizarImagemForca() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/" + contador + ".png"));
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(lblImagemForca.getWidth(), lblImagemForca.getHeight(), Image.SCALE_DEFAULT);
        lblImagemForca.setIcon(new ImageIcon(newImage));
    }
}
