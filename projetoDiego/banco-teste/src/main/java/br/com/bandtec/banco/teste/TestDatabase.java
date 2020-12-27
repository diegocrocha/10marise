package br.com.bandtec.banco.teste;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDatabase {

    public static void main(String[] args) {

        Connection config = new Connection();
        JdbcTemplate con = new JdbcTemplate(config.getDatasource());

        // A sintaxe utilizada no H2 é "praticamente" a mesma do MySQL.
        // Dropando a tabela caso já exista.
        con.execute("DROP TABLE IF EXISTS pokemon");

        // Um jeito de criar longos textos de maneira organizada.
        StringBuilder createStatement = new StringBuilder();

        // Criando o comando para criar tabela pokemon.
        // o método append funciona como se fosse uma concatenação.
        createStatement.append("CREATE TABLE pokemon (");
        createStatement.append("id INT PRIMARY KEY AUTO_INCREMENT,");
        createStatement.append("nome VARCHAR(255),");
        createStatement.append("tipo VARCHAR(255)");
        createStatement.append(")");

        // Executando o comando de criação de fato.
        con.execute(createStatement.toString());

        // Texto padrão para insert
        String insertStatement = "INSERT INTO pokemon VALUES (null, ?, ? )";

        // Parametros para o primeiro insert
        String pikachu = "pikachu";
        String tipoPikachu = "eletricidade";

        // Utilizamos o comando "update" para inserir e/ou atualizar registros.
        // Podemos utilizar variavéis como o exemplo abaixo:
        con.update(insertStatement, pikachu, tipoPikachu);

        // Ou passar diretamente dentro do método
        // exemplo:
        con.update("INSERT INTO pokemon VALUES (?, ?, ?)",
                null, "squirtle", "agua");

        // Para buscar informações devemos utilizar o comando queryForList ou query,
        // exemplo de uso do queryForList:
        List pokemonSimpleUse = con.queryForList("SELECT * FROM pokemon");

        // Exibindo o resultado
        System.out.println("EXIBINDO DA MANEIRA MAIS SIMPLES:");
        System.out.println(pokemonSimpleUse);

        // Funciona porém há uma maneira mais "útil" para nosso sistema,
        // o comando query que juntamente com um "assistente" chamado
        // BeanPropertyRowMapper tomba o que chamamos de "resultSet"
        // para objetos prontinhos para usarmos na nossa aplicação.
        // Para isso ser possível precisamos criar uma classe "modelo"
        // com os atributos iguais ao da tabela utilizada, nesse caso
        // vamos utilizar uma classe chamada Pokemon, exemplo:
        // Perceba que dessa maneira é possível utilizar tipos genéricos em nossas listas.
        List<Pokemon> pokemonAdvancedUse = con.query("SELECT * FROM pokemon", new BeanPropertyRowMapper(Pokemon.class));

        System.out.println("\nEXIBINDO DA MANEIRA MAIS ÚTIL:");
        for (Pokemon pokemon : pokemonAdvancedUse) {
            System.out.println(pokemon);
        }

        System.out.println("\nExibindo um objeto da lista retornada:");
        System.out.println(pokemonAdvancedUse.get(1));

        con.update(insertStatement, "bulbasaur", "planta");
        con.update(insertStatement, "magikarp", "agua");
        con.update(insertStatement, "gyarados", "agua");
        con.update(insertStatement, "charmander", "fogo");
        con.update(insertStatement, "charizard", "fogo");
        con.update(insertStatement, "blastoise", "agua");
        con.update(insertStatement, "venusaur", "planta");

        pokemonAdvancedUse = con.query("SELECT * FROM pokemon", new BeanPropertyRowMapper(Pokemon.class));

        System.out.println("RESULTADO DOS NOVOS INSERTS:");
        // usando o "for" clássico dessa vez:
        for (int i = 0; i < pokemonAdvancedUse.size(); i++) {
            System.out.println(pokemonAdvancedUse.get(i));
        }

        // Caso queira passar parâmetros na pesquisa não tem problemas,
        // basta adiciona-los após a declaração do assistente, exemplo:
        List<Pokemon> onlyFireType = con.query("SELECT * FROM pokemon WHERE tipo = ?",
                new BeanPropertyRowMapper(Pokemon.class), "fogo");

        System.out.println("Exibindo somente pokemons do tipo fogo:");
        // Dessa vez usando "forEach":
        onlyFireType.forEach(pokemon -> System.out.println(pokemon));

        // Caso precise também é possível usar LIKE, exemplo:
        List<Pokemon> onlyBeginningWithLetterB = con.query("SELECT * FROM pokemon WHERE nome LIKE ?",
                new BeanPropertyRowMapper(Pokemon.class), "b%");

        System.out.println("Exibindo somente pokemons que começam com a letra B:");
        onlyBeginningWithLetterB.forEach(pokemon -> System.out.println(pokemon));

        // Caso precise deletar algum pokemon utilize o update também:
        con.update("DELETE FROM pokemon WHERE nome = ?", "blastoise");

        pokemonAdvancedUse = con.query("SELECT * FROM pokemon", new BeanPropertyRowMapper(Pokemon.class));

        System.out.println("\nDELETANDO BLASTOISE:");
        for (Pokemon pokemon : pokemonAdvancedUse) {
            System.out.println(pokemon);
        }

        // se precisar atualizar algo também utilize o update, exemplo:
        con.update("UPDATE pokemon SET nome = ? WHERE nome = ?", "blastoise", "squirtle");

        pokemonAdvancedUse = con.query("SELECT * FROM pokemon", new BeanPropertyRowMapper(Pokemon.class));

        System.out.println("\nTRANSFORMANDO O SQUIRTLE EM BLASTOISE:");
        for (Pokemon pokemon : pokemonAdvancedUse) {
            System.out.println(pokemon);
        }   

        // Tome cuidado, assim como em qualquer editor de consulta, é possível fazer besteira, exemplo:
        con.update("DELETE FROM pokemon");
        
        pokemonAdvancedUse = con.query("SELECT * FROM pokemon", new BeanPropertyRowMapper(Pokemon.class));

        if (pokemonAdvancedUse.isEmpty()) {
            System.out.println("JAMAIS FAÇA DELETE OU UPDATE SEM WHERE!!!!");
            System.out.println("PENSE DUAS VEZES ANTES DE EXECUTAR UMA QUERY!!!");
        }else{
            System.out.println("Já deve ter passado apuros kkk");
        }
        
        // Caso queira brincar com esse app no editor de consulta do H2,
        // Comente a linha 127.
        // Até a próxima e bons estudos!
        
        // :)
    }
}
