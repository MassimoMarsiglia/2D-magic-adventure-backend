package com.example.demo.LevelData;

import java.util.List;

public class LevelData {

    private int requiredCoins;

    private List<Tile> tiles;
    private List<SpawnableTile> spawnableTiles;
    private List<Objects> objects;

    // Constructors, getters, and setters
    public LevelData() {}

    public LevelData(List<Tile> tiles, List<Objects> objects, int requiredCoins, List<SpawnableTile> spawnableTiles) {
        this.tiles = tiles;
        this.objects = objects;
        this.requiredCoins = requiredCoins;
        this.spawnableTiles = spawnableTiles;
    }

    public List<SpawnableTile> getSpawnableTiles() {
        return spawnableTiles;
    }

    public void setSpawnableTiles(List<SpawnableTile> spawnableTiles){
        this.spawnableTiles = spawnableTiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Objects> getObjects() {
        return objects;
    }

    public void setObjects(List<Objects> objects) {
        this.objects = objects;
    }

    public int getRequiredCoins() {
        return requiredCoins;
    }

    public void setRequiredCoins(int requiredCoins) {
        this.requiredCoins = requiredCoins;
    }
}
