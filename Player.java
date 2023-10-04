public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * This method calculates the longest chain per tile to be used when checking the win condition
     */
    public int[] calculateLongestChainPerTile() {
        // keep a seperate copy of the tiles since findLongestChainOf sorts them
        Tile[] tilesCopy = new Tile[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            tilesCopy[i] = playerTiles[i];
        }

        // make the calculations
        int[] chainLengths = new int[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            chainLengths[i] = findLongestChainOf(tilesCopy[i]);
        }

        // revert the playerTiles to its original form
        for (int i = 0; i < numberOfTiles; i++) {
            playerTiles[i] = tilesCopy[i];
        }

        return chainLengths;
    }

    public int findLongestChainOf(Tile t) {
        int tilePosition;

        sortTilesColorFirst();
        tilePosition = findPositionOfTile(t);

        int longestChainColorFirst = 1;
        
        // For left side
        while(playerTiles[tilePosition-1].getValue()==playerTiles[tilePosition].getValue()-1 && tilePosition !=0 && playerTiles[tilePosition-1].getColor()==playerTiles[tilePosition].getColor() ){
            longestChainColorFirst++;
            tilePosition--;
        }
        
        // For right side
        tilePosition = findPositionOfTile(t);
        while(playerTiles[tilePosition+1].getValue()==playerTiles[tilePosition].getValue()+1 && tilePosition != 14 && playerTiles[tilePosition+1].getColor()==playerTiles[tilePosition].getColor()){
            longestChainColorFirst++;
            tilePosition++;
        }

        sortTilesValueFirst();
        tilePosition = findPositionOfTile(t);
        

        int longestChainValueFirst = 1;
        int valOfTile = playerTiles[tilePosition].getValue();

         // For left side
        while(playerTiles[tilePosition-1].getValue()== valOfTile && tilePosition !=0 ){
            if(playerTiles[tilePosition-1].color != playerTiles[tilePosition].color){
                longestChainValueFirst++;
            }
            tilePosition --;
        }
        

        //For right side
        tilePosition = findPositionOfTile(t);
        while(playerTiles[tilePosition+1].getValue()== valOfTile && tilePosition != 14){
            if(playerTiles[tilePosition+1].color != playerTiles[tilePosition].color){
                longestChainValueFirst++;
            }
            tilePosition ++;
        }


        if(longestChainColorFirst > longestChainValueFirst) {
            return longestChainColorFirst;
        }
        else{
            return longestChainValueFirst;
        }
    }

    public Tile getAndRemoveTile(int index) {
        Tile t = playerTiles[index];
        for(int i=index+1;i<numberOfTiles;i++)
            playerTiles[i]=playerTiles[i-1];
        numberOfTiles--;
        return t;
    }

    public void addTile(Tile t) {
        playerTiles[numberOfTiles++]=t;
        
        if(numberOfTiles>15) System.out.print("too many tiles!");
    }

    public void sortTilesColorFirst() {
        Tile s;
        for(int i=0;i<numberOfTiles;i++)
            for(int j=i+1;j<numberOfTiles;j++)
                if(playerTiles[i].compareToColorFirst(playerTiles[j])<0){
                    s=playerTiles[i];
                    playerTiles[i]=playerTiles[j];
                    playerTiles[j]=s;
                }
            
    }

    public void sortTilesValueFirst() {
        Tile s;
        for(int i=0;i<numberOfTiles;i++)
            for(int j=i+1;j<numberOfTiles;j++)
                if(playerTiles[i].compareToValueFirst(playerTiles[j])<0){
                    s=playerTiles[i];
                    playerTiles[i]=playerTiles[j];
                    playerTiles[j]=s;
                }
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
