package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;

public class MathFuncs {
    public static boolean intersectCircleLine(float xBegin, float yBegin, float xEnd, float yEnd, float x, float y, float r) {
        Vector2 d = new Vector2(xEnd - xBegin, yEnd - yBegin);
        Vector2 f = new Vector2(xBegin - x, yBegin - y);

        float a = d.dot(d);
        float b = 2 * f.dot(d);
        float c = f.dot(f) - r * r;

        float discriminant = b * b - 4 * a * c;

        if (discriminant >= 0) {

            discriminant = (float) Math.sqrt(discriminant);

            float t1 = (-b - discriminant) / (2 * a);
            float t2 = (-b + discriminant) / (2 * a);

            return (t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1);
        }
        return false;
    }

    public static float getHypothenuse(float x, float y) {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static float distanceBetweenPoints(Vector2 v0, Vector2 v1) {
        return (float) Math.sqrt(Math.pow(v0.x - v1.x, 2) + Math.pow(v0.y - v1.y, 2));
    }

    public static float distanceBetweenPoints(Vector2 v0, float x1, float y1) {
        return (float) Math.sqrt(Math.pow(v0.x - x1, 2) + Math.pow(v0.y - y1, 2));
    }

    public static float getSum(float[] floats) {
        float sum = 0;
        for (float f : floats) {
            sum += f;
        }
        return sum;
    }

    public static float angleBetweenPoints(Vector2 v0, Vector2 v1) {
        return correctAtan2Output((float) Math.atan2(v1.y - v0.y, v1.x - v0.x));
    }

    public static float correctAtan2Output(float atan2Output) {
        if (atan2Output >= 0) {
            return atan2Output;
        } else {
            return (float) (Math.PI - Math.abs(atan2Output) + Math.PI);
        }
    }

    public static boolean pointInRectangle(float x, float y, float bx, float by, float bw, float bh) {
        return (x > bx && x < bx + bw && y > by && y < by + bh);
    }

    public static float toPPF(float mps) { // meter per second to pixels per frame (frame = 1 / 60 s)
        return mps / 60 * Main.PPM;
    }

    public static float interpolateAngle(float from, float to, float amount) {
        float shortest_angle = (float) (((((to - from) % (Math.PI * 2)) + (Math.PI * 1.5f)) % (Math.PI * 2)) - (Math.PI));
        return (float) (from + (shortest_angle * amount) % (Math.PI * 2));
    }

    public static float getShortestAngle(float from, float to) {
        return (float) (((((to - from) % (Math.PI * 2)) + (Math.PI * 1.5f)) % (Math.PI * 2)) - (Math.PI));
    }


    public static float loopRadians(float f, float add) {
        return (f + add) % ((float) Math.PI * 2);
    }

    public static float loopOne(float f, float add) {
        return (f + add) % 1;
    }

    public static float loop(float f, float add, float amount) {
        return (f + add) % amount;
    }


    public static void translateVertices(float[] verts, float x, float y) {
        for (int i = 0; i < verts.length / 2; i++) {
            verts[i * 2] += x;
            verts[i * 2 + 1] += y;
        }
    }

    public static void negateVertices(float[] verts) {
        for (int i = 0; i < verts.length; i++) {
            verts[i] *= -1;
        }
    }
}
