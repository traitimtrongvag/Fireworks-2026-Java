package org.example.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BurstPattern {
    protected final Random random;
    
    protected BurstPattern() {
        this.random = new Random();
    }
    
    public abstract List<FireworkParticle> createBurst(double x, double y);
    
    public static BurstPattern randomPattern() {
        BurstPattern[] patterns = {
            new SphereBurst(),
            new RingBurst(),
            new WillowBurst(),
            new PalmBurst(),
            new HeartBurst(),
            new CrossBurst(),
            new DoubleBurst()
        };
        return patterns[new Random().nextInt(patterns.length)];
    }
}

class SphereBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        int count = 40 + random.nextInt(30);
        
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = 1.5 + random.nextDouble() * 2.5;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            
            particles.add(new FireworkParticle(x, y, vx, vy, 25 + random.nextInt(15), '*'));
        }
        return particles;
    }
}

class RingBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        int rings = 2 + random.nextInt(2);
        
        for (int ring = 0; ring < rings; ring++) {
            int count = 30;
            double speed = 2.0 + ring * 0.6;
            
            for (int i = 0; i < count; i++) {
                double angle = (2 * Math.PI * i) / count;
                double vx = Math.cos(angle) * speed;
                double vy = Math.sin(angle) * speed * 0.6;
                
                particles.add(new FireworkParticle(x, y, vx, vy, 28 + random.nextInt(12), '*'));
            }
        }
        return particles;
    }
}

class WillowBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        int streams = 20 + random.nextInt(10);
        
        for (int i = 0; i < streams; i++) {
            double angle = (2 * Math.PI * i) / streams;
            double baseSpeed = 2.0 + random.nextDouble() * 1.0;
            
            for (int j = 0; j < 6; j++) {
                double speed = baseSpeed * (0.8 + j * 0.08);
                double vx = Math.cos(angle) * speed;
                double vy = Math.sin(angle) * speed - 0.5;
                
                particles.add(new FireworkParticle(x, y, vx, vy, 30 + random.nextInt(15), '+'));
            }
        }
        return particles;
    }
}

class PalmBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        int branches = 8 + random.nextInt(6);
        
        for (int i = 0; i < branches; i++) {
            double angle = (2 * Math.PI * i) / branches;
            
            for (int j = 0; j < 10; j++) {
                double speed = 2.5 + j * 0.15;
                double vx = Math.cos(angle) * speed;
                double vy = Math.sin(angle) * speed * 0.5;
                
                particles.add(new FireworkParticle(x, y, vx, vy, 32 + random.nextInt(10), '*'));
            }
        }
        return particles;
    }
}

class HeartBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        int points = 80;
        
        for (int i = 0; i < points; i++) {
            double t = (2 * Math.PI * i) / points;
            double hx = 16 * Math.pow(Math.sin(t), 3);
            double hy = 13 * Math.cos(t) - 5 * Math.cos(2*t) - 2 * Math.cos(3*t) - Math.cos(4*t);
            
            double scale = 0.12;
            particles.add(new FireworkParticle(x, y, hx * scale, -hy * scale, 35 + random.nextInt(10), '*'));
        }
        return particles;
    }
}

class CrossBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        
        for (int axis = 0; axis < 4; axis++) {
            double angle = axis * Math.PI / 2;
            
            for (int i = 0; i < 15; i++) {
                double speed = 1.5 + i * 0.2;
                double vx = Math.cos(angle) * speed;
                double vy = Math.sin(angle) * speed;
                
                particles.add(new FireworkParticle(x, y, vx, vy, 25 + random.nextInt(15), '+'));
            }
        }
        return particles;
    }
}

class DoubleBurst extends BurstPattern {
    @Override
    public List<FireworkParticle> createBurst(double x, double y) {
        List<FireworkParticle> particles = new ArrayList<>();
        
        SphereBurst inner = new SphereBurst();
        RingBurst outer = new RingBurst();
        
        particles.addAll(inner.createBurst(x, y));
        
        for (FireworkParticle p : outer.createBurst(x, y)) {
            particles.add(new FireworkParticle(p.getX(), p.getY(), 
                p.getX() - x, p.getY() - y, 30, '*'));
        }
        
        return particles;
    }
}
