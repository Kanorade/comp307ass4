import java.io.File;
import java.util.List;

public class main {

    public static void main(String[] args) {
        //String inst = "n32-k5"; // or "n80-k10"
        String inst = "n80-k10";

        File instFile = new File("files/" + inst + ".vrp");
        VRPInstance instance = VRPIO.loadInstance(instFile);

        System.out.println("optimal solution:");
        File bestFile = new File("files/" + inst + ".sol");
        VRPSolution bestPySol = VRPIO.loadSolution(bestFile);
        // Need to increase all the numbers by one
        VRPSolution bestSol = reversePySolution(bestPySol);
        bestSol.setTotalCost(Utility.calculateTotalCost(bestSol, instance));
        System.out.println("Total cost: " + bestSol.getTotalCost());

        System.out.println("\nNearest Neighbour Heuristic: ");
        VRPSolution nnSol = Utility.nearestNeighbourHeuristic(instance);
        nnSol.setTotalCost(Utility.calculateTotalCost(nnSol, instance));
        System.out.println(nnSol.getRoutes());
        System.out.println("Total cost: " + nnSol.getTotalCost());

        System.out.println("\nSavings Heurustic: ");
        VRPSolution svSol = Utility.savingsHeuristic(instance);
        svSol.setTotalCost(Utility.calculateTotalCost(svSol, instance));
        System.out.println(svSol.getRoutes());
        System.out.println("Total cost: " + svSol.getTotalCost());

        VRPIO.writeSolution(nnSol, inst + "nn.sol");
        VRPIO.writeSolution(svSol, inst + "sv.sol");


        // Make alternative solutions for displaying on python
        VRPSolution nnSolPy = createPySolution(nnSol);
        VRPIO.writeSolution(nnSolPy, inst + "nn-py.sol");
        VRPSolution svSolPy = createPySolution(svSol);
        VRPIO.writeSolution(svSolPy, inst + "sv-py.sol");
    }

    private static VRPSolution reversePySolution(VRPSolution bestPySol) {
        List<List<Integer>> newRoutes = bestPySol.getRoutes();

        for (int i = 0; i < newRoutes.size(); i++) {
            for (int j = 0; j < newRoutes.get(i).size(); j++) {
                newRoutes.get(i).set(j, newRoutes.get(i).get(j) + 1);
            }
        }
        System.out.println(newRoutes);

        return new VRPSolution(newRoutes);
    }

    private static VRPSolution createPySolution(VRPSolution nnSol) {
        List<List<Integer>> newRoutes = nnSol.getRoutes();

        for (int i = 0; i < newRoutes.size(); i++) {
            for (int j = 0; j < newRoutes.get(i).size(); j++) {
                newRoutes.get(i).set(j, newRoutes.get(i).get(j) - 1);
            }
        }

        return new VRPSolution(newRoutes);
    }
}
