package sk.tomsik68.particleworkshop.impl;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Quota")
public class QuotaData {
    protected String playerID;
    protected float maxQuota;
    protected float freeQuota;

    public float getFreeQuota() {
        return freeQuota;
    }

    public void setFreeQuota(float free) {
        freeQuota = free;
    }

    public UUID getPlayerID() {
        return UUID.fromString(playerID);
    }

    public void setPlayerID(UUID id) {
        playerID = id.toString();
    }
}
