import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;
    int currentTile = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    public void distributeTilesToPlayers() {
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 14; j++){
                players[i].playerTiles[j] = tiles[--currentTile];
                
            }
        }
        players[0].playerTiles[14] = tiles[--currentTile];
    }

    public String getLastDiscardedTile() {
        if (lastDiscardedTile != null)  {
            players[currentPlayerIndex].addTile(lastDiscardedTile);
            return lastDiscardedTile.toString();
        }
        else 
            return "No tile has been discarded yet.";
    }

    public String getTopTile() {
        Tile topTile = tiles[--currentTile];
        players[currentPlayerIndex].addTile(topTile);
        tiles[currentTile]= null; 
        return topTile.toString();
    }


    public void shuffleTiles() {
        List<Tile> tilesArraylist = Arrays.asList(tiles);
        Collections.shuffle(tilesArraylist);
        tilesArraylist.toArray(tiles);
    }

    public boolean didGameFinish() {
        int[] nums=players[currentPlayerIndex].calculateLongestChainPerTile();

        //first case
        int cnt0=0,cnt1=0;
        for(int i=0;i<players[currentPlayerIndex].numberOfTiles;i++){
            if(nums[i]>=4) cnt0++;
            else if(nums[i]>=3) cnt1++;
        }
        if(cnt0>=8&&cnt1>=6) 
            return true;

        //second case
        cnt0=0;cnt1=0;
        for(int i=0;i<players[currentPlayerIndex].numberOfTiles;i++){
            if(nums[i]>=5) cnt0++;
            else if(nums[i]>=3) cnt1++;
        }
        if(cnt0>=5&&cnt1>=9) 
            return true;

        return false;
    }

    public void pickTileForComputer() {
        Player currentPlayer = players[getCurrentPlayerIndex()];
        int num = (int) (Math.random()*2);
        if (num==0){
            System.out.println(currentPlayer.getName() + " Picked a tile from top: " +getTopTile());
        }
        else{
           System.out.println(currentPlayer.getName()+ " picked discarded tile: " + getLastDiscardedTile()); 
        }
    }

    public void discardTileForComputer() {
        Player currentPlayer = players[getCurrentPlayerIndex()]; 
        int [] longestChains = currentPlayer.calculateLongestChainPerTile(); 
        int TileDiscardIndex = -1; 
        int cx = 100; 
        for ( int i =0; i<longestChains.length; i++){
            if(longestChains[i]< cx){
                cx = longestChains[i];
                TileDiscardIndex = i; 
            }
        }
        if(TileDiscardIndex != -1){
            Tile DiscardedTile = currentPlayer.getAndRemoveTile(TileDiscardIndex);
            System.out.println(currentPlayer.getName() + "discarded tile: " + DiscardedTile.toString());
            lastDiscardedTile=DiscardedTile;
        }
    }

    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void currentPlayerSortTilesColorFirst() {
        players[currentPlayerIndex].sortTilesColorFirst();
    }

    public void currentPlayerSortTilesValueFirst() {
        players[currentPlayerIndex].sortTilesValueFirst();
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
