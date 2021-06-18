package dev.zyko.starfight.entity;

public class EntitySpaceship extends EntityMovable {

    private String name;

    public EntitySpaceship(int id, double posX, double posY, double width, double height, double rotation, String name) {
        super(id, posX, posY, width, height, rotation);
        this.name = name;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    public String getName() {
        return name;
    }

}
