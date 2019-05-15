package edu.wgu.student.tomasgray.captstone;

import java.util.concurrent.ThreadLocalRandom;

public final class Helpers
{
    public static int rand(int lower, int upper) {
        return
                ThreadLocalRandom.current().nextInt(lower, upper);
    }
}
