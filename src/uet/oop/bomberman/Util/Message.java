package uet.oop.bomberman.Util;

import java.awt.*;

public class Message {
    public int x;
    public int y;
    public String message;
    public int duration;
    public Color color;
    public int size;

    public Message(String message_, int x_, int y_, int duration_, Color color_, int size_)
    {
        this.x = x_;
        this.y = y_;
        this.message = message_;
        this.duration = duration_*60;
        this.color = color_;
        this.size = size_;
    }

    public String getMessage() {
        return message;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



}
