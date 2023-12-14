
public final class Activity implements Action{

    private Actionable entity;
    private final WorldModel world;
    private final ImageStore imageStore;



    public Activity(Actionable entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        this.entity.executeActivity(this.world, this.imageStore, scheduler);
    }
}
