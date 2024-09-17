import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelFileSearcher {
    public static void searchFiles(String nameToSearch, boolean reverseList, boolean randomThreadsPicks) {
        String directoryPath = "C:\\Users\\leona\\Desktop\\Faculdade\\Computacao_paralela_e_distribuida\\grande";

        // Cria um objeto File com o diretório especificado
        File directory = new File(directoryPath);

        // Obtém todos os arquivos .txt no diretório
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            List<File> fileList = Arrays.asList(files);

            if(reverseList) {
                Collections.reverse(fileList);
            }

            long startTime = System.currentTimeMillis();
            // Cria um pool de threads com um número fixo de threads
            try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {

                if(randomThreadsPicks) {
                    assignFilesToThreads(fileList, nameToSearch, executorService);
                } else {
                    // Submete uma tarefa para cada arquivo .txt
                    for (File file : fileList) {
                        executorService.submit(() -> searchInFile(file, nameToSearch, startTime));
                    }
                    executorService.shutdown();
                }
            }
        } else {
            System.out.println("Nenhum arquivo .txt encontrado no diretório.");
        }
    }

    private static void searchInFile(File file, String nameToSearch, long startTime) {
        long threadStartTime = System.currentTimeMillis();
        System.out.println("Thread " + Thread.currentThread().getName() + " começando a ler o arquivo: " + file.getName() + " em " + threadStartTime);
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
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void assignFilesToThreads(List<File> fileList, String nameToSearch, ExecutorService executorService) {
        int threadCount = 4; // Número de threads
        int fileCount = fileList.size();

        // Variáveis para determinar quais arquivos são atribuídos a qual thread
        int left = 0;
        int right = fileCount - 1;

        for (int i = 0; i < fileCount; i++) {
            final File file;
            if (i % 2 == 0) {
                // Alterna entre o início e o fim da lista
                file = fileList.get(left++);
            } else {
                file = fileList.get(right--);
            }

            executorService.submit(() -> searchInFile(file, nameToSearch, System.currentTimeMillis()));
        }
    }
}