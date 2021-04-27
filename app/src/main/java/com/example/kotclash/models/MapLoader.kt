package com.example.kotclash.models

import android.graphics.Paint
import android.util.Log
import com.example.kotclash.App


class MapLoader() {

    var paint = Paint()
    var map = Map()

    //Only the base - doesn't care about the specific type of tower
    val posBases = mutableMapOf<String, Pair<Float, Float>>()
    val posAllyTower = mutableMapOf<Int, Pair<Float, Float>>() //Keep indices to match with future user choice
    val posEnemyTower = mutableMapOf<Int, Pair<Float, Float>>()


    fun returnMap() : Map {
        return map
    }


    fun loadMap(mapName : String) : Map {

        if (map.grid.isNotEmpty()){
            map.clearMap()
        }

        //TODO Add other maps
        when (mapName){
            "spring" -> map = parseFile("spring")
        }
        return map
    }


    fun parseFile(filename : String) : Map {

        posBases.clear()
        posAllyTower.clear()
        posEnemyTower.clear()

        var enemy_t_id = 0
        var ally_t_id = 0

        //val inputStream = context.assets.open("$filename.txt")
        val inputStream = App.getContext().resources.assets.open("$filename.txt")
        var lineList = mutableListOf<String>()



        var xi = 0f
        var yi = 0f
        var i = 0


        inputStream.bufferedReader().use(){
            it.forEachLine {
                line -> lineList = line.split(" ") as MutableList<String>

                map.grid.add(mutableListOf()) //Add a new row
                Log.d("tt", "$lineList")
                for (tileElement in lineList){
                    when(tileElement){
                        "G" -> map.grid[i].add(Tile(xi, yi, "grass"))
                        "*" -> map.grid[i].add(Tile(xi, yi, "soil"))
                        "A" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            posBases.put("ally", Pair(xi, yi))
                            //posSimpleTowers1.put("ally", Pair(xi-5, yi-1))
                            //posSimpleTowers2.put("ally", Pair(xi+5, yi-1))
                            Log.d("allyPosBase", "$xi $yi")
                        }

                        "L" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            posAllyTower[ally_t_id] = Pair(xi, yi)
                            ally_t_id++
                        }
                        "E" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            posBases.put("enemy", Pair(xi, yi))
                            //posSimpleTowers1.put("enemy", Pair(xi-5, yi+1))
                            //posSimpleTowers2.put("enemy", Pair(xi+5, yi+1))
                            Log.d("enemyPosBase", "$xi $yi")
                        }
                        "N" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            posEnemyTower[enemy_t_id] = Pair(xi, yi)
                            enemy_t_id++
                        }
                    }
                    xi++

                }
                val m = map.grid[i].size
                Log.e("e", "$m")
                yi++
                i++
                xi = 0f
            }

                return map
            }
    }





}
