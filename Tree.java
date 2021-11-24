import java.util.ArrayList;
import java.util.Scanner;

public class Tree {
    private int HeurFunc; // эвристическая функция
    private ArrayList<Peak> nodes; // список ожидающих вершин
    private ArrayList<Peak> PeakList; // список проверенных вершин
    private int countSum; // количество раскрытых вершин
    private int countRun; // количество рассмотренных вершин
    private char[][] startState = {{'6', ' ', '8'}, {'5', '2', '1'}, {'4', '3', '7'}};
    private char[][] endState = {{'1', '2', '3'}, {'8', ' ', '4'}, {'7', '6', '5'}};

    public Peak getFirstNode()
    {
        return nodes.get(0);
    }

    public int getCountSum() {
        return countSum;
    }

    public int getCountRun() {
        return countRun;
    }

    // дерево решений
    public Tree(int heurFunc) {
        HeurFunc = heurFunc;
        Peak peak = new Peak(startState, null, " ", 0, 0 , 0);
        if (HeurFunc == 1) {
            peak.setHn(h1(startState));
        } else {
            peak.setHn(h2(startState));
        }
        nodes = new ArrayList<>();
        PeakList = new ArrayList<>();
        countSum = 0;
        countRun = 0;
        nodes.add(peak);
        PeakList.add(peak);
    }

    // копирует состояние
    public char[][] copy(char [][] b){
        char[][] a = new char[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
        return a;
    }

    public int h1(char[][] state)
    {
        int cnt = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if(state[i][j] != endState[i][j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public int h2(char[][] state)
    {
        int mh = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] != ' ') {
                    for (int k = 0; k < state.length; k++) {
                        for (int l = 0; l < state[0].length; l++) {
                            if (i != k || j != l) {
                                if (state[i][j] == endState[k][l]) {
                                    mh += Math.abs(i - k) + Math.abs(j - l);
                                }
                            }
                        }
                    }
                }
            }
        }
        return mh;
    }

    // проверка на целевое состояние
    public boolean GoalTest(char[][] checkState) {
        boolean key = false;
        for (int i = 0; i < checkState.length; i++) {
            for (int j = 0; j < checkState[0].length; j++) {
                if (checkState[i][j] == endState[i][j]) key = true;
                else {
                    key = false;
                    i = checkState.length;
                    j = checkState[0].length;
                }
            }
        }
        return key;
    }

    // проверка на повторные вершины
    public boolean CheckPeak(Peak peak) {
        boolean ok = false;
        boolean key = false;
        for (int i = 0; i < PeakList.size(); i++) {
            key = true;
            for (int j = 0; j < peak.getState().length; j++) {
                for (int k = 0; k < peak.getState()[0].length; k++) {
                    key = key && (peak.getState()[j][k] == PeakList.get(i).getState()[j][k]);
                }
            }
            if (key) {
                if (peak.getGn() < PeakList.get(i).getGn()) {
                    ok = true;
                    PeakList.remove(i);
                }
                i = PeakList.size();
            }
        }
        if (!key) {
            ok = true;
        }
        return ok;
    }

    // функция развертки вершины
    public void Expand(Peak peak) {
        int[] pos = new int[]{0, 0};
        for (int i = 0; i < peak.getState().length; i++)
            for (int j = 0; j < peak.getState()[0].length; j++) {
                if (peak.getState()[i][j] == ' ') {
                    pos[0] = i;
                    pos[1] = j;
                    i = peak.getState().length;
                    j = peak.getState()[0].length;
                }
            }
        if (pos[0] + 1 < 3) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]+1][pos[1]];
            newState[pos[0]+1][pos[1]] = ' ';
            Peak newPeak = new Peak(newState,peak,"Вверх",peak.getDepth()+1, 0, peak.getGn() + peak.getHn());
            if (CheckPeak(newPeak)) {
                if (HeurFunc == 1) {
                    newPeak.setHn(h1(newPeak.getState()));
                }
                else {
                    newPeak.setHn(h2(newPeak.getState()));
                }
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
            }
        }
        if (pos[0] - 1 >= 0) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]-1][pos[1]];
            newState[pos[0]-1][pos[1]] = ' ';
            Peak newPeak = new Peak(newState,peak,"Вниз",peak.getDepth()+1, 0, peak.getGn() + peak.getHn());
            if (CheckPeak(newPeak)) {
                if (HeurFunc == 1) {
                    newPeak.setHn(h1(newPeak.getState()));
                }
                else {
                    newPeak.setHn(h2(newPeak.getState()));
                }
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
            }
        }
        if (pos[1] + 1 < 3) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]][pos[1]+1];
            newState[pos[0]][pos[1]+1] = ' ';
            Peak newPeak = new Peak(newState,peak,"Влево",peak.getDepth()+1, 0, peak.getGn() + peak.getHn());
            if (CheckPeak(newPeak)) {
                if (HeurFunc == 1) {
                    newPeak.setHn(h1(newPeak.getState()));
                }
                else {
                    newPeak.setHn(h2(newPeak.getState()));
                }
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
            }
        }
        if (pos[1] - 1 >= 0) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]][pos[1]-1];
            newState[pos[0]][pos[1]-1] = ' ';
            Peak newPeak = new Peak(newState,peak,"Вправо",peak.getDepth()+1, 0, peak.getGn() + peak.getHn());
            if (CheckPeak(newPeak)) {
                if (HeurFunc == 1) {
                    newPeak.setHn(h1(newPeak.getState()));
                }
                else {
                    newPeak.setHn(h2(newPeak.getState()));
                }
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
            }
        }
    }

    // поиск решения
    public Peak GeneralSearch(int step) {
        if (step == 2) {
            System.out.println("Дальше? 1 - да, 2 - выход\n");
        }
        Peak peak = new Peak();
        boolean key = true;
        while (key) {
            if (nodes.isEmpty()) {
                // решений нет
                key = false;
                peak = null;
                continue;
            }
            int pos = 0;
            peak = nodes.get(0);
            for (int i = 0; i < nodes.size() - 1; i++) {
                if (peak.getGn() + peak.getHn() > nodes.get(i+1).getGn() + nodes.get(i+1).getHn()) {
                    peak = nodes.get(i+1);
                    pos = i+1;
                }
            }
            nodes.remove(pos);

            // пошаговый вывод вершин
            if (step == 2)
            {
                System.out.print("Ввод: ");
                int value = new Scanner(System.in).nextInt();
                System.out.println("");
                switch (value)
                {
                    case 1: {
                        System.out.println("h(n): " + peak.getHn());
                        System.out.println("g(n): " + peak.getGn());
                        System.out.println("Движение: " + peak.getAction());
                        System.out.println("Глубина: " + peak.getDepth());
                        peak.printPeak();
                        break;
                    }
                    default: {
                        key = false;
                        peak = null;
                        return peak;
                    }
                }
            }

            // проверка на целевое состояние
            if (GoalTest(peak.getState())) {
                key = false;
            } else {
                // раскрытие вершины
                Expand(peak);
            }
            countRun ++;
        }
        // возвращаем конечную вершину, которая является решением
        return peak;
    }
}

// 1. берём вершину из стека вершин
// 2. проверка на целевое состояние:
//    * если успешно - сохраняем вершину, выводим решение
//    * если неуспешно - п.3
// 3. раскрываем вершину - генерируем потомков (вершины) и проверяем каждый на повторное состояние:
//    * если успешно - добавляем его в список проверенных и в стек
//    * если неуспешно - потомок игнорируется
// 4. п.1 - п.3 выполняются пока не найдено решение и стек не пуст