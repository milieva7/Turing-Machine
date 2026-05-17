package bg.tu_varna.sit.f24621691.project.model;

import bg.tu_varna.sit.f24621691.project.model.exceptions.*;

/**
 * Класът представя лентата на Машина на Тюринг.
 * Лентата съхранява символите, върху които машината работи,
 * както и текущата позиция на главата.
 */
public class Tape {
    private StringBuilder tapeContent; //Съдържанието на лентата
    private int headPosition; //Текущата позиция на главата

    /**
     * Създава нова лента с начален вход.
     * Ако входът е празен или null, лентата започва с празен символ "_".
     *
     * @param initialInput началният вход, който се записва върху лентата
     */
    public Tape(String initialInput) {
        //Ако входът е null, го третираме като празен
        if (initialInput == null) {
            initialInput = "";
        }

        //Ако входът е празен, започваме с _
        this.tapeContent = new StringBuilder(
                initialInput.isEmpty() ? "_" : initialInput
        );

        //Главата започва от първия символ
        this.headPosition = 0;
    }

    /**
     * Чете символа на текущата позиция на главата.
     *
     * @return символът под главата
     */
    public char read() {
        return tapeContent.charAt(headPosition);
    }

    /**
     * Записва нов символ на текущата позиция на главата.
     *
     * @param symbol символът, който ще бъде записан
     */
    public void write(char symbol) {
        tapeContent.setCharAt(headPosition, symbol);
    }

    /**
     * Премества главата наляво, надясно или я оставя на същата позиция.
     * Позволените посоки са L, R и S.
     * Ако главата излезе извън текущите граници на лентата,
     * се добавя празен символ "_".
     *
     * @param direction посоката на движение
     * @throws InvalidDirectionException ако посоката е различна от L, R или S
     */
    public void move(char direction) {
        //Нормализираме посоката (за да приемаме и малки букви)
        direction = Character.toUpperCase(direction);

        //Проверка за валидна посока
        if (direction != 'L' && direction != 'R' && direction != 'S') {
            throw new InvalidDirectionException("Невалидна посока: " + direction);
        }

        //Преместване на главата
        if (direction == 'L') headPosition--;
        else if (direction == 'R') headPosition++;
        //При 'S' не правим нищо

        //Ако излезем вляво извън лентата, добавяме _ отпред
        if (headPosition < 0) {
            tapeContent.insert(0, '_');
            headPosition = 0;
        }
        //Ако излезем вдясно, добавяме _ в края
        else if (headPosition >= tapeContent.length()) {
            tapeContent.append('_');
        }
    }

    /**
     * Връща текущата позиция на главата.
     *
     * @return индексът на текущата позиция на главата
     */
    public int getHeadPosition() {
        return headPosition;
    }

    /**
     * Връща съдържанието на лентата като текст.
     *
     * @return съдържанието на лентата
     */
    public String getContent() {
        return tapeContent.toString();
    }
}