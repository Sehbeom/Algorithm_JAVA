package boj.boj1148;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        List<DictionaryWord> dictionaryWords = new ArrayList<>();

        String input = in.readLine();
        while (!"-".equals(input)) {
            dictionaryWords.add(new DictionaryWord(input));
            input = in.readLine();
        }

        List<Puzzle> puzzles = new ArrayList<>();
        input = in.readLine();
        while (!"#".equals(input)) {
            puzzles.add(new Puzzle(input));
            input = in.readLine();
        }

        List<DictionaryWord> canMake;
        for (Puzzle puzzle : puzzles) {
            canMake = new ArrayList<>();

            continuePoint : for (DictionaryWord dictionaryWord : dictionaryWords) {
                for (char alphabet : puzzle.alphabets) {
                    if (puzzle.alphabetCount[alphabet - 65] < dictionaryWord.alphabetCount[alphabet - 65]) {
                        continue continuePoint;
                    }
                }
                for (char alphabet : dictionaryWord.alphabets) {
                    if (puzzle.alphabetCount[alphabet - 65] < dictionaryWord.alphabetCount[alphabet - 65]) {
                        continue continuePoint;
                    }
                }
                canMake.add(dictionaryWord);
            }

            puzzle.setDifficultyInfo(canMake);

            Map.Entry<Integer, List<Character>> easiest = puzzle.difficultyInfo.firstEntry();
            Map.Entry<Integer, List<Character>> hardest = puzzle.difficultyInfo.lastEntry();

            for (char alphabet : easiest.getValue()) {
                sb.append(alphabet);
            }
            sb.append(" ").append(easiest.getKey()).append(" ");

            for (char alphabet : hardest.getValue()) {
                sb.append(alphabet);
            }
            sb.append(" ").append(hardest.getKey()).append("\n");
        }

        System.out.println(sb);
    }
}

class DictionaryWord {
    String word;
    int[] alphabetCount;
    Set<Character> alphabets;

    public DictionaryWord(String word) {
        this.word = word;
        this.alphabetCount = new int[26];
        setAlphabetCount();
        this.alphabets = new HashSet<>();
        setAlphabets();
    }

    private void setAlphabetCount() {
        for (int i = 0; i < this.word.length(); i++) {
            this.alphabetCount[this.word.charAt(i) - 65] += 1;
        }
    }

    private void setAlphabets() {
        for (int i = 0; i < this.word.length(); i++) {
            this.alphabets.add(this.word.charAt(i));
        }
    }
}

class Puzzle {
    int[] alphabetCount;
    Set<Character> alphabets;
    TreeMap<Integer, List<Character>> difficultyInfo;

    public Puzzle(String puzzleAlphabets) {
        this.alphabetCount = new int[26];
        setAlphabetCount(puzzleAlphabets);
        this.alphabets = new TreeSet<>();
        setAlphabets(puzzleAlphabets);

        this.difficultyInfo = new TreeMap<>();
    }

    private void setAlphabetCount(String puzzleAlphabets) {
        for (int i = 0; i < puzzleAlphabets.length(); i++) {
            this.alphabetCount[puzzleAlphabets.charAt(i) - 65] += 1;
        }
    }

    private void setAlphabets(String puzzleAlphabets) {
        for (int i = 0; i < puzzleAlphabets.length(); i++) {
            this.alphabets.add(puzzleAlphabets.charAt(i));
        }
    }

    public void setDifficultyInfo(List<DictionaryWord> canMake) {
        int count;
        for (char alphabet : this.alphabets) {
            count = 0;
            for (DictionaryWord dictionaryWord : canMake) {
                if (dictionaryWord.alphabetCount[alphabet - 65] > 0) {
                    count += 1;
                }
            }
            if (this.difficultyInfo.containsKey(count)) {
                this.difficultyInfo.get(count).add(alphabet);
            } else {
                this.difficultyInfo.put(count, new ArrayList<>(Arrays.asList(alphabet)));
            }
        }
    }
}
