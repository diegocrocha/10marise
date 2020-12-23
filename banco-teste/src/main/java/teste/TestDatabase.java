package teste;

import java.util.Scanner;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDatabase {

    public static void main(String[] args) {

        Connection config = new Connection();
        JdbcTemplate con = new JdbcTemplate(config.getDatasource());
        Scanner ler = new Scanner(System.in);

        // Utilizamos o comando "update" para inserir e/ou atualizar registros.
        for (int i = 0; i < 10; i++) {
            System.out.print("Insira um nome: ");
            String nome = ler.nextLine();
            
            System.out.println("Inserindo dados...");
            con.update("INSERT INTO aluno VALUES (null, ?)", nome);
        }
    }
}
