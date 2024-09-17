import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NoParallelFileSearcher {

    public static void searchFiles(String nameToSearch) {
        String directoryPath = "C:\\Users\\leona\\Desktop\\Faculdade\\Computacao_paralela_e_distribuida";

        // Cria um objeto File com o diretório especificado
        File directory = new File(directoryPath);

        // Obtém todos os arquivos .txt no diretório
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            long startTime = System.currentTimeMillis();
            // Percorre todos os arquivos .txt
            for (File file : files) {
                System.out.println("file" + file);
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    int lineNumber = 1;
                    // Lê o arquivo linha por linha
                    while ((line = br.readLine()) != null) {
                        // Se a linha contém o nome a ser buscado
                        if (line.contains(nameToSearch)) {
                            long endTime = System.currentTimeMillis();
                            long elapsedTime = endTime - startTime;

                            System.out.println("Tempo total de busca: " + elapsedTime + " milissegundos");
                            System.out.println("Arquivo: " + file.getName() + ", Linha: " + lineNumber);
                            return;
                        }
                        lineNumber++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            System.out.println("Tempo total de busca: " + elapsedTime + " milissegundos");
        } else {
            System.out.println("Nenhum arquivo .txt encontrado no diretório.");
        }
    }
}
