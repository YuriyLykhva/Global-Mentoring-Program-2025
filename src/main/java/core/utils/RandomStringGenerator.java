package core.utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class RandomStringGenerator {

    public static String getTargetDashboardName() {
        String random = String.valueOf(new Random().nextInt(100)); // Generates a random number between 0 and 99}
        String targetDashboardName = new Faker().color().name() + "-" + random;
        return targetDashboardName;
    }
}
