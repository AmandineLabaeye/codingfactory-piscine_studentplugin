/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.codingdojo.students;


import fr.rphstudio.codingdojo.game.Pod;
import fr.rphstudio.codingdojo.game.PodPlugIn;

/**
 * @author Romuald GRIGNON
 */
public class Student23 extends PodPlugIn {
    public Student23(Pod p) {
        super(p);
    }

    //-------------------------------------------------------
    // DECLARE YOUR OWN VARIABLES AND FUNCTIONS HERE

    boolean charge = true;

    boolean updateCharging2() {

        float batterie = getShipBatteryLevel();

        if (batterie < 20) {
            charge = true;
        } else if (batterie > 95) {
            charge = false;
        }

        if (charge && batterie < 95) {
            return true;
        } else {
            return false;
        }
    }

    float MouvCharge() {

        float nbcheckpointmax = getNbRaceCheckPoints(); // Récupération du nombre maximum du checkpoint
        int I;
        int checkcharge = 0;
        for (I = 0; I < nbcheckpointmax; I++) {
            if (isCheckPointCharging(I)) {
                checkcharge = I;
            }
        }

        float AngleChargeShip= getShipAngle();
        float shipX = getShipPositionX(); // Récupération de la position X du bateau
        float shipY = getShipPositionY(); // Récupération de la position Y du bateau
        float ChargeX = getCheckPointX(checkcharge);
        float ChargeY = getCheckPointY(checkcharge);
        float getAbsoluteAngeCharge = getAbsoluteAngleFromPositions(shipX, shipY, ChargeX, ChargeY);
        float getAbsoluteAngleDiffCharge = getRelativeAngleDifference(AngleChargeShip, getAbsoluteAngeCharge);
        return getAbsoluteAngleDiffCharge;

    }

    float MouvCheck () {

        int nextCheckPoint = getNextCheckPointIndex(); // Récupération postion du prochaine checkpoint

        float shipX = getShipPositionX(); // Récupération de la position X du bateau
        float shipY = getShipPositionY(); // Récupération de la position Y du bateau

        float CheckX = getCheckPointX(nextCheckPoint);
        float CheckY = getCheckPointY(nextCheckPoint);

        float getAbsoluteAngeCheck = getAbsoluteAngleFromPositions(shipX, shipY, CheckX, CheckY);

        float getAbsoluteAngleDiffCheck = getRelativeAngleDifference(getShipAngle(), getAbsoluteAngeCheck);

        return getAbsoluteAngleDiffCheck;

    }

    // END OF VARIABLES/FUNCTIONS AREA
    //-------------------------------------------------------

    @Override
    public void process(int delta) {
        //-------------------------------------------------------
        // WRITE YOUR OWN CODE HERE

        setPlayerName("Amandine");
        selectShip(8);
        setPlayerColor(43, 59, 236, 200);

        if (updateCharging2()) {
            turn(MouvCharge());
            accelerateOrBrake(0.75f);
        } else {
            turn(MouvCheck());
            accelerateOrBrake(0.75f);
        }


        // END OF CODE AREA
        //-------------------------------------------------------
    }

}
