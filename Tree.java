import java.util.*;

import processing.core.PImage;

public final class Tree implements Actionable,Plant {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;

    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Tree(id, position, images, actionPeriod, animationPeriod, health);
    }


    public Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.setHealth(health);
    }


    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public double getActionPeriod() {
        return this.actionPeriod;
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return Plant.super.transform(world, scheduler, imageStore);
    }

    public PImage getCurrentImage() {
        return getImages().get(this.getImageIndex() % this.getImages().size());
    }

    public void nextImage() {
        this.imageIndex = this.getImageIndex() + 1;
    }

    public Point getPosition() { return position; }

    public void setPosition(Point position) { this.position = position; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex()
    {
        return imageIndex;
    }

}