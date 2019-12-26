package factory;

import units.*;

import java.util.Random;

public  class UnitFactory
{
    public AllUnits createUnit()
    {
        Random rndm = new Random();
        int chance = rndm.nextInt(100);

        if (chance < 35)
            return new LightUnit();
        else if (chance < 55)
            return new HeavyUnit();
        else if (chance < 75)
            return new ClericUnit();
        else if (chance < 90)
            return new BowmanUnit();
        else
            return new WizardUnit();
    }
}
