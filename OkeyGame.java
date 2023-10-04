package LABS.OkeyGame;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

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

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already sorted
     */
    public void distributeTilesToPlayers() {
        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 14; j++){
                players[i].playerTiles[j] = tiles[k++];
                
            }
        }
        players[0].playerTiles[14] = tiles[k];
    }

    public String getLastDiscardedTile() {
         if (lastDiscardedTile != null) {
        return lastDiscardedTile.toString();
    } else {
        return "No tile has been discarded yet.";
    }
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Tile topTile = tiles[tiles.length-1];
       tiles[tiles.length-1]= null; 
       return topTile.toString();
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        ArrayList<Tile> tilesArraylist = (ArrayList<Tile>) Arrays.asList(tiles);
        Collections.shuffle(tilesArraylist);
        tilesArraylist.toArray(tiles);
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. Use calculateLongestChainPerTile method to get the
     * longest chains per tile.
     * To win, you need one of the following cases to be true:
     * - 8 tiles have length >= 4 and remaining six tiles have length >= 3 the last one can be of any length
     * - 5 tiles have length >= 5 and remaining nine tiles have length >= 3 the last one can be of any length
     * These are assuming we check for the win condition before discarding a tile
     * The given cases do not cover all the winning hands based on the original
     * game and for some rare cases it may be erroneous but it will be enough
     * for this simplified version
     */
    public boolean didGameFinish() {
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
            if(TileDiscardIndex != -1){
                Tile DiscardedTile = currentPlayer.getAndRemoveTile(TileDiscardIndex);
                System.out.println(currentPlayer.getName() + "discarded tile: " + DiscardedTile.toString());
            }
        }
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {

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
