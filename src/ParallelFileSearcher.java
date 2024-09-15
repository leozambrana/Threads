import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelFileSearcher {
    public static void searchFiles() {
        // Diretório onde os arquivos .txt estão localizados
        String directoryPath = "C:\\Users\\leona\\Desktop\\Faculdade\\Computacao_paralela_e_distribuida";
        // Nome a ser buscado
        String nameToSearch = "Jeffrey Patel";

        // Cria um objeto File com o diretório especificado
        File directory = new File(directoryPath);

        // Obtém todos os arquivos .txt no diretório
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            // Cria um pool de threads com um número fixo de threads
            try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {

                // Submete uma tarefa para cada arquivo .txt
                for (File file : files) {
                    executorService.submit(() -> searchInFile(file, nameToSearch));
                }

                // Encerra o pool de threads
                executorService.shutdown();
            } // Ajuste o número de threads conforme necessário
        } else {
            System.out.println("Nenhum arquivo .txt encontrado no diretório.");
        }
    }

    private static void searchInFile(File file, String nameToSearch) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            // Lê o arquivo linha por linha
            while ((line = br.readLine()) != null) {
                // Se a linha contém o nome a ser buscado
                if (line.contains(nameToSearch)) {
                    System.out.println("Arquivo: " + file.getName() + ", Linha: " + lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}