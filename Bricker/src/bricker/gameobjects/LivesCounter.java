package bricker.gameobjects;

/**
 * this class is a simple way to follow the player's remaining lives.
 */
public class LivesCounter {
    //class fields
    private int currentLivesNumber;
    private int maxLives;

    /**
     * Class constructor.
     * @param currentLivesNumber : How many lives does the player start with.
     * @param maxLives : The maximum lives a player can have.
     */
    public LivesCounter(int currentLivesNumber, int maxLives){
        this.currentLivesNumber = currentLivesNumber;
        this.maxLives = maxLives;
    }

    /**
     * if possible (current lives is less than maximum) add live to a player.
     */
    public void addLife(){
        if(currentLivesNumber<maxLives){
            this.currentLivesNumber++;
        }
    }

    /**
     * remove a life from a player.
     */
    public void removeLive(){
        if(currentLivesNumber>0){
            this.currentLivesNumber--;
        }
    }

    /**
     * check if the players is not out of lives.
     * @return true if the currentLivesNumber bigger than 0.
     */
    public boolean isPlayerAlive(){
        return !(currentLivesNumber<=0);
    }

    /**
     * get the current lives number
     * @return current lives number
     */
    public int getCurrentLivesNumber(){
        return this.currentLivesNumber;
    }

    public int getMaxLives(){
        return this.maxLives;
    }
}
