package at.htl.leoben.windows.games;

import at.htl.leoben.engine.Window;
import at.htl.leoben.engine.configurations.AdvancedColor;
import at.htl.leoben.engine.configurations.AlignmentHorizontal;
import at.htl.leoben.engine.configurations.AlignmentVertical;
import at.htl.leoben.engine.configurations.data.AlignmentTypeHorizontal;
import at.htl.leoben.engine.configurations.data.AlignmentTypeVertical;
import at.htl.leoben.engine.data.WindowElementItem;
import at.htl.leoben.socket.data.SpecialCharacterKey;
import at.htl.leoben.windows.style.DefaultStyle;
import static org.fusesource.jansi.Ansi.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


public class SnakeGame extends GameWindowBase<String> {
    public SnakeGame() {
        super(2);
    }

    private Window root = new Window(20, 40);

    @Override
    public Window getWindow() {
        return root;
    }

    WindowElementItem[] items = new WindowElementItem[10];

    WindowElementItem gameOverScreen = null;
    WindowElementItem player0 = null;

    String gameOverText = "Game Over";

    SpecialCharacterKey direction0 = SpecialCharacterKey.RIGHT;

    SpecialCharacterKey direction1 = SpecialCharacterKey.RIGHT;


    HashMap<Integer, Integer> positionPlayer0 = new HashMap<Integer, Integer>();
    ArrayList<WindowElementItem> snakeBodyPlayer0 = new ArrayList<>();

    ArrayList<WindowElementItem> snakeBodyPlayer1 = new ArrayList<>();

    ArrayList<WindowElementItem> apples = new ArrayList<>();

    int appleCount = 6;

    boolean gameOver = false;


    @Override
    public void onStart() throws Exception {
        root.setTitle("SNAKE 2023");
        items[2] = root.setText(new AlignmentHorizontal(AlignmentTypeHorizontal.LEFT, 2, 0), root.getHeight() - 2, null, root.formatter.getFormat(AdvancedColor.WHITE, Attribute.INTENSITY_BOLD, DefaultStyle.borderFormat));
        items[3] = root.setText(new AlignmentHorizontal(AlignmentTypeHorizontal.RIGHT, 2, 1), root.getHeight() - 2, null, root.formatter.getFormat(AdvancedColor.WHITE, Attribute.INTENSITY_BOLD, DefaultStyle.borderFormat));


        //#####################################################################################################
        // STUDENT TODO: Create snakes here
        for (int idx = 0; idx < 4; idx++) {
            WindowElementItem body0 = root.setElement(idx, 2, 'X', null);
            snakeBodyPlayer0.add(body0);
            WindowElementItem body1 = root.setElement(idx + 2, 6, 'Y', null);
            snakeBodyPlayer1.add(body1);
        }
        //#####################################################################################################


        gameOverScreen = root.setText(new AlignmentHorizontal(AlignmentTypeHorizontal.CENTER), new AlignmentVertical(AlignmentTypeVertical.MIDDLE), null, null);


    }

    @Override
    public void onTick() throws Exception {
        SpecialCharacterKey keyStrokePlayer0 = getSpecialInput(0);
        SpecialCharacterKey keyStrokePlayer1 = getSpecialInput(1);
        items[2].setText("P1: " + String.valueOf(keyStrokePlayer0));
        items[3].setText("P2: " + String.valueOf(keyStrokePlayer1));
        //#####################################################################################################
        // STUDENT TODO: Implement a method to get the players keystroke and update the direction accordingly.

        if (gameOver && this.direction0 != SpecialCharacterKey.NONE && this.direction1 != SpecialCharacterKey.NONE) {
            this.direction0 = SpecialCharacterKey.NONE;
            this.direction1 = SpecialCharacterKey.NONE;
        } else if (gameOver && this.direction0 == SpecialCharacterKey.NONE && this.direction1 == SpecialCharacterKey.NONE) {
            if (keyStrokePlayer0 == SpecialCharacterKey.ESCAPE || keyStrokePlayer1 == SpecialCharacterKey.ESCAPE) {
                this.gameOverScreen.setText("");
                this.gameOver = false;
                this.direction0 = SpecialCharacterKey.RIGHT;
                this.direction1 = SpecialCharacterKey.RIGHT;
                onStart();
            }
        }

        if (keyStrokePlayer0 != SpecialCharacterKey.NONE && !gameOver) {
            this.direction0 = keyStrokePlayer0;
        }
        if (keyStrokePlayer1 != SpecialCharacterKey.NONE && !gameOver) {
            this.direction1 = keyStrokePlayer1;
        }

        //#####################################################################################################

        // DO NOT CHANGE SOMETHING IN HERE

        this.updatePosition(this.snakeBodyPlayer0, direction0);
        this.updatePosition(this.snakeBodyPlayer1, direction1);

        //#####################################################################################################
        // STUDENT TODO: Check if a payer is gameover

        if (!this.snakeBodyPlayer0.isEmpty()) {
            isGameOver(this.snakeBodyPlayer0);
        }
        if (!this.snakeBodyPlayer1.isEmpty()) {
            isGameOver(this.snakeBodyPlayer1);
        }

        //#####################################################################################################


    }



