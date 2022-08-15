package com.flatcode.simplemultiapps.CandyCrushGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityCandyCrashGameBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandyCrushGameActivity extends AppCompatActivity {

    private ActivityCandyCrashGameBinding binding;
    private final Context context = CandyCrushGameActivity.this;

    int[] candies = {
            R.drawable.bluecandy, R.drawable.greencandy, R.drawable.redcandy,
            R.drawable.orangecandy, R.drawable.yellowcandy, R.drawable.purplecandy};

    int widthOfBlock, noOfBlocks = 8, widthOfScreen;
    ArrayList<ImageView> candy = new ArrayList<>();
    int candyToBeDragged, candyToBeReplaced;
    int notCandy = R.drawable.transparent;
    Handler mHandler = new Handler();
    int interval = 100;
    int score = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityCandyCrashGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(getString(R.string.candy_crush_game));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;
        int hightOfScreen = displayMetrics.heightPixels;
        widthOfBlock = widthOfScreen / noOfBlocks;
        createBoard();
        for (ImageView imageView : candy) {
            imageView.setOnTouchListener(new OnSwipeListener(context) {

                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - 1;
                    candyInterChange();
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + 1;
                    candyInterChange();
                }

                @Override
                public void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - noOfBlocks;
                    candyInterChange();
                }

                @Override
                public void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + noOfBlocks;
                    candyInterChange();
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
    }

    private void checkRowForThree() {
        for (int i = 0; i < 62; i++) {
            int choosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i)) {
                int x = i;
                if ((int) candy.get(x++).getTag() == choosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == choosedCandy &&
                        (int) candy.get(x).getTag() == choosedCandy) {
                    score = score + 3;
                    binding.toolbarScore.scoreList.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                }
            }
        }
        moveDownCandies();
    }

    private void checkColumnForThree() {
        for (int i = 0; i < 47; i++) {
            int choosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            int x = i;
            if ((int) candy.get(x).getTag() == choosedCandy && !isBlank &&
                    (int) candy.get(x + noOfBlocks).getTag() == choosedCandy &&
                    (int) candy.get(x + 2 * noOfBlocks).getTag() == choosedCandy) {
                score = score + 3;
                binding.toolbarScore.scoreList.setText(String.valueOf(score));
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
            }
        }
        moveDownCandies();
    }

    private void moveDownCandies() {
        Integer[] firstRow = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(firstRow);
        for (int i = 55; i >= 0; i--) {
            if ((int) candy.get(i + noOfBlocks).getTag() == notCandy) {
                candy.get(i + noOfBlocks).setImageResource((int) candy.get(i).getTag());
                candy.get(i + noOfBlocks).setTag(candy.get(i).getTag());
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);
                if (list.contains(i) && (int) candy.get(i).getTag() == notCandy) {
                    int randomColor = (int) Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomColor]);
                    candy.get(i).setTag(candies[randomColor]);
                }
            }
        }
        for (int i = 0; i < 0; i++) {
            if ((int) candy.get(i).getTag() == notCandy) {
                int randomColor = (int) Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);
            }
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkRowForThree();
                checkColumnForThree();
                moveDownCandies();
            } finally {
                mHandler.postDelayed(repeatChecker, interval);
            }
        }
    };

    void startRepeat() {
        repeatChecker.run();
    }

    private void candyInterChange() {
        int background = (int) candy.get(candyToBeReplaced).getTag();
        int background2 = (int) candy.get(candyToBeDragged).getTag();
        candy.get(candyToBeDragged).setImageResource(background);
        candy.get(candyToBeReplaced).setImageResource(background2);
        candy.get(candyToBeDragged).setTag(background);
        candy.get(candyToBeReplaced).setTag(background2);
    }

    private void createBoard() {
        binding.board.setRowCount(noOfBlocks);
        binding.board.setColumnCount(noOfBlocks);
        binding.board.getLayoutParams().width = widthOfScreen;
        binding.board.getLayoutParams().height = widthOfScreen;
        for (int i = 0; i < noOfBlocks * noOfBlocks; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(widthOfBlock, widthOfBlock));
            imageView.setMaxHeight(widthOfBlock);
            imageView.setMaxWidth(widthOfBlock);
            int randomCandy = (int) Math.floor(Math.random() * candies.length);
            imageView.setImageResource(candies[randomCandy]);
            imageView.setTag(candies[randomCandy]);
            candy.add(imageView);
            binding.board.addView(imageView);
        }
    }
}