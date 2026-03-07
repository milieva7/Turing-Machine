package bg.tu_varna.sit.f24621691.project.model;

public class Tape {
        private StringBuilder tapeContent;
        private int headPosition;

        public Tape(String initialInput) {
            // Ако входът е празен, слагаме празен символ
            this.tapeContent = new StringBuilder(initialInput.isEmpty() ? "_" : initialInput);
            this.headPosition = 0;
        }
    public char read() {
        return tapeContent.charAt(headPosition); // Чете символа на който сме
    }

    public void write(char symbol) {
        tapeContent.setCharAt(headPosition, symbol); // Пише нов символ
    }

    public void move(char direction) {
        if (direction == 'L') headPosition--; // Мести наляво
        else if (direction == 'R') headPosition++; // Мести надясно
        // Ако е S, позицията не се променя

        // Ако излезем извън лентата добавяме празен символ
        if (headPosition < 0) {
            tapeContent.insert(0, '_');
            headPosition = 0;
        } else if (headPosition >= tapeContent.length()) {
            tapeContent.append('_');
        }
    }
    }

