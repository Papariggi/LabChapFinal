package units;

import units.observer.Observer;
import units.observer.ObserverManager;
import units.observer.Subscriber;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Unit implements AllUnits
{
    private int health;
    private int attack;
    private int armor;
    private int cost;
    private String name;
    private Observer observer;

    /**
     * Fight between units.
     * @param unit
     * @return Returns unit after fight.
     */
    public AllUnits fight(AllUnits unit) {
        if (unit.getArmor() > attack) {
            unit.setArmor(unit.getArmor() - attack);
            return unit;
        }

        unit.setHealth(unit.getHealth() - (attack - unit.getArmor()));
        unit.setArmor(0);

        if(unit.getHealth() < 0)
            unit.setHealth(0);

        return unit;
    }

    public String getInfo()
    {
        return String.format("%s - attack: %d, armor: %d, health: %d",
                getName(), getAttack(), getArmor(), getHealth());
    }

    @Override
    public AllUnits copy() {
        return null;
    }
    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        if (this.observer != null)
            this.observer = null;
    }

    @Override
    public void notifyObs() {
        if (this.observer != null)
            observer.handleEvent(this);
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return health == unit.health &&
                attack == unit.attack &&
                armor == unit.armor &&
                cost == unit.cost &&
                name.equals(unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, attack, armor, cost, name);
    }


}
