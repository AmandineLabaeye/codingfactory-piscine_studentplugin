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

    float distanceBetween(float x0, float y0, float x1, float y1) {
        float dX = x1 - x0;
        float dY = y1 - y0;

        return sqrt((dX * dX) + (dY * dY));
    }

    int getFirstCharging() {

        int nbcheckpointmax = getNbRaceCheckPoints(); // Récupération du nombre maximum du checkpoint
        int checkcharge = 0;
        for (int I = 0; I < nbcheckpointmax; I++) {
            if (isCheckPointCharging(I)) {
                checkcharge = I;
            }
        }
        return checkcharge;
    }

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

        int nbcheckpointmax = getNbRaceCheckPoints(); // Récupération du nombre maximum du checkpoint
        int checkcharge = 0;
        for (int I = 0; I < nbcheckpointmax; I++) {
            if (isCheckPointCharging(I)) {
                checkcharge = I;
            }
        }

        float AngleChargeShip = getShipAngle();
        float shipX = getShipPositionX(); // Récupération de la position X du bateau
        float shipY = getShipPositionY(); // Récupération de la position Y du bateau
        float ChargeX = getCheckPointX(checkcharge);
        float ChargeY = getCheckPointY(checkcharge);
        float getAbsoluteAngeCharge = getAbsoluteAngleFromPositions(shipX, shipY, ChargeX, ChargeY);
        float getAbsoluteAngleDiffCharge = getRelativeAngleDifference(AngleChargeShip, getAbsoluteAngeCharge);
        return getAbsoluteAngleDiffCharge;

    }

    float MouvCheck() {

        int nextCheckPoint = getNextCheckPointIndex(); // Récupération postion du prochaine checkpoint

        float shipX = getShipPositionX(); // Récupération de la position X du bateau
        float shipY = getShipPositionY(); // Récupération de la position Y du bateau

        float CheckX = getCheckPointX(nextCheckPoint);
        float CheckY = getCheckPointY(nextCheckPoint);

        float getAbsoluteAngeCheck = getAbsoluteAngleFromPositions(shipX, shipY, CheckX, CheckY);

        float getAbsoluteAngleDiffCheck = getRelativeAngleDifference(getShipAngle(), getAbsoluteAngeCheck);

        return getAbsoluteAngleDiffCheck;

    }

    float speed;

    // END OF VARIABLES/FUNCTIONS AREA
    //-------------------------------------------------------

    @Override
    public void process(int delta) {
        //-------------------------------------------------------
        // WRITE YOUR OWN CODE HERE

        setPlayerName("Amandine");
        selectShip(8);
        setPlayerColor(43, 59, 236, 255);

        float DistanceCheck = distanceBetween(getShipPositionX(), getShipPositionY(), getCheckPointX(getNextCheckPointIndex()), getCheckPointY(getNextCheckPointIndex()));
        float DistanceCharge = distanceBetween(getShipPositionX(), getShipPositionY(), getCheckPointX(getFirstCharging()), getCheckPointY(getFirstCharging()));

        if (updateCharging2() == true) {
            turn(MouvCharge());
            setPlayerColor(255, 255, 255, 255);
            if (DistanceCharge < 1f) {
                speed = -0.39f;
                setPlayerColor(32, 213, 224, 255);
            } else if (DistanceCharge > 2f) {
                speed = 0.75f;
                setPlayerColor(255, 255, 255, 255);
            } else {
                speed = 0.75f;
            }
            accelerateOrBrake(speed);
        } else {
            turn(MouvCheck());
            if (DistanceCheck < 1f) {
                speed = -0.39f;
                setPlayerColor(171, 0, 0, 255);
            } else if (DistanceCheck > 2f) {
                speed = 0.75f;
                setPlayerColor(253, 0, 0, 255);
            } else {
                speed = 0.75f;
                setPlayerColor(32, 213, 0, 255);
            }
            accelerateOrBrake(speed);
        }


        // END OF CODE AREA
        //-------------------------------------------------------
    }

}
