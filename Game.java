package frogger;

import java.awt.Canvas;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class Game extends Canvas implements Runnable{
    public static int WIDTH = 640;
    public static int HEIGHT = 720;
    private Thread thread;
    private boolean running = false;
    Player player;

    private Handler handler;
    public Game(){
        player = new Player(400, 400, ID.Player);
        new Window(WIDTH, HEIGHT, "Game", this, player);
        handler = new Handler();
        handler.addObject(player);
        File music = new File("frogger/Audio/start.wav");
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta --;
            }
            if (running){
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                // System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        g.dispose();
        bs.show();
    }
    public static void main(String[] args){
        new Game();
    }
}