import java.util.List;
import java.util.function.Predicate;
import java.util.function.BiPredicate;


public interface Movable extends Actionable{

    //
    default boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            moveHelper(world, target, scheduler);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    void moveHelper(WorldModel world, Entity target, EventScheduler scheduler);

    default Point nextPosition(WorldModel world, Point destPos) {

        PathingStrategy strat = new AStarPathingStrategy();

        List<Point> path = strat.computePath(getPosition(), destPos, pos -> pos.equals(destPos) || world.withinBounds(pos) && (!world.isOccupied(pos) || posHelper(world, pos)), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() > 0)
        {
            return path.get(0);
        }
        else {
            return getPosition();
        }




    }

    boolean posHelper(WorldModel world, Point newPos);

    default void transform(Movable dude, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }
}
