import java.io.File;

public class main {

    public static void main(String[] args) {
        String inst = "n32-k5"; // or "n80-k10"

        File instFile = new File("files/" + inst + ".vrp");
        VRPInstance instance = VRPIO.loadInstance(instFile);

        File bestFile = new File("files/" + inst + ".sol");
        VRPSolution bestSol = VRPIO.loadSolution(bestFile);
        System.out.println(bestSol.getRoutes());
        bestSol.setTotalCost(Utility.calculateTotalCost(bestSol, instance));
        System.out.println("Best total cost: " + bestSol.totalCost);

        VRPSolution nnSol = Utility.nearestNeighbourHeuristic(instance);
        nnSol.setTotalCost(Utility.calculateTotalCost(nnSol, instance));
//
//        VRPSolution svSol = Utility.savingsHeuristic(instance);
//        svSol.setTotalCost(Utility.calculateTotalCost(svSol, instance));
//
        VRPIO.writeSolution(nnSol, inst + "nn.sol");
//        VRPIO.writeSolution(svSol, inst + "sv.sol");
    }
}
