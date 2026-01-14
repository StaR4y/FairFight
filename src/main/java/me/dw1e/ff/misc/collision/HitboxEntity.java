package me.dw1e.ff.misc.collision;

public final class HitboxEntity {

    public int serverPosX, serverPosY, serverPosZ;
    public double posX, posY, posZ;
    private BoundingBox box;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX, otherPlayerMPY, otherPlayerMPZ;

    public HitboxEntity(int serverPosX, int serverPosY, int serverPosZ) {
        this.serverPosX = serverPosX;
        this.serverPosY = serverPosY;
        this.serverPosZ = serverPosZ;

        posX = serverPosX / 32.0;
        posY = serverPosY / 32.0;
        posZ = serverPosZ / 32.0;

        float expandX = 0.3F;

        box = new BoundingBox(posX - expandX, posY, posZ - expandX, posX + expandX, posY + 1.8F, posZ + expandX);
    }

    public void onLivingUpdate() {
        if (otherPlayerMPPosRotationIncrements > 0) {
            double d0 = posX + (otherPlayerMPX - posX) / (double) otherPlayerMPPosRotationIncrements;
            double d1 = posY + (otherPlayerMPY - posY) / (double) otherPlayerMPPosRotationIncrements;
            double d2 = posZ + (otherPlayerMPZ - posZ) / (double) otherPlayerMPPosRotationIncrements;

            --otherPlayerMPPosRotationIncrements;

            setPosition(d0, d1, d2);
        }
    }

    public void setPosition(double x, double y, double z) {
        posX = x;
        posY = y;
        posZ = z;

        float expandX = 0.3F;

        box = new BoundingBox(x - expandX, y, z - expandX, x + expandX, y + 1.8F, z + expandX);
    }

    public void setPosition2(double x, double y, double z) {
        otherPlayerMPX = x;
        otherPlayerMPY = y;
        otherPlayerMPZ = z;

        otherPlayerMPPosRotationIncrements = 3;
    }

    public BoundingBox getBox() {
        return box;
    }
}