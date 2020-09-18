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

    // Variable Global la charge
    boolean charge = true;

    // Remplace la fonction distanceBetween
    float distanceBetween(float x0, float y0, float x1, float y1) {
        float dX = x1 - x0;
        float dY = y1 - y0;

        return sqrt((dX * dX) + (dY * dY));
    }

    // Remplace la fonction getFirstCharging
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

    // Remplace la fonction updateCharging
    boolean updateCharging2() {

        // Variable batterie
        float batterie = getShipBatteryLevel();

        // Si la batterie est inférieure à 20
        if (batterie < 20) {
            // Alors variable charge = vraie
            charge = true;
            // Sinon si la batterie est supérieure à 90
        } else if (batterie > 99) {
            // Alors variable charge = fausse
            charge = false;
        }

        // Si la charge est activée donc vrai et que la batterie est inférieur à 99
        if (charge && batterie < 99) {
            // On retourne vraie
            return true;
            // Sinon
        } else {
            // On retourne faux
            return false;
        }

    }

    // Fonction pour les mouvements de recharge (Mode batterie)
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

    // Fonction pour les mouvements de checkpoint (Mode normal)
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

    // Variable speed

    float speed;

    // END OF VARIABLES/FUNCTIONS AREA
    //-------------------------------------------------------

    @Override
    public void process(int delta) {
        //-------------------------------------------------------
        // WRITE YOUR OWN CODE HERE

        // Définir le nom
        setPlayerName("Amandine");
        // Définir le vaisseau que l'on souhaite
        selectShip(8);
        // Définir la couleur du vaisseau
        setPlayerColor(43, 59, 236, 255);


        float DistanceCheck = distanceBetween(getShipPositionX(), getShipPositionY(), getCheckPointX(getNextCheckPointIndex()), getCheckPointY(getNextCheckPointIndex()));
        float DistanceCharge = distanceBetween(getShipPositionX(), getShipPositionY(), getCheckPointX(getFirstCharging()), getCheckPointY(getFirstCharging()));

        // La batterie est activé
        if (updateCharging2() == true) {
            // alors on execute la fonction turn pour le mouvement de recharge
            turn(MouvCharge());
            // On set un couleur pour se reperer
            setPlayerColor(255, 255, 255, 255);
            // Si la vitesse est strictement égale à 0
            if (speed == 0.0f) {
                // Si la distance est inférieure à 1
                if (DistanceCharge < 1f) {
                    // Alors on mets la vitesse à - 0.39f pour freiner
                    speed = -0.39f;
                    setPlayerColor(32, 213, 224, 255);
                    // Sinon si la distance et supérieur à 2
                } else if (DistanceCharge > 2f) {
                    // On mets la vitesse à 0.75 pour accélerer
                    speed = 0.75f;
                    setPlayerColor(255, 255, 255, 255);
                } else {
                    speed = 0.75f;
                }
                // Alors on défini la vitesse à 0.75
                speed = 0.75f;
                // Sinon
            } else {
                // Si la distance est strictement égale à 0
                if (DistanceCharge == 0f) {
                    // On mets le speed -1 pour arreter le vaisseau
                    speed = -1f;
                    // Sinon
                } else {
                    // On accélère
                    speed = 0.75f;
                }
            }
            // Fonction pour accélerer ou freiner ou on intégre la variable vitesse
            accelerateOrBrake(speed);
        } else {
            // alors on execute la fonction turn pour le mouvement de checkpoint
            turn(MouvCheck());
            // Si le speed est strictement égale à 0
            if (speed == 0.0f) {
                // Si la distance est inférieure à 1
                if (DistanceCheck < 1f) {
                    // Alors on mets la vitesse à - 0.39f pour freiner
                    speed = -0.39f;
                    setPlayerColor(171, 0, 0, 255);
                    // Sinon si la distance et supérieur à 2
                } else if (DistanceCheck > 2f) {
                    // On mets la vitesse à 0.75 pour accélerer
                    speed = 0.75f;
                    setPlayerColor(253, 0, 0, 255);
                    // Sinon
                } else {
                    // Alors On mets la vitesse à 0.75 par défaut
                    speed = 0.75f;
                    setPlayerColor(32, 213, 0, 255);
                }
                // On mets la vitesse à 0.75 par défaut
                speed = 0.75f;
            }
            // Fonction pour accélerer ou freiner ou on intégre la variable vitesse
            accelerateOrBrake(speed);
        }


        // END OF CODE AREA
        //-------------------------------------------------------
    }

}
