import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Sapling implements Plant{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;
    private final int healthLimit;


    private static final double TREE_ANIMATION_MAX = 0.600;
    private static final double TREE_ANIMATION_MIN = 0.050;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;


    public static Sapling createSapling(String id, Point position, List<PImage> images, int health) {
        return new Sapling(id, position, images,  SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_HEALTH_LIMIT);
    }


    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int healthLimit) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.healthLimit = healthLimit;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
    public double getActionPeriod() {
        return this.actionPeriod;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health = (this.health + 1);
        Plant.super.executeActivity(world, imageStore, scheduler);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        if (!Plant.super.transform(world, scheduler, imageStore) && this.health >= this.healthLimit) {
            Tree tree = Tree.createTree(WorldModel.getTreeKey() + "_" + this.id, this.position, Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), Functions.getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(WorldModel.getTreeKey()));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }





    public Action createAnimationAction(int repeatCount) {
        return new Animation( this, repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<PImage> getImages() {
        return images;
    }


    public int getImageIndex() {
        return imageIndex;
    }

}