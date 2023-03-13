package uet.oop.bomberman.Entities.MobileEntity.enemy.ai;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Enemy;
import uet.oop.bomberman.Entities.StaticEntity.Brick;
import uet.oop.bomberman.Input.Map;


public class AIBfs extends AI {
    public ArrayList<Integer> shortestPath = new ArrayList();
    private int mapHeight = 13;
    private int mapWidth = 31;
    private Bomber bomber;
    private Enemy mob;
    private Board board;
    private int[][] node = new int[mapHeight * mapWidth][4];
    private int maxNodeNum = mapHeight * mapWidth;
    private int[][] matrix = new int[mapHeight][mapWidth];


    public AIBfs(Bomber bomber, Enemy mob, Board board) {
        this.bomber = bomber;
        this.mob = mob;
        this.board = board;
    }

    public void getMatrix() {
        int verText = 1;

        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                if (Map.map[x][y] == '#') {
                    matrix[x][y] = 0;
                } else if (Map.map[x][y] == '*' || Map.map[x][y] == 'x' || Map.map[x][y] == 'b' || Map.map[x][y] == 'f' || Map.map[x][y] == 's' || Map.map[x][y] == 'r') {
                    matrix[x][y] = verText * (-1);
                    verText++;
                } else {
                    this.matrix[x][y] = verText;
                    verText++;
                }
            }
        }
    }

    public void updateDestroyedBricks() {
        if (Brick.destroyedBrickX.isEmpty()){
            return;
        }

        int brickX;
        int brickY;
        for (int index = 0; index < Brick.destroyedBrickX.size(); index++) {
            brickX = Brick.destroyedBrickX.get(index);
            brickY = Brick.destroyedBrickY.get(index);
            if (matrix[brickY][brickX] < 0) {
                matrix[brickY][brickX] *= -1;
            }
        }
    }

    public void convertNearNodeMatrix() {

        for (int x = 1; x < 12; x++) {
            for (int y = 1; y < 30; y++) {
                if (this.matrix[x][y] > 0) {
                    this.node[this.matrix[x][y]][0] = Math.max(this.matrix[x][y - 1], 0);
                    this.node[this.matrix[x][y]][1] = Math.max(this.matrix[x][y + 1], 0);
                    this.node[this.matrix[x][y]][2] = Math.max(this.matrix[x - 1][y], 0);
                    this.node[this.matrix[x][y]][3] = Math.max(this.matrix[x + 1][y], 0);
                }
            }
        }
    }

    int BFS(int start, int end) throws IllegalStateException {
        Queue<Integer> queueNode = new LinkedList<>();
        int[] parent = new int[maxNodeNum + 1];
        boolean[] visited = new boolean[maxNodeNum + 1];

        if (start < 0) {
            start *= -1;
        }
        if (end < 0) {
            end *= -1;
        }

        visited[start] = false;
        parent[start] = -1;
        parent[end] = -1;

        queueNode.add(start);
        int currentNode;
        while (!queueNode.isEmpty()) {
            currentNode = queueNode.poll();
            for (int x = 0; x < 4; x++) {
                if (!visited[node[currentNode][x]] && node[currentNode][x] != 0) {
                    visited[node[currentNode][x]] = true;
                    parent[node[currentNode][x]] = currentNode;
                    queueNode.add(node[currentNode][x]);
                }
            }
        }

        int parentEnd = parent[end];
        if (parentEnd != -1) {
            shortestPath.add(end);
            shortestPath.add(parentEnd);
            while (parentEnd != start) {
                parentEnd = parent[parentEnd];
                shortestPath.add(parentEnd);
            }
            return shortestPath.get(shortestPath.size() - 2);
        }
        return -1;
    }

    @Override
    public int calculateDirection() {

        this.getMatrix();
        this.updateDestroyedBricks();
        this.convertNearNodeMatrix();
        int start = this.matrix[this.mob.getYTile()][this.mob.getXTile()];
        int end = this.matrix[this.bomber.getYTile()][this.bomber.getXTile()];
        int result = this.BFS(start, end);

        if (result == -1) {
            return -1;
        }
        if (result - start == 1) {
            return 1;
        }
        if (start - result == 1) {
            return 3;
        }
        if (start > result) {
            return 0;
        }
        if (start < result) {
            return 2;
        }
        return -1;
    }
}
