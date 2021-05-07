package com.example.kotclash.models

import android.graphics.Paint
import android.util.Log
import com.example.kotclash.App


class MapLoader() {

    var paint = Paint()
    var map = Map()


    fun returnMap() : Map {
        return map
    }


    fun loadMap(mapName : String) : Map {

        if (map.grid.isNotEmpty()){
            map.clearMap()
        }

        when (mapName){
            "spring" -> map = parseFile("spring")
            "lava" -> map = parseFile("lava")
            "frost" -> map = parseFile("frost")
        }
        return map
    }


    private fun parseFile(filename : String) : Map {

        map.clearAllPos()


        var ally_t_id = 0
        var enemy_t_id = 0
        var ally_s_id = 0
        var enemy_s_id = 0
        var gate_id = 0
        var wt_id = 0


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
                        "°" -> map.grid[i].add(Tile(xi, yi, "grass"))
                        "*" -> map.grid[i].add(Tile(xi, yi, "soil"))
                        "A" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posBases.put("ally", Pair(xi, yi))
                        }

                        "At" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posAllyTower[ally_t_id] = Pair(xi, yi)
                            ally_t_id++
                        }
                        "E" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posBases.put("enemy", Pair(xi, yi))
                        }
                        "Et" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posEnemyTower[enemy_t_id] = Pair(xi, yi)
                            enemy_t_id++
                        }
                        "As" -> { //Spawn Ally
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posAllySpawn[ally_s_id] = Pair(xi, yi)
                            ally_s_id++
                        }
                        "Es" -> { //Spawn Enemy
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posEnemySpawn[enemy_s_id] = Pair(xi, yi)
                            enemy_s_id++
                        }
                        "W" -> { //Wall
                            map.grid[i].add(Tile(xi, yi, "wall"))
                        }
                        "T" -> { //Wall + Tag
                            map.grid[i].add(Tile(xi, yi, "wall"))
                            map.wallTag[wt_id] = Pair(xi, yi)
                            wt_id++
                        }
                        "G" -> { //Gate
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            map.posGate[gate_id] = Pair(xi, yi)
                            gate_id++
                        }
                    }
                    xi++

                }

                yi++
                i++
                xi = 0f
            }

                return map
            }
    }





}