    public void isGameOver(ArrayList<WindowElementItem> snakeBody)
    {
        // STUDENT TODO: Implement logic to check if a snake hit another snake or border. To do this you mus
        // implement the function Boolean inSnake(ArrayList<WindowElementItem> snakeBody)
        // After that set class member gameover to true and clear screen
        if (inSnake(snakeBody)) {
            this.gameOver = true;
        }
        if (snakeBody.get(0).getX() < 0 || snakeBody.get(0).getY() > root.getWidth()) {
            this.gameOver = true;
        }
        if (snakeBody.get(0).getY() < 0 || snakeBody.get(0).getY() > root.getHeight()) {
            this.gameOver = true;
        }

        if (gameOver) {
            this.snakeBodyPlayer0.forEach(b -> b.setText(""));
            this.snakeBodyPlayer1.forEach(b -> b.setText(""));
            this.snakeBodyPlayer0.clear();
            this.snakeBodyPlayer1.clear();
            this.gameOverScreen.setText(this.gameOverText);
        }
    }

    public Boolean inSnake(ArrayList<WindowElementItem> snakeBody)
    {
        // STUDENT TODO: Implement a method to check if a head of the provided snake body hit
        // another snake body
        int posHeadX = snakeBody.get(0).getX();
        int posHeadY = snakeBody.get(0).getY();

        if (snakeBody.equals(snakeBodyPlayer0)) {
            for (int i = 0; i < snakeBodyPlayer1.size(); i++) {
                if (snakeBodyPlayer1.get(i).getX() == posHeadX && snakeBodyPlayer1.get(i).getY() == posHeadY) {
                    return true;
                }
            }
        } else {
            for (int i = 1; i < snakeBodyPlayer0.size(); i++) {
                if (snakeBodyPlayer0.get(i).getX() == posHeadX && snakeBodyPlayer0.get(i).getY() == posHeadY) {
                    return true;
                }
            }
        }

        return false;
    }

    public void updatePosition(ArrayList<WindowElementItem> snakeBody, SpecialCharacterKey direction)
    {

        if (this.gameOver)
            return;

        int oldLastY = snakeBody.get(snakeBody.size()-1).getY();
        int oldLastX = snakeBody.get(snakeBody.size()-1).getX();
        if (direction == SpecialCharacterKey.LEFT) {
            int old_x = snakeBody.get(0).getX();
            int old_y = snakeBody.get(0).getY();;
            snakeBody.get(0).decrementX();
            for (int index = 1; index < snakeBody.size(); index++) {
                int tmp_x = snakeBody.get(index).getX();
                int tmp_y = snakeBody.get(index).getY();
                snakeBody.get(index).setX(old_x);
                snakeBody.get(index).setY(old_y);
                old_x = tmp_x;
                old_y = tmp_y;
            }
        } else if (direction == SpecialCharacterKey.RIGHT) {
            int old_x = snakeBody.get(0).getX();
            int old_y = snakeBody.get(0).getY();;
            snakeBody.get(0).incrementX();
            for (int index = 1; index < snakeBody.size(); index++) {
                int tmp_x = snakeBody.get(index).getX();
                int tmp_y = snakeBody.get(index).getY();
                snakeBody.get(index).setX(old_x);
                snakeBody.get(index).setY(old_y);
                old_x = tmp_x;
                old_y = tmp_y;
            }
        } else if (direction == SpecialCharacterKey.DOWN) {
            int old_x = snakeBody.get(0).getX();
            int old_y = snakeBody.get(0).getY();;
            snakeBody.get(0).incrementY();
            for (int index = 1; index < snakeBody.size(); index++) {
                int tmp_x = snakeBody.get(index).getX();
                int tmp_y = snakeBody.get(index).getY();
                snakeBody.get(index).setX(old_x);
                snakeBody.get(index).setY(old_y);
                old_x = tmp_x;
                old_y = tmp_y;
            }
        } else if (direction == SpecialCharacterKey.UP)  {
            int old_x = snakeBody.get(0).getX();
            int old_y = snakeBody.get(0).getY();;
            snakeBody.get(0).decrementY();
            for (int index = 1; index < snakeBody.size(); index++) {
                int tmp_x = snakeBody.get(index).getX();
                int tmp_y = snakeBody.get(index).getY();
                snakeBody.get(index).setX(old_x);
                snakeBody.get(index).setY(old_y);
                old_x = tmp_x;
                old_y = tmp_y;
            }
        }

        // Student TODO: Call a method to detect if the snake just ate an apple

    }


    public void hitApple(int oldLastX, int oldLastY, ArrayList<WindowElementItem> snakeBody)
    {
        int posHeadX = snakeBody.get(0).getX();
        int posHeadY = snakeBody.get(0).getY();
        int hittedApple = -1;
        for (int i = 0; i < apples.size(); i++)
        {
            if (apples.get(i).getX() == posHeadX && apples.get(i).getY() == posHeadY)
            {
                hittedApple = i;
                break;
            }
        }

        // Student TODO: Write a method to add a new body element to the snake and print it to the gamescreen

    }


    public void spawnApple()
    {
        // STUDENT TODO: Write a method to randomly spawn apples. Use the applescount member variable for this task
    }

}
