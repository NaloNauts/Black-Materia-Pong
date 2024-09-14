package com.example.java_pong;

import java.util.ArrayList;
import java.util.List;
import com.example.java_pong.model.BallModel;

public class MemoryStressTest {
    private static List<BallModel> ballList = new ArrayList<>();

    public static void main(String[] args) {
        //stackTest(0);

        memoryTest();
    }

    private static void stackTest(int depth) {
        try
        {
            BallModel ball = new BallModel(50, 50);
            ballList.add(ball);
            System.out.println("Stack depth: " + depth);
            stackTest(depth + 1); //
        }
        catch (StackOverflowError e)
        {
            System.err.println("Stack overflow error occurred: " + e.getMessage());
        }
    }


    private static void memoryTest() {
        // Test memory allocation
        int objectCounter = 0;
        try {
            while (true) {
                // Infinitely make new ball model objects
                BallModel ball = new BallModel(50, 50);
                ballList.add(ball);
                objectCounter++;
                System.out.println(objectCounter + " Objects Created");
            }
        }
        catch (OutOfMemoryError e)
        {
            System.err.println("Out of memory error occurred: " + e.getMessage());
        }
    }
}
