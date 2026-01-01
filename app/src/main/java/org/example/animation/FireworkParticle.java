package org.example.animation;

public class FireworkParticle {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private int life;
    private final int maxLife;
    private final char symbol;
    
    public FireworkParticle(double x, double y, double vx, double vy, int life, char symbol) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.life = life;
        this.maxLife = life;
        this.symbol = symbol;
    }
    
    public void update() {
        x += vx;
        y += vy;
        vy += 0.15;
        vx *= 0.98;
        life--;
    }
    
    public boolean isDead() {
        return life <= 0;
    }
    
    public int getX() {
        return (int) x;
    }
    
    public int getY() {
        return (int) y;
    }
    
    public char getSymbol() {
        double lifeFactor = (double) life / maxLife;
        if (lifeFactor > 0.7) return symbol;
        if (lifeFactor > 0.4) return '+';
        if (lifeFactor > 0.2) return '·';
        return '.';
    }
    
    public double getLifeFactor() {
        return (double) life / maxLife;
    }
}
