package sk.tomsik68.particleworkshop;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;

public class PlayerWandData {
    private Material wandType;
    private IParticlePlayer particle;
    private boolean follow, repeat;
    private int data;
    private IParticlePlaySituation situation;
    private Vector relativeVector;

    public PlayerWandData() {

    }

    public Material getWandType() {
        return wandType;
    }

    public void setWandType(Material wandType) {
        this.wandType = wandType;
    }

    public IParticlePlayer getParticle() {
        return particle;
    }

    public void setParticle(IParticlePlayer particle) {
        this.particle = particle;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public IParticlePlaySituation getSituation() {
        return situation;
    }

    public void setSituation(IParticlePlaySituation situation) {
        this.situation = situation;
    }

    public Vector getRelativeVector() {
        return relativeVector;
    }

    public void setRelativeVector(Vector relativeVector) {
        this.relativeVector = relativeVector;
    }

}
