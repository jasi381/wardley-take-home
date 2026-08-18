package com.bel.c22.class8.b_association_aggregation_composition;

import java.util.ArrayList;
import java.util.List;

/**
 * AGGREGATION — "has-a" (weak ownership) relationship
 *
 * One object CONTAINS another, but the contained object can exist INDEPENDENTLY.
 * The container doesn't control the lifecycle of the contained object.
 * If the container is destroyed, the contained objects SURVIVE.
 *
 * Real-world analogy: A Cricket Team has Players.
 * - The team "has" players, but players exist outside the team too.
 * - If the team is disbanded, players still exist — they join other teams.
 * - Players are NOT created by the team; they are added from outside.
 *
 * KEY CHARACTERISTICS:
 * - Weak "has-a" — container has a reference, not ownership
 * - Child object's lifecycle is INDEPENDENT of parent
 * - Child is typically created OUTSIDE and passed IN (via constructor/setter)
 * - Deleting parent does NOT delete children
 */
public class AggregationDemo {

    static class Player {
        private String name;
        private String role;

        Player(String name, String role) {
            this.name = name;
            this.role = role;
        }

        String getName() { return name; }
        String getRole() { return role; }

        public String toString() { return name + " (" + role + ")"; }
    }

    static class CricketTeam {
        private String teamName;
        private List<Player> players;  // Team HAS players (aggregation)

        CricketTeam(String teamName) {
            this.teamName = teamName;
            this.players = new ArrayList<>();
        }

        // Players are created OUTSIDE and added to the team
        void addPlayer(Player player) {
            players.add(player);
            System.out.println("  " + player.getName() + " joined " + teamName);
        }

        void removePlayer(Player player) {
            players.remove(player);
            System.out.println("  " + player.getName() + " left " + teamName);
        }

        void showSquad() {
            System.out.println("  " + teamName + " squad: " + players);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== AGGREGATION: 'has-a' (weak) relationship ===\n");

        // Players are created INDEPENDENTLY (not by the team)
        Player virat = new Player("Virat", "Batsman");
        Player rohit = new Player("Rohit", "Batsman");
        Player bumrah = new Player("Bumrah", "Bowler");

        // Team aggregates players
        CricketTeam india = new CricketTeam("India");
        india.addPlayer(virat);
        india.addPlayer(rohit);
        india.addPlayer(bumrah);
        india.showSquad();

        // Key difference from composition: if team is dissolved, players survive
        System.out.println("\n  Disbanding the team...");
        india = null;  // Team is gone!

        // But players still exist!
        System.out.println("  Players still exist: " + virat + ", " + rohit);

        // Players can join another team
        CricketTeam iplTeam = new CricketTeam("RCB");
        iplTeam.addPlayer(virat);  // Same player, new team
        iplTeam.showSquad();

        System.out.println("\n  Key point: Players were created outside the team.");
        System.out.println("  Team was destroyed, but players survived.");
        System.out.println("  Players can belong to multiple teams.\n");

        System.out.println("  Other examples of aggregation:");
        System.out.println("  - Department has Professors (professors exist independently)");
        System.out.println("  - Library has Books (books can move to another library)");
        System.out.println("  - Playlist has Songs (songs exist without the playlist)");
    }
}
