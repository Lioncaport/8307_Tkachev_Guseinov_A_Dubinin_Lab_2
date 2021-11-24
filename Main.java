import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int value = 0;
        while (value != -1) {
            System.out.println("Поиск: 1 - по h1, 2 - по h2, 3 - выход");
            System.out.print("Ввод: ");
            value = new Scanner(System.in).nextInt();
            switch (value) {
                case 1: {
                    Tree tree1 = new Tree(1);
                    Peak initState = tree1.getFirstNode();
                    System.out.println("\nНачальное состояние:");
                    initState.printPeak();

                    int step = 0;
                    System.out.println("Режим: 1 - автоматический, 2 - ручной");
                    System.out.print("Ввод: ");
                    step = new Scanner(System.in).nextInt();
                    System.out.println("");
                    if (step != 1 && step != 2) step = 1;

                    Peak solution = tree1.GeneralSearch(step);
                    if (solution != null)
                    {
                        System.out.println("Целевое состояние:");
                        solution.printPeak();
                        System.out.println("Количество шагов:" + solution.getDepth());
                    }
                    else
                    {
                        System.out.println("Решения нет");
                    }
                    System.out.println("Временная сложность: " + tree1.getCountRun());
                    System.out.println("Ёмкостная сложность: " + tree1.getCountSum() + "\n");
                    break;
                }
                case 2: {
                    Tree tree2 = new Tree(2);
                    Peak initState = tree2.getFirstNode();
                    System.out.println("\nНачальное состояние:");
                    initState.printPeak();

                    int step = 0;
                    System.out.println("Режим: 1 - автоматический, 2 - ручной");
                    System.out.print("Ввод: ");
                    step = new Scanner(System.in).nextInt();
                    System.out.println("");
                    if (step != 1 && step != 2) step = 1;

                    Peak solution = tree2.GeneralSearch(step);
                    if (solution != null)
                    {
                        System.out.println("Целевое состояние:");
                        solution.printPeak();
                        System.out.println("Количество шагов:" + solution.getDepth());
                    }
                    else
                    {
                        System.out.println("Решения нет");
                    }
                    System.out.println("Временная сложность: " + tree2.getCountRun());
                    System.out.println("Ёмкостная сложность: " + tree2.getCountSum() + "\n");
                    break;
                }
                default : value = -1;
            }
        }
    }
}
