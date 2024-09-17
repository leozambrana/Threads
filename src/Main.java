public class Main {
    public static void main(String[] args) {
        boolean reverseList = false;
        boolean randomThreadsPicks = true;
        String nameToSearch = "Lisa Case";

        //NoParallelFileSearcher.searchFiles(nameToSearch);
        ParallelFileSearcher.searchFiles(nameToSearch, reverseList, randomThreadsPicks);
    }
}